package com.example.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class homeScreen extends AppCompatActivity {
    private ImageButton setAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setAlarmButton = findViewById(R.id.setAlarmButton);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeScreen.this, alarmSettings.class);
                finish();
                startActivity(intent);
            }
        });
    }
}