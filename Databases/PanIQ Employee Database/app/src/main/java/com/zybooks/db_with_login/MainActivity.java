//
//  Adam Sissoko
//  Project Two
//
//  This application requests a user log-in or register a
//  username and password. It will also request permission
//  to read a user's SMS messages. It then displays a list
//  of employees which the user can add to with the add button,
//  edit by clicking on a user, or delete a user by clicking
//  on the trashcan icon for the user.
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int SMS_PERMISSION_CODE = 1;
    EditText username, password;
    Button sign_in_button, register_button, request_button;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username_one);
        password = (EditText) findViewById(R.id.password_one);
        sign_in_button = (Button) findViewById(R.id.sign_in_button_one);
        register_button = (Button) findViewById(R.id.register_button);
        request_button = (Button) findViewById(R.id.request_button);
        DB = new DBHelper(this);

        requestSmsPermission();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please check all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass = DB.checkUsernamePassword(user, pass);
                    if(checkuserpass == true){
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

        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();

                } else {
                    requestSmsPermission();
                }

            }
        });


    }

    private void requestSmsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed in order to enhance user experience.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }

        }
    }
}