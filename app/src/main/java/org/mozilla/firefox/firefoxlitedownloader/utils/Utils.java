package org.mozilla.firefox.firefoxlitedownloader.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.util.Locale;


public final class Utils {

    private Utils() {
        // no instance
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("manny","body");
            return baseDir;
        } else {
            Log.d("mommy","sosi");
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

}
