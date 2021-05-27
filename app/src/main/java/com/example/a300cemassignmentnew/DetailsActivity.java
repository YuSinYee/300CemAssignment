package com.example.a300cemassignmentnew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity {

    TextView markertxt;
    ImageView image;
    TextView txt;
    SeekBar seekBar;
    TextView playerPosition, playerDuration;
    ImageView btPlay,btPause;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        markertxt =  findViewById(R.id.marker);
        image =  (ImageView) findViewById(R.id.imageView);
        txt = (TextView) findViewById(R.id.placetxt);

        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);


        String title = getIntent().getStringExtra("title");
        markertxt.setText(title);

        if( title.equals("Cultural Centre")){
            mediaPlayer = MediaPlayer.create(this,R.raw.voice_culturalcentre);
            image.setImageDrawable(getDrawable(R.drawable.culturalcentre));
            String text = "";
            try {
                InputStream is = getAssets().open("txt_culturalcentre.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt.setText(text);


        }else if(title.equals("Clock Tower")){
            mediaPlayer = MediaPlayer.create(this,R.raw.voice_clocktower);
            image.setImageDrawable(getDrawable(R.drawable.clocktower));

            String text = "";
            try {
                InputStream is = getAssets().open("txt_clocktower.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt.setText(text);

        }else if(title.equals("Museum Of Art")){
            image.setImageDrawable(getDrawable(R.drawable.museum_of_art));
            mediaPlayer = MediaPlayer.create(this,R.raw.voice_museum_of_art);

            String text = "";
            try {
                InputStream is = getAssets().open("txt_museum_of_art.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt.setText(text);
        }else if(title.equals("Chung King Mansions")){
            image.setImageDrawable(getDrawable(R.drawable.chungking));
            mediaPlayer = MediaPlayer.create(this,R.raw.voice_chungking);

            String text = "";
            try {
                InputStream is = getAssets().open("txt_chungking.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt.setText(text);
        }





        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        int duration = mediaPlayer.getDuration();
        String sDuration = converFormat(duration);
        playerDuration.setText(sDuration);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlay.setVisibility(View.GONE);
                btPause.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                handler.postDelayed(runnable,0);
            }
        });
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                playerPosition.setText(converFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                mediaPlayer.seekTo(0);

            }
        });

    }



    @SuppressLint("DefaultLocale")
    private String converFormat(int duration) {
        return String.format("%02d:%02d"
                , TimeUnit.MILLISECONDS.toMinutes(duration)
                ,TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }


}