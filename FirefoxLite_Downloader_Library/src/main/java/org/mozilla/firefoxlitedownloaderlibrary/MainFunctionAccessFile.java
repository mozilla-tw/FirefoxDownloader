package org.mozilla.firefoxlitedownloaderlibrary;

import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainFunctionAccessFile {


    public void download()
    {

        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DownloadFileWorker.class).build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    public void scheduleDownloads()
    {

        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DownloadFileWorker.class).setInitialDelay(1,TimeUnit.MINUTES).build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

    }
}
