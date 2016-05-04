package com.example.dell_pc.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by liaoyt on 16-4-10.
 */
public class SelectPictureActivity extends Activity implements View.OnClickListener {

    private ImageView iv;
    private Button start;
    private Button level;
    private Button timer;
    private String bmpDir;
    private Uri imageUri;
    private int modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);

        iv = (ImageView)findViewById(R.id.selected_img);

        Intent itt = getIntent();
        int mode = itt.getIntExtra("mode", 0);
        switch (mode){
            case 0:
                modes=0;
                int pic_num = itt.getIntExtra("position", 0);
                bmpDir="puzzle/pic"+(pic_num+1)+".jpg";
                Picasso.with(this).load("file:///android_asset/puzzle/pic"+(pic_num+1)+".jpg").into(iv);
                break;
            case 1:
                modes=1;
                getLocalPic();
                break;
            case 2:
                break;
        }

        start = (Button)findViewById(R.id.start);
        level = (Button)findViewById(R.id.level);
        timer = (Button)findViewById(R.id.timer);
        start.setOnClickListener(this);
    }

    private void getLocalPic(){
       /* File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/cpm.jpg");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");// 截取图片
        intent.putExtra("aspectX", 1);//
        intent.putExtra("aspectY", 1);// 表示x/y方向上的比例,在此设置为1:2
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("outputFormat", "JPEG");*/
        File outputImage =  new File(Environment.getExternalStorageDirectory().getPath() + "/cpm.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale",true);
        intent.putExtra("aspectX", 1);//
        intent.putExtra("aspectY", 1);// 表示x/y方向上的比例,在此设置为1:2
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, 0x24);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x24 && resultCode == RESULT_OK && null != data) {
            try{
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                iv.setImageBitmap(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(SelectPictureActivity.this, PuzzleActivity.class);
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels;     // 屏幕宽度（像素）
                int height = metric.heightPixels;   // 屏幕高度（像素）
                int length=width>height?height:width;
                int pieceNum=5;
                length=(int)(length*0.9);
                length=length+(pieceNum-length%pieceNum);

                intent.putExtra("mode",modes);
                if(modes==1){
//                    intent.putExtra("Uri",imageUri.toString());
                    intent.setData(imageUri);
                }
                else if(modes==0){
                    intent.putExtra("bitmap",bmpDir);
                }
                intent.putExtra("width",width);
                intent.putExtra("height",height);
                intent.putExtra("length",length);
                intent.putExtra("pieceNum",pieceNum);
                intent.putExtra("offX",(float)((width-length)/2));
                intent.putExtra("offY",(float)((height-length)/2));

                startActivity(intent);
                break;
            case R.id.level:
                break;
            case R.id.timer:
                break;
        }
    }
}
