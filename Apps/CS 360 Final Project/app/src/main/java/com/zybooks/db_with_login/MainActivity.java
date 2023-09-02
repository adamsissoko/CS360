
//  Adam Sissoko
//  Final Project
//  August 14, 2022
//
//  This application requests a user log-in or register a
//  username and password. It will also request permission
//  to read a user's SMS messages. It then displays a list
//  of events which the user can add to with the add button,
//  edit by clicking on an event, or delete an event by clicking
//  on the pencil icon for the event.
//
//  Events are stored in a database, and events are fired as
//  notifications at the set date and time the user specifies
//
//  Log in information is stored in a database, and employees
//  are stored in a separate database.


package com.zybooks.db_with_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int SMS_PERMISSION_CODE = 1;
    EditText username, password;
    Button sign_in_button, register_button, request_button;

    Login_DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username_one);
        password = (EditText) findViewById(R.id.password_one);
        sign_in_button = (Button) findViewById(R.id.sign_in_button_one);
        register_button = (Button) findViewById(R.id.register_button);
        request_button = (Button) findViewById(R.id.request_button);
        DB = new Login_DBHelper(this);

        createNotificationChannel();    // create the channel to handle notifications in the app

        // Log in
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // check that fields have data
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please check all fields", Toast.LENGTH_SHORT).show();
                }
                // check if the user and password exist and match in the database
                else{
                    Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                    if(checkUserPass == true){
                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        // allow user to register and store login info in database
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createNotificationChannel(){
        CharSequence name = "ReminderChannel";
        String description = "Channel for Reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyText", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}