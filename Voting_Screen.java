package com.example.jamesking.choice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
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

public class Voting_Screen extends AppCompatActivity {

    private Button approveButton, againstButton, backButton, proceedButton;
    private TextView currentVoteTextView, electionInformationTextView, electionNameTextView, onlineUserTextView;
    String electionID, currentElection, electionInformation, vote, username, firstID, secondID, thirdID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_screen);

        electionID = getIntent().getStringExtra("ELECTIONID");
        currentElection = getIntent().getStringExtra("ELECTIONNAME");
        electionInformation = getIntent().getStringExtra("ELECTIONINFO");
        username = getIntent().getStringExtra("USERNAME");

        approveButton = (Button)findViewById(R.id.approveButton);
        againstButton = (Button)findViewById(R.id.againstButton);
        backButton = (Button)findViewById(R.id.backButton);
        proceedButton = (Button)findViewById(R.id.loginButton);

        currentVoteTextView = (TextView)findViewById(R.id.currentVoteTextView);
        electionInformationTextView = (TextView)findViewById(R.id.electionInformationTextView);
        electionNameTextView = (TextView)findViewById(R.id.electionNameTextView);
        onlineUserTextView = (TextView)findViewById(R.id.onlineUserTextView);

        onlineUserTextView.setText(username);
        electionInformationTextView.setText(electionInformation);
        electionNameTextView.setText(currentElection);

        approveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                vote = "Approve";
                currentVoteTextView.setText(vote);
            }
        });

        againstButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                vote = "Against";
                currentVoteTextView.setText(vote);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                final Intent backToElectionChoice = new Intent(Voting_Screen.this, Election_Choice.class);
                backToElectionChoice.putExtra("USERNAME",username);
                startActivity(backToElectionChoice);
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(vote!=null)
                {
                    createDialog(2,"","");
                }
                else
                {
                    createDialog(7,"","");
                }
            }
        });
    }

    public void createDialog(int code, String potential_network_error, final String retry_id) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Voting_Screen.this);
        final EditText enter_id_edittext = new EditText(Voting_Screen.this);
        enter_id_edittext.setKeyListener(DigitsKeyListener.getInstance());
        switch (code)
        {
            //1 is successful login
            //2 is for first ID
            //3 is for second ID
            //4 is for third ID
            //5 is for non-digit input and non-existent ID's
            //6 is for network error
            //7 is for updating the choice

            case 1:
            {
                dialog.setTitle("Successful Vote!");
                dialog.setMessage("Your vote has been validated");
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final Intent toConfirmationIntent = new Intent(Voting_Screen.this, Confirmation_Screen.class);
                        toConfirmationIntent.putExtra("USERNAME",username);
                        toConfirmationIntent.putExtra("ELECTIONNAME",currentElection);
                        toConfirmationIntent.putExtra("VOTE",vote);
                        startActivity(toConfirmationIntent);
                    }
                });
                break;
            }

            case 2:
            {
                dialog.setTitle("First Voter ID");
                dialog.setMessage("Please Enter First Voter ID: ");
                dialog.setView(enter_id_edittext);
                dialog.setNeutralButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!TextUtils.isEmpty(enter_id_edittext.getText().toString()))
                        {
                            firstID = enter_id_edittext.getText().toString();
                            if(TextUtils.isDigitsOnly(enter_id_edittext.getText()))
                            {
                                validateFirstID_NetworkCall();
                            }
                            else
                            {
                                createDialog(5,"","First");
                            }
                        }
                        else
                        {
                            createDialog(5,"","First");
                        }
                    }
                });
                break;
            }

            case 3:
            {
                dialog.setTitle("Second Voter ID");
                dialog.setMessage("Please Enter Second Voter ID: ");
                dialog.setView(enter_id_edittext);
                dialog.setNeutralButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!TextUtils.isEmpty(enter_id_edittext.getText().toString()))
                        {
                            secondID = enter_id_edittext.getText().toString();
                            if(TextUtils.isDigitsOnly(enter_id_edittext.getText()))
                            {
                                validateSecondID_NetworkCall();
                            }
                            else
                            {
                                createDialog(5,"","Second");
                            }
                        }
                        else
                        {
                            createDialog(5,"","Second");
                        }
                    }
                });
                break;
            }
            case 4:
            {
                dialog.setTitle("Third Voter ID");
                dialog.setMessage("Please Enter Third Voter ID: ");
                dialog.setView(enter_id_edittext);
                dialog.setNeutralButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!TextUtils.isEmpty(enter_id_edittext.getText().toString()))
                        {
                            thirdID = enter_id_edittext.getText().toString();
                            if(TextUtils.isDigitsOnly(enter_id_edittext.getText()))
                            {
                                validateThirdID_NetworkCall();
                            }
                            else
                            {
                                createDialog(5,"","Third");
                            }
                        }
                        else
                        {
                            createDialog(5,"","Third");
                        }
                    }
                });
                break;
            }
            case 5:
            {
                dialog.setTitle("Invalid "+ retry_id + " ID");
                dialog.setMessage("Please Enter A Valid ID: ");
                dialog.setView(enter_id_edittext);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!TextUtils.isEmpty(enter_id_edittext.getText().toString()) && TextUtils.isDigitsOnly(enter_id_edittext.getText()))
                        {
                            if(firstID == null)
                            {
                                firstID = enter_id_edittext.getText().toString();
                                validateFirstID_NetworkCall();
                            }
                            else if(secondID==null)
                            {
                                secondID = enter_id_edittext.getText().toString();
                                validateSecondID_NetworkCall();
                            }
                            else if(thirdID==null)
                            {
                                thirdID = enter_id_edittext.getText().toString();
                                validateThirdID_NetworkCall();
                            }
                            else
                            {
                                thirdID = enter_id_edittext.getText().toString();
                                validateThirdID_NetworkCall();
                            }
                        }
                        else
                        {
                            createDialog(5,"",retry_id);
                        }
                    }
                });
                break;
            }

            case 6:
            {
                dialog.setTitle("Network Error");
                dialog.setMessage(potential_network_error);
                dialog.setView(enter_id_edittext);
                dialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!TextUtils.isEmpty(enter_id_edittext.getText().toString()) && TextUtils.isDigitsOnly(enter_id_edittext.getText()))
                        {
                            if(firstID == null)
                            {
                                firstID = enter_id_edittext.getText().toString();
                                validateFirstID_NetworkCall();
                            }
                            else if(secondID==null)
                            {
                                secondID = enter_id_edittext.getText().toString();
                                validateSecondID_NetworkCall();
                            }
                            else if(thirdID==null)
                            {
                                thirdID = enter_id_edittext.getText().toString();
                                validateThirdID_NetworkCall();
                            }
                            else
                            {
                                thirdID = enter_id_edittext.getText().toString();
                                validateThirdID_NetworkCall();
                            }
                        }
                        else
                        {
                            createDialog(5,"",retry_id);
                        }
                    }
                });

                break;
            }

            case 7:
            {
                dialog.setTitle("No Vote Cast");
                dialog.setMessage("Please Choose Which Vote To Cast" + potential_network_error);
                dialog.setNeutralButton("Back to Voting Screen", new DialogInterface.OnClickListener(){
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

    public void validateFirstID_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest firstIDRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SS"))
                        {
                            createDialog(3,"","");
                        }
                        else
                        {
                            firstID = null;
                            createDialog(5,"","First");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(6,error.toString(), "");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "validateFirstID");
                params.put("username", username);
                params.put("electionID", electionID);
                params.put("firstID", firstID);

                return params;
            }
        };
        queue.add(firstIDRequest);
    }

    public void validateSecondID_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest validateSecondID_Request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SS"))
                        {
                            createDialog(4,"","");
                        }
                        else
                        {
                            secondID = null;
                            createDialog(5,response,"Second");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(6,error.toString(), "");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "validateSecondID");
                params.put("username", username);
                params.put("electionID", electionID);
                params.put("secondID", secondID);

                return params;
            }
        };
        queue.add(validateSecondID_Request);
    }

    public void validateThirdID_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest validateThirdID_Request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SS"))
                        {
                            createDialog(1,"","");
                        }
                        else
                        {
                            thirdID = null;
                            createDialog(5,"","Third");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(6,error.toString(), "");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action", "validateThirdID");
                params.put("username", username);
                params.put("electionID", electionID);
                params.put("thirdID", thirdID);
                params.put("vote", vote);

                return params;
            }
        };
        queue.add(validateThirdID_Request);
    }
}
