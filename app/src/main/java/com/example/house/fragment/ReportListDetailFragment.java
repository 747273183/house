package com.example.house.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;
import com.example.house.adapter.ReportListBaseAdapter;
import com.example.house.model.Constant;
import com.example.house.model.PeportListDetail;
import com.example.house.model.ReportList;
import com.example.house.util.MyOkHttpUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    private static final String TAG = "ReportListFragment=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reportlist_detail, container, false);
       ImageView iv_back= view.findViewById(R.id.iv_back);
       iv_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().popBackStack();
           }
       });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //获得report_id
        Intent intent = getActivity().getIntent();
        long report_id = intent.getLongExtra("report_id",0);

        //请求结果格式
        /*{
                "code":1,
                "msg":"请求成功",

                "data":{
                    "id":148,
                "member_id":"高惟新",
                "company":"淮滨县住房和城乡建设局",
                "master":"张三",
                "use_id":"住宅"
                ,"time":"2019-10-13",
                "img_top":"http:\/\/zhapp.t.100help.net\/public\/uploads\/20191013\\76f45ab14b803387a32d36f59966d10e.",
                "img_left":"http:\/\/zhapp.t.100help.net\/public\/uploads\/20191013\\c7c3340c371a47d07cc2c85dbd67fd89.",
                "img_right":"http:\/\/zhapp.t.100help.net\/public\/uploads\/20191013\\4737270bae02efa50cf38ef5d2f3878a.",
                "complete":"上世纪八九十年代",
                "floor":"一层",
                "type_id":"砖木",
                "foundation":"b",
                "wall":"b",
                "beam":"b",
                "build":"b",
                "judge":"B",
                "auditor":"李国栋",
                "ratify":"刘金超",
                "opinion":"该住房为安全性住房。",
                "appraiser":"王利伟、杨志伟",
                "statement":"1、报告无鉴定人、审核人、批准人无效，报告有涂改现象无效，报告未盖“鉴定专用章”无效；\r\n2、如您对本报告有异议，请于收到报告 15 日内向我公司书面提请复议，逾期不予受理。\r\n                ",
                    "city":"河南省信阳市淮滨县 三空桥乡张营村"}
        }*/

        //获得要显示数据的控件



        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("report_id",report_id+"");

        MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_REPORT_DETAIL, false, params, new Callback() {
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
                        Log.d(TAG, "report_detail: " + body);
                        JSONObject jsonObject = new JSONObject(body);
                        int code = jsonObject.getInt("code");
                        final String msg = jsonObject.getString("msg");

                        if (code == 1) {
//                            请求成功,获得数据封装在Standard对象中
//                            gson解析
                            JSONObject data = jsonObject.getJSONObject("data");
                            Gson gson = new Gson();
                            gson.fromJson(data.toString(), PeportListDetail.class);

                            //显示
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

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
