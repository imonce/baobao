package com.example.gobywind.xcccf.model;

/**
 * Created by Administrator on 2016/7/8.
 */
public class ResponseData {

    private String token;
    private Info info;
    private Err err;

    public Info getInfo() {
        return info;
    }

    public ResponseData setInfo(Info info) {
        this.info = info;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Err getError() {
        return err;
    }

    public void setError(Err error) {
        this.err = error;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "token=" + token +
                ", error=" + err +
                '}';
    }
}
