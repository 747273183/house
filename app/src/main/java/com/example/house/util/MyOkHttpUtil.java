package com.example.house.util;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Okio;

public class MyOkHttpUtil {

    //登录
    public static void Login(String url,String user,String password,okhttp3.Callback callback){
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
                .url(url)
                .post(body)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);

        //第五步发起请求
        call.enqueue(callback);//execute 是异步请求 ，enqueue是同步请求

    }

}
