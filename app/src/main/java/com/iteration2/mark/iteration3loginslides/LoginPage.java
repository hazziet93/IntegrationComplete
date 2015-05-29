package com.iteration2.mark.iteration3loginslides;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zharif20 on 5/29/15.
 */
public class LoginPage extends ActionBarActivity {
    public TextView titleEmail,titlePassword;
    public EditText inputEmail,inputPassword;

    public Button loginButton,linkRegister;


    // url of server php files
    // JSON parser class
    JSONParserForDatabase jsonParser = new JSONParserForDatabase();

    private static final String url_login = "http://46.101.25.6/database_connection_code/db_check_userlogin.php";
    //private static final String url_create_account = "http://192.168.1.85/Database_connection_php/db_login_create_account.php";

    // JSON Node names
    private static final String TAG_SUCCEEDED = "succeeded";
    private static final String TAG_USERDATA = "user_data";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_DEBUGMESSAGE = "debugMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLayoutItem();
        addLoginButton();
        addLinkRegisterButton();

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public void setLayoutItem() {

        titleEmail      = (TextView)findViewById(R.id.textEmail);
        titlePassword   = (TextView)findViewById(R.id.textPassword);
        inputEmail      = (EditText)findViewById(R.id.editEmail);
        inputPassword   = (EditText)findViewById(R.id.editPassword);
    }

    public void addLoginButton(){
        loginButton = (Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.trim().length() > 0 && password.trim().length() > 0) {

                    /**
                     * Reference email validation
                     * http://www.mirc.org/mishbox/reference/re.email.htm
                     * Format email example:
                     * example@example.com - matches
                     * example.example@com - non-matches
                     */
                    if (inputEmail.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)" +
                            "@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){

                        if (inputPassword.length() > 5) {
//                            Intent homeScreenIntent = new Intent(Login.this, HomeScreen.class);
//                            startActivity(homeScreenIntent);
//                            addLoginAccount();
                            new Login().execute();
//                            Toast.makeText(getApplicationContext(), "Access Granted!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Sign Up!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        inputEmail.setError("Invalid Email!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Details!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addLinkRegisterButton(){
        linkRegister = (Button)findViewById(R.id.buttonToRegisterPage);
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Link the page to register page
                Intent registerPageIntent = new Intent(LoginPage.this, CreateAccount.class);
                startActivity(registerPageIntent);
            }
        });
    }

//    public void addLoginAccount() {
//        final Dialog dialog1 = new Dialog(LoginPage.this);
//        dialog1.setContentView(R.layout.activity_valid_login);
//        dialog1.setTitle("Log In");
//
//        final TextView accCreate = (TextView) dialog1.findViewById(R.id.accCreateText);
//        final Button buttonCreate = (Button) dialog1.findViewById(R.id.buttonOk);
//
//        buttonCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Link the page to register page
//                Intent homeScreenIntent = new Intent(LoginPage.this, LoggedIn.class);
//                startActivity(homeScreenIntent);
//                dialog1.dismiss();
//
//            }
//        });
//        dialog1.show();
//    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Get complete product details
     * */
    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateStatusMessage("Accessing Database");
        }

        /**
         * Accessing DB in background thread
         * */
        protected String doInBackground(String... params) {

            int succeeded;

            try {
                // Building Parameters
                String debugMessage;
                JSONObject json;

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
                dataToSend.add(new BasicNameValuePair("username", email));
                dataToSend.add(new BasicNameValuePair("password", password));

                // Check the username and password against the database by making HTTP GET request
                // A GET request is used as we are merely retrieving information and not writing to the database
                json = jsonParser.makeHttpRequest(url_login, "GET", dataToSend);
                // check your log for json response
                Log.d("User login status", json.toString());

                //The returned json onject should be an array in the form [Succeeded: (value), debugMessage (message) Login details: [username: (username string), password: (password string)]]

                // json success tag
                succeeded = json.getInt(TAG_SUCCEEDED);
                if (succeeded == 1) {
                    debugMessage= json.getString(TAG_DEBUGMESSAGE);
                    updateStatusMessage(debugMessage);
                    // successfully received login details
                    JSONArray userDataArray = json.getJSONArray(TAG_USERDATA); // JSON Array
                    // get first user login details from JSON Array
                    JSONObject userData = userDataArray.getJSONObject(0);

                    SharedPreferences userLoginDetails = getSharedPreferences("userlogindetails", 0);
                    SharedPreferences.Editor editor = userLoginDetails.edit();
                    editor.putString("username", userData.getString(TAG_USERNAME));
                    editor.putString("password", userData.getString(TAG_PASSWORD));
                    editor.commit();

                    Intent intent = new Intent(LoginPage.this, LoggedIn.class);
                    //Start the activity
                    startActivity(intent);

                }else{
                    // user login details incorrect
                    debugMessage= json.getString(TAG_DEBUGMESSAGE);
                    updateStatusMessage(debugMessage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                updateStatusMessage("Error: connection problems");
            }
            return null;
        }

        //This is the function to update UI objects from the background thread. If you try to do so directly from
        //a background thread it will crash
        private void updateStatusMessage(final String statusMessage) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                addLoginAccount();
                                //statusText = (TextView)findViewById(R.id.statusText);
                                //statusText.setText(statusMessage);
                            }
                        });
                    } catch (final Exception ex) {
                        Log.i("Error:","Exception when updating status message in UI thread");
                    }
                }
            }.start();
        }


    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}