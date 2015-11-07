package com.whohatescardio.stubbsnation.whohatescardio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private final int SCAN_FOR_CAL = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Sets the onclick listner for the button
        Button scanButton = (Button) findViewById(R.id.btnScan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Create on click function here
                Intent i = new Intent(MainActivity.this, MillisThing.class);
                startActivityForResult(i, SCAN_FOR_CAL);
            }
        });
    }

    public void onActiviyResult(int reqCode, int resCode, Intent result){
        super.onActivityResult(reqCode, resCode, result);
        if (resCode == Activity.RESULT_OK) {
            //TODO Get extra data as shown in above example
            if(reqCode == SCAN_FOR_CAL){
                Double calories = result.getDoubleExtra("calories",0.0);
                TextView calTV = (TextView) findViewById(R.id.lblTotalCal);
                String content = calTV.getText().toString();
                content = content + calories;
                calTV.setText(content);
            }
        }



    }

}
