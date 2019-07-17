package org.mozilla.firefoxlitedownloaderlibrary;

import android.content.Context;



public interface OnDownloadListener {

    void onDownloadComplete();

    void onError(Error error);

}
