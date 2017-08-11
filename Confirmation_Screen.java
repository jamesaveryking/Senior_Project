package com.example.jamesking.choice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Confirmation_Screen extends AppCompatActivity {
    TextView loadedUsernameTextView, loadedVoteTextView, loadedElectionTextView;
    String username, vote, election, password;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_screen);

        loadedUsernameTextView = (TextView)findViewById(R.id.loadedUsernameTextView);
        loadedElectionTextView = (TextView)findViewById(R.id.loadedElectionTextView);
        loadedVoteTextView = (TextView)findViewById(R.id.loadedVoteTextView);



        logoutButton = (Button)findViewById(R.id.logoutButton);

        username = getIntent().getStringExtra("USERNAME");
        vote = getIntent().getStringExtra("VOTE");
        election = getIntent().getStringExtra("ELECTIONNAME");
        password = getIntent().getStringExtra("PASSWORD");


        loadedUsernameTextView.setText(username);
        loadedVoteTextView.setText(vote);
        loadedElectionTextView.setText(election);


        logoutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                logoutUser_NetworkCall();
            }
        });
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
                            final Intent toHomeScreen = new Intent(Confirmation_Screen.this, Login_Screen.class);
                            startActivity(toHomeScreen);
                        }
                        else
                        {
                            createDialog(response);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        createDialog(error.toString());
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

    public void createDialog(String potential_network_error) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Confirmation_Screen.this);

        dialog.setTitle("Network Error:");
        dialog.setMessage(potential_network_error);
        dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog shown = dialog.create();
        shown.show();
    }
}
