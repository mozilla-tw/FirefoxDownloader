package org.mozilla.firefoxlitedownloaderlibrary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataHelper {

    private MediatorLiveData<Integer> _percent = new MediatorLiveData<>();
    private static LiveDataHelper liveDataHelper;

    private LiveDataHelper() {
    }

    synchronized public static LiveDataHelper getInstance() {

        if (liveDataHelper == null)
            liveDataHelper = new LiveDataHelper();
        return liveDataHelper;

    }
    public void updatePercentage(int percentage) {

        _percent.postValue(percentage);

    }
    public LiveData<Integer> observePercentage()
    {
        return _percent;
    }
}