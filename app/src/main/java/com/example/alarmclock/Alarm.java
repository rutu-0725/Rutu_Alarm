package com.example.alarmclock;

import android.net.Uri;

import java.io.Serializable;

public class Alarm implements Serializable {
    private String alarmName;
    private int id;
    private int hour;
    private int minute;
    private boolean isOn;
    private Uri selectedAlarmTone;

    public Alarm(String alarmName, boolean isOn, Uri selectedAlarmTone, int hour, int minute) {
        this.alarmName = alarmName;
        this.isOn = isOn;
        this.selectedAlarmTone = isOn ? selectedAlarmTone : null;
        this.hour = hour;
        this.minute = minute;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public boolean isOn() {
        return isOn;
    }

    public void toggleAlarmStatus() {
        isOn = !isOn;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Uri getSelectedAlarmTone() {
        return selectedAlarmTone;
    }

    public void setSelectedAlarmTone(Uri selectedAlarmTone) {
        this.selectedAlarmTone = selectedAlarmTone;
    }

    public String getName() {
        String name=alarmName;
        return name;
    }

    public String getTimeString() {
        // Format the hour and minute as a string (e.g., "12:30")
        return String.format("%02d:%02d", hour, minute);

    }
}
