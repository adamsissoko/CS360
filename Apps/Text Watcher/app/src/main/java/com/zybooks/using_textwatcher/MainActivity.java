package com.zybooks.using_textwatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textInput;
    private Button buttonSubmit;
    private TextView errorMessage;
    private TextView textGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.edit_text_username);
        buttonSubmit = findViewById(R.id.confirm_button);
        errorMessage = findViewById(R.id.continue_message);
        textGreeting = findViewById(R.id.hello_message);

        textInput.addTextChangedListener(textWatcher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textInput.getText().toString().trim();
                sayHello(name);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String usernameInput = textInput.getText().toString().trim();

            buttonSubmit.setEnabled(!usernameInput.isEmpty());
            if (usernameInput.isEmpty()){
                errorMessage.setVisibility(View.VISIBLE);
                textGreeting.setVisibility(View.INVISIBLE);

            }
            else{
                errorMessage.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void sayHello(String name){
        if (name != null){
            Toast.makeText(this, "Hello " + name + "!", Toast.LENGTH_SHORT).show();
            textGreeting.setText("Hello " + name + "!");
            textGreeting.setVisibility(View.VISIBLE);

        }else{
            Toast.makeText(this, "You must enter your name!", Toast.LENGTH_SHORT).show();

        }


    }


}