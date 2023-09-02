package com.zybooks.db_with_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText name_input, pos_input, phone_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        pos_input = findViewById(R.id.pos_input);
        phone_input = findViewById(R.id.phone_input);
        add_button = findViewById(R.id.add_new);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDB = new MyDatabase(AddActivity.this);
                myDB.addEmployee(name_input.getText().toString().trim(),
                        pos_input.getText().toString().trim(),
                        phone_input.getText().toString().trim());

                finish();

            }
        });





    }
}