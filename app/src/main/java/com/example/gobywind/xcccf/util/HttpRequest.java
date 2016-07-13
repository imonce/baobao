package com.example.gobywind.xcccf.util;


import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2016/7/8.
 */
public class HttpRequest implements Runnable{
    public Monitor lock = new Monitor();

    private String url;

    private String param;

    private String response;

    public String getUrl() {
        return url;
    }

    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getParam() {
        return param;
    }

    public HttpRequest setParam(String param) {
        this.param = param;
        return this;
    }

    public String getResponse() {
        return response;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        try{
            synchronized (lock) {
                URL mURL = new URL(url);
                conn = (HttpURLConnection) mURL.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStream out = conn.getOutputStream();
                PrintWriter printWriter = new PrintWriter(out);
                printWriter.print(param);

                printWriter.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    response = getStringFromInputStream(is);
                } else {
                    throw new NetworkErrorException("response status is " + responseCode);
                }
                lock.isNotify = true;
                lock.notify();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
