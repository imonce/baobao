package com.example.gobywind.xcccf.model;

/**
 * Created by Administrator on 2016/7/11.
 */
public class Info {
    private String username;
    private String sex;
    private String description;
    private double money;
    private String userId;
    private String password;
    private String payword;
    private String newPassword;
    private String newPayword;

    public String getPayword() {
        return payword;
    }

    public Info setPayword(String payword) {
        this.payword = payword;
        return this;
    }

    public String getNewPayword() {
        return newPayword;
    }

    public Info setNewPayword(String newPayword) {
        this.newPayword = newPayword;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Info setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public Info setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Info setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public Info setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Info setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getMoney() {
        return money;
    }

    public Info setMoney(double money) {
        this.money = money;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Info setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Info{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", description='" + description + '\'' +
                ", money=" + money +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", payword='" + payword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPayword='" + newPayword + '\'' +
                '}';
    }
}
