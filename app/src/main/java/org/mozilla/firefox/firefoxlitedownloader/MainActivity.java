package org.mozilla.firefox.firefoxlitedownloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.mozilla.firefoxlitedownloaderlibrary.MainFunctionAccessFile;
import org.mozilla.firefoxlitedownloaderlibrary.LiveDataHelper;
import org.mozilla.firefox.firefoxlitedownloader.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {
    Button DownloadButton;
    Button ScheduleDownloadButton;
    ProgressBar progressBarDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarDownload=findViewById(R.id.progressBar);
        DownloadButton=findViewById(R.id.btnDownload);
        ScheduleDownloadButton=findViewById(R.id.btnSchedule);
        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFunctionAccessFile accessFile=new MainFunctionAccessFile();
                accessFile.download();

            }
        });
        ScheduleDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFunctionAccessFile accessFile=new MainFunctionAccessFile();
                accessFile.scheduleDownloads();
            }
        });
        LiveDataHelper.getInstance().observePercentage()
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        progressBarDownload.setProgress(integer);
                    }
                });
    }
}
