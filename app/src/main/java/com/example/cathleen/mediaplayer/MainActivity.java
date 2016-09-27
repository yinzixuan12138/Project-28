package com.example.cathleen.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myTag";
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button startResoure, pause, stop, loop;
    private TextView showLoop;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isChanging=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();

        //播放完停止
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.v(TAG,"播放完成即停止");
            }
        });


        startResoure = (Button)findViewById(R.id.startResoure);
        pause = (Button)findViewById(R.id.pause);
        stop = (Button)findViewById(R.id.stop);
        loop = (Button)findViewById(R.id.loop);
        showLoop = (TextView)findViewById(R.id.showLoop);

        loop.setEnabled(false);
        pause.setEnabled(false);
        stop.setEnabled(false);

        //开始
        startResoure.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     Log.v(TAG, "startSourse");
                     mediaPlayer.reset();

                     AssetManager assetManager = getAssets();
                     AssetFileDescriptor assetFileDescriptor =
                             assetManager.openFd("test1.mp3");
                     mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                             assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                     mediaPlayer.prepare();

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 mTimer = new Timer();
                 mTimerTask = new TimerTask() {
                     @Override
                     public void run() {
                         if(isChanging == true){
                             return;
                         }
                     }
                 };
                 mTimer.schedule(mTimerTask,0,10);
                 mediaPlayer.start();
                 pause.setEnabled(true);
                 stop.setEnabled(true);
                 loop.setEnabled(true);

             }
         });
        //暂停播放
         pause.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mediaPlayer.isPlaying()) {
                     pause.setText("播放");
                     mediaPlayer.pause();
                 } else {
                     pause.setText("暂停");
                     mediaPlayer.start();
                 }
             }
         });
        //停止播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
            }
        });
        //循环播放
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Looping");
                boolean loop = mediaPlayer.isLooping();
                mediaPlayer.setLooping(!loop);
                if (!loop)
                    showLoop.setText("循环播放");
                else
                    showLoop.setText("一次播放");
            }
        });

    }


}
