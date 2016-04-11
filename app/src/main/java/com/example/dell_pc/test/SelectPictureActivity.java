package com.example.dell_pc.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * Created by liaoyt on 16-4-10.
 */
public class SelectPictureActivity extends Activity implements View.OnClickListener {

    private ImageView iv;
    private ParcelFileDescriptor mInputPFD;
    private Button start;
    private Button level;
    private Button timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);

        iv = (ImageView)findViewById(R.id.selected_img);

        Intent itt = getIntent();
        int mode = itt.getIntExtra("mode", 0);
        switch (mode){
            case 0:
                int pic_num = itt.getIntExtra("position", 0);
                Picasso.with(this).load(R.drawable.pic1+pic_num).into(iv);
                break;
            case 1:
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



        File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/cpm.jpg");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");// 截取图片
        intent.putExtra("aspectX", 1);//
        intent.putExtra("aspectY", 1);// 表示x/y方向上的比例,在此设置为1:2
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("outputFormat", "JPEG");

        startActivityForResult(intent, 0x3001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x3001 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            try {
                mInputPFD = getContentResolver().openFileDescriptor(selectedImage, "r");
            } catch (FileNotFoundException e){
                e.printStackTrace();
                return;
            }

            FileDescriptor fd = mInputPFD.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);
            iv.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(SelectPictureActivity.this, PuzzleActivity.class);
                startActivity(intent);
                break;
            case R.id.level:
                break;
            case R.id.timer:
                break;
        }
    }
}
