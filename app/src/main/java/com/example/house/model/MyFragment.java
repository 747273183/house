package com.example.house.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;
import com.example.house.controller.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFragment extends Fragment {

    private static final String TAG = "MyFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_password:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("修改密码");
        builder.setIcon(R.drawable.logo);
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null, false);
        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获得用户id,原密码，新密码，确认密码
                Intent intent = getActivity().getIntent();
                Account account= (Account) intent.getSerializableExtra("account");
                int id = account.getId();
                EditText et_old_pwd= v.findViewById(R.id.et_old_pwd);
                EditText et_new_pwd= v.findViewById(R.id.et_new_pwd);
                EditText et_pas_pwd=v.findViewById(R.id.et_pas_pwd);
                String old_pwd = et_old_pwd.toString();
                String new_pwd = et_new_pwd.toString();
                String pas_pwd = et_pas_pwd.toString();
              //发送修改密码的请求
                postChangePassword(id, old_pwd, new_pwd, pas_pwd);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void postChangePassword(int id, String old_pwd, String new_pwd, String pas_pwd) {
        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步创建RequestBody
        RequestBody body = new FormBody.Builder()
                .add("old_pwd", old_pwd)
                .add("new_pwd", new_pwd)
                .add("pas_pwd", pas_pwd)
                .add("id", id+"")
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
                     String msg = object.getString("msg");


                    //在子线程中弹出提示
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();

                    if (code==1)
                    {
                        //重新登录
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
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
