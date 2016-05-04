package com.example.dell_pc.test.View;

/**
 * Created by Administrator on 2016-4-13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.dell_pc.test.model.PieceEdge;
import com.example.dell_pc.test.model.PieceGroup;

//Callback接口用于SurfaceHolder 对SurfaceView 的状态进行监听
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // 用于控制SurfaceView 的大小、格式等，并且主要用于监听SurfaceView 的状态
    private int numOfPiece;
    private int tableWidth;
    private Bitmap source;
    private Path[] path;
    private PieceGroup group;
    private SurfaceHolder sfh;
    // 声明一个画笔
    private Paint paint;
    // 声明一个线程
    private Thread th;
    // 线程消亡的标识符
    private boolean flag;
    // 声明一个画布
    private Canvas canvas;
    private float offX;
    private float offY;
    private PieceEdge[] rawEdge;
    private PieceEdge[] colEdge;
    private Path table;
    private boolean isReady;


    /**
     * SurfaceView 初始化函数
     *
     * @param context
     */
    public MySurfaceView(Context context,AttributeSet attrs) {
        super(context,attrs);
        // 实例SurfaceView
        sfh = this.getHolder();
        // 为SurfaceView添加状态监听
        sfh.addCallback(this);
        // 实例一个画笔
        paint = new Paint();
        // 设置字体大小
        paint.setTextSize(20);
        // 设置画笔的颜色
        paint.setColor(Color.WHITE);
        // 设置焦点
        setFocusable(true);
    }

    /**
     * SurfaceView 视图创建，响应此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isReady=false;
        flag = true;
        // 实例线程
        th = new Thread(this);
        // 启动线程
        th.start();
    }

    /**
     * SurfaceView 视图状态发生改变时，响应此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }
    /**
     * SurfaceView 视图消亡时，响应此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
    /**
     * 游戏绘图
     */
    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null&&source!=null) {
                Paint paint2=new Paint();
                paint2.setColor(Color.BLACK);
                paint2.setStrokeWidth(3);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setAntiAlias(true);
                while(true) {
                    int index = 0;
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    canvas.drawPath(table,paint2);
                    int head = group.head;
                    while (head != -1) {
                        canvas.save();
                        canvas.translate(group.pos[head].x, group.pos[head].y);
                        canvas.translate(- ((head / numOfPiece) * (tableWidth / numOfPiece)), -((head % numOfPiece) * (tableWidth / numOfPiece)));
                        canvas.drawPath(path[head], paint);
                        canvas.drawPath(path[head], paint2);
                        canvas.restore();
                        head = group.pointerTo[head];
                        index++;
                    }
                    if(index==numOfPiece*numOfPiece)
                        break;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }
    /**
     * 触屏事件监听
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nAct = event.getAction();
        switch (nAct) {
            case MotionEvent.ACTION_MOVE: {
                group.pieceMove(event.getX(), event.getY());
            }
            break;
            case MotionEvent.ACTION_DOWN: {
                group.pieceDown(event.getX(), event.getY());
            }
            break;
            case MotionEvent.ACTION_UP: {
                group.pieceUp();
            }
            break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 游戏逻辑
     */
    private void logic() {
    }
    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            if(!isReady) {
                iniPath();
                isReady=true;
            }
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 5) {
                    Thread.sleep(5 - (end - start));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Path createPiecePath(int min,int offX,int offY,PieceEdge eTop,PieceEdge eRight,PieceEdge eBottom,PieceEdge eLeft){
        Path p = new Path();
        p.moveTo(offX,offY);
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
    public void ini(int pieceNum,int length,Bitmap bsource,float offx,float offy) {
        numOfPiece=pieceNum;
        tableWidth=length;
        source=bsource;
        offX=offx;
        offY=offy;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Shader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }
    private void iniPath() {
        group=new PieceGroup(tableWidth,tableWidth/numOfPiece,numOfPiece,offX,offY);
        table=new Path();
        path=new Path[numOfPiece*numOfPiece];
        rawEdge = new PieceEdge[numOfPiece*numOfPiece];
        colEdge = new PieceEdge[numOfPiece*numOfPiece];
        PieceEdge e=new PieceEdge();
        for(int i=0;i<numOfPiece*numOfPiece;i++) {
            rawEdge[i]=e.getRawEdge((float)Math.random(),tableWidth/numOfPiece);
            colEdge[i]=e.getColEdge((float) Math.random(), tableWidth / numOfPiece);
        }

        PieceEdge eTop;
        PieceEdge eRight;
        PieceEdge eBottom;
        PieceEdge eLeft;
        for(int i=0;i<numOfPiece;i++){
            for(int j=0;j<numOfPiece;j++) {
//                bmp[i*numOfPiece+j] = createPieceImage(source,tableWidth/numOfPiece,i*tableWidth/numOfPiece,j*tableWidth/numOfPiece);
                eTop=j==0?null:rawEdge[i*numOfPiece+j-1];
                eRight=i==(numOfPiece-1)?null:colEdge[i*numOfPiece+j];
                eBottom=j==(numOfPiece-1)?null:rawEdge[i*numOfPiece+j];
                eLeft=i==0?null:colEdge[i*numOfPiece+j-numOfPiece];
                path[i*numOfPiece+j]=createPiecePath(tableWidth/numOfPiece,i*tableWidth/numOfPiece,j*tableWidth/numOfPiece,
                        eTop,eRight,eBottom,eLeft);
                table.addPath(path[i*numOfPiece+j],offX,offY);
            }
        }
    }
}
