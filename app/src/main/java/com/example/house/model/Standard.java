package com.example.house.model;

import java.io.Serializable;

public class Standard implements Serializable {

    private String foundation;    //地基基础
    private String wall;          //墙体
    private String beam;        // 木梁、柱
    private String concrete;      //混凝土梁、柱
    private String build;         //楼、屋盖

    private String id;            //评定标准id
    private String part_id;       //结构组成部分id
    private String level;         //评定标准等级
    private String des;          // 评定标准描述

    public Standard() {
    }

    public Standard(String foundation, String wall, String beam, String concrete, String build, String id, String part_id, String level, String des) {
        this.foundation = foundation;
        this.wall = wall;
        this.beam = beam;
        this.concrete = concrete;
        this.build = build;
        this.id = id;
        this.part_id = part_id;
        this.level = level;
        this.des = des;
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

    public String getConcrete() {
        return concrete;
    }

    public void setConcrete(String concrete) {
        this.concrete = concrete;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Standard{" +
                "foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", beam='" + beam + '\'' +
                ", concrete='" + concrete + '\'' +
                ", build='" + build + '\'' +
                ", id='" + id + '\'' +
                ", part_id='" + part_id + '\'' +
                ", level='" + level + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
