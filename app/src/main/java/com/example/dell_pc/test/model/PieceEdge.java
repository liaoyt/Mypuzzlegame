package com.example.dell_pc.test.model;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by Administrator on 2016-4-14.
 */
public class PieceEdge {
    public PieceEdgePoint[] mEdge;
    private int mIndex;
    public PieceEdge() {
        mEdge=new PieceEdgePoint[6];
        mIndex=0;
    }
    public void addPoint(PieceEdgePoint point){
        mEdge[mIndex++]=point;
    }

    public PieceEdge getRawEdge(float random,float PieceWidth){
        if(random>0.5)
            return Up(PieceWidth);
        else
            return Down(PieceWidth);
    }

    public PieceEdge getColEdge(float random,float PieceWidth){
        if(random>0.5)
            return Left(PieceWidth);
        else
            return Right(PieceWidth);
    }

    private PieceEdge Down(float PieceWidth)
    {
        PieceEdge P=new PieceEdge();
        P.addPoint(new PieceEdgePoint(new PointF(0,0),new PointF(0,0),new PointF(0,0)));
        Random d=new Random();
        float f1 = PieceWidth;
        float f2 = 0.3333333F * f1;
        float f3 = 0.25F * f1;
        float f4 = 0.05F * f1;
        PointF localPointF2 = new PointF(f2, 0.0F);
        localPointF2.offset(d.nextFloat() * f4 - f4 / 2.0F, 0.0F);
        Object localObject1 = new PointF(f1 - f2, 0.0F);
        ((PointF)localObject1).offset(d.nextFloat() * f4 - f4 / 2.0F, 0.0F);
        PointF localPointF3 = new PointF(localPointF2.x, f3);
        localPointF3.offset(0.0F, -d.nextFloat() * 0.1666667F * f1);
        PointF localPointF4 = new PointF(((PointF)localObject1).x, f3);
        localPointF4.offset(0.0F, -d.nextFloat() * 0.1666667F * f1);
        f2 = 0.08333334F * f1;
        Object localObject2 = new PointF();
        PointF localPointF1 = new PointF();
        ((PointF) localObject2).offset(f2, -f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF2.x + offx, localPointF2.y+offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF2);
        ((PointF) localObject2).offset(f2, f2);
        localPointF1.set(localPointF3);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF3.x + offx, localPointF3.y+offy);
       P.addPoint(new PieceEdgePoint(localPointF3,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF3);
        ((PointF) localObject2).offset(f2, f2);
        localPointF1.set(localPointF4);
        localPointF1.offset(-f2, f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF4.x + offx, localPointF4.y+offy);
        P.addPoint(new PieceEdgePoint(localPointF4,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF4);
        ((PointF) localObject2).offset(f2, -f2);
        localPointF1.set((PointF) localObject1);
        localPointF1.offset(-f2, f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, ((PointF) localObject1).x+offx, ((PointF) localObject1).y+offy);
        P.addPoint(new PieceEdgePoint((PointF) localObject1,(PointF)localObject2,localPointF1));
        localPointF2 = new PointF(f1,0.0F);
        ((PointF) localObject2).set((PointF) localObject1);
        ((PointF) localObject2).offset(f2, -f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, localPointF2.x+offx, localPointF2.y+offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        return P;
    }
    private PieceEdge Right(float PieceWidth)
    {
        PieceEdge P=new PieceEdge();
        P.addPoint(new PieceEdgePoint(new PointF(0,0),new PointF(0,0),new PointF(0,0)));
        Random d=new Random();
        float f1 =PieceWidth;
        float f2 = 0.3333333F * f1;
        float f3 = 0.25F * f1;
        float f4 = 0.05F * f1;
        PointF localPointF2 = new PointF( 0.0F,f2);
        localPointF2.offset( 0.0F,d.nextFloat() * f4 - f4 / 2.0F);
        Object localObject1 = new PointF( 0.0F,f1 - f2);
        ((PointF)localObject1).offset( 0.0F,d.nextFloat() * f4 - f4 / 2.0F);
        PointF localPointF3 = new PointF( f3,localPointF2.y);
        localPointF3.offset( -d.nextFloat() * 0.1666667F * f1,0.0F);
        PointF localPointF4 = new PointF(f3,((PointF)localObject1).y);
        localPointF4.offset( -d.nextFloat() * 0.1666667F * f1,0.0F);
        f2 = 0.08333334F * f1;
        Object localObject2 = new PointF();
        PointF localPointF1 = new PointF();
        ((PointF) localObject2).offset(-f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF2.x + offx, localPointF2.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF2);
        ((PointF) localObject2).offset(f2, f2);
        localPointF1.set(localPointF3);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF3.x + offx, localPointF3.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF3,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF3);
        ((PointF) localObject2).offset(f2, f2);
        localPointF1.set(localPointF4);
        localPointF1.offset(f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF4.x + offx, localPointF4.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF4,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF4);
        ((PointF)localObject2).offset(-f2, f2);
        localPointF1.set((PointF) localObject1);
        localPointF1.offset(f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, ((PointF) localObject1).x+offx, ((PointF) localObject1).y+offy);
        P.addPoint(new PieceEdgePoint((PointF) localObject1,(PointF)localObject2,localPointF1));
        localPointF2 = new PointF(0.0F,f1);
        ((PointF) localObject2).set((PointF) localObject1);
        ((PointF)localObject2).offset(-f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, localPointF2.x+offx, localPointF2.y+offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        return P;
    }
    private PieceEdge Up(float PieceWidth)
    {
        PieceEdge P=new PieceEdge();
        P.addPoint(new PieceEdgePoint(new PointF(0,0),new PointF(0,0),new PointF(0,0)));
        Random d=new Random();
        float f1 =PieceWidth;
        float f2 = 0.3333333F * f1;
        float f3 = 0.25F * f1;
        float f4 = 0.05F * f1;
        PointF localPointF2 = new PointF(f2, 0.0F);
        localPointF2.offset(d.nextFloat() * f4 - f4 / 2.0F, 0.0F);
        Object localObject1 = new PointF(f1 - f2, 0.0F);
        ((PointF)localObject1).offset(d.nextFloat() * f4 - f4 / 2.0F, 0.0F);
        PointF localPointF3 = new PointF(localPointF2.x, -f3);
        localPointF3.offset(0.0F, d.nextFloat() * 0.1666667F * f1);
        PointF localPointF4 = new PointF(((PointF)localObject1).x, -f3);
        localPointF4.offset(0.0F, d.nextFloat() * 0.1666667F * f1);
        f2 = 0.08333334F * f1;
        Object localObject2 = new PointF();
        PointF localPointF1 = new PointF();
        ((PointF)localObject2).offset(f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, f2);
//        p.cubicTo(((PointF) localObject2).x + off, ((PointF) localObject2).y + off, localPointF1.x + off, localPointF1.y + off, localPointF2.x + off, localPointF2.y + off);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        ((PointF)localObject2).set(localPointF2);
        ((PointF)localObject2).offset(f2, -f2);
        localPointF1.set(localPointF3);
        localPointF1.offset(-f2, f2);
//        p.cubicTo(((PointF) localObject2).x + off, ((PointF) localObject2).y + off, localPointF1.x + off, localPointF1.y + off, localPointF3.x + off, localPointF3.y + off);
        P.addPoint(new PieceEdgePoint(localPointF3,(PointF)localObject2,localPointF1));
        ((PointF)localObject2).set(localPointF3);
        ((PointF)localObject2).offset(f2, -f2);
        localPointF1.set(localPointF4);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + off, ((PointF) localObject2).y + off, localPointF1.x + off, localPointF1.y + off, localPointF4.x + off, localPointF4.y + off);
        P.addPoint(new PieceEdgePoint(localPointF4,(PointF)localObject2,localPointF1));
        ((PointF)localObject2).set(localPointF4);
        ((PointF)localObject2).offset(f2, f2);
        localPointF1.set((PointF) localObject1);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+off, ((PointF) localObject2).y+off, localPointF1.x+off, localPointF1.y+off, ((PointF) localObject1).x+off, ((PointF) localObject1).y+off);
        P.addPoint(new PieceEdgePoint((PointF) localObject1,(PointF)localObject2,localPointF1));
        localPointF2 = new PointF(f1, 0.0F);
        ((PointF)localObject2).set((PointF) localObject1);
        ((PointF)localObject2).offset(f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(-f2, f2);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        return P;
    }
    private PieceEdge Left(float PieceWidth)
    {
        PieceEdge P=new PieceEdge();
        P.addPoint(new PieceEdgePoint(new PointF(0,0),new PointF(0,0),new PointF(0,0)));
        Random d=new Random();
        float f1 =PieceWidth;
        float f2 = 0.3333333F * f1;
        float f3 = 0.25F * f1;
        float f4 = 0.05F * f1;
        PointF localPointF2 = new PointF( 0.0F,f2);
        localPointF2.offset( 0.0F,d.nextFloat() * f4 - f4 / 2.0F);
        Object localObject1 = new PointF( 0.0F,f1 - f2);
        ((PointF)localObject1).offset( 0.0F,d.nextFloat() * f4 - f4 / 2.0F);
        PointF localPointF3 = new PointF( -f3,localPointF2.y);
        localPointF3.offset( d.nextFloat() * 0.1666667F * f1,0.0F);
        PointF localPointF4 = new PointF(-f3,((PointF)localObject1).y);
        localPointF4.offset( d.nextFloat() * 0.1666667F * f1,0.0F);
        f2 = 0.08333334F * f1;
        Object localObject2 = new PointF();
        PointF localPointF1 = new PointF();
        ((PointF) localObject2).offset(f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF2.x + offx, localPointF2.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF2);
        ((PointF) localObject2).offset(-f2, f2);
        localPointF1.set(localPointF3);
        localPointF1.offset(f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF3.x + offx, localPointF3.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF3,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF3);
        ((PointF) localObject2).offset(-f2, f2);
        localPointF1.set(localPointF4);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x + offx, ((PointF) localObject2).y + offy, localPointF1.x + offx, localPointF1.y + offy, localPointF4.x + offx, localPointF4.y + offy);
        P.addPoint(new PieceEdgePoint(localPointF4,(PointF)localObject2,localPointF1));
        ((PointF) localObject2).set(localPointF4);
        ((PointF)localObject2).offset(f2, f2);
        localPointF1.set((PointF) localObject1);
        localPointF1.offset(-f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, ((PointF) localObject1).x+offx, ((PointF) localObject1).y+offy);
        P.addPoint(new PieceEdgePoint((PointF) localObject1,(PointF)localObject2,localPointF1));
        localPointF2 = new PointF(0.0F,f1);
        ((PointF) localObject2).set((PointF) localObject1);
        ((PointF)localObject2).offset(f2, f2);
        localPointF1.set(localPointF2);
        localPointF1.offset(f2, -f2);
//        p.cubicTo(((PointF) localObject2).x+offx, ((PointF) localObject2).y+offy, localPointF1.x+offx, localPointF1.y+offy, localPointF2.x+offx, localPointF2.y+offy);
        P.addPoint(new PieceEdgePoint(localPointF2,(PointF)localObject2,localPointF1));
        return P;
    }
}
