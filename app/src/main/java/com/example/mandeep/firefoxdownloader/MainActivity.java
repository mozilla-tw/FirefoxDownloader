package com.example.mandeep.firefoxdownloader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.firefoxlitedownloaderlibrary.AccessFile;
import com.example.firefoxlitedownloaderlibrary.LiveDataHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        schedule=findViewById(R.id.Schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessFile accessFile=new AccessFile();
                accessFile.download();
            }
        });
        LiveDataHelper.getInstance().observePercentage()
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        progressBar.setProgress(integer);
                    }
                });



    }
}
