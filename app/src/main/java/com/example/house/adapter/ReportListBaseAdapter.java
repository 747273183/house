package com.example.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.house.R;
import com.example.house.model.ReportList;

import java.util.List;

public class ReportListBaseAdapter extends BaseAdapter {

    private Context context;
    private List<ReportList> reportLists;

    public ReportListBaseAdapter(Context context, List<ReportList> reportLists) {
        this.context = context;
        this.reportLists = reportLists;
    }

    @Override
    public int getCount() {
        return reportLists.size();
    }

    @Override
    public ReportList getItem(int position) {
        return reportLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reportLists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.listitem_reportlist,null,false);
            viewHolder=new ViewHolder();
            viewHolder.tv_master= convertView.findViewById(R.id.tv_master);
            viewHolder.tv_city= convertView.findViewById(R.id.tv_city);
            viewHolder.tv_company= convertView.findViewById(R.id.tv_company);
            viewHolder.tv_time= convertView.findViewById(R.id.tv_time);

            convertView.setTag(viewHolder);

        }
        else
        {
           viewHolder= (ViewHolder) convertView.getTag();
        }

        //获得数据
        ReportList reportList = getItem(position);
        //设置数据
        viewHolder.tv_master.setText(reportList.getMaster());
        viewHolder.tv_city.setText("("+reportList.getCity()+")");
        viewHolder.tv_company.setText("委托单位:"+reportList.getCompany());
        viewHolder.tv_time.setText("提交时间:"+reportList.getTime());

        return convertView;
    }

    private  class ViewHolder
    {
        TextView tv_master;
        TextView tv_city;
        TextView tv_company;
        TextView tv_time;
    }
}
