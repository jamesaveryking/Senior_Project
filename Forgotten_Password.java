package com.example.jamesking.choice;

//returns null on first try, then the correct password on second

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

public class Forgotten_Password extends AppCompatActivity {
    private Button submitButton, backButton;
    private EditText sq1AnswerEditText, sq2AnswerEditText, vID1InputEditText;
    private Spinner sq1Spinner, sq2Spinner;
    private String password, sq1, sq1A, sq2, sq2A, firstID;
    private TextView passwordOutputTextView;
    private List<String> sq1Questions, sq2Questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        passwordOutputTextView = (TextView) findViewById(R.id.passwordOutputTextView);

        sq1AnswerEditText = (EditText) findViewById(R.id.sq1AnswerEditText);
        sq2AnswerEditText = (EditText) findViewById(R.id.sq2AnswerEditText);
        vID1InputEditText = (EditText) findViewById(R.id.vID1InputEditText);

        backButton = (Button) findViewById(R.id.backButton);
        submitButton = (Button) findViewById(R.id.submitButton);

        sq1Spinner = (Spinner) findViewById(R.id.sq1Spinner);
        sq1Questions = new ArrayList<String>();
        sq1Questions.add("What is your middle name?");
        sq1Questions.add("What is your age?");

        sq2Spinner = (Spinner) findViewById(R.id.sq2Spinner);
        sq2Questions = new ArrayList<String>();
        sq2Questions.add("What town were you born in?");
        sq2Questions.add("What is your favorite sport?");
        sq2Questions.add("What is your favorite movie?");

        ArrayAdapter<String> adapter_sq1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sq1Questions);
        adapter_sq1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sq1Spinner.setAdapter(adapter_sq1);
        sq1Spinner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m)
            {
                dropKey(Forgotten_Password.this);
                return false;
            }
        });

        ArrayAdapter<String> adapter_sq2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sq2Questions);
        adapter_sq2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sq2Spinner.setAdapter(adapter_sq2);
        sq2Spinner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m)
            {
                dropKey(Forgotten_Password.this);
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent toLoginScreenIntent = new Intent(Forgotten_Password.this, Login_Screen.class);
                startActivity(toLoginScreenIntent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sq1AnswerEditText.getText().toString()) && !TextUtils.isEmpty(sq2AnswerEditText.getText().toString())
                        && !TextUtils.isEmpty(vID1InputEditText.getText().toString())) {
                    //network call
                    firstID = vID1InputEditText.getText().toString();
                    sq1 = sq1Spinner.getSelectedItem().toString();
                    sq1A = sq1AnswerEditText.getText().toString();
                    sq2 = sq2Spinner.getSelectedItem().toString();
                    sq2A = sq2AnswerEditText.getText().toString();


                    retrievePassword_NetworkCall();

                } else {
                    createDialog(2, "");
                }
            }
        });
    }

    public void createDialog(int code, String potential_network_error) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Forgotten_Password.this);
        switch (code) {
            //1 is successful security info update
            //2 is for filling in edit text fields
            //3 is for network connection failure
            case 1: {
                dialog.setTitle("Successful Password Retrieval");
                dialog.setMessage("Your Password is: " + password);
                dialog.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                break;
            }

            case 2: {
                dialog.setTitle("All Fields Not Filled");
                dialog.setMessage("Please make sure all necessary information is entered");
                dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                break;
            }

            case 3: {
                dialog.setTitle("Network Error");
                dialog.setMessage("There was an error in your network connection: \n"
                        + potential_network_error);
                dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                break;
            }
        }
        AlertDialog shown = dialog.create();
        shown.show();
    }

    public void retrievePassword_NetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.senior-project-james-king.info/post_to_database.php";
        StringRequest retrievePasswordRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String password_response = response;
                        if (password_response.substring(0, 1).equals("S") && password_response.substring(1,password_response.length())!=null) {
                            password = password_response.substring(1, password_response.length());
                            passwordOutputTextView.setText(password);
                            createDialog(1, password);
                        }
                        else if(response.equals("SF"))
                        {
                            createDialog(3,"Your information was not found");
                        }
                        else
                        {
                            createDialog(3,"Please try again");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createDialog(3, error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "requestPassword");
                params.put("firstID", firstID);
                params.put("firstSQ", sq1);
                params.put("firstSQA", sq1A);
                params.put("secondSQ", sq2);
                params.put("secondSQA", sq2A);
                return params;
            }
        };
        queue.add(retrievePasswordRequest);
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

