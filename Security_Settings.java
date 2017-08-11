package com.example.jamesking.choice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Security_Settings extends AppCompatActivity {
    TextView vID1OutputTextView, vID2OutputTextView, vID3OutputTextView;
    String vID1, vID2, vID3, sq1, sq2, sq1A, sq2A, password;
    Button backButton, proceedButton;
    Spinner sq1Spinner, sq2Spinner;
    List<String> sq1questions, sq2questions;
    EditText sq1AnswerEditText, sq2AnswerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);



        sq1AnswerEditText = (EditText)findViewById(R.id.sq1AnswerEditText);
        sq2AnswerEditText = (EditText)findViewById(R.id.sq2AnswerEditText);

        vID1OutputTextView = (TextView)findViewById(R.id.vID1OutputTextView);
        vID2OutputTextView = (TextView)findViewById(R.id.vID2OutputTextView);
        vID3OutputTextView = (TextView)findViewById(R.id.vID3OutputTextView);

        backButton = (Button)findViewById(R.id.backButton);
        proceedButton = (Button)findViewById(R.id.loginButton);

        sq1Spinner = (Spinner)findViewById(R.id.sq1Spinner);
        sq1questions = new ArrayList<String>();
        sq1questions.add("What is your middle name?");
        sq1questions.add("What is your age?");

        sq2Spinner = (Spinner)findViewById(R.id.sq2Spinner);
        sq2questions = new ArrayList<String>();
        sq2questions.add("What town were you born in?");
        sq2questions.add("What is your favorite sport?");
        sq2questions.add("What is your favorite movie?");

        ArrayAdapter<String> adapter_sq1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sq1questions);
        adapter_sq1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sq1Spinner.setAdapter(adapter_sq1);
        sq1Spinner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m)
            {
                dropKey(Security_Settings.this);
                return false;
            }
        });

        ArrayAdapter<String> adapter_sq2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sq2questions);
        adapter_sq2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sq2Spinner.setAdapter(adapter_sq2);
        sq2Spinner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m)
            {
                dropKey(Security_Settings.this);
                return false;
            }
        });

        vID1 = getIntent().getStringExtra("ID1");
        vID2 = getIntent().getStringExtra("ID2");
        vID3 = getIntent().getStringExtra("ID3");
        password = getIntent().getStringExtra("PASSWORD");

        vID1OutputTextView.setText(vID1);
        vID2OutputTextView.setText(vID2);
        vID3OutputTextView.setText(vID3);

        proceedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(!TextUtils.isEmpty(sq1AnswerEditText.getText().toString())&&!TextUtils.isEmpty(sq2AnswerEditText.getText().toString()))
                {
                    sq1 = sq1Spinner.getSelectedItem().toString();
                    sq2 = sq2Spinner.getSelectedItem().toString();
                    sq1A = sq1AnswerEditText.getText().toString();
                    sq2A = sq2AnswerEditText.getText().toString();

                    insertUserSecurityInfo_NetworkCall();
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
                final Intent backToCreateUserIntent = new Intent(Security_Settings.this, Create_Account.class);
                startActivity(backToCreateUserIntent);
            }
        });
    }

    public void createDialog(int code, String potential_network_error) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Security_Settings.this);
        switch (code)
        {
            //1 is successful security info update
            //2 is for filling in edit text fields
            //3 is for network connection failure
            case 1:
            {
                dialog.setTitle("Successful Security Update!");
                dialog.setMessage("Your security information has been updated");
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final Intent toUserCreatedConfirmationIntent = new Intent(Security_Settings.this, User_Created_Confirmation.class);
                        //add network call for above and below information
                        toUserCreatedConfirmationIntent.putExtra("ID1", vID1);
                        toUserCreatedConfirmationIntent.putExtra("ID2", vID2);
                        toUserCreatedConfirmationIntent.putExtra("ID3", vID3);
                        toUserCreatedConfirmationIntent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                        toUserCreatedConfirmationIntent.putExtra("STATE", getIntent().getStringExtra("STATE"));
                        toUserCreatedConfirmationIntent.putExtra("CITY", getIntent().getStringExtra("CITY"));
                        toUserCreatedConfirmationIntent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                        toUserCreatedConfirmationIntent.putExtra("PASSWORD",password);
                        toUserCreatedConfirmationIntent.putExtra("SQ1",sq1);
                        toUserCreatedConfirmationIntent.putExtra("SQ1A",sq1A);
                        toUserCreatedConfirmationIntent.putExtra("SQ2",sq2);
                        toUserCreatedConfirmationIntent.putExtra("SQ2A",sq2A);
                        startActivity(toUserCreatedConfirmationIntent);
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
                dialog.setMessage("There was an error in your network connection: \n"
                        +potential_network_error);
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

    public void insertUserSecurityInfo_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest securitySettingsRequest = new StringRequest(Request.Method.POST, url,
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
                            createDialog(4,response);
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
                params.put("action", "addSecurityInformation");
                params.put("firstID", vID1);
                params.put("secondID", vID2);
                params.put("thirdID", vID3);
                params.put("firstSQ", sq1);
                params.put("firstSQA", sq1A);
                params.put("secondSQ", sq2);
                params.put("secondSQA", sq2A);
                params.put("password", password);
                return params;
            }
        };
        queue.add(securitySettingsRequest);
    }
    public void dropKey(Activity current)
    {
        InputMethodManager manager = (InputMethodManager) current.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View current_v = current.getCurrentFocus();
        if(current_v==null)
        {
            current_v = new View(current);
        }
        manager.hideSoftInputFromWindow(current_v.getWindowToken(),0);
    }
}
