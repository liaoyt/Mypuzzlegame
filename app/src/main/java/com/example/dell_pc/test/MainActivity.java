package com.example.dell_pc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity01.class);
                startActivity(intent);
            }
        });

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity02.class);
                startActivity(intent);
            }
        });

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PictureListActivity.class);
                startActivity(intent);
            }
        });

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v)
            {
                //跳至排行榜！
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Ranking.class);
                startActivity(intent);
            }
        });

    }
}
