package com.netease.shopping.model;

public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private int mark;

    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.mark = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
