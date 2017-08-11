package com.example.jamesking.choice;

//Works

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login_Screen extends AppCompatActivity {
    private String username, password;
    private EditText usernameEditText, passwordEditText;
    private Button toCreateUserButton, loginButton, forgotPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        if(getIntent().getStringExtra("USERNAME")!=null)
        {
            username = getIntent().getStringExtra("USERNAME");
            password = getIntent().getStringExtra("PASSWORD");
            usernameEditText.setText(username);
            passwordEditText.setText(password);
        }

        toCreateUserButton = (Button)findViewById(R.id.toCreateUserButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        forgotPasswordButton = (Button)findViewById(R.id.forgotPasswordButton);

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if((!usernameEditText.getText().toString().trim().equals(""))&&(!passwordEditText.getText().toString().trim().equals("")))
                {
                    username = usernameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    if(checkConnectivity())
                    {
                        loginUser_NetworkCall();
                    }
                    else
                    {
                        createDialog(4,"");
                    }
                }
                else
                {
                    createDialog(2,"");
                }

            }
        });

        toCreateUserButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(checkConnectivity())
                {
                    final Intent toCreateAccountIntent = new Intent(Login_Screen.this, Create_Account.class);
                    startActivity(toCreateAccountIntent);
                }
                else
                {
                    createDialog(4,"");
                }
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(checkConnectivity())
                {
                    final Intent toForgottenPasswordIntent = new Intent(Login_Screen.this, Forgotten_Password.class);
                    startActivity(toForgottenPasswordIntent);
                }
                else
                {
                    createDialog(4,"");
                }
            }
        });
    }

    public void loginUser_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SS"))
                        {
                            createDialog(1,"");
                        }
                        else if(response.equals("SF"))
                        {
                            createDialog(4,response);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof NetworkError)
                        {
                            createDialog(4,"Please Check Your Internet Connection");
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "loginUser");
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(loginRequest);
    }

    public void createDialog(int code, String potential_network_error)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Login_Screen.this);
        switch(code)
        {
            //1 is successful login
            //2 is for filling in edit text fields
            //3 is for invalid login credentials
            //4 is for a network connection error

            case 1:
            {
                dialog.setTitle("Successful Login!");
                dialog.setMessage("Your login credentials have been validated");
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        final Intent toElectionChoiceIntent = new Intent(Login_Screen.this, Election_Choice.class);
                        toElectionChoiceIntent.putExtra("USERNAME",username);
                        startActivity(toElectionChoiceIntent);
                        finish();
                    }
                });
                break;
            }

            case 2:
            {
                dialog.setTitle("Invalid Login Credentials");
                dialog.setMessage("Please enter your username and password");
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                });
                break;
            }

            case 3:
            {
                dialog.setTitle("Login Error");
                dialog.setMessage("Your credentials do not exist");
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                });
                break;
            }

            case 4:
            {
                dialog.setTitle("Network Error");
                dialog.setMessage("There was an error in connecting to the network:\n" + potential_network_error);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                });
                break;
            }
        }
        AlertDialog shown = dialog.create();
        shown.show();
    }

    public boolean checkConnectivity()
    {
        boolean internet = false;
        try {
            ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo state_of_network = connection.getActiveNetworkInfo();
            internet = (state_of_network.isConnected());
        }
        catch(Exception not_working)
        {
            internet = false;
        }
        return internet;
    }

}
