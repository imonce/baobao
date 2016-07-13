package com.example.gobywind.xcccf.model;

/**
 * Created by Administrator on 2016/7/8.
 */
public class User {

    private String userId;

    private String password;

    private String username;

    private String payword;

    public String getPayword() {
        return payword;
    }

    public User setPayword(String payword) {
        this.payword = payword;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }



    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", payword='" + payword + '\'' +
                '}';
    }
}

