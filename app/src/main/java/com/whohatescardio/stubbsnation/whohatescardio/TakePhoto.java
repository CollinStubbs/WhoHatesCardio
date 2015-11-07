package com.whohatescardio.stubbsnation.whohatescardio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class TakePhoto extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 0;

    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        takePhoto();
    }

    // Deal with the result of selection of the photos and faces.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri;
            if (data == null || data.getData() == null) {
                imageUri = mUriPhotoTaken;
            } else {
                imageUri = data.getData();
            }
            Intent intent = new Intent();
            intent.setData(imageUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    // Launch the camera to allow the user to take a photo
    public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                mUriPhotoTaken = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                Log.d("ERROR", e.getMessage());
            }
        }
    }


}
