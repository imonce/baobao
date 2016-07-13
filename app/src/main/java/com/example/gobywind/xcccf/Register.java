package com.example.gobywind.xcccf;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.model.User;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.MD5;
import com.example.gobywind.xcccf.util.MessageSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends Activity {
    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmText;
    private EditText phoneText;
    private EditText payText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView(){
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        confirmText = (EditText) findViewById(R.id.confirm);
        phoneText = (EditText) findViewById(R.id.phone);
        payText = (EditText) findViewById(R.id.payword);
    }


    public void register(View view){
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirm = confirmText.getText().toString();
        String phone = phoneText.getText().toString();
        String pay = payText.getText().toString();

        if(phone.trim().equals("")){
            Toast.makeText(this, "Phone Number can't be empty", Toast.LENGTH_SHORT).show();
        }else if(password.trim().equals("") || confirm.trim().equals("")){
            Toast.makeText(this, "Password can;t be empty", Toast.LENGTH_SHORT).show();
        }else if(username.trim().equals("")){
            Toast.makeText(this, "Nickname can't be empty", Toast.LENGTH_SHORT).show();
        }else if (pay.trim().equals("")){
            Toast.makeText(this, "Pay word can't be empty", Toast.LENGTH_SHORT).show();
        }else{
            if(password.trim().length() < 6){
                Toast.makeText(this, "Password should be longer than 6", Toast.LENGTH_SHORT).show();
            }else if(!password.trim().equals(confirm.trim())){
                Toast.makeText(this, "Please repeat your password correctly", Toast.LENGTH_SHORT).show();
            }else if(!checkPhoneNumber(phone.trim())){
                Toast.makeText(this, "Please phone number correctly", Toast.LENGTH_SHORT).show();
            }else if(pay.trim().length() != 6){
                Toast.makeText(this, "Pay word length must be 6", Toast.LENGTH_SHORT).show();
            }else{
                String passwordMD5 = MD5.EncodeByMd5(password.trim());
                String paywordMD5 = MD5.EncodeByMd5(pay.trim());
                User newUser = new User();
                newUser.setUserId(phone).setPassword(passwordMD5).setUsername(username).setPayword(paywordMD5);

                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String param = gson.toJson(newUser);
                System.out.println(param);
                String response = MessageSender.sendMessage(Config.getRegisterPayUrl(), param);
                ResponseData error = gson.fromJson(response, ResponseData.class);
                System.out.println(response);
//
//                String cfRegister = MessageSender.sendMessage(Config.getRegisterCfUrl(), param);
//                ResponseData cfError = gson.fromJson(cfRegister, ResponseData.class);
//                Log.d("responseData", cfError.toString());
//                Log.d("regster", cfRegister);

                if(error.getError() == null){
                    Toast.makeText(this, "Register successfully!", Toast.LENGTH_SHORT).show();
                    Register.this.finish();
                }else {
                    Toast.makeText(this, error.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    confirmText.setText("");
                    payText.setText("");
                }
            }

        }
    }
    //check whether the phone number is legal
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
