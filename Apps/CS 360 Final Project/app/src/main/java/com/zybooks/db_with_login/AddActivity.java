package com.zybooks.db_with_login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private final int SMS_PERMISSION_CODE = 1;

    EditText title_input, description_input;
    Button add_button, time_button, date_button, request_button;
    String alarm_generator;

    // storing the notification id for AlarmManager reference
    long notificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // ID's for buttons and text input
        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.description_input);
        time_button = findViewById(R.id.btnTime);
        date_button = findViewById(R.id.btnDate);
        add_button = findViewById(R.id.add_new);

        // TEXT WATCHERS to disable 'ADD' button
        title_input.addTextChangedListener(textWatcher);
        description_input.addTextChangedListener(textWatcher);
        time_button.addTextChangedListener(textWatcher);
        date_button.addTextChangedListener(textWatcher);

        // BUTTON to request permissions from user
        request_button = (Button) findViewById(R.id.request_button);

        // INPUT DATE and TIME
        date_button.setOnClickListener(view -> selectDate());
        time_button.setOnClickListener(view -> selectTime());

        // BUTTON to add the event
        add_button.setOnClickListener(view -> {
            // Check if SMS permission has been granted before adding the event
            requestSmsPermission();

            // ADD event to Database
            Main_DBHelper myDB = new Main_DBHelper(AddActivity.this);

                notificationCounter = myDB.addReminder(title_input.getText().toString().trim(),
                    description_input.getText().toString().trim(),
                    date_button.getText().toString().trim(),
                    time_button.getText().toString().trim());

            // set alarm here
            // if permission has not been granted, don't set an alarm
            // but still add event to database for display
            if (ContextCompat.checkSelfPermission(AddActivity.this,
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
                Toast.makeText(AddActivity.this, "You must grant permission to use this feature", Toast.LENGTH_SHORT).show();
            }

           finish();

        });

        // allow user to set permission after denying / check permission status
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddActivity.this,
                        Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();

                } else {
                    requestSmsPermission();
                }

            }
        });

    }

    // TEXT WATCHER to check that all text has been entered
    // Disable 'ADD' button if false
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = title_input.getText().toString().trim();
            String description = description_input.getText().toString().trim();
            String date = date_button.getText().toString().trim();
            String time = time_button.getText().toString().trim();

            add_button.setEnabled(!title.isEmpty() && !description.isEmpty() &&
                    !date.isEmpty() && !time.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };



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
        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (datePicker, year1, month1, day1) ->
                date_button.setText(String.format(
                        "%d-%d-%d", day1, month1 + 1, year1)),
                        year, month, day);
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

        // format input time to be readable
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

        Intent intent = new Intent(AddActivity.this, AlarmBroadcast.class);
        intent.putExtra("event", text);
        intent.putExtra("description", description);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                AddActivity.this,
                (int)notificationCounter,               // use the ID field from db
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);     // use this flag to update the event
                                                        // else event gets overwritten
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String dateAndTime = date + " " + alarm_generator;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");

        try{
            Date dateToSet = formatter.parse(dateAndTime);
            assert dateToSet != null;       // check that date is not null

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    dateToSet.getTime(),
                    pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm set!", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //****************************************
    //
    // Checking for permissions
    //
    //****************************************

    private void requestSmsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)){

            new AlertDialog.Builder(this)
                    .setTitle("Read SMS Messages")
                    .setMessage("This permission is needed in order to set an SMS reminder for your events.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    AddActivity.this,
                                    new String[] {Manifest.permission.READ_SMS},
                                    SMS_PERMISSION_CODE);
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
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_SMS},
                    SMS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}