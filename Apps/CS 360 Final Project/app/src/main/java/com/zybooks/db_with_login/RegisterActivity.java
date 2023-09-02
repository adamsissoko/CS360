package com.zybooks.db_with_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//**************************************
//
//  REGISTER a user to the app
//
//**************************************

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup, signin;
    Login_DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        signup = (Button)  findViewById(R.id.sign_up_button);
        signin = (Button) findViewById(R.id.sign_in_button);

        DB = new Login_DBHelper(this);

        // let user sign up based on username and password entered
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                // check if all fields have data or not
                if(user.equals("") || (pass.equals("") || repass.equals("")))
                    Toast.makeText(RegisterActivity.this, "Please check all fields", Toast.LENGTH_SHORT).show();
                else{
                    // check if passwords are same
                    if(pass.equals(repass)){
                        // check if username is in db already
                        Boolean checkuser = DB.checkUsername(user);
                        if(checkuser == false){
                            // insert username and password into db
                            Boolean insert = DB.insertData(user, pass);
                            // start the home activity
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "User already exists; please sign-in", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists; please sign-in", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Go back to log in screen
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
    }
}