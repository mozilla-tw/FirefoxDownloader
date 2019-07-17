
package org.mozilla.FirefoxLite.firefoxdownloader;

import android.app.Application;

import org.mozilla.firefoxlitedownloaderlibrary.MozzDownloader;
import org.mozilla.firefoxlitedownloaderlibrary.MozzDownloaderConfig;



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
