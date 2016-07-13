package com.example.gobywind.xcccf.util;

/**
 * Created by Administrator on 2016/7/9.
 */
public class MessageSender {

    public static String sendMessage(String url, String param){
        String response;
        HttpRequest fetcher = new HttpRequest().setUrl(url).setParam(param);
        new Thread(fetcher).start();
        synchronized (fetcher.lock) {
            while(!fetcher.lock.isNotify) {
                try {
                    fetcher.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        response = fetcher.getResponse();
        System.out.println(response);
        return response;
    }
}
