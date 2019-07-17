
package org.mozilla.firefoxlitedownloaderlibrary.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.mozilla.firefoxlitedownloaderlibrary.OnProgressListener;
import org.mozilla.firefoxlitedownloaderlibrary.Response;
import org.mozilla.firefoxlitedownloaderlibrary.Status;
import org.mozilla.firefoxlitedownloaderlibrary.request.DownloadRequest;


import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


public class
DownloadRequestQueue    {
    public LifecycleRegistry mLifecycleRegistry;
    public  Context context;
    String abc;
    OnProgressListener onProgressListener1;
    public static DownloadRequestQueue instance;
    public final Map<Integer, DownloadRequest> currentRequestMap;
    public final AtomicInteger sequenceGenerator;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mLifecycleRegistry=new LifecycleRegistry(this);
//        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mLifecycleRegistry=new LifecycleRegistry(this);
//        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
//    }

//    @NonNull
//    @Override
//    public Lifecycle getLifecycle() {
//        mLifecycleRegistry=new LifecycleRegistry(this);
//        Log.d("uu",mLifecycleRegistry+"");
//        return mLifecycleRegistry;
//    }

    public   DownloadRequestQueue(Context context) {
        this.context = context;

        currentRequestMap = new ConcurrentHashMap<>();
        sequenceGenerator = new AtomicInteger();

    }

    public static void initialize(Context context) {
        getInstance(context);


    }

    public static DownloadRequestQueue getInstance(Context context) {
        if (instance == null) {
            synchronized (DownloadRequestQueue.class) {
                if (instance == null) {

                    instance = new DownloadRequestQueue(context);
                }
            }
        }
        return instance;
    }

    private int getSequenceNumber() {
        return sequenceGenerator.incrementAndGet();
    }

    public void pause(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            request.setStatus(Status.PAUSED);
        }
    }

    public void resume(int downloadId,LifecycleOwner lifecycleOwner) {
        final Response response = new Response();
        final DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {

            request.setStatus(Status.QUEUED);
            request.deliverStartEvent();
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            Log.d("fff",request.getOnProgressListener()+"");
            String json = gson.toJson(request);
            prefsEditor.putString("SerializableObject", json);
            prefsEditor.commit();
            Data data = new Data.Builder().putInt("ID", downloadId).build();
            final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
            WorkManager.getInstance().enqueue(workRequest);
            WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId()).observe(lifecycleOwner, new Observer<WorkInfo>() {
                @Override
                public void onChanged(@Nullable WorkInfo workInfo) {
                    if (workInfo != null) {
                        Log.d("this","dip");
                        if (workInfo.getState().isFinished()) {
                            Data data = workInfo.getOutputData();
                            String output = data.getString("Response");
                            if (output.equals(Status.CANCELLED + "")) {
                                response.setCancelled(true);
                            }
                            if (output.equals(Status.PAUSED + "")) {
                                response.setPaused(true);
                            }
                            if (output.equals("abc")) {
                                Error error = new Error();
                                //error.setConnectionError(true);
                                //error.setConnectionException(new Exception());
                                //response.setError(error);
                            }
                            if (output.equals("def")) {
                                Error error = new Error();
                                //error.setServerError(true);
                                //response.setError(error);
                            }
                        }
                        if (response.isSuccessful()) {
                            request.deliverSuccess();
                        } else if (response.isPaused()) {
                            request.deliverPauseEvent();
                        } else if (response.getError() != null) {
                           // request.deliverError(response.getError());
                        } else if (!response.isCancelled()) {
                            request.deliverError(new Error());
                        }

                    }
                }
            });

//            request.setFuture(Core.getInstance()
//                    .getExecutorSupplier()
//                    .forDownloadTasks()
//                    .submit(new DownloadRunnable(request)));
        }
    }

    private void cancelAndRemoveFromMap(DownloadRequest request) {
        if (request != null) {
            request.cancel();
            currentRequestMap.remove(request.getDownloadId());
        }
    }

    public void cancel(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        cancelAndRemoveFromMap(request);
    }

    public void cancel(Object tag) {
        for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            if (request.getTag() instanceof String && tag instanceof String) {
                final String tempRequestTag = (String) request.getTag();
                final String tempTag = (String) tag;
                if (tempRequestTag.equals(tempTag)) {
                    cancelAndRemoveFromMap(request);
                }
            } else if (request.getTag().equals(tag)) {
                cancelAndRemoveFromMap(request);
            }
        }
    }

    public void cancelAll() {
        for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            cancelAndRemoveFromMap(request);
        }
    }

    public Status getStatus(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            return request.getStatus();
        }
        return Status.UNKNOWN;
    }

    public void addRequest(final DownloadRequest request, LifecycleOwner lifecycleOwner) {
        Log.d("did", request.getUrl() + "");
        final Response response = new Response();
        Log.d("yoii", request.getOnProgressListener()+"");
        request.setStatus(Status.QUEUED);
        abc=request.getOnProgressListener()+"";
        onProgressListener1=request.getOnProgressListener();
       // request.deliverStartEvent();
        currentRequestMap.put(request.getDownloadId(), request);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        Log.d("fff",request.getOnProgressListener()+"");
        request.setOnProgressListener(request.getOnProgressListener());
        String json = gson.toJson(request);
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        arrayList.add(1);

        prefsEditor.putString("SerializableObject", json);
        prefsEditor.commit();

        if (request != null) {
            Log.d("999", currentRequestMap.get(request.getDownloadId()).getUrl() + "");
        }
        if (request == null) {
            Log.d("none", "bro");
        }
        //request.setStatus(Status.QUEUED);
        //request.setSequenceNumber(getSequenceNumber());
        if (request != null) {
            Log.d("all", request.getUrl() + "");
        }
        if (request == null) {
            Log.d("none", "bro");
        }

        Data data = new Data.Builder().putInt("ID", request.getDownloadId()).putString("status", request.getStatus() + "").build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        WorkManager.getInstance().enqueue(workRequest);
        Log.d("boss", "pls");
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId()).observe(lifecycleOwner, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                Log.d("abd","see");
                if(workInfo==null){
                    Log.d("bug","buggy");
                }
                if (workInfo != null) {
                    Log.d("boss2", "pls2");
                    if (workInfo.getState().isFinished()) {
                        Log.d("boss3", "pls3");
                        Data data = workInfo.getOutputData();
                        String output = data.getString("Response");
                        Log.d("boss4", output);
                        if (output.equals(Status.CANCELLED + "")) {
                            response.setCancelled(true);
                        }
                        if (output.equals(Status.PAUSED + "")) {
                            response.setPaused(true);
                        }
                        if (output.equals("succ")) {
                            response.setSuccessful(true);
                        }
                        if (output.equals("abc")) {
                            Error error = new Error();
                            //error.setConnectionError(true);
                            //error.setConnectionException(new Exception());
                            //response.setError(error);
                        }
                        if (output.equals("def")) {
                            Error error = new Error();
                            //error.setServerError(true);
                            //response.setError(error);
                        }
                    }
                    if (response.isSuccessful()) {
                        request.deliverSuccess();
                    } else if (response.isPaused()) {
                        request.deliverPauseEvent();
                    } else if (response.getError() != null) {
                        //request.deliverError(response.getError());
                    } else if (!response.isCancelled()) {
                        request.deliverError(new Error());
                    }

                }
            }
        });
        //request.deliverStartEvent();
        //response.setPaused(true);
//        if (response.isSuccessful()) {
//            request.deliverSuccess();
          //  if (response.isPaused()) {
           // request.deliverPauseEvent();
       // }
        //else if (response.getError() != null) {
//            request.deliverError(response.getError());
//        } else if (!response.isCancelled()) {
//            request.deliverError(new Error());
//        }


        Log.d("steve","smith");
//        if (response.isSuccessful()) {
//            request.deliverSuccess();
//        } else if (response.isPaused()) {
//            request.deliverPauseEvent();
//        } else if (response.getError() != null) {
//            request.deliverError(response.getError());
//        } else if (!response.isCancelled()) {
//            request.deliverError(new Error());
//        }
//        request.setFuture(Core.getInstance()
//                .getExecutorSupplier()
//                .forDownloadTasks()
//                .submit(new DownloadRunnable(request)));
    }

    public void finish(DownloadRequest request) {
        currentRequestMap.remove(request.getDownloadId());
    }



}
