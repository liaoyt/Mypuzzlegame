package com.example.dell_pc.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.dell_pc.test.View.MySurfaceView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016-4-13.
 */
public class PuzzleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new MySurfaceView(this));
        setContentView(R.layout.second_layout);

        Intent intent=getIntent();
        int mode=intent.getIntExtra("mode", 0);
        int width =intent.getIntExtra("width", 490);
        int height =intent.getIntExtra("height",960);
        int length=intent.getIntExtra("length",510);
        int pieceNum=intent.getIntExtra("pieceNum",3);
        float offX=intent.getFloatExtra("offX",0);
        float offY=intent.getFloatExtra("offY",0);

        ImageView view=(ImageView)findViewById(R.id.imageBlock);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getLayoutParams());
        lp.setMargins((width-length)/2-5,(height-length)/2-5,0, 0);
        lp.height=length+10;
        lp.width=length+10;
        view.setLayoutParams(lp);

        MySurfaceView v=(MySurfaceView) findViewById(R.id.puzzles);
        v.setZOrderOnTop(true);
        v.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        if(mode==0){
            String bmpDir=intent.getStringExtra("bitmap");
            Bitmap bmp= getImageFromAssetsFile(bmpDir);
            bmp=Bitmap.createScaledBitmap(bmp,length,length,true);
            v.ini(pieceNum,length,bmp,offX,offY);

            /*Bitmap bmp= BitmapFactory.decodeResource(getResources(),bmpId);
            bmp=Bitmap.createScaledBitmap(bmp,length,length,true);
            v.ini(pieceNum,length,bmp,offX,offY);*/
        }
        else if(mode==1){
            Uri imageUri=intent.getData();
            try{
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                bmp=Bitmap.createScaledBitmap(bmp,length,length,true);
                v.ini(pieceNum, length, bmp, offX, offY);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    private Bitmap getImageFromAssetsFile(String fileName)
    {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }
}
