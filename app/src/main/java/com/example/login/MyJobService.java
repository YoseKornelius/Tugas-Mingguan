package com.example.login;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean jobCancelled = false;
    Context context;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        context = getApplicationContext();
        Toast.makeText( getApplicationContext(),"Job Started",Toast.LENGTH_SHORT ).show();
        doBackgroundWork(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        Toast.makeText( getApplicationContext(),"Job cancelled before completion",Toast.LENGTH_SHORT ).show();
        jobCancelled = true;
        return true;
    }

    private void doBackgroundWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    final int tes=i;
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"TUGAS WFH"+ String.valueOf(tes),Toast.LENGTH_SHORT).show();
                        }
                    });
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e( TAG,"run",e.getCause()  );
                    }
                }
                Toast.makeText(getApplicationContext(),"job finished",Toast.LENGTH_SHORT ).show();
                jobFinished(params, false);
            }
        }).start();
    }



}