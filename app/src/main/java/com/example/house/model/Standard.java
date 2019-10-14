package com.example.house.model;

import java.io.Serializable;

public class Standard implements Serializable {


    private String partName;       //组成部分名称
    private String id;            //评定标准id
    private String part_id;       //结构组成部分id
    private String level;         //评定标准等级
    private String des;          // 评定标准描述

    public Standard() {
    }

    public Standard(String partName, String id, String part_id, String level, String des) {
        this.partName = partName;
        this.id = id;
        this.part_id = part_id;
        this.level = level;
        this.des = des;
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

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Override
    public String toString() {
        return

        level+":"+des;
    }
}
