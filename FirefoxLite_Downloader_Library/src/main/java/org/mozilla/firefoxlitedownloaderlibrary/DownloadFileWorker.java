package org.mozilla.firefoxlitedownloaderlibrary;

import android.content.Context;
import android.os.Environment;

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
            URL url = new URL("http://mattmahoney.net/dc/enwik8.pmd");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            InputStream in = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int len=0;
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DownloaderSampleDownloadFile";
            File storageDir = new File(path);
            FileOutputStream f = new FileOutputStream(storageDir);
            long total = 0;
            while ((len = in.read(buffer)) > 0) {
                total = total+len;
                int percent = (int) ((total * 100) / lengthOfFile);
                liveDataHelper.updatePercentage(percent);
                f.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
