package org.mozilla.firefox.firefoxlitedownloader;

import android.Manifest;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.MozzDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.sample.utils.Utils;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private int STORAGE_PERMISSIONS_CODE=1;
    private static String dirPath;

    final String URL3 = "https://media.giphy.com/media/Bk0CW5frw4qfS/giphy.gif";
    final String URL14 = "http://www.appsapk.com/downloading/latest/WeChat-6.5.7.apk";
    final String URL1 = "http://mattmahoney.net/dc/enwik8.pmd";
    final String URL10 = "http://www.appsapk.com/downloading/latest/Emoji%20Flashlight%20-%20Brightest%20Flashlight%202018-2.0.1.apk";
    final String URL5 = "http://www.appsapk.com/downloading/latest/Screen%20Recorder-7.7.apk";
    final String URL6 = "http://www.appsapk.com/downloading/latest/Call%20Recorder%20-%20Automatic%20Call%20Recorder-1.6.0.apk";
    final String URL15 = "http://www.appsapk.com/downloading/latest/Sound%20Profile%20(+%20volume%20scheduler)-5.25.apk";
    final String URL8 = "http://www.appsapk.com/downloading/latest/Evernote%20-%20stay%20organized.-7.9.7.apk";
    final String URL9 = "http://www.appsapk.com/downloading/latest/UC-Browser.apk";
    final String URL2 = "http://www.appsapk.com/downloading/latest/Barcode%20Scanner-1.2.apk";
    final String URL11 = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_640x360.m4v";
    final String URL13 = "http://www2.sdfi.edu.cn/netclass/jiaoan/englit/download/Harry%20Potter%20and%20the%20Sorcerer's%20Stone.pdf";
    final String URL4 = "https://media.giphy.com/media/Bk0CW5frw4qfS/giphy.gif";
    final String URL7 = "http://techslides.com/demos/sample-videos/small.mp4";
    final String URL12 = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_10mb.mp4";

    Button buttonOne, buttonTwo, buttonThree, buttonFour,
            buttonFive, buttonSix, buttonSeven, buttonEight,
            buttonNine, buttonTen, buttonEleven, buttonTwelve,
            buttonThirteen, buttonFourteen, buttonFifteen,
            buttonCancelOne, buttonCancelTwo, buttonCancelThree,
            buttonCancelFour, buttonCancelFive, buttonCancelSix,
            buttonCancelSeven, buttonCancelEight, buttonCancelNine,
            buttonCancelTen, buttonCancelEleven, buttonCancelTwelve,
            buttonCancelThirteen, buttonCancelFourteen, buttonCancelFifteen
            ,buttonScheduleOne;


    Button textViewProgressOne;
    Button textViewProgressTwo;
    TextView  textViewProgressThree,
            textViewProgressFour, textViewProgressFive, textViewProgressSix,
            textViewProgressSeven, textViewProgressEight, textViewProgressNine,
            textViewProgressTen, textViewProgressEleven, textViewProgressTwelve,
            textViewProgressThirteen, textViewProgressFourteen, textViewProgressFifteen;

    ProgressBar progressBarOne, progressBarTwo, progressBarThree,
            progressBarFour, progressBarFive, progressBarSix,
            progressBarSeven, progressBarEight, progressBarNine,
            progressBarTen, progressBarEleven, progressBarTwelve,
            progressBarThirteen, progressBarFourteen, progressBarFifteen;

    int downloadIdOne, downloadIdTwo, downloadIdThree, downloadIdFour,
            downloadIdFive, downloadIdSix, downloadIdSeven,
            downloadIdEight, downloadIdNine, downloadIdTen,
            downloadIdEleven, downloadIdTwelve, downloadIdThirteen,
            downloadIdFourteen, downloadIdFifteen;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"You have already been granted the permissions",Toast.LENGTH_SHORT).show();
        }
        else{
            requeststoragepermission();
        }
        mainActivity=this;
        dirPath = Utils.getRootDirPath(getApplicationContext());

        init();

        onClickListenerOne();
        // onClickListenerTwo();
        //onClickListenerThree();
        //onClickListenerFour();
        //onClickListenerFive();
        //onClickListenerSix();
        //onClickListenerSeven();
        //onClickListenerEight();
        //onClickListenerNine();
        //onClickListenerTen();
        //onClickListenerEleven();
        //onClickListenerTwelve();
        //onClickListenerThirteen();
        //onClickListenerFourteen();
        //onClickListenerFifteen();
    }

    private void requeststoragepermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission Needed").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSIONS_CODE);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(STORAGE_PERMISSIONS_CODE==requestCode){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"PERMISSION DENIES",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);
        buttonThree = findViewById(R.id.buttonThree);
        buttonFour = findViewById(R.id.buttonFour);
        buttonFive = findViewById(R.id.buttonFive);
        buttonSix = findViewById(R.id.buttonSix);
        buttonSeven = findViewById(R.id.buttonSeven);
        buttonEight = findViewById(R.id.buttonEight);
        buttonNine = findViewById(R.id.buttonNine);
        buttonTen = findViewById(R.id.buttonTen);
        buttonEleven = findViewById(R.id.buttonEleven);
        buttonTwelve = findViewById(R.id.buttonTwelve);
        buttonThirteen = findViewById(R.id.buttonThirteen);
        buttonFourteen = findViewById(R.id.buttonFourteen);
        buttonFifteen = findViewById(R.id.buttonFifteen);

        buttonCancelOne = findViewById(R.id.buttonCancelOne);
        buttonCancelTwo = findViewById(R.id.buttonCancelTwo);
        buttonCancelThree = findViewById(R.id.buttonCancelThree);
        buttonCancelFour = findViewById(R.id.buttonCancelFour);
        buttonCancelFive = findViewById(R.id.buttonCancelFive);
        buttonCancelSix = findViewById(R.id.buttonCancelSix);
        buttonCancelSeven = findViewById(R.id.buttonCancelSeven);
        buttonCancelEight = findViewById(R.id.buttonCancelEight);
        buttonCancelNine = findViewById(R.id.buttonCancelNine);
        buttonCancelTen = findViewById(R.id.buttonCancelTen);
        buttonCancelEleven = findViewById(R.id.buttonCancelEleven);
        buttonCancelTwelve = findViewById(R.id.buttonCancelTwelve);
        buttonCancelThirteen = findViewById(R.id.buttonCancelThirteen);
        buttonCancelFourteen = findViewById(R.id.buttonCancelFourteen);
        buttonCancelFifteen = findViewById(R.id.buttonCancelFifteen);

        buttonScheduleOne=findViewById(R.id.schedule);

        textViewProgressOne = findViewById(R.id.textViewProgressOne);
        textViewProgressTwo = findViewById(R.id.textViewProgressTwo);
        textViewProgressThree = findViewById(R.id.textViewProgressThree);
        textViewProgressFour = findViewById(R.id.textViewProgressFour);
        textViewProgressFive = findViewById(R.id.textViewProgressFive);
        textViewProgressSix = findViewById(R.id.textViewProgressSix);
        textViewProgressSeven = findViewById(R.id.textViewProgressSeven);
        textViewProgressEight = findViewById(R.id.textViewProgressEight);
        textViewProgressNine = findViewById(R.id.textViewProgressNine);
        textViewProgressTen = findViewById(R.id.textViewProgressTen);
        textViewProgressEleven = findViewById(R.id.textViewProgressEleven);
        textViewProgressTwelve = findViewById(R.id.textViewProgressTwelve);
        textViewProgressThirteen = findViewById(R.id.textViewProgressThirteen);
        textViewProgressFourteen = findViewById(R.id.textViewProgressFourteen);
        textViewProgressFifteen = findViewById(R.id.textViewProgressFifteen);

        progressBarOne = findViewById(R.id.progressBarOne);
        progressBarTwo = findViewById(R.id.progressBarTwo);
        progressBarThree = findViewById(R.id.progressBarThree);
        progressBarFour = findViewById(R.id.progressBarFour);
        progressBarFive = findViewById(R.id.progressBarFive);
        progressBarSix = findViewById(R.id.progressBarSix);
        progressBarSeven = findViewById(R.id.progressBarSeven);
        progressBarEight = findViewById(R.id.progressBarEight);
        progressBarNine = findViewById(R.id.progressBarNine);
        progressBarTen = findViewById(R.id.progressBarTen);
        progressBarEleven = findViewById(R.id.progressBarEleven);
        progressBarTwelve = findViewById(R.id.progressBarTwelve);
        progressBarThirteen = findViewById(R.id.progressBarThirteen);
        progressBarFourteen = findViewById(R.id.progressBarFourteen);
        progressBarFifteen = findViewById(R.id.progressBarFifteen);
    }
    public void displaynotification(View view){
        final NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        final int notifyid=1;
        final NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int icr;
                for(icr=0;icr<=100;icr+=5){
                    builder.setProgress(100,icr,false);
                }
            }
        });

    }
    public void onClickListenerOne() {
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jjk",MozzDownloader.getStatus(downloadIdOne)+"");
//                if (Status.RUNNING == MozzDownloader.getStatus(downloadIdOne)) {
//                    MozzDownloader.pause(downloadIdOne);
//                    return;
//                }
                if (Status.QUEUED == MozzDownloader.getStatus(downloadIdOne)) {
                    MozzDownloader.pause(downloadIdOne);
                    return;
                }

                buttonOne.setEnabled(false);
                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == MozzDownloader.getStatus(downloadIdOne)) {
                    MozzDownloader.resume(getApplicationContext(),mainActivity,downloadIdOne);
                    return;
                }

                downloadIdOne = MozzDownloader.download(URL1, dirPath, "mandys.pmd")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
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
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress( (int)progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(getApplicationContext(),mainActivity,new OnDownloadListener() {
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
                                textViewProgressOne.setText("");
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
        buttonScheduleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker=new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });
        textViewProgressOne.setOnClickListener(new View.OnClickListener() {
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
                    MozzDownloader.resume(getApplicationContext(),mainActivity,downloadIdOne);
                    return;
                }

                downloadIdOne = MozzDownloader.download(URL1, dirPath, "facebook.apk")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
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
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress( (int) progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(getApplicationContext(),mainActivity,new OnDownloadListener() {
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
                                textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                buttonOne.setEnabled(true);
                            }
                        });


            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int scheduledhours, int minute) {
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        if (currentHourIn24Format==0){
            currentHourIn24Format=24;
        }
        int diffhours=0;
        int currentminutes = rightNow.get(Calendar.MINUTE);
        if(scheduledhours>currentHourIn24Format){
            diffhours=scheduledhours-currentHourIn24Format;
        }
        else if (scheduledhours==currentHourIn24Format){
            diffhours=0;
        }
        else{
            diffhours=(24-currentHourIn24Format)+scheduledhours;
        }
        int diffminutes=Math.abs(minute-currentminutes);
        int totaldiffminutes=diffhours*60+diffminutes;
        Toast.makeText(getApplicationContext(),totaldiffminutes+"",Toast.LENGTH_SHORT).show();
        downloadIdOne = MozzDownloader.download(URL1, dirPath, "mandys.pmd")
                .build().setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        progressBarOne.setProgress( (int)progressPercent);
                        textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        progressBarOne.setIndeterminate(false);
                    }
                }).setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
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
                        textViewProgressOne.setText("");
                        downloadIdOne = 0;
                        progressBarOne.setIndeterminate(false);
                    }
                }).schedule(getApplicationContext(),mainActivity,totaldiffminutes);

    }


}

