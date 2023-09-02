package com.zybooks.db_with_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    CustomAdaptor customAdaptor;

    Main_DBHelper myDB;
    ArrayList<String> event_id, event_title, event_description, event_date, event_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // build a recycler view to display events from db
        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_new);

        // action button to add event
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);       // deprecated use here

            }
        });

        // create arrays to store db data
        myDB = new Main_DBHelper(HomeActivity.this);
        event_id = new ArrayList<>();
        event_title = new ArrayList<>();
        event_description = new ArrayList<>();
        event_date = new ArrayList<>();
        event_time = new ArrayList<>();

        // access db and get data to fill arrays
        storeDataInArrays();

        // use the customAdaptor class to build each row
        customAdaptor = new CustomAdaptor(HomeActivity.this, this, event_id, event_title,
                event_description, event_date, event_time);
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

    // get data from db and create rows in recycler view for scroll
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();

        }
        else{
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_title.add(cursor.getString(1));
                event_description.add(cursor.getString(2));
                event_date.add(cursor.getString(3));
                event_time.add(cursor.getString(4));


            }
        }
    }
    void delete(){

    }
}