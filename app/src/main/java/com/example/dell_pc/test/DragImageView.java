package com.example.dell_pc.test;

/**
 * Created by Administrator on 2016-3-20.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class DragImageView extends View{
    private int pieceNum=5;
    private int length=0;
    private int xOff=0;
    private int yOff=0;
    private int[] X=new int [pieceNum*pieceNum];
    private int[] Y=new int [pieceNum*pieceNum];
    private boolean fit=false;
    private int fitX=0;
    private int fitY=0;
    private Bitmap bmp=null;
    private PointF orgPos=new PointF(0,0);
    private PointF downPos=new PointF(0,0);
    private PointF movePos=new PointF(0,0);
    private boolean bMove=false;
    private int nDstWidth=0;
    private int nDstHeight=0;
    private Rect rcSrc=new Rect(0,0,0,0);
    private RectF rcDst=new RectF(0,0,0,0);
    private Paint paint=null;
    public DragImageView(Context context){
        super(context);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public DragImageView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DragImageView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        int indexX=0,indexY=0;
        for(int i=0;i<pieceNum;i++){
            for(int j=0;j<pieceNum;j++) {
                X[indexX++]=j;
                Y[indexY++]=i;
            }
        }
        WindowManager wm=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        int nScrWidth=wm.getDefaultDisplay().getWidth();
        @SuppressWarnings("deprecation")
        int nScrHeight=wm.getDefaultDisplay().getHeight();
        length=nScrHeight>nScrWidth?nScrWidth:nScrHeight;
        length=(int)(length*0.9);
        length=length+(pieceNum-length%pieceNum);
        xOff=(nScrWidth-length)/2;
        yOff=(nScrHeight-length)/2;
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        int idId=attrs.getAttributeResourceValue(null, "id", 0);
        int id=Integer.parseInt(context.getResources().getText(idId).toString());
        fitX=xOff+X[id-1]*length/pieceNum;
        fitY=yOff+Y[id-1]*length/pieceNum;

        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.saber);
        bmp=createPieceImage(bmp,length/pieceNum);
    }

    @Override
    public void addTouchables(ArrayList<View>views){
        super.addTouchables(views);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(fit) {
            return false;
        }
        float fPosX = event.getX();
        float fPosY = event.getY();
        int nAct = event.getAction();
        switch (nAct) {
            case MotionEvent.ACTION_MOVE: {
                if (!bMove)
                    bMove = true;
                movePos.x = fPosX - downPos.x;
                movePos.y = fPosY - downPos.y;
                downPos.x = fPosX;
                downPos.y = fPosY;
                invalidate();
            }
            break;
            case MotionEvent.ACTION_DOWN: {
                if(fPosX<orgPos.x||fPosX>orgPos.x+nDstWidth||fPosY<orgPos.y||fPosY>orgPos.y+nDstHeight)
                    return false;
                this.bringToFront();
                requestLayout();
                downPos.x = fPosX;
                downPos.y = fPosY;
            }
            break;
            case MotionEvent.ACTION_UP:{
                movePos.x = fPosX - downPos.x;
                movePos.y = fPosY - downPos.y;
                if(orgPos.x+movePos.x>=fitX-24&&orgPos.x+movePos.x<=fitX+24&&orgPos.y+movePos.y>=fitY-24&&orgPos.y+movePos.y<=fitY+24){
                    fit=true;
                }
                invalidate();
            }
                break;
        }
        return true;
    }

    private Bitmap createPieceImage(Bitmap source, int min) {
        Matrix matrix = new Matrix();
        matrix.setScale(min*pieceNum*1.0f/ source.getWidth(),min*pieceNum*1.0f / source.getHeight());
        source=Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
// 注意一定要用ARGB_8888，否则因为背景不透明导致遮罩失败
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
// 产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
// 首先绘制圆形
        canvas.drawRect(0, 0, min,min, paint);
// 使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
// 绘制图片
        canvas.save();
        canvas.translate(-(fitX-xOff), -(fitY-yOff));
        canvas.drawBitmap(source, 0, 0, paint);
        canvas.restore();
        return target;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(bmp==null)
            return ;
        int nWidth=bmp.getWidth();
        int nHeight=bmp.getHeight();
        if(!bMove)
            orgPos=GetRandomPos();
        else
        {
                orgPos.x += movePos.x;
                orgPos.y += movePos.y;
        }
        if(fit){
            orgPos.x=fitX;
            orgPos.y=fitY;
        }
        rcSrc.right=nWidth;
        rcSrc.bottom=nHeight;
        rcDst.left=orgPos.x;
        rcDst.top=orgPos.y;
        rcDst.right=orgPos.x+nDstWidth;
        rcDst.bottom=orgPos.y+nDstHeight;
        canvas.drawBitmap(bmp, rcSrc, rcDst, paint);
    }

    protected PointF GetRandomPos(){
        PointF pt=new PointF(0,0);
        if(bmp==null)
            return pt;
        int nWidth=bmp.getWidth();
        int nHeight=bmp.getHeight();
        nDstHeight=nHeight;
        nDstWidth=nWidth;
        pt.x=(float)Math.random()*length;
        pt.y=(float)Math.random()*length;
        return pt;
    }
    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}
