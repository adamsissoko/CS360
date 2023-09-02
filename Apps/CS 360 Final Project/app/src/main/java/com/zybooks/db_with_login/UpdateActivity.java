package com.zybooks.db_with_login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, description_input;
    Button update_button, delete_button, time_button, date_button;
    String alarm_generator;

    String id, title, description, date, time;

    int notificationCounter;    // used for setting id when creating alarm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // ID's for buttons and text input
        title_input = findViewById(R.id.title_input_update);
        description_input = findViewById(R.id.description_input_update);
        date_button = findViewById(R.id.btn_Date_Update);
        time_button = findViewById(R.id.btn_Time_Update);
        update_button = findViewById(R.id.add_new_update);
        delete_button = findViewById(R.id.edit_button);

        // Get data necessary to fill in text inputs / buttons for editing
        getAndSetIntentData();

        // TEXT WATCHERS to disable 'UPDATE' button
        title_input.addTextChangedListener(textWatcher);
        description_input.addTextChangedListener(textWatcher);
        time_button.addTextChangedListener(textWatcher);
        date_button.addTextChangedListener(textWatcher);

        // INPUT DATE and TIME
        time_button.setOnClickListener(view -> {selectTime();});
        date_button.setOnClickListener(view -> selectDate());

        // BUTTON to update event (enabled unless fields are empty)
        update_button.setOnClickListener(view -> {

            Main_DBHelper myDB = new Main_DBHelper(UpdateActivity.this);
            myDB.updateData(id, title_input.getText().toString().trim(),
                    description_input.getText().toString().trim(),
                    date_button.getText().toString().trim(),
                    time_button.getText().toString().trim());

            // set alarm here
            // if permission has not been granted, don't set an alarm
            if (ContextCompat.checkSelfPermission(UpdateActivity.this,
                    Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                try {
                    setAlarm(title_input.getText().toString().trim(),
                            description_input.getText().toString().trim(),
                            date_button.getText().toString().trim(),
                            time_button.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else{
                Toast.makeText(UpdateActivity.this, "You must grant permission to use this feature",
                        Toast.LENGTH_SHORT).show();
            }

            finish();

        });
        delete_button.setOnClickListener(view -> confirmDialog());

    }

    // TEXT WATCHER to check that all text has been entered
    // Disable 'UPDATE' button if false
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = title_input.getText().toString().trim();
            String description = description_input.getText().toString().trim();
            String date = date_button.getText().toString().trim();
            String time = time_button.getText().toString().trim();

            update_button.setEnabled(!title.isEmpty() && !description.isEmpty() &&
                    !date.isEmpty() && !time.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //****************************************
    //
    // Setting data for text and buttons
    //
    //****************************************

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("description") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("time")){

            // getting data from intent
            id = getIntent().getStringExtra("id");
            int i = Integer.parseInt(id);
            notificationCounter = (int)i;
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");

            // setting intent data
            title_input.setText(title);
            description_input.setText(description);
            date_button.setText(date);
            time_button.setText(time);

        }else{
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
        }
    }

    //****************************************
    //
    // DELETING the event
    //
    //****************************************

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure you want to delete " + title + "?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Main_DBHelper myDB = new Main_DBHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        builder.create().show();

    }


    //****************************************
    //
    // Setting date and time methods
    //
    //****************************************

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, i, i1) -> {
            alarm_generator = i + ":" + i1;
            time_button.setText(formatTime(i, i1));
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::onDateSet, year, month, day);
        datePickerDialog.show();
    }

    public String formatTime(int hour, int minute) {

        String time;
        String minutes_reformatted;

        if (minute / 10 == 0) {
            minutes_reformatted = "0" + minute;
        } else {
            minutes_reformatted = "" + minute;
        }

        if (hour == 0) {
            time = "12" + ":" + minutes_reformatted + " AM";
        } else if (hour < 12) {
            time = hour + ":" + minutes_reformatted + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + minutes_reformatted + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + minutes_reformatted + " PM";
        }
        return time;
    }

    //****************************************
    //
    // Setting the alarm and notifications
    //
    //****************************************

    private void setAlarm(String text, String description, String date, String time) throws ParseException {

        Intent intent = new Intent(UpdateActivity.this,
                AlarmBroadcast.class);
        intent.putExtra("event", text);
        intent.putExtra("description", description);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(UpdateActivity.this,
                notificationCounter,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String dateAndTime = date + " " + alarm_generator;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");

        Date dateToSet = null;

        try{
            dateToSet = formatter.parse(dateAndTime);
            assert dateToSet != null;   // check that date is not null

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                   dateToSet.getTime(),
                   pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm set!", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    private void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
        date_button.setText(String.format("%d-%d-%d", day1, month1 + 1, year1));
    }
}