package com.example.house.util;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.house.controller.LoginActivity;
import com.example.house.model.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Okio;

public class MyOkHttpUtil {

    private static final String TAG = "MyFragment=";

    /**
     * 通用的post请求
     * @param url
     * @param isEncrypt
     * @param params 如果是加密的,key需按升序
     * @param callback
     */
    public static void postRequest(String url,boolean isEncrypt, LinkedHashMap<String,String> params, okhttp3.Callback callback){
        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步创建RequestBody

        //求出加密的pwd

        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        StringBuilder sb=new StringBuilder();

        if (isEncrypt)
        {
           while (it.hasNext())
           {
               Map.Entry<String, String> next = it.next();
               String key = next.getKey();
               String value = next.getValue();
               sb.append(key+"="+value+"&");
           }

            String pwd = AppKeyUtil.getPwd(sb.toString()+Constant.appkey);

            params.put("pwd",pwd);
        }

        it = params.entrySet().iterator();
        FormBody.Builder builder = new FormBody.Builder();
        while (it.hasNext())
        {
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            String value = next.getValue();
            Log.d(TAG, key+":"+value);
            builder.add(key,value);
        }
        RequestBody body = builder.build();
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
