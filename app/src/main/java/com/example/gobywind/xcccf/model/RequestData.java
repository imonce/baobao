package com.example.gobywind.xcccf.model;

/**
 * Created by Administrator on 2016/7/10.
 */
public class RequestData {
    private String token;    //current user
    private String payToken;
    private String userId;
    private String username;
    private String password;
    private double amount;
    private String payword;
    private Info info;

    public Info getInfo() {
        return info;
    }

    public RequestData setInfo(Info info) {
        this.info = info;
        return this;
    }

    public String getPayword() {
        return payword;
    }

    public RequestData setPayword(String payword) {
        this.payword = payword;
        return this;
    }

    public String getToken() {
        return token;
    }

    public RequestData setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPayToken() {
        return payToken;
    }

    public RequestData setPayToken(String payToken) {
        this.payToken = payToken;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RequestData setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RequestData setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RequestData setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public RequestData setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "token='" + token + '\'' +
                ", payToken='" + payToken + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", amount=" + amount +
                ", payword='" + payword + '\'' +
                ", info=" + info +
                '}';
    }
}
