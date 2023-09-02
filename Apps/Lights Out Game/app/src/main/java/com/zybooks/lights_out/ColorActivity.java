package com.zybooks.lights_out;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ColorActivity extends AppCompatActivity {

    public static final String EXTRA_COLOR = "com.zybooks.lightsout.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        Intent intent = getIntent();
        int colorId = intent.getIntExtra(EXTRA_COLOR, R.color.yellow);

        int radioId = 0;
        switch (colorId) {
            case R.color.red:
                radioId = R.id.radio_red;
                break;
            case R.color.orange:
                radioId = R.id.radio_orange;
                break;
            case R.color.yellow:
                radioId = R.id.radio_yellow;
                break;
            case R.color.green:
                radioId = R.id.radio_green;
                break;
        }

        RadioButton radio = findViewById(radioId);
        radio.setChecked(true);


    }

    public void onColorSelected(View view) {
        int colorId = R.color.black;
        switch (view.getId()) {
            case R.id.radio_red:
                colorId = R.color.red;
                break;
            case R.id.radio_orange:
                colorId = R.color.orange;
                break;
            case R.id.radio_yellow:
                colorId = R.color.yellow;
                break;
            case R.id.radio_green:
                colorId = R.color.green;
                break;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_COLOR, colorId);
        setResult(RESULT_OK, intent);
        finish();
    }
}