package com.zybooks.db_with_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    CustomAdaptor customAdaptor;

    MyDatabase myDB;
    ArrayList<String> employee_id, employee_name, employee_position, employee_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_new);

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        myDB = new MyDatabase(HomeActivity.this);
        employee_id = new ArrayList<>();
        employee_name = new ArrayList<>();
        employee_position = new ArrayList<>();
        employee_phone = new ArrayList<>();

        storeDataInArrays();
        customAdaptor = new CustomAdaptor(HomeActivity.this, this, employee_id, employee_name,
                employee_position, employee_phone);
        recyclerView.setAdapter(customAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();

        }
        else{
            while (cursor.moveToNext()){
                employee_id.add(cursor.getString(0));
                employee_name.add(cursor.getString(1));
                employee_position.add(cursor.getString(2));
                employee_phone.add(cursor.getString(3));


            }
        }
    }
    void delete(){

    }
}