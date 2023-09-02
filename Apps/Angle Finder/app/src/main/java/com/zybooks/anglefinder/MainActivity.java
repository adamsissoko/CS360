
// ********************
// Adam Sissoko
// SensorManager
// August 7, 2022
// ********************


package com.zybooks.anglefinder;

import static java.lang.Math.toDegrees;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // textview items
    TextView angle_X, angle_Y, angle_Z;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a sensor manager isntance
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // create two sensor types
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // set the sensor delays
        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);

        //set the textView items
        angle_X = findViewById(R.id.angle_view_X);
        angle_Y = findViewById(R.id.angle_view_Y);
        angle_Z = findViewById(R.id.angle_view_Z);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister the listener
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private final SensorEventListener listener = new SensorEventListener() {

        // declare all floats to use
        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        final float[] R = new float[9];
        final float[] values = new float[3];

        @Override
        public void onSensorChanged(SensorEvent event) {
            //check what sensor is reporting data
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                accelerometerValues = event.values.clone();
            else {
                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    magneticValues = event.values.clone();
                }
            }

            // build the lists of values
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);

            // put values on screen for user to view
            angle_X.setText(String.valueOf(toDegrees(values[1])));
            angle_Y.setText(String.valueOf(toDegrees(values[2])));
            angle_Z.setText(String.valueOf(toDegrees(values[0])));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };
}
