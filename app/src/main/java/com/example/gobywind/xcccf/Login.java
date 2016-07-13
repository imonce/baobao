package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.model.User;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.DataUtil;
import com.example.gobywind.xcccf.util.MD5;
import com.example.gobywind.xcccf.util.MessageSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class Login extends Activity
{
    private boolean flag = false;
    public static final String EXTRA_MESSAGE = "com.example.gobywind.qsbc.MESSAGE";
//    public static final String url = "http://10.180.82.153:3000/login";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginFunc(View view) throws IOException {
        EditText usernameText = (EditText) findViewById(R.id.us_name);
        EditText userPasswd = (EditText) findViewById(R.id.us_pwd);

        String username = usernameText.getText().toString();
        String passwd = userPasswd.getText().toString();

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        if(username.trim().equals("") || passwd.trim().equals("")){
            Toast.makeText(this, "PhoneNumber and password can't be empty", Toast.LENGTH_SHORT).show();
        }else {
            String md5 = MD5.EncodeByMd5(passwd);
            User user = new User();
            user.setUserId(username).setPassword(md5);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String param = gson.toJson(user);
            System.out.println(param);
            // send messages to pay server
            String result = MessageSender.sendMessage(Config.getLoginPayUrl(), param);

            //for test
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);

            ResponseData data = gson.fromJson(result, ResponseData.class);
            Log.d("response", data.toString());
            if(data == null){
                Toast.makeText(this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
                return;
            }
            //for tests
            if(flag) {
                //send messages to crowd funding server
                String cfResult = MessageSender.sendMessage(Config.getLoginCfUrl(), param);
                ResponseData cfResponse = gson.fromJson(cfResult, ResponseData.class);
                System.out.println(cfResponse);
                if (cfResponse == null) {
                    Toast.makeText(this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
//            && cfResponse.getToken() != null
            if (data.getToken() != null) {
//                DataUtil.cfToken = cfResponse.getToken();
                DataUtil.payToken = data.getToken();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, data.getError().getMessage(), Toast.LENGTH_SHORT).show();
                userPasswd.setText("");
                userPasswd.hasFocus();
            }
        }
    }

    public void goToRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}

