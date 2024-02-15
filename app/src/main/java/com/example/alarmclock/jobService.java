package com.example.alarmclock;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class jobService extends JobService {

    private static final int JOB_FINISHED = 1;
    public static final String EXTRA_RINGTONE_URI = "ringtoneUri";

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == JOB_FINISHED) {
                // Perform any cleanup or additional tasks if needed

                // Start the next activity
                startActivity();
                jobFinished((JobParameters) msg.obj, false); // Job finished
            }
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        // Simulate work by delaying for 5 seconds

        handler.sendMessageDelayed(handler.obtainMessage(JOB_FINISHED, params), 5000);
        return true; // There is ongoing work
    }




    @Override
    public boolean onStopJob(JobParameters params) {
        handler.removeMessages(JOB_FINISHED); // Remove any pending messages
        return true; // Reschedule the job if necessary
    }

    private void startActivity() {
        Intent intent = new Intent(this, alarmReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
