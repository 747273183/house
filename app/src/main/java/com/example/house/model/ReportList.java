package com.example.house.model;

import java.io.Serializable;
//报告列表
public class ReportList implements Serializable {

    private int id;//         报告id
    private  String company;//    委托单位
    private String time;//       报告提交时间
    private  String master;//     户主姓名
    private String  city; //      住房地址

    public ReportList() {
    }

    public ReportList(int id, String company, String time, String master, String city) {
        this.id = id;
        this.company = company;
        this.time = time;
        this.master = master;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ReportList{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", time='" + time + '\'' +
                ", master='" + master + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
