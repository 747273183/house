package com.example.house.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.house.R;
import com.example.house.model.Account;
import com.example.house.model.Constant;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user,et_password;
    private static final String TAG = "LoginActivity-";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_user = findViewById(R.id.et_user);
        et_password= findViewById(R.id.et_password);

    }

    //登录
    public void onClick(View view) {
        //接口参数 String username,String password
        String user = et_user.getText().toString();
        String password = et_password.getText().toString();

        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }


        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步创建RequestBody
        RequestBody body = new FormBody.Builder()
                .add("user", user)
                .add("password", password)
                .build();
        //第三步创建Rquest
        Request request = new Request.Builder()
                .url(Constant.URL+Constant.PATH_LOGIN)
                .post(body)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String result = response.body().string();

                    Log.d(TAG, "result: "+result);

                    JSONObject object=new JSONObject(result);
                    int code = object.getInt("code");
                    final String msg = object.getString("msg");

                    //判断登录
                    if (code==1)
                    {
                        JSONObject data = object.getJSONObject("data");
                        Gson gson=new Gson();
                        Account account= gson.fromJson(data.toString(), Account.class);
                        //进入首页
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        //在子线程中弹出提示
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
