package com.example.alarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class alarmReceiver extends Activity {

    private MediaPlayer mediaPlayer;
    private Button snoozeButton;
    private Button dismissButton;
    private Uri alarmToneUri;
    private TextView alarmTimeTextView;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_receiver);

        // Initialize MediaPlayer with the desired sound resource
        mediaPlayer = MediaPlayer.create(this, R.raw.alarmringtone); // Replace with your sound file

        // Start playing the sound
        mediaPlayer.start();

        // Initialize UI elements
        snoozeButton = findViewById(R.id.snooze_button);
        dismissButton = findViewById(R.id.dismiss_button);
        alarmTimeTextView = findViewById(R.id.alarm_time_textview);

        // Initialize vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Start vibration when the activity is created
        startVibration();


        // Set snooze button click listener
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snoozeAlarm();
                stopVibration();
            }
        });

        // Set dismiss button click listener
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAlarm();
            }
        });
    }

    private void stopVibration() {
        // Cancel the ongoing vibration
        vibrator.cancel();
    }

    private void snoozeAlarm() {
        stopVibration();
        stopSound(); // Stop the alarm sound.

        // Display a snooze message
        Toast.makeText(this, "Alarm snoozed for 5 minutes", Toast.LENGTH_SHORT).show();

        // Close the activity after a delay (simulating snooze)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(alarmReceiver.this, alarmReceiver.class);
                finish();
                startActivity(intent);
            }
        }, 5 * 60 * 1000); // 5 minutes in milliseconds
    }

    private void dismissAlarm() {

        stopVibration();
        stopSound();

        // Display a dismiss message
        Toast.makeText(this, "Alarm dismissed", Toast.LENGTH_SHORT).show();

        finish(); // Close the activity.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startVibration() {

        for (int i = 0; i <50; i++) {

            // Vibrate for 5000 milliseconds (adjust duration as needed)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(250);
            }
        }
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when the activity is destroyed
        stopSound();
    }

}

