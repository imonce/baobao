package com.example.gobywind.xcccf.util;

/**
 * Created by Administrator on 2016/7/9.
 */
public class Config {
    private static final String loginPayUrl = "http://10.180.87.154:3000/login";
    private static final String registerPayUrl = "http://10.180.87.154:3000/register";
    private static final String withdrawUrl = "http://10.180.87.154:3000/withdraw";
    private static final String rechargeUrl = "http://10.180.87.154:3000/topup";
    private static final String userInfoUrl = "http://10.180.87.154:3000/getUserInfo";
    private static final String selfInfoUrl = "http://10.180.87.154:3000/getSelfInfo";
    private static final String transferUrl = "http://10.180.87.154:3000/transfer";
    private static final String changePayUrl = "http://10.180.87.154:3000/updateUserInfo";
    private static final String logoutUrl = "http://10.180.87.154:3000/logout";

    private static final String loginCfUrl = "http://10.180.87.154:4000/login";
    private static final String registerCfUrl = "http://10.180.87.154:4000/register";

    public static String getLogoutUrl() {
        return logoutUrl;
    }

    public static String getSelfInfoUrl() {
        return selfInfoUrl;
    }

    public static String getChangePayUrl() {
        return changePayUrl;
    }

    public static String getUserInfoUrl() {
        return userInfoUrl;
    }

    public static String getTransferUrl() {
        return transferUrl;
    }

    public static String getWithdrawUrl() {
        return withdrawUrl;
    }

    public static String getLoginPayUrl() {
        return loginPayUrl;
    }

    public static String getRegisterPayUrl() {
        return registerPayUrl;
    }

    public static String getRechargeUrl() {
        return rechargeUrl;
    }

    public static String getLoginCfUrl() {
        return loginCfUrl;
    }

    public static String getRegisterCfUrl() {
        return registerCfUrl;
    }
}
