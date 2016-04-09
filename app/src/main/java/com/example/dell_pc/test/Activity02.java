package com.example.dell_pc.test;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Activity02 extends AppCompatActivity {
    ImageButton back;
    Button mbutton;
    private  boolean playingOrNot = false;
    public MediaPlayer mediaplayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02);

        mbutton = (Button)findViewById(R.id.mbutton);
        mbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (playingOrNot) {
                    playingOrNot = false;
                    mediaplayer.stop();
                    mediaplayer.release();
                    Toast toast = Toast.makeText(Activity02.this, "音乐已关闭", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    playingOrNot = true;
                    mediaplayer = MediaPlayer.create(Activity02.this, R.raw.test);
                    mediaplayer.setLooping(true);
                    mediaplayer.start();
                    Toast toast = Toast.makeText(Activity02.this, "音乐已开启", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        back = (ImageButton)findViewById(R.id.backbutton);
        back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity02.this, MainActivity.class);
                startActivity(intent);
                Activity02.this.finish();
            }
        });
    }

}
