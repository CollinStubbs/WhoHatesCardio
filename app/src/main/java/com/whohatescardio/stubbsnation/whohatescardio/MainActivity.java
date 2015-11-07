package com.whohatescardio.stubbsnation.whohatescardio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGE = 0;
    private Uri mImageUri;
    private Bitmap mBitmap;

    private TextView mTextResult;
    private VisionServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (client==null){
            client = new VisionServiceRestClient(getString(R.string.subscription_key));
        }

        mTextResult = (TextView) findViewById(R.id.textResult);

        Button takePhoto = (Button) findViewById(R.id.button);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhoto = new Intent(MainActivity.this, TakePhoto.class);
                startActivityForResult(takePhoto, REQUEST_SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            // If image is selected successfully, set the image URI and bitmap.
            mImageUri = data.getData();

            mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
            if (mBitmap != null) {

                doRecognize();
            }
        }
    }

    public void doRecognize() {
        try {
            new doRequest().execute();
        } catch (Exception e)
        {
            mTextResult.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        private Exception e = null;

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            if (e != null) {
                mTextResult.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += word.text + " ";
                        }
                        result += "\n";
                    }
                    result += "\n\n";
                }

                mTextResult.setText(extractString("Calories", result));
            }
        }

        public String extractString(String title, String target){
            String result;
            Pattern pattern = Pattern.compile(title + " (\\d+) ");
            Matcher matcher = pattern.matcher(target);
            if (matcher.find())
            {
                result = matcher.group(0);
                // Remove all non-number characters
                result = result.replaceAll("\\D+","");
            }else{
                result = "ERROR: Could not find "+title;
            }
            return result;
        }
    }

}

