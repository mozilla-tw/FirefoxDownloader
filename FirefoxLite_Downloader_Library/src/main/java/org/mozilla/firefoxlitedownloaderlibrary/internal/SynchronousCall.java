package org.mozilla.firefoxlitedownloaderlibrary.internal;

import org.mozilla.firefoxlitedownloaderlibrary.Response;
import org.mozilla.firefoxlitedownloaderlibrary.request.DownloadRequest;

public class SynchronousCall {

    public final DownloadRequest request;

    public SynchronousCall(DownloadRequest request) {
        this.request = request;
    }

    public Response execute() {
        DownloadTask downloadTask = DownloadTask.create(request);
        return downloadTask.run();
    }

}
