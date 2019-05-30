package com.example.firefoxlitedownloaderlibrary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadFileWorker extends Worker {

    private LiveDataHelper liveDataHelper;

    public DownloadFileWorker(@NonNull Context context, @NonNull WorkerParameters
            workerParams) {
        super(context, workerParams);
        liveDataHelper = LiveDataHelper.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            URL u = new URL("https://i.pinimg.com/originals/49/70/17/497017869c892b73b128ff72f2732035.jpg");
            URLConnection c = u.openConnection();
            c.connect();
            int lengthOfFile = c.getContentLength();
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1=0;
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mozilla_checkinngggggg";
            File storageDir = new File(path);
            FileOutputStream f = new FileOutputStream(storageDir);
            long total = 0;
            while ((len1 = in.read(buffer)) > 0) {
                total += len1; //total = total + len1
                int percent = (int) ((total * 100) / lengthOfFile);
                liveDataHelper.updatePercentage(percent);

                f.write(buffer,0,len1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
