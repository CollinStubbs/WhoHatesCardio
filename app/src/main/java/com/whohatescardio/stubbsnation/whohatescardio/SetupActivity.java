package com.whohatescardio.stubbsnation.whohatescardio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {
    private final int NEW_USER = 1;
    private final int EDIT_USER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras.getInt("req_code") == EDIT_USER){
            //TODO: Read from database, and populate fields with exsisting values
        }

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Grab all the values of the form
                String name = ((TextView)findViewById(R.id.txtName)).getText().toString();
                String unparsedAge = ((TextView) findViewById(R.id.txtAge)).getText().toString();
                String unparsedHeight = ((TextView) findViewById(R.id.txtHeight)).getText().toString();
                String unparsedWeight = ((TextView) findViewById(R.id.txtWeight)).getText().toString();

                if(name == null || unparsedAge == null || unparsedHeight == null || unparsedWeight == null){
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill out form completely";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                int age = Integer.parseInt(unparsedAge);
                float height = Float.parseFloat(unparsedHeight);
                char sex = ((RadioButton) findViewById(R.id.btnMale)).isChecked() ? 'm' : 'f';
                String levelOfActivity = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                float weight = Float.parseFloat(unparsedWeight);



                //TODO: Create a user object
                User user = new User(name, sex, age, weight, levelOfActivity);

                //TODO: Create an async task to save the new user to a database
            }
        });





    }

}
