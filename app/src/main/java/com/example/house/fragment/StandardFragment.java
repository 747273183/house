package com.example.house.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.house.R;
import com.example.house.model.Constant;
import com.example.house.util.MyOkHttpUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class StandardFragment extends Fragment {

    private View view;
    private ListView lv_standard;
    private SimpleAdapter adapter;

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

//        lv_standard = view.findViewById(R.id.lv_standard);
//        //设置适配器
//        List<Map<String,Object>> data= getStandardData();
//        String[] from={"foundation","wall","beam","concrete","build","id","part_id","level"};
//        adapter=new SimpleAdapter(getContext(),data,R.layout.listitem_standard,from,to);
//        lv_standard.setAdapter(adapter);

        //standard: {"code":1,"msg":"请求成功",
        // "data":{"foundation":[{"id":1,"part_id":1,"level":"a","des":"完好、地基、基础稳固。"},{"id":2,"part_id":1,"level":"b","des":"基础埋深略小；有轻微不均匀沉降。"},{"id":3,"part_id":1,"level":"c","des":"基础埋深度偏小；有明显不均匀沉降。"},{"id":4,"part_id":1,"level":"d","des":"地基失稳；基础局部或整体坍塌。"}],
        // "wall":[{"id":5,"part_id":2,"level":"a","des":"砌筑质量良好；无裂缝、剥蚀、歪斜。"},{"id":10,"part_id":2,"level":"b","des":"砌筑质量一般或较差；有轻微开裂或剥蚀。"},{"id":14,"part_id":2,"level":"c","des":"砌筑质量很差；裂缝较多，剥蚀严重；纵横墙体脱闪，个别墙体歪斜。"},{"id":15,"part_id":2,"level":"d","des":"墙体严重开裂；部分严重歪斜；局部倒塌或有倒塌危险。"}],
        // "beam":[{"id":6,"part_id":3,"level":"a","des":"无腐朽或虫蛀；无变形；有轻微干缩裂缝。"},{"id":16,"part_id":3,"level":"b","des":"轻微腐朽或虫蛀；有轻微变形；构件纵向干缩裂缝深度超过木材直径的1\/6。"},{"id":17,"part_id":3,"level":"c","des":"有明显腐朽或虫蛀；梁檩跨中明显挠曲，或出现横纹裂缝；梁檩端部出现劈裂；柱身明显歪斜；柱础错位；构件纵向干缩裂缝深度超过木材直径的1\/4；榫卯节点有破损或有拔榫迹象。"},{"id":18,"part_id":3,"level":"d","des":"严重腐朽或虫蛀；梁檩跨中出现严重裂缝；柱身严重歪斜；柱础严重错位；构件纵向干缩裂缝深度超过木材直径的1\/3；榫卯节点失效或多处拔榫。"}],
        // "concrete":[{"id":7,"part_id":4,"level":"a","des":"表面无剥蚀；无裂缝；无变形。"},{"id":19,"part_id":4,"level":"b","des":"表面轻微剥蚀、或出现轻微开裂、变形。"},{"id":20,"part_id":4,"level":"c","des":"表面剥蚀严重；出现明显开裂、变形。"},{"id":21,"part_id":4,"level":"d","des":"表面剥蚀严重，钢筋外露；出现严重开裂变形。"}],
        // "build":[{"id":8,"part_id":5,"level":"a","des":"无变形；无渗水现象；椽、瓦完好。"},{"id":22,"part_id":5,"level":"b","des":"局部轻微沉陷；较小范围渗水；椽、瓦个别部位有损坏。"},{"id":23,"part_id":5,"level":"c","des":"较大范围出现沉陷；较大范围渗水；椽、瓦有部分损坏。"},{"id":24,"part_id":5,"level":"d","des":"较大范围渗水漏雨；椽、瓦损坏严重。"}]
        // }}

        LinkedHashMap<String, String> params=new LinkedHashMap<String, String>();
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
                        String data = response.body().string();
                        Log.d(TAG, "standard: " + data);
                        JSONObject jsonObject = new JSONObject(data);
                        int code = jsonObject.getInt("code");
                        final String msg = jsonObject.getString("msg");

                        if (code == 1) {
//                            //请求成功,显示个人信息
//                            final ImageView iv_head=view.findViewById(R.id.iv_head);
//                            final TextView tv_name= view.findViewById(R.id.tv_name);
//                            final TextView tv_phone=view.findViewById(R.id.tv_phone);
//
//                            //gson解析
//                            JSONObject data1 = jsonObject.getJSONObject("data");
//                            Gson gson=new Gson();
//                            final Account account1 = gson.fromJson(data1.toString(), Account.class);
//                            Log.d(TAG, "onResponse: "+account1.toString());
//
//
//                            //显示
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    tv_name.setText("姓名:"+account1.getName());
//                                    tv_phone.setText("手机号:"+account1.getTel());
//
//                                    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                                            .skipMemoryCache(true);
//
//
//                                    Glide.with(getContext())
//                                            .load(account1.getImg().toString().replace("\\","/"))
//                                            .placeholder(R.drawable.place_image)//图片加载出来前，显示的图片
//                                            .error(R.drawable.error_image)//图片加载失败后，显示的图片
//                                            .apply(mRequestOptions)
//                                            .into(iv_head);
//                                }
//                            });
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
