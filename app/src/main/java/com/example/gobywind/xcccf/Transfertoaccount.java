package com.example.gobywind.xcccf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.RequestData;
import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.DataUtil;
import com.example.gobywind.xcccf.util.MD5;
import com.example.gobywind.xcccf.util.MessageSender;
import com.example.gobywind.xcccf.util.MoneyFilter;
import com.google.gson.Gson;


public class Transfertoaccount extends Activity implements TradePwdPopUtils.CallBackTradePwd {
    private EditText moneyText;
    private TextView userInfoText;
    private String message;
    private TradePwdPopUtils pop;
    private LinearLayout lin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_to_account);
        initView();
    }

    public void initView(){
        moneyText = (EditText) findViewById(R.id.money);
        userInfoText = (TextView) findViewById(R.id.userInfo);
        lin = (LinearLayout) findViewById(R.id.transfer_lin);
        pop = new TradePwdPopUtils();
        pop.setCallBackTradePwd(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = bundle.getString("userId");
        InputFilter[] filters = {new MoneyFilter()};
        moneyText.setFilters(filters);

        RequestData requestData = new RequestData();
        requestData.setUserId(message);
        Gson gson = new Gson();
        String request = gson.toJson(requestData);
        String response = MessageSender.sendMessage(Config.getUserInfoUrl(), request);
        ResponseData responseData = gson.fromJson(response, ResponseData.class);
        System.out.println(responseData);
        userInfoText.setText(responseData.getInfo().getUsername() + "\n" + message);
    }

    public void doTransfer(View view){
        String sum = moneyText.getText().toString();
        if(sum.trim().equals("")){
            Toast.makeText(this, "Please input valid amount!", Toast.LENGTH_LONG).show();
            moneyText.setText("");
            moneyText.hasFocus();
        }else{
            pop.showPopWindow(this, this, lin);
        }
    }

    @Override
    public void callBaceTradePwd(String pwd) {
        String moneyStr = moneyText.getText().toString();
        double money = Double.parseDouble(moneyStr);
        RequestData requestData = new RequestData();
        String paywordMD5 = MD5.EncodeByMd5(pwd);

        Gson gson = new Gson();
        requestData.setToken(DataUtil.payToken).setAmount(money).setUserId(message).setPayword(paywordMD5);
        String param = gson.toJson(requestData);
        String response = MessageSender.sendMessage(Config.getTransferUrl() ,param);

        ResponseData responseData = gson.fromJson(response, ResponseData.class);
        System.out.println(response);
        System.out.println(responseData);

        if(responseData.getError() == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Recharge successfully！");
            builder.setTitle("Tip");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent();
                    Transfertoaccount.this.setResult(RESULT_OK , intent);
                    Transfertoaccount.this.finish();
                }
            });
            builder.show();
        }else {
            Toast.makeText(this,  responseData.getError().getMessage(), Toast.LENGTH_LONG).show();
            moneyText.setText("");
            moneyText.hasFocus();
        }
    }
}
