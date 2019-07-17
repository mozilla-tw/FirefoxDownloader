package org.mozilla.FirefoxLite.firefoxdownloader;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.mozilla.FirefoxLite.firefoxdownloader.utils.Utils;
import org.mozilla.firefoxlitedownloaderlibrary.Error;
import org.mozilla.firefoxlitedownloaderlibrary.MainFunctionAccessFile;
import org.mozilla.firefoxlitedownloaderlibrary.LiveDataHelper;
import org.mozilla.firefoxlitedownloaderlibrary.MozzDownloader;
import org.mozilla.firefoxlitedownloaderlibrary.OnCancelListener;
import org.mozilla.firefoxlitedownloaderlibrary.OnDownloadListener;
import org.mozilla.firefoxlitedownloaderlibrary.OnPauseListener;
import org.mozilla.firefoxlitedownloaderlibrary.OnProgressListener;
import org.mozilla.firefoxlitedownloaderlibrary.OnStartOrResumeListener;
import org.mozilla.firefoxlitedownloaderlibrary.Progress;
import org.mozilla.firefoxlitedownloaderlibrary.Status;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {
    private static String dirPath;
    final String URL1 = "http://www.appsapk.com/downloading/latest/Facebook-119.0.0.23.70.apk";
    final String URL2 = "http://www.appsapk.com/downloading/latest/WeChat-6.5.7.apk";
    //Button DownloadButton;
    //Button ScheduleDownloadButton;
    //ProgressBar progressBarDownload;
    Button buttonOne, buttonTwo;
    Button  buttonCancelOne, buttonCancelTwo;
    TextView textViewProgressOne, textViewProgressTwo;
    ProgressBar progressBarOne, progressBarTwo;
    int downloadIdOne, downloadIdTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dirPath = Utils.getRootDirPath(getApplicationContext());
        init();
        onClickListenerOne();
        onClickListenerTwo();
       // progressBarDownload=findViewById(R.id.progressBar);
        //DownloadButton=findViewById(R.id.btnDownload);

//        ScheduleDownloadButton=findViewById(R.id.btnSchedule);
//
//        DownloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // MainFunctionAccessFile accessFile=new MainFunctionAccessFile();
//               // accessFile.download();
//
//            }
//        });
//        ScheduleDownloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // MainFunctionAccessFile accessFile=new MainFunctionAccessFile();
//               // accessFile.scheduleDownloads();
//            }
//        });
//        LiveDataHelper.getInstance().observePercentage()
//                .observe(this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(@Nullable Integer integer) {
//                        progressBarDownload.setProgress(integer);
//                    }
//                });
    }
    private void init() {
        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);


        buttonCancelOne = findViewById(R.id.buttonCancelOne);
        buttonCancelTwo = findViewById(R.id.buttonCancelTwo);

        textViewProgressOne = findViewById(R.id.textViewProgressOne);
        textViewProgressTwo = findViewById(R.id.textViewProgressTwo);


        progressBarOne = findViewById(R.id.progressBarOne);
        progressBarTwo = findViewById(R.id.progressBarTwo);

    }

    public void onClickListenerOne() {
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Status.RUNNING == MozzDownloader.getStatus(downloadIdOne)) {
                    MozzDownloader.pause(downloadIdOne);
                    return;
                }

                buttonOne.setEnabled(false);
                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(

                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
                if (Status.PAUSED == MozzDownloader.getStatus(downloadIdOne)) {
                    MozzDownloader.resume(downloadIdOne);
                    return;
                }

                downloadIdOne = MozzDownloader.download(getApplicationContext(),URL1, dirPath, "facebook.apk")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                Log.d("fault","faulty");
                                progressBarOne.setIndeterminate(false);
                                buttonOne.setEnabled(true);
                                buttonOne.setText(R.string.pause);
                                buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                buttonOne.setText(R.string.resume);
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                buttonOne.setText(R.string.start);
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setProgress(0);
                                //textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress((int) progressPercent);
                                //textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                buttonOne.setEnabled(false);
                                buttonCancelOne.setEnabled(false);
                                buttonOne.setText(R.string.completed);
                            }


                            @Override
                            public void onError(Error error) {
                                buttonOne.setText(R.string.start);
                                Toast.makeText(getApplicationContext(), getString(R.string.some_error_occurred) + " " + "1", Toast.LENGTH_SHORT).show();
                                //textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                buttonOne.setEnabled(true);
                            }
                        });
            }
        });

        buttonCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MozzDownloader.cancel(downloadIdOne);
            }
        });
    }

    public void onClickListenerTwo() {
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status.RUNNING == MozzDownloader.getStatus(downloadIdTwo)) {
                    MozzDownloader.pause(downloadIdTwo);
                    return;
                }

                buttonTwo.setEnabled(false);
                progressBarTwo.setIndeterminate(true);
                progressBarTwo.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == MozzDownloader.getStatus(downloadIdTwo)) {
                    MozzDownloader.resume(downloadIdTwo);
                    return;
                }
                downloadIdTwo = MozzDownloader.download(getApplicationContext(),URL2, dirPath, "wechat.apk")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                Log.d("lasting","thing");
                                progressBarTwo.setIndeterminate(false);
                                buttonTwo.setEnabled(true);
                                buttonTwo.setText(R.string.pause);
                                buttonCancelTwo.setEnabled(true);
                                buttonCancelTwo.setText(R.string.cancel);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                buttonTwo.setText(R.string.resume);
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                downloadIdTwo = 0;
                                buttonTwo.setText(R.string.start);
                                buttonCancelTwo.setEnabled(false);
                                progressBarTwo.setProgress(0);
                                textViewProgressTwo.setText("");
                                progressBarTwo.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarTwo.setProgress((int) progressPercent);
                                textViewProgressTwo.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                buttonTwo.setEnabled(false);
                                buttonCancelTwo.setEnabled(false);
                                buttonTwo.setText(R.string.completed);
                            }

                            @Override
                            public void onError(Error error) {
                                buttonTwo.setText(R.string.start);
                                Toast.makeText(getApplicationContext(), getString(R.string.some_error_occurred) + " " + "2", Toast.LENGTH_SHORT).show();
                                textViewProgressTwo.setText("");
                                progressBarTwo.setProgress(0);
                                downloadIdTwo = 0;
                                buttonCancelTwo.setEnabled(false);
                                progressBarTwo.setIndeterminate(false);
                                buttonTwo.setEnabled(true);
                            }
                        });
            }
        });

        buttonCancelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MozzDownloader.cancel(downloadIdTwo);
            }
        });
    }




}
