package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.systemControllers.LoginController;
import com.example.arlingtonrentacar.database.DatabaseHelper;

public class RenterHomeScreen extends AppCompatActivity {
    private final String LOG_TAG = RenterHomeScreen.class.getSimpleName();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_home_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra(LoginController.USERNAME);
        Log.d(LOG_TAG, "Username passed from login screen: " + username);
        initGreetingForRenter(username);
    }


    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchRequestCarActivity(View view) {
        Intent intent = new Intent(this, RequestCarActivity.class);
        startActivity(intent);
    }

    private void initGreetingForRenter(String username){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(this);
        String fullName = systemUserDAO.getFullNameByUsername(username);
        TextView userGreeting = findViewById(R.id.tvUserGreeting);

        String greeting = AAUtil.getGreetingByHour();
        greeting += ", " + fullName;

        userGreeting.setText(greeting);
    }


}