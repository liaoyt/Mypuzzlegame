package com.example.dell_pc.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sword on 2016/4/4 0004.
 */
public class Login extends Activity01 implements View.OnClickListener {
    ImageButton back;
    private EditText user,pass;
    private Button mSubmit,mRegister;

    //Progress Dialog
    private ProgressDialog pDialog;

    //JSON parser class
    JSONParser jsonParser=new JSONParser();

    private static final String LOGIN_URL="http://172.18.42.97/login.php";


    private static final String TAG_SUCCESS="success";
    private static final String TAG_MESSAGE="message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        back = (ImageButton)findViewById(R.id.backbutton);
        back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this, MainActivity.class);
                startActivity(intent);
                Login.this.finish();
            }
        });

        user=(EditText) findViewById(R.id.username);
        pass=(EditText) findViewById(R.id.password);

        mSubmit=(Button) findViewById(R.id.login);
        mRegister=(Button) findViewById(R.id.register);

        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                new AttemptLogin().execute();
                break;
            case R.id.register:
                Intent i=new Intent(this,Register.class);
                startActivity(i);
                break;

            default:
                break;

        }
    }

    class AttemptLogin extends AsyncTask<String,String,String>{
        boolean failure=false;
        String username=user.getText().toString();
        String password=pass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            try{
                List<NameValuePair> par=new ArrayList<NameValuePair>();
                par.add(new BasicNameValuePair("username", username));
                par.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                JSONObject json=jsonParser.makeHttpRequest(LOGIN_URL,"POST",par);

                Log.d("Login attempt7777",json.toString());

                success=json.getInt(TAG_SUCCESS);

                if(success==1)
                {
                    Log.d("Login Successful",json.toString());
                    Intent i=new Intent(Login.this, Activity02.class);//这里要跳转至一个新的activity，可看用户信息
                    finish();
                    startActivity(i);
                }else{
                    Log.d("Login Failure",json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if(s!=null)
                Toast.makeText(Login.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
