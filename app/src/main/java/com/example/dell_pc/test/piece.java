package com.example.dell_pc.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dell_pc.test.model.PieceEdge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class piece extends AppCompatActivity {
    public int times = 200;
    public int timems = 00;
    public int finish = 0;
    public int piece;
    public Bitmap source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        int length = intent.getIntExtra("length", 510);
        piece = intent.getIntExtra("pieceNum", 3);
        if (mode == 0) {
            int bmpID = intent.getIntExtra("bitmap", R.drawable.archer);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),bmpID);
            source = Bitmap.createScaledBitmap(bmp, length, length, true);
        } else if (mode == 1) {
            Uri imageUri = intent.getData();
            try {
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                source = Bitmap.createScaledBitmap(bmp, length, length, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Myview myview = new Myview(this);
        super.setContentView(myview);
        final TextView timecount = new TextView(this);
        timecount.setText("" + times + ":" + timems);
        timecount.setTextSize(33);
        FrameLayout.LayoutParams timeposition = new FrameLayout.LayoutParams(800, 800);
        timeposition.setMargins(500, 130, 0, 0);
        super.addContentView(timecount, timeposition);
        final Handler finishhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 75533) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(piece.this);
                    builder.setTitle("游戏结束");
                    builder.setMessage("你的得分：" + times);
                    builder.show();
                }
            }
        };
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 75532 && finish == 0) {
                    timems--;
                    if (timems < 0) {
                        times--;
                        timems += 10;
                    }
                    if (times >= 100)
                        timecount.setText("" + times + ":" + timems);
                    else if (times >= 10)
                        timecount.setText("0" + times + ":" + timems);
                    else if (times > 0 || timems > 0)
                        timecount.setText("00" + times + ":" + timems);
                    else {
                        timecount.setTextSize(28);
                        finish = 1;
                        timecount.setText("游戏结束");
                    }
                }
                if (finish == 1) {
                    Message msg2 = new Message();
                    msg2.what = 75533;
                    finishhandler.sendMessage(msg2);
                    finish = 2;
                }
            }

            ;
        };
        Timer mtimer = new Timer(true);
        TimerTask mtimertask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 75532;
                handler.sendMessage(msg);
            }
        };
        mtimer.schedule(mtimertask, 0, 100);
    }
        //new Thread(new ThreadShow()).start();

    class Myview extends View {
        Path table;
        Path[] path;
        PieceEdge[] rawEdge;
        PieceEdge[] colEdge;
        LinkedList<Integer> list = new LinkedList<Integer>();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        float wwidth = wm.getDefaultDisplay().getWidth();
        float wheight = wm.getDefaultDisplay().getHeight();
        int width = (int)(wwidth*0.9);
        int height = width;
        Bitmap background;
        InputStream bcakground2;
        Bitmap clockimg = BitmapFactory.decodeResource(this.getResources(),R.drawable.clock);
        Bitmap timebackground = BitmapFactory.decodeResource(this.getResources(),R.drawable.time);
        //自由拼接
        int[] x = new int[piece*piece];
        int[] y = new int[piece*piece];
        boolean[] f = new boolean[piece*piece];
        int[] positionx = new int[piece*piece];
        int[] positiony = new int[piece*piece];
        int wid = 0;
        int hei = 0;
        int index = 0;
        double ox=0,nx=0,oy=0,ny=0;
        int index2 = -1;
        int sx = (int)(wwidth-width)/2;
        int sy = (int)(wheight-height)/3;
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        //用与记录拼图完成
        int count = 0;

        public Myview(Context context) {
            super(context);
            width = width-width%piece;
            height = height-height%piece;
            wid = width/piece;
            hei = height / piece;
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(24);
            source = Bitmap.createScaledBitmap(source, width, height, true);
            iniPath();
            Shader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint2.setAntiAlias(true);
            paint2.setColor(Color.BLACK);
            paint2.setStyle(Paint.Style.STROKE);
            for(int i=0;i<piece;i++){
                for(int t=0;t<piece;t++){
                    positionx[index] =  t*wid+sx;
                    positiony[index] = i*hei+sy;
                    x[index] = (int) (Math.random() * width);
                    y[index] = (int) (Math.random() * (wheight-width)/2-(width/piece)+(wheight-width)/2+width);
                    f[index]=false;
                    list.add(index);
                    index++;
                }
            }
            bcakground2 = getResources().openRawResource(R.raw.bg);
            BitmapFactory.Options opts=new BitmapFactory.Options();
            opts.inTempStorage = new byte[100 * 1024];
            background = BitmapFactory.decodeStream(bcakground2, null, opts);
            background = Bitmap.createScaledBitmap(background, (int) wwidth, (int) wheight, true);
            clockimg = Bitmap.createScaledBitmap(clockimg,(int)(wwidth/5),(int)(wwidth/5),true);
            timebackground = Bitmap.createScaledBitmap(timebackground, (int) (wwidth / 5 * 2), (int) (wwidth / 5 / 2), true);
        }

        @Override
        public void onDraw(Canvas canvas){
            //canvas.drawColor(Color.GRAY);
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(clockimg, (int) (wwidth / 5), sy - (int) (wwidth / 5) - 10, null);
            canvas.drawBitmap(timebackground,(int)(wwidth/5*2),sy-(int)(wwidth/5/4*3)-10,null);

            Paint whitePaint=new Paint();
            whitePaint.setColor(Color.WHITE);
            canvas.drawPath(table,whitePaint);
            canvas.drawPath(table,paint2);
            for(int i=0;i<list.size();i++){
                int t= list.get(i);
                canvas.translate(x[t],y[t]);
                canvas.translate(-t%piece*wid,-t/piece*hei);
                canvas.drawPath(path[t % piece*piece+t/piece], paint);
                canvas.drawPath(path[t%piece*piece+t/piece], paint2);
                canvas.translate(-x[t],-y[t]);
                canvas.translate(t%piece*wid,t/piece*hei);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            nx = event.getX();
            ny = event.getY();
            if(finish!=0)
                return false;
            int action = event.getAction();
            if(action==MotionEvent.ACTION_DOWN){
                ox = nx;
                oy = ny;
                index2 = -1;
                for(int i=piece*piece-1;i>=0;i--){
                    int t = list.get(i);
                    if(f[t] == false && (nx - x[t]) > 0 && (nx - x[t] < wid) && (ny - y[t]) > 0 && (ny - y[t]) < hei){
                        index2 = t;
                        break;
                    }
                }
                if (index2 < 0)
                    return false;
                else{
                    list.remove(list.indexOf(index2));
                    list.addLast(index2);
                }
                //}
            }
            if(action==MotionEvent.ACTION_MOVE){
                if(index2<0)
                    return false;
                x[index2]+=(int)(nx-ox);
                y[index2]+=(int)(ny-oy);
                ox=nx;
                oy=ny;
            }
            if(action==MotionEvent.ACTION_UP){
                if(index2<0)
                    return false;
                if(Math.abs(x[index2]-positionx[index2])<=25 && Math.abs(y[index2]-positiony[index2])<=25){
                    x[index2]=positionx[index2];
                    y[index2]=positiony[index2];
                    f[index2]=true;
                    count++;
                    list.remove(list.indexOf(index2));
                    list.addFirst(index2);
                    if(count==piece*piece){
                        finish = 1;
                    }
                }
            }
            invalidate();
            return true;
        }

        private Path createPiecePath(int min,int offX,int offY,PieceEdge eTop,PieceEdge eRight,PieceEdge eBottom,PieceEdge eLeft){
            Path p = new Path();
            p.moveTo(offX, offY);
            if(eTop==null)
                p.lineTo(offX+min,offY);
            else {
                for (int i = 1; i < 6; i++)
                    p.cubicTo(eTop.mEdge[i].ctrlA.x + offX, eTop.mEdge[i].ctrlA.y + offY,
                            eTop.mEdge[i].ctrlB.x + offX, eTop.mEdge[i].ctrlB.y + offY,
                            eTop.mEdge[i].p.x + offX, eTop.mEdge[i].p.y + offY);
            }
            if(eRight==null)
                p.lineTo(offX+min,offY+min);
            else {
                for (int i = 1; i < 6; i++)
                    p.cubicTo(eRight.mEdge[i].ctrlA.x + offX + min, eRight.mEdge[i].ctrlA.y + offY,
                            eRight.mEdge[i].ctrlB.x + offX + min, eRight.mEdge[i].ctrlB.y + offY,
                            eRight.mEdge[i].p.x + offX + min, eRight.mEdge[i].p.y + offY);
            }
            if(eBottom==null)
                p.lineTo(offX,offY+min);
            else {
                for (int i = 5; i >= 1; i--)
                    p.cubicTo(eBottom.mEdge[i].ctrlB.x + offX, eBottom.mEdge[i].ctrlB.y + offY + min,
                            eBottom.mEdge[i].ctrlA.x + offX, eBottom.mEdge[i].ctrlA.y + offY + min,
                            eBottom.mEdge[i - 1].p.x + offX, eBottom.mEdge[i - 1].p.y + offY + min);
            }
            if(eLeft==null)
                p.lineTo(offX,offY);
            else {
                for (int i = 5; i >= 1; i--)
                    p.cubicTo(eLeft.mEdge[i].ctrlB.x + offX, eLeft.mEdge[i].ctrlB.y + offY,
                            eLeft.mEdge[i].ctrlA.x + offX, eLeft.mEdge[i].ctrlA.y + offY,
                            eLeft.mEdge[i - 1].p.x + offX, eLeft.mEdge[i - 1].p.y + offY);
            }
            return p;
        }

        private void iniPath() {
            table=new Path();
            path=new Path[piece*piece];
            rawEdge = new PieceEdge[piece*piece];
            colEdge = new PieceEdge[piece*piece];
            PieceEdge e=new PieceEdge();
            for(int i=0;i<piece*piece;i++) {
                rawEdge[i]=e.getRawEdge((float)Math.random(),wid);
                colEdge[i]=e.getColEdge((float) Math.random(), wid);
            }

            PieceEdge eTop;
            PieceEdge eRight;
            PieceEdge eBottom;
            PieceEdge eLeft;
            for(int i=0;i<piece;i++){
                for(int j=0;j<piece;j++) {
//                bmp[i*numOfPiece+j] = createPieceImage(source,tableWidth/numOfPiece,i*tableWidth/numOfPiece,j*tableWidth/numOfPiece);
                    eTop=j==0?null:rawEdge[i*piece+j-1];
                    eRight=i==(piece-1)?null:colEdge[i*piece+j];
                    eBottom=j==(piece-1)?null:rawEdge[i*piece+j];
                    eLeft=i==0?null:colEdge[i*piece+j-piece];
                    path[i*piece+j]=createPiecePath(wid,i*wid,j*wid,eTop,eRight,eBottom,eLeft);
                    table.addPath(path[i*piece+j],sx,sy);
                }
            }
        }
    }
}