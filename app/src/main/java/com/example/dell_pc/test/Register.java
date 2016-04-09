package com.example.dell_pc.test;

import android.app.Activity;
import android.app.ProgressDialog;
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
public class Register extends Activity implements View.OnClickListener {
    ImageButton back;
    private EditText user,pass,nick;
    private Button mRegister;

    private ProgressDialog pDialog;

    JSONParser jsonParser=new JSONParser();

    private static final String REGISTER_URL="http://172.18.42.97/registe.php";

    private static final String TAG_SUCCESS="success";
    private static final String TAG_MESSAGE="message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        back = (ImageButton)findViewById(R.id.backbutton);
        back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Register.this, MainActivity.class);
                startActivity(intent);
                Register.this.finish();
            }
        });

        user=(EditText) findViewById(R.id.username);
        pass=(EditText) findViewById(R.id.password);
        nick=(EditText) findViewById(R.id.nickname);

        mRegister=(Button) findViewById(R.id.register);

        mRegister.setOnClickListener(this);

        ;
    }
    @Override
    public void onClick(View v) {
        new CreateUser().execute();
    }

    class CreateUser extends AsyncTask<String,String,String>{
        boolean failure=false;
        String username=user.getText().toString();
        String password=pass.getText().toString();
        String nickname=nick.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(Register.this);
            pDialog.setMessage("Attempting Register...");
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
                par.add(new BasicNameValuePair("nickname",nickname));

                Log.d("request!", "starting");

                JSONObject json=jsonParser.makeHttpRequest(REGISTER_URL,"POST",par);

                Log.d("Register attempt",json.toString());

                success=json.getInt(TAG_SUCCESS);

                if(success==1)
                {
                    Log.d("Login Successful",json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Register Failure",json.getString(TAG_MESSAGE));
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
                Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();
        }
    }
}
