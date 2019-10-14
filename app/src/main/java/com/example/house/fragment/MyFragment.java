package com.example.house.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.house.R;
import com.example.house.activity.LoginActivity;
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

public class MyFragment extends Fragment {

    private static final String TAG = "MyFragment=";
    private Button btn_change_password;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        btn_change_password = view.findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //请求个人信息
        LinkedHashMap<String, String> params=new LinkedHashMap<>();
        Intent intent = getActivity().getIntent();
        final Account account = (Account) intent.getSerializableExtra("account");
        int id = account.getId();
        params.put("id",id+"");


        MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_PERSONAL_INFO, true, params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //在子线程中弹出提示
                Looper.prepare();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        String data = response.body().string();
                        Log.d(TAG, "个人信息: " + data);
                        JSONObject jsonObject = new JSONObject(data);
                        int code = jsonObject.getInt("code");
                        final String msg = jsonObject.getString("msg");

                        if (code == 1) {
                            //请求成功,显示个人信息
                            final ImageView iv_head=view.findViewById(R.id.iv_head);
                            final TextView tv_name= view.findViewById(R.id.tv_name);
                            final TextView tv_phone=view.findViewById(R.id.tv_phone);

                            //gson解析
                            JSONObject data1 = jsonObject.getJSONObject("data");
                            Gson gson=new Gson();
                            final Account account1 = gson.fromJson(data1.toString(), Account.class);
                            Log.d(TAG, "onResponse: "+account1.toString());


                            //显示
                           getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_name.setText("姓名:"+account1.getName());
                                    tv_phone.setText("手机号:"+account1.getTel());

                                    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                                            .skipMemoryCache(true);


                                    Glide.with(getContext())
                                            .load(account1.getImg().toString().replace("\\","/"))
                                            .placeholder(R.drawable.place_image)//图片加载出来前，显示的图片
                                            .error(R.drawable.error_image)//图片加载失败后，显示的图片
                                            .apply(mRequestOptions)
                                            .into(iv_head);
                                }
                            });


                        }

                        //在子线程中弹出提示
                        Looper.prepare();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        Looper.loop();




                    } else {
                        //在子线程中弹出提示
                        Looper.prepare();
                        Toast.makeText(getActivity(), "请求成功,修改失败,响应码为:" + response.code(), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("修改密码");
        builder.setIcon(R.drawable.logo);
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null, false);
        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获得用户id,原密码，新密码，确认密码
                Intent intent = getActivity().getIntent();
                Account account = (Account) intent.getSerializableExtra("account");
                int id = account.getId();
                EditText et_old_pwd = v.findViewById(R.id.et_old_pwd);
                EditText et_new_pwd = v.findViewById(R.id.et_new_pwd);
                EditText et_pas_pwd = v.findViewById(R.id.et_pas_pwd);
                String old_pwd = et_old_pwd.getText().toString();
                String new_pwd = et_new_pwd.getText().toString();
                String pas_pwd = et_pas_pwd.getText().toString();

                //发送修改密码的请求
                final LinkedHashMap<String, String> params = new LinkedHashMap<>();
                params.put("id", id + "");
                params.put("new_pwd", new_pwd);
                params.put("old_pwd", old_pwd);
                params.put("pas_pwd", pas_pwd);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_CHANGE_PWD, true, params, new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                //在子线程中弹出提示
                                Looper.prepare();
                                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                {
                                    try {
                                        if (response.code() == 200) {
                                            String data = response.body().string();
                                            Log.d(TAG, "onResponse: " + data);
                                            JSONObject jsonObject = new JSONObject(data);
                                            int code = jsonObject.getInt("code");
                                            final String msg = jsonObject.getString("msg");

                                            if (code == 1) {
                                                //修改成功,重新登录(放在Toast前面,否则执行不到)
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                getActivity().startActivity(intent);
                                                getActivity().finish();
                                            }

                                            //在子线程中弹出提示
                                            Looper.prepare();
                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                            Looper.loop();




                                        } else {
                                            //在子线程中弹出提示
                                            Looper.prepare();
                                            Toast.makeText(getActivity(), "请求成功,修改失败,响应码为:" + response.code(), Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }).start();


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


}
