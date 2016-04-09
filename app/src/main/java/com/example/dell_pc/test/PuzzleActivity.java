package com.example.dell_pc.test;

/**
 * Created by Administrator on 2016-3-21.
 */

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PuzzleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        int length = width > height ? height : width;
        int pieceNum = 5;
        length = (int) (length * 0.9);
        length = length + (pieceNum - length % pieceNum);

        ImageView view = (ImageView) findViewById(R.id.imageView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getLayoutParams());
        lp.setMargins((width - length) / 2, (height - length) / 2, 0, 0);
        lp.height = length;
        lp.width = length;
        view.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(view.getLayoutParams());
        TextView v = (TextView) findViewById(R.id.timer);
        lp2.setMargins((width - length) / 2, (height - length) / 2 - 150, 0, 0);
        lp2.height = 100;
        lp2.width = length;
        v.setLayoutParams(lp2);
        v.getBackground().setAlpha(77);
        v.setTextSize(80);
        v.setText(0 + ":" + 0 + ":" + 0);
        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/miao.ttf");//根据路径得到Typeface
        v.setTypeface(tf);//设置字体
        Drawable[] drawable = v.getCompoundDrawables();
        // 数组下表0~3,依次是:左上右下
        drawable[0].setBounds(20, 0, 100, 80);
        v.setCompoundDrawables(drawable[0], drawable[1], drawable[2],
                drawable[3]);
    }
}
