package com.example.i.myapplication;

import android.graphics.PointF;

/**
 * Created by Administrator on 2016-4-14.
 */
public class PieceEdgePoint {
    public PointF p=new PointF();
    public PointF ctrlA=new PointF();
    public PointF ctrlB=new PointF();
    public PieceEdgePoint(PointF P,PointF CA,PointF CB) {
        p = new PointF(P.x, P.y);
        ctrlA = new PointF(CA.x,CA.y);
        ctrlB = new PointF(CB.x,CB.y);
    }
}
