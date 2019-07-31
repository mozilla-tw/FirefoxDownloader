package org.mozilla.firefox.firefoxlitedownloader;


import android.app.Application;

import com.downloader.MozzDownloader;
import com.downloader.MozzDownloaderConfig;

public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MozzDownloaderConfig config = MozzDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        MozzDownloader.initialize(this, config);
    }

}
