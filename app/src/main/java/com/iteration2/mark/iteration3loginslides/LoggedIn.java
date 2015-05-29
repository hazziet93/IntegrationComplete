package com.iteration2.mark.iteration3loginslides;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.Bundle;


/**
 * Activity that starts when the user is logged in
 */
public class LoggedIn extends Activity {
    TextView usernameDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in);
        SharedPreferences userLoginDetails = getSharedPreferences("userlogindetails", 0);
        String username = userLoginDetails.getString("username", "Error: no login data stored");
        usernameDisplay = (TextView) findViewById(R.id.usernameDisplay);
        usernameDisplay.setText(username);
    }

    public void toUserHome(View view) {
        //setContentView(R.layout.activity_user_home);
        Intent userHomeIntent = new Intent(LoggedIn.this, UserHome.class);
        startActivity(userHomeIntent);
    }


}