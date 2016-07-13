package com.example.gobywind.xcccf.model;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Err {
    private int statusCode;

    private String host;

    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Err{" +
                "statusCode=" + statusCode +
                ", host='" + host + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
