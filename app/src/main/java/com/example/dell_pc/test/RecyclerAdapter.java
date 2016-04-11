package com.example.dell_pc.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by liaoyt on 16-3-27.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> mData;
    private int width;

    public RecyclerAdapter(Context context, List<String> data) {
        this.context = context;
        mData = data;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // LinearLayout
//        width = wm.getDefaultDisplay().getWidth() * 95 / 100;

        // StaggeredGridLayout
        width = wm.getDefaultDisplay().getWidth() * 98 / 100 / 2;
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.myImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        class MyTransform implements Transformation {

            @Override
            public Bitmap transform(Bitmap source) {

                float scale = ((float) width) / source.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);

                Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

                if (bitmap != source)
                    source.recycle();

                return bitmap;
            }

            @Override
            public String key() {
                return "scale";
            }
        }

        // 建立起ViewHolder中视图与数据的关联
//        viewHolder.imageView.setImageResource(R.drawable.pic1 + position);
        Picasso.with(context).load(R.drawable.pic1+position).transform(new MyTransform()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}