package com.example.house.model;

import java.io.Serializable;

//账号
public class Account implements Serializable {
    private  int id;
    private String user;
    private String name;
    private String tel;
    private String img;

    public Account() {
    }

    public Account(int id, String user, String name, String tel, String img) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.tel = tel;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
