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
import com.example.house.util.MyOkHttpUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user, et_password;
    private static final String TAG = "LoginActivity-";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);

    }

    //登录
    public void onClick(View view) {
        //接口参数 String username,String password
        final String user = et_user.getText().toString();
        final String password = et_password.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("user", user);
        params.put("password", password);

        //发起请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_LOGIN, false, params, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: 请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            if (response.code() == 200) {
                                String data = response.body().string();
                                JSONObject jsonObject = new JSONObject(data);

                                final String msg = jsonObject.getString("msg");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                });
                                int code = jsonObject.getInt("code");


                                if (code == 1) {
                                    //登录成功
                                    JSONObject data1 = jsonObject.getJSONObject("data");
                                    Gson gson = new Gson();
                                    Account account = gson.fromJson(data1.toString(), Account.class);
                                    //进入首页
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("account", account);
                                    startActivity(intent);
                                    finish();
                                }


                            } else {
                                //在子线程中弹出提示
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this, "请求成功,修改失败,响应码为:" + response.code(), Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();


    }
}
