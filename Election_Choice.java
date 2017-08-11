package com.example.jamesking.choice;

//Works but check error handling

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Election_Choice extends AppCompatActivity {

    String username, election_to_push, election_from_server, election_info_from_server;
    Button backButton, proceedButton;
    TextView onlineUserTextView;
    EditText electionNumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_choice);

        username = getIntent().getStringExtra("USERNAME");

        backButton = (Button)findViewById(R.id.backButton);
        proceedButton = (Button)findViewById(R.id.loginButton);
        onlineUserTextView = (TextView)findViewById(R.id.onlineUserTextView);
        electionNumberEditText = (EditText)findViewById(R.id.electionNumberEditText);

        onlineUserTextView.setText(username);


        proceedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(!TextUtils.isEmpty(electionNumberEditText.getText().toString()) && TextUtils.isDigitsOnly(electionNumberEditText.getText()))
                {
                    election_to_push = electionNumberEditText.getText().toString();
                    validateElection_NetworkCall();
                }
                else
                {
                    createDialog(2,"");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                logoutUser_NetworkCall();
            }
        });
    }

    public void createDialog(int code, String potential_network_error) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Election_Choice.this);
        final EditText retry_election_edittext = new EditText(Election_Choice.this);
        switch (code)
        {
            //1 is successful login
            //2 is for filling in edit text fields
            //3 is for election not found
            //4 is for a network connection error

            case 1:
            {
                dialog.setTitle("Successful Election Validation");
                dialog.setMessage("Your election has been validated");
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final Intent toVotingScreenIntent = new Intent(Election_Choice.this, Voting_Screen.class);
                        toVotingScreenIntent.putExtra("USERNAME", username);
                        toVotingScreenIntent.putExtra("ELECTIONID", election_to_push);
                        toVotingScreenIntent.putExtra("ELECTIONNAME", election_from_server);
                        toVotingScreenIntent.putExtra("ELECTIONINFO", election_info_from_server);
                        startActivity(toVotingScreenIntent);
                    }
                });
                break;
            }

            case 2:
            {
                dialog.setTitle("Invalid Election Credentials");
                dialog.setMessage("Please enter a number for the Election ID");
                dialog.setView(retry_election_edittext);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        if(!TextUtils.isEmpty(retry_election_edittext.getText().toString()))
                        {
                            election_to_push = retry_election_edittext.getText().toString();
                            validateElection_NetworkCall();
                        }
                        else
                        {
                            createDialog(2,"");
                        }
                    }
                });
                break;
            }

            case 3:
            {
                dialog.setTitle("Invalid Election Credentials");
                dialog.setMessage("This election was not found for your account:\n" + potential_network_error);
                dialog.setView(retry_election_edittext);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        if(!TextUtils.isEmpty(retry_election_edittext.getText().toString()))
                        {
                            election_to_push = retry_election_edittext.getText().toString();
                            validateElection_NetworkCall();
                        }
                        else
                        {
                            createDialog(2,"");
                        }
                    }
                });
                break;
            }
            case 4:
            {
                dialog.setTitle("Network Error");
                dialog.setMessage(potential_network_error);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                break;
            }
        }
        AlertDialog shown = dialog.create();
        shown.show();
    }

    public void validateElection_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest validateElection_Request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SS"))
                        {
                            loadElectionName_NetworkCall();
                        }
                        else if(response.equals("SF"))
                        {
                            createDialog(3,"");
                        }
                        else
                        {
                            createDialog(4,"No Database Connection");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(4,error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "validateElection");
                params.put("username", username);
                params.put("electionID", election_to_push);

                return params;
            }
        };
        queue.add(validateElection_Request);
    }

    public void loadElectionName_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest getElectionNameRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.substring(0,1).equals("S"))
                        {
                            election_from_server = response.substring(1,response.length());
                            loadElectionInfo_NetworkCall();
                        }
                        else
                        {
                            createDialog(3,response);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(4,error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "loadElectionName");
                params.put("electionID",election_to_push);

                return params;
            }
        };
        queue.add(getElectionNameRequest);
    }

    public void loadElectionInfo_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest getElectionInfoRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.substring(0,1).equals("S"))
                        {
                            election_info_from_server = response.substring(1,response.length());
                            createDialog(1,"");
                        }
                        else
                        {
                            createDialog(3,response);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(4,error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "loadElectionInfo");
                params.put("electionID",election_to_push);

                return params;
            }
        };
        queue.add(getElectionInfoRequest);
    }

    public void logoutUser_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest logoutRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("SS"))
                        {
                            final Intent backToLoginScreen = new Intent(Election_Choice.this, Login_Screen.class);
                            startActivity(backToLoginScreen);
                        }
                        else
                        {
                            createDialog(3,response);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        createDialog(4,error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "logoutUser");
                params.put("username", username);
                return params;
            }
        };
        queue.add(logoutRequest);
    }
}
