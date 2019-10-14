package com.example.house.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;
import com.example.house.adapter.StandardBaseAdapter;
import com.example.house.model.Constant;
import com.example.house.model.Standard;
import com.example.house.util.MyOkHttpUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class StandardFragment extends Fragment {

    private View view;
    private ListView lv_standard;
    private BaseAdapter adapter;

    private static final String TAG = "StandardFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_standard, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        lv_standard = view.findViewById(R.id.lv_standard);
//        适配器的数据
        final List<Map<String,Standard[]>> adapter_data= new ArrayList<>();


//请求结果格式
        //standard: {"code":1,"msg":"请求成功",
        // "data":{
        // "foundation":[{"id":1,"part_id":1,"level":"a","des":"完好、地基、基础稳固。"},{"id":2,"part_id":1,"level":"b","des":"基础埋深略小；有轻微不均匀沉降。"},{"id":3,"part_id":1,"level":"c","des":"基础埋深度偏小；有明显不均匀沉降。"},{"id":4,"part_id":1,"level":"d","des":"地基失稳；基础局部或整体坍塌。"}],
        // "wall":[{"id":5,"part_id":2,"level":"a","des":"砌筑质量良好；无裂缝、剥蚀、歪斜。"},{"id":10,"part_id":2,"level":"b","des":"砌筑质量一般或较差；有轻微开裂或剥蚀。"},{"id":14,"part_id":2,"level":"c","des":"砌筑质量很差；裂缝较多，剥蚀严重；纵横墙体脱闪，个别墙体歪斜。"},{"id":15,"part_id":2,"level":"d","des":"墙体严重开裂；部分严重歪斜；局部倒塌或有倒塌危险。"}],
        // "beam":[{"id":6,"part_id":3,"level":"a","des":"无腐朽或虫蛀；无变形；有轻微干缩裂缝。"},{"id":16,"part_id":3,"level":"b","des":"轻微腐朽或虫蛀；有轻微变形；构件纵向干缩裂缝深度超过木材直径的1\/6。"},{"id":17,"part_id":3,"level":"c","des":"有明显腐朽或虫蛀；梁檩跨中明显挠曲，或出现横纹裂缝；梁檩端部出现劈裂；柱身明显歪斜；柱础错位；构件纵向干缩裂缝深度超过木材直径的1\/4；榫卯节点有破损或有拔榫迹象。"},{"id":18,"part_id":3,"level":"d","des":"严重腐朽或虫蛀；梁檩跨中出现严重裂缝；柱身严重歪斜；柱础严重错位；构件纵向干缩裂缝深度超过木材直径的1\/3；榫卯节点失效或多处拔榫。"}],
        // "concrete":[{"id":7,"part_id":4,"level":"a","des":"表面无剥蚀；无裂缝；无变形。"},{"id":19,"part_id":4,"level":"b","des":"表面轻微剥蚀、或出现轻微开裂、变形。"},{"id":20,"part_id":4,"level":"c","des":"表面剥蚀严重；出现明显开裂、变形。"},{"id":21,"part_id":4,"level":"d","des":"表面剥蚀严重，钢筋外露；出现严重开裂变形。"}],
        // "build":[{"id":8,"part_id":5,"level":"a","des":"无变形；无渗水现象；椽、瓦完好。"},{"id":22,"part_id":5,"level":"b","des":"局部轻微沉陷；较小范围渗水；椽、瓦个别部位有损坏。"},{"id":23,"part_id":5,"level":"c","des":"较大范围出现沉陷；较大范围渗水；椽、瓦有部分损坏。"},{"id":24,"part_id":5,"level":"d","des":"较大范围渗水漏雨；椽、瓦损坏严重。"}]
        // }}

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        MyOkHttpUtil.postRequest(Constant.URL + Constant.PATH_STANDARD, false, params, new Callback() {
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
                        String body = response.body().string();
                        Log.d(TAG, "standard: " + body);
                        JSONObject jsonObject = new JSONObject(body);
                        int code = jsonObject.getInt("code");
                        final String msg = jsonObject.getString("msg");

                        if (code == 1) {
                            //请求成功,获得数据封装在Standard对象中
                            //gson解析
                            JSONObject data = jsonObject.getJSONObject("data");
                            Gson gson = new Gson();
                            //一、地基基础
                            JSONArray foundations = data.getJSONArray("foundation");
                            Map<String,Standard[]> map1=new HashMap<>();
                            Standard[] standard1=new Standard[4];
                            for (int i = 0; i <foundations.length() ; i++) {
                                Standard standard = gson.fromJson(foundations.getJSONObject(i).toString(), Standard.class);
                                standard1[i]=standard;
                            }
                            map1.put("一、地基基础",standard1);
                            adapter_data.add(map1);

                            //二、墙体
                            JSONArray walls = data.getJSONArray("wall");
                            Map<String,Standard[]> map2=new HashMap<>();
                            Standard[] standard2=new Standard[4];
                            for (int i = 0; i <walls.length() ; i++) {
                                Standard standard = gson.fromJson(walls.getJSONObject(i).toString(), Standard.class);
                                standard2[i]=standard;
                            }
                            map2.put("二、墙体",standard2);
                            adapter_data.add(map2);

                            //三、梁、柱(木梁、柱)
                            JSONArray beams = data.getJSONArray("beam");
                            Map<String,Standard[]> map3=new HashMap<>();
                            Standard[] standard3=new Standard[4];
                            for (int i = 0; i <beams.length() ; i++) {
                                Standard standard = gson.fromJson(beams.getJSONObject(i).toString(), Standard.class);
                                standard3[i]=standard;
                            }
                            map3.put("三、梁、柱(木梁、柱)",standard3);
                            adapter_data.add(map3);

                            //四、梁、柱(混凝土梁、柱)
                            JSONArray concretes = data.getJSONArray("concrete");
                            Map<String,Standard[]> map4=new HashMap<>();
                            Standard[] standard4=new Standard[4];
                            for (int i = 0; i <concretes.length() ; i++) {
                                Standard standard = gson.fromJson(concretes.getJSONObject(i).toString(), Standard.class);
                                standard4[i]=standard;
                            }
                            map4.put("四、梁、柱(混凝土梁、柱)",standard4);
                            adapter_data.add(map4);

                            // 五、楼、屋盖
                            JSONArray builds = data.getJSONArray("build");
                            Map<String,Standard[]> map5=new HashMap<>();
                            Standard[] standard5=new Standard[4];
                            for (int i = 0; i <builds.length() ; i++) {

                                Standard standard = gson.fromJson(builds.getJSONObject(i).toString(), Standard.class);
                                standard5[i]=standard;
                            }
                            map5.put("五、楼、屋盖",standard5);
                            adapter_data.add(map5);

                            //显示
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //设置适配器
                                     adapter=new StandardBaseAdapter(getContext(),adapter_data);
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
