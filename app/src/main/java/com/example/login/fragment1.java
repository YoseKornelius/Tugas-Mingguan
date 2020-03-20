package com.example.login;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class fragment1 extends Fragment {

    private Button btnStart;
    private Button btnStop;


    View view;
    public fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment,container,false);

        btnStart = view.findViewById( R.id.btnStart );
        btnStop = view.findViewById( R.id.btnStop );

        btnStart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName componentName = new ComponentName( getActivity(), MyJobService.class );
                JobInfo jobInfo = new JobInfo.Builder( 123, componentName )
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED )
                        .setPersisted( true )
                        .setPeriodic( 15 * 60 * 1000 )
                        .build();

                JobScheduler scheduler = (JobScheduler) getActivity().getSystemService( JOB_SCHEDULER_SERVICE );
                int resultCode = scheduler.schedule( jobInfo );
                if (resultCode == JobScheduler.RESULT_SUCCESS){
                    Log.i( TAG, "schedulejob: Job Scheduled" );
                }
                else {
                    Log.i( TAG, "schedulejob: Job Scheduling failed" );
                }


            }
        } );

        btnStop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler scheduler = (JobScheduler) getActivity().getSystemService( JOB_SCHEDULER_SERVICE );
                scheduler.cancel( 123 );
                Log.i( TAG, "schedulejob: Job cancelled" );
            }
        } );

        return view;

    }


}