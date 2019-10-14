package com.example.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.house.R;
import com.example.house.model.Standard;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 评定标准适配器
 */
public class StandardBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Standard[]>> adapter_data;

    public StandardBaseAdapter(Context context, List<Map<String, Standard[]>> adapter_data) {

        this.context = context;
        this.adapter_data = adapter_data;

    }

    @Override
    public int getCount() {
        return adapter_data.size();
    }

    @Override
    public Map<String, Standard[]> getItem(int position) {
        return adapter_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_standard, null, false);

            viewHolder=new ViewHolder();
            viewHolder.tv_partname= convertView.findViewById(R.id.tv_partname);
            viewHolder.tv_level_desc=convertView.findViewById(R.id.tv_level_desc);

            convertView.setTag(viewHolder);

        }
        else
        {
          viewHolder= (ViewHolder) convertView.getTag();

        }
        //获得数据
        Map<String, Standard[]> map = getItem(position);
        //设置数据
        Iterator<Map.Entry<String, Standard[]>> it = map.entrySet().iterator();
        String partName="";
        String str_level_des="";
        while (it.hasNext())
        {
            Map.Entry<String, Standard[]> entry = it.next();
            partName = entry.getKey();
            Standard[] standards = entry.getValue();
            for (int i = 0; i < standards.length; i++) {
                str_level_des+=standards[i]+"\n";
            }
        }
        viewHolder.tv_partname.setText(partName);
        viewHolder.tv_level_desc.setText(str_level_des);



        return convertView;
    }

    private  class  ViewHolder
    {
        TextView tv_partname;
        TextView tv_level_desc;
    }
}
