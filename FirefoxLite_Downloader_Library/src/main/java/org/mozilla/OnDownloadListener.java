
package org.mozilla;



public interface OnDownloadListener {

    void onDownloadComplete();

    void onError(Error error);

}
