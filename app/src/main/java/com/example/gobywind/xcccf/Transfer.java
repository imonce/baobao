package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.model.User;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.MessageSender;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/9.
 */
public class Transfer extends Activity {
//    public static final String EXTRA_MESSAGE = "com.example.gobywind.qsbc.MESSAGE";
    private EditText IdText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        initView();
    }

    public void initView(){
        IdText = (EditText) findViewById(R.id.userId);

    }

    public void nextTransfer(View view){
        String userId = IdText.getText().toString();
        boolean flag = checkPhoneNumber(userId);
        if(flag) {
            User user = new User();
            user.setUserId(userId);
            Gson gson = new Gson();
            String response = MessageSender.sendMessage(Config.getUserInfoUrl(),gson.toJson(user));
            ResponseData responseData = gson.fromJson(response, ResponseData.class);
            System.out.println(responseData);
            System.out.println();
            if(responseData.getError() == null) {
                Intent intent = new Intent(this, Transfertoaccount.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else{
                Toast.makeText(this, responseData.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please input correct phone number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            this.finish();
        }
    }

    public boolean checkPhoneNumber(String phone) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(phone);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
