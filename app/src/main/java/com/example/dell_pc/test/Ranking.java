package com.example.dell_pc.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sword on 2016/4/10 0010.
 */
public class Ranking extends Activity{
    private TextView myRank,rankFirst,rankSecond,rankThird;

    private static final String RANK_URL="http://172.18.42.97/getRank.php";

    JSONParser jsonParser=new JSONParser();

    private static final String TAG_SUCCESS="success";
    private static final String TAG_MESSAGE="message";

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);

        Log.d("hello", "rank is running");
        myRank=(TextView) findViewById(R.id.uerRank);
        rankFirst=(TextView) findViewById(R.id.rankFirst);
        rankSecond=(TextView) findViewById(R.id.rankSecond);
        rankThird=(TextView) findViewById(R.id.rankThird);

        new AttemptGetRank().execute();

    }

    class AttemptGetRank extends AsyncTask<String,String,String>{
        boolean failure=false;
        rankJson rankjson=new rankJson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Ranking.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            Gson gson=new Gson();

                List<NameValuePair> par=new ArrayList<NameValuePair>();
                par.add(new BasicNameValuePair("score", "1000"));

                Log.d("request!", "starting");

                JSONObject json=jsonParser.makeHttpRequest(RANK_URL,"POST",par);

                Log.d("Get rank attempt",json.toString());

                rankjson=gson.fromJson(json.toString(),rankJson.class);

                success=rankjson.getSuccess();

                if(success==1)
                {
                    Log.d("Get the rank success", json.toString());
                   // finish();
                }else{
                    Log.d("Get rank failure",json.toString());
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();

            if(rankjson.getSuccess()==1)
            {
                try{
                    myRank.setText("你的排名:  " + rankjson.getMyRank());
                    /*Toast.makeText(Ranking.this,rankjson.getFirst().getTopUsername(),Toast.LENGTH_LONG).show();*/
                    rankFirst.setText("第一名：   "+rankjson.getFirst().getTopUsername()+"  "+rankjson.getFirst().getTopScore());
                    rankSecond.setText("第二名：   "+rankjson.getSecond().getTopUsername()+"  "+rankjson.getSecond().getTopScore());
                    rankThird.setText("第三名：   "+rankjson.getThird().getTopUsername()+"  "+rankjson.getThird().getTopScore());
                }catch(NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
}
