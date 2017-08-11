package com.example.jamesking.choice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_Created_Confirmation extends AppCompatActivity {

    private String name, username, password, state, city, sq1, sq1A, sq2, sq2A;
    private TextView loadedNameTextView, loadedUsernameTextView, loadedPasswordTextView, loadedStateTextView, loadedCityTextView,
                    loadedSQ1TextView, loadedSQ2TextView, loadedSQ1ATextView, loadedSQ2ATextView;
    private Button backButton, loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_created_confirmation);

        name = getIntent().getStringExtra("NAME");
        username = getIntent().getStringExtra("USERNAME");
        password = getIntent().getStringExtra("PASSWORD");
        state = getIntent().getStringExtra("STATE");
        city = getIntent().getStringExtra("CITY");
        sq1 = getIntent().getStringExtra("SQ1");
        sq1A = getIntent().getStringExtra("SQ1A");
        sq2 = getIntent().getStringExtra("SQ2");
        sq2A = getIntent().getStringExtra("SQ2A");

        loadedNameTextView = (TextView)findViewById(R.id.loadedNameTextView);
        loadedUsernameTextView = (TextView)findViewById(R.id.loadedUsernameTextView);
        loadedPasswordTextView = (TextView)findViewById(R.id.loadedPasswordTextView);
        loadedStateTextView = (TextView)findViewById(R.id.loadedStateTextView);
        loadedCityTextView = (TextView)findViewById(R.id.loadedCityTextView);
        loadedSQ1TextView = (TextView)findViewById(R.id.loadedSQ1TextView);
        loadedSQ1ATextView = (TextView)findViewById(R.id.loadedSQ1ATextView);
        loadedSQ2TextView = (TextView)findViewById(R.id.loadedSQ2TextView);
        loadedSQ2ATextView = (TextView)findViewById(R.id.loadedSQ2ATextView);

        backButton = (Button)findViewById(R.id.backButton);
        loginButton = (Button)findViewById(R.id.loginButton);

        loadedNameTextView.setText(name);
        loadedUsernameTextView.setText(username);
        loadedPasswordTextView.setText(password);
        loadedStateTextView.setText(state);
        loadedCityTextView.setText(city);
        loadedSQ1TextView.setText(sq1);
        loadedSQ1ATextView.setText(sq1A);
        loadedSQ2TextView.setText(sq2);
        loadedSQ2ATextView.setText(sq2A);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                final Intent toSecuritySettingIntent = new Intent(User_Created_Confirmation.this, Security_Settings.class);
                startActivity(toSecuritySettingIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                final Intent toLoginScreen = new Intent(User_Created_Confirmation.this, Login_Screen.class);
                toLoginScreen.putExtra("USERNAME",username);
                toLoginScreen.putExtra("PASSWORD",password);
                startActivity(toLoginScreen);
            }
        });


    }
}
