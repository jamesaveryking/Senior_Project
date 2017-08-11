package com.example.jamesking.choice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Create_Account extends AppCompatActivity {

    private Button backButton, proceedButton;
    private EditText nameEditText, usernameEditText, passwordEditText, stateEditText, cityEditText;
    private String name, username, password, state, city, firstID, secondID, thirdID;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        backButton = (Button)findViewById(R.id.backButton);
        proceedButton = (Button)findViewById(R.id.loginButton);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        stateEditText = (EditText)findViewById(R.id.stateEditText);
        cityEditText = (EditText)findViewById(R.id.cityEditText);



        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                final Intent toLoginIntent = new Intent(Create_Account.this, Login_Screen.class);
                startActivity(toLoginIntent);
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(allEditTextsAreFilled())
                {
                    generateIDs();
                    createUserNetworkCall();
                }
            }
        });
    }

    public boolean allEditTextsAreFilled()
    {
        if(!TextUtils.isEmpty(nameEditText.getText().toString())&&!TextUtils.isEmpty(usernameEditText.getText().toString())
                &&!TextUtils.isEmpty(passwordEditText.getText().toString())&&!TextUtils.isEmpty(stateEditText.getText().toString())
                &&!TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            name = nameEditText.getText().toString();
            username = usernameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            state = stateEditText.getText().toString();
            state = state.toLowerCase();
            state = state.substring(0,1).toUpperCase() + state.substring(1,state.length());
            city = cityEditText.getText().toString();
            city = city.toLowerCase();
            city = city.substring(0,1).toUpperCase() + city.substring(1,city.length());
            return true;
        }
        else
        {
            createDialog(2,"");
            return false;
        }
    }

    public void createUserNetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest createRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("SS"))
                        {
                            createDialog(1,"");
                        }
                        else
                        {
                            createDialog(4,"");
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(3,error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "insertUser");
                params.put("name", name);
                params.put("username", username);
                params.put("password", password);
                params.put("state", state);
                params.put("city", city);
                params.put("firstID", firstID);
                params.put("secondID", secondID);
                params.put("thirdID", thirdID);
                return params;
            }
        };
        queue.add(createRequest);
    }

    public void createDialog(int code, String network_response) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Create_Account.this);
        switch (code)
        {
            //1 is successful user creation
            //2 is for filling in edit text fields
            //3 is for election not found
            case 1:
            {
                dialog.setTitle("Successful User Creation!");
                dialog.setMessage("Your account has been created");
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final Intent toSecuitySettingsIntent = new Intent(Create_Account.this, Security_Settings.class);
                        toSecuitySettingsIntent.putExtra("USERNAME", username);
                        toSecuitySettingsIntent.putExtra("PASSWORD",password);
                        toSecuitySettingsIntent.putExtra("STATE", state);
                        toSecuitySettingsIntent.putExtra("CITY", city);
                        toSecuitySettingsIntent.putExtra("NAME", name);
                        toSecuitySettingsIntent.putExtra("ID1",firstID);
                        toSecuitySettingsIntent.putExtra("ID2",secondID);
                        toSecuitySettingsIntent.putExtra("ID3",thirdID);
                        startActivity(toSecuitySettingsIntent);
                    }
                });
                break;
            }

            case 2:
            {
                dialog.setTitle("All Fields Not Filled");
                dialog.setMessage("Please make sure all necessary information is entered");
                dialog.setNeutralButton("Dismiss",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                });
                break;
            }

            case 3:
            {
                dialog.setTitle("Network Error");
                dialog.setMessage("There was an error in your network connection: \n"+network_response);
                dialog.setNeutralButton("Dismiss",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                });
                break;
            }

            case 4:
            {
                dialog.setTitle("Insertion Status");
                dialog.setMessage(network_response);
                dialog.setNeutralButton("Dismiss",new DialogInterface.OnClickListener(){
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

    public void generateIDs()
    {
        firstID = Integer.toString(rand.nextInt(20000));
        secondID = Integer.toString(rand.nextInt(20000));
        thirdID = Integer.toString(rand.nextInt(20000));
    }
}
