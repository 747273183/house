package com.example.house.model;

import java.io.Serializable;

public class PeportListDetail implements Serializable {
    private String member_id;//       鉴定人
    private String company;//         委托单位
    private String master;//          户主姓名
    private String city;//            住房地址
    private String use_id;//        房屋用途
    private String complete;//        建成时间
    private String floor;//          层数
    private String type_id;//         结构类型
    private String foundation;//      地基检查结果
    private String wall;//           墙体检查结果
    private String beam; //           梁、柱检查结果
    private String build;    //       楼、屋盖检查结果
    private String judge;    //       综合评定等级
    private String opinion;  //       处理建议
    private String appraiser;  //     鉴定人
    private String auditor;   //      审核人
    private String ratify;   //     批准人
    private String img_top;   //      房屋现状图1
    private String img_left;  //      房屋现状图1
    private String img_right;  //     房屋现状图1
    private String statement;//       声明

    public PeportListDetail() {
    }

    public PeportListDetail(String member_id, String company, String master, String city, String use_id, String complete, String floor, String type_id, String foundation, String wall, String beam, String build, String judge, String opinion, String appraiser, String auditor, String ratify, String img_top, String img_left, String img_right, String statement) {
        this.member_id = member_id;
        this.company = company;
        this.master = master;
        this.city = city;
        this.use_id = use_id;
        this.complete = complete;
        this.floor = floor;
        this.type_id = type_id;
        this.foundation = foundation;
        this.wall = wall;
        this.beam = beam;
        this.build = build;
        this.judge = judge;
        this.opinion = opinion;
        this.appraiser = appraiser;
        this.auditor = auditor;
        this.ratify = ratify;
        this.img_top = img_top;
        this.img_left = img_left;
        this.img_right = img_right;
        this.statement = statement;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getUse_id() {
        return use_id;
    }

    public void setUse_id(String use_id) {
        this.use_id = use_id;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getBeam() {
        return beam;
    }

    public void setBeam(String beam) {
        this.beam = beam;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getRatify() {
        return ratify;
    }

    public void setRatify(String ratify) {
        this.ratify = ratify;
    }

    public String getImg_top() {
        return img_top;
    }

    public void setImg_top(String img_top) {
        this.img_top = img_top;
    }

    public String getImg_left() {
        return img_left;
    }

    public void setImg_left(String img_left) {
        this.img_left = img_left;
    }

    public String getImg_right() {
        return img_right;
    }

    public void setImg_right(String img_right) {
        this.img_right = img_right;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "PeportListDetail{" +
                "member_id='" + member_id + '\'' +
                ", company='" + company + '\'' +
                ", master='" + master + '\'' +
                ", city='" + city + '\'' +
                ", use_id=" + use_id +
                ", complete='" + complete + '\'' +
                ", floor='" + floor + '\'' +
                ", type_id='" + type_id + '\'' +
                ", foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", beam='" + beam + '\'' +
                ", build='" + build + '\'' +
                ", judge='" + judge + '\'' +
                ", opinion='" + opinion + '\'' +
                ", appraiser='" + appraiser + '\'' +
                ", auditor='" + auditor + '\'' +
                ", ratify='" + ratify + '\'' +
                ", img_top='" + img_top + '\'' +
                ", img_left='" + img_left + '\'' +
                ", img_right='" + img_right + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }
}
