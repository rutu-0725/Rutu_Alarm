package com.example.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class alarmManagment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_managment);
        // Retrieve the data passed from ActivityA
        Intent intent = getIntent();
        if (intent != null) {
            String alarmName = intent.getStringExtra("name");
            String selectedHourOfDay = intent.getStringExtra("hour");
            String selectedMinute = intent.getStringExtra("Min");

            // Update UI elements with alarm information
            TextView alarmNameTextView = findViewById(R.id.alarmNameTextView);
            alarmNameTextView.setText("Alarm Name: " + alarmName);

            TextView alarmTimeTextView = findViewById(R.id.alarmTimeTextView);
            String alarmTime= selectedHourOfDay + ":" + selectedMinute;
            alarmTimeTextView.setText("Alarm Time: " + alarmTime);

            ToggleButton toggleButton = findViewById(R.id.toggleButton);

            Button editButton = findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(alarmManagment.this, alarmSettings.class);
                    intent.putExtra("name", alarmName);
                    finish();
                    startActivity(intent);
                }
            });

            Button exitButton = findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(alarmManagment.this, homeScreen.class);
                    finish();
                    startActivity(intent);
                }
            });
        }
    }
}