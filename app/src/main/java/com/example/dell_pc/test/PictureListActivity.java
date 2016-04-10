package com.example.dell_pc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PictureListActivity extends AppCompatActivity {

    private Button loadmore;
    private PopupMenu popupMenu;

    private RecyclerView mRclist;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);

        mRclist = (RecyclerView) findViewById(R.id.rc_list);
//        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRclist.setLayoutManager(mLayoutManager);
        mRclist.setHasFixedSize(true);

        mRclist.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 6; i++) {
            mData.add("test");
        }
        mAdapter = new RecyclerAdapter(this, mData);
        mRclist.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(
                new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {
                        Toast.makeText(PictureListActivity.this, "test" + position, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PictureListActivity.this, SelectPictureActivity.class);
                        intent.putExtra("mode", 0);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });

        createPopupMenu();
    }

    private void createPopupMenu() {
        loadmore = (Button) findViewById(R.id.load_more);
        popupMenu = new PopupMenu(this, loadmore);
        popupMenu.getMenuInflater().inflate(R.menu.loadmore_pic, popupMenu.getMenu());

        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.native_pic) {
                    Intent intent = new Intent(PictureListActivity.this, SelectPictureActivity.class);
                    intent.putExtra("mode", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PictureListActivity.this, SelectPictureActivity.class);
                    intent.putExtra("mode", 2);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}
