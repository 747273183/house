package com.example.house.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;
import com.example.house.adapter.ReportListBaseAdapter;
import com.example.house.model.Account;
import com.example.house.model.Constant;
import com.example.house.model.ReportList;
import com.example.house.util.MyOkHttpUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReportListDetailFragment extends Fragment {

    private View view;
    private ReportListBaseAdapter adapter;
    private List<ReportList> reportLists;
    private ListView lv_standard;

    private static final String TAG = "ReportListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reportlist, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_standard = view.findViewById(R.id.lv_standard);
        reportLists=new ArrayList<>();


        //请求结果格式
       //  reportList: {"
        //  code":1,
        //  "msg":"请求成功",

        //  "data":[
        //  {"id":148,
        //  "company":"淮滨县住房和城乡建设局",
        //  "time":"2019-10-13",
        //  "master":"张三",
        //  "city":"河南省信阳市淮滨县 三空桥乡张营村"},

        //  {"id":147,
        //  "company":"洛阳市委",
        //  "time":"2019-10-10",
        //  "master":"乔峰",
        //  "city":"河南省洛阳市洛龙区洛阳大学"},

        //  {"id":146,
        //  "company":"撒逼逼",
        //  "time":"2019-10-10",
        //  "master":"公民",
        //  "city":"北京市市辖区东城区共鸣"}
        //
        //  ]}

        Intent intent = getActivity().getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        int id = account.getId();
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("id",id+"");
        params.put("page","1");
        params.put("pagenum","6");
        MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_REPORT_LIST, false, params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //在子线程中弹出提示
                Looper.prepare();
                Toast.makeText(getActivity(),"请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        String body = response.body().string();
                        Log.d(TAG, "reportList: " + body);
                        JSONObject jsonObject = new JSONObject(body);
                        int code = jsonObject.getInt("code");
                        final String msg = jsonObject.getString("msg");

                        if (code == 1) {
                            //请求成功,获得数据封装在Standard对象中
                            //gson解析
                            JSONArray data = jsonObject.getJSONArray("data");
                            Gson gson = new Gson();
                            for (int i=0;i<data.length();i++)
                            {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                ReportList reportList = gson.fromJson(jsonObject1.toString(), ReportList.class);
                                reportLists.add(reportList);
                            }
                            //显示
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //设置适配器
                                    adapter=new ReportListBaseAdapter(getContext(),reportLists);
                                    lv_standard.setAdapter(adapter);
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
}
