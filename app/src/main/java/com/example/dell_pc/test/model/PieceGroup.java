package com.example.dell_pc.test.model;

import android.graphics.PointF;

/**
 * Created by Administrator on 2016-4-10.
 */
public class PieceGroup {
    private int tableWidth;
    private int pieceWidth;
    private int numOfPiece;
    private PointF[] fitPos;
    private boolean[] fit;
    public int[] pointerTo;
    private int[] pointerBack;
    public PointF[] pos;
    public int head;
    private int tail;
    private boolean ismove;
    private int movePiece;
    private PointF org;
    private float offX;
    private float offY;
    public PieceGroup(int paramInt1,int paramInt2,int paramInt3,float paramInt4,float paramInt5){
        tableWidth=paramInt1;
        pieceWidth=paramInt2;
        numOfPiece=paramInt3;
        offX=paramInt4;
        offY=paramInt5;

        ismove=false;
        org=new PointF(0,0);

        pointerTo=new int[numOfPiece*numOfPiece];
        head=0;
        for(int i=0;i<numOfPiece*numOfPiece-1;i++)
            pointerTo[i]=i+1;
        pointerTo[numOfPiece*numOfPiece-1]=-1;

        pointerBack=new int[numOfPiece*numOfPiece];
        tail=numOfPiece*numOfPiece-1;
        for(int i=1;i<numOfPiece*numOfPiece;i++)
            pointerBack[i]=i-1;
        pointerBack[0]=-1;

        fit=new boolean[numOfPiece*numOfPiece];
        for(int i=0;i<numOfPiece*numOfPiece;i++)
            fit[i]=false;

        pos=new PointF[numOfPiece*numOfPiece];
        for(int i=0;i<numOfPiece*numOfPiece;i++){
            float x=(float)Math.random()*tableWidth;
            float y=(float)Math.random()*(offY-pieceWidth);
            PointF f=new PointF(x,y+offY+tableWidth);
            pos[i]=f;
        }

        fitPos=new PointF[numOfPiece*numOfPiece];
        for(int i=0;i<numOfPiece;i++){
            for(int j=0;j<numOfPiece;j++) {
                float x = offX+i*pieceWidth;
                float y = offY+j*pieceWidth;
                PointF f = new PointF(x, y);
                fitPos[i*numOfPiece+j] = f;
            }
        }
    }
    public void pieceDown(float x,float y){
        int num=tail;
        while(num!=-1){
            if(x>=pos[num].x-pieceWidth/4&&x<=pos[num].x+pieceWidth+pieceWidth/4&&
                    y>=pos[num].y-pieceWidth/4&&y<=pos[num].y+pieceWidth+pieceWidth/4&&!fit[num]){
                ismove=true;
                PointF tmp=new PointF(x,y);
                org=tmp;
                movePiece=num;
                if(tail!=num) {
                    int back=pointerBack[num];
                    int next=pointerTo[num];
                    pointerTo[tail]=num;
                    pointerBack[num]=tail;
                    pointerTo[num]=-1;
                    tail=num;
                    if(head==num){
                        pointerBack[next]=back;
                        head=next;
                    }
                    else {
                        pointerBack[next] = back;
                        pointerTo[back] = next;
                    }
                }
                break;
            }
            num=pointerBack[num];
        }
        //此处改变指针
    }
    public void pieceMove(float x,float y){
        if(ismove) {
            pos[movePiece].x+=(x-org.x);
            pos[movePiece].y+=(y-org.y);
        }
        PointF tmp=new PointF(x,y);
        org=tmp;
    }
    public void pieceUp(){
        float x=pos[tail].x-fitPos[tail].x;
        if(x<0)
            x=-x;
        float y=pos[tail].y-fitPos[tail].y;
        if(y<0)
            y=-y;
        if(ismove&&x<40&&y<40){
            fit[tail]=true;
            pos[tail]=fitPos[tail];
            int back=pointerBack[tail];
            pointerBack[head]=tail;
            pointerBack[tail]=-1;
            pointerTo[tail]=head;
            pointerTo[back]=-1;
            head=tail;
            tail=back;
        }
        ismove=false;
    }
}
