package com.iteration2.mark.iteration3loginslides;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

/**
 * Created by zharif20 on 5/10/15.
 *
 * Example homescreen
 */
public class HomeScreen extends ActionBarActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    //    private DatabaseHandler databaseHandler;
    public EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
//        databaseHandler = new DatabaseHandler(getApplicationContext());

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
//        databaseHandler.deleteUser();

        // Launching the login activity
//        Intent intent = new Intent(HomeScreen.this, Login.class);
//        startActivity(intent);
//        finish();
    }
}