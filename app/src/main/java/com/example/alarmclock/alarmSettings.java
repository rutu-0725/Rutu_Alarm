package com.example.alarmclock;

import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


public class alarmSettings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView selectedTimeTextView;
    private static final int RINGTONE_PICKER_REQUEST_CODE = 1;

    public static final String EXTRA_RINGTONE_URI = "ringtoneUri";

    private Uri selectedAlarmTone;

    static final int ALARM_REQ=100;
    private static final int JOB_ID = 123; // Unique ID for the job
    private int selectedHourOfDay;
    private int selectedMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);

        EditText editText = findViewById(R.id.editName);
        Intent intent = getIntent();
        if (intent != null) {
            String alarmName = intent.getStringExtra("name");
            editText.setText(alarmName);
        }else{
            editText.setText("Alarm Name");
        }

        Button pickTimeButton = findViewById(R.id.pickTimeButton);
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        Button pickToneButton = findViewById(R.id.pickToneButton);
        pickToneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickAlarmTone();
            }
        });

        Button saveAlarmButton = findViewById(R.id.saveAlarmButton);
        saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarm();
            }
        });
    }


    private void showTimePickerDialog() {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedHourOfDay = hourOfDay;
        selectedMinute = minute;
        String time = hourOfDay + ":" + minute;
        selectedTimeTextView.setText("Selected Time: " + time);
    }


    private void pickAlarmTone() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        startActivityForResult(intent, RINGTONE_PICKER_REQUEST_CODE);
    }


    private void saveAlarm() {

        EditText editText = findViewById(R.id.editName);
        // Get the text entered by the user
        String userInput = editText.getText().toString().trim();

        // Set a default value if the user did not enter any text

        String alarmName = userInput.isEmpty() ? "Alarm" : userInput;


        Intent intent = new Intent(this, alarmManagment.class);

        // Put the data you want to pass into the Intent
        intent.putExtra("name", alarmName);
        intent.putExtra("hour", String.valueOf(selectedHourOfDay));
        intent.putExtra("Min", String.valueOf(selectedMinute));

        // Start ActivityB
        startActivity(intent);


        scheduleJob(selectedHourOfDay, selectedMinute);
        Toast.makeText(this, "Alarm saved!", Toast.LENGTH_SHORT).show();

    }

    private void scheduleJob(int hourOfDay, int minute) {
        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();

        // Set the desired time for the job
        long scheduledTimeMillis = calculateScheduledTime(hourOfDay, minute);

        // Create a JobInfo.Builder with the necessary parameters
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, jobService.class))
                .setPersisted(true) // Job survives device reboots
                .setMinimumLatency(scheduledTimeMillis - currentTimeMillis) // Delay before the job runs
                .setOverrideDeadline(scheduledTimeMillis + 1000); // Maximum delay for the job

        // Schedule the job
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(builder.build());

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            // Job scheduled successfully
        } else {
            // Job scheduling failed
        }
    }


    private long calculateScheduledTime(int hourOfDay, int minute) {
        // Get the current date and time
        long currentTimeMillis = System.currentTimeMillis();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        // Set the desired time
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(java.util.Calendar.MINUTE, minute);
        calendar.set(java.util.Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }
}






