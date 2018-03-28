package com.udemy.sbsapps.chaffr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    Switch userTypeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        userTypeSwitch = findViewById(R.id.userTypeSwitch);

        if(ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){
                        Log.i("Info", "Anonymous login successful");
                    } else {
                        Log.i("Info", "Anonymous login failed");
                    }
                }
            });
        } else {
            if(ParseUser.getCurrentUser().get("riderOrDriver") != null){
                // Start activity based on userType
                redirectActivity();
            }
        }
    }

    public void getStarted(View view){
        Log.i("Switch",String.valueOf(userTypeSwitch.isChecked()));

        String userType = "rider";

        if(userTypeSwitch.isChecked()) {
            userType = "driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectActivity();
            }
        });

    }

    public void redirectActivity() {
        Intent intent;
        if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {
            intent = new Intent(this, RiderActivity.class);
            startActivity(intent);
        } else if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){
            // Start Driver activity
//                    intent = new Intent(this, RiderActivity.class);
//                    startActivity(intent);
        }
    }
}
