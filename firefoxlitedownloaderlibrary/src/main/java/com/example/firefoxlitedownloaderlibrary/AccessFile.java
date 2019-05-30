package com.example.firefoxlitedownloaderlibrary;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class AccessFile {


    public void download()
    {

        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DownloadFileWorker.class)
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }
}
