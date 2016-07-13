package com.example.gobywind.xcccf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.RequestData;
import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.DataUtil;
import com.example.gobywind.xcccf.util.MD5;
import com.example.gobywind.xcccf.util.MessageSender;
import com.example.gobywind.xcccf.util.MoneyFilter;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Withdraw extends Activity implements TradePwdPopUtils.CallBackTradePwd{
    private EditText withdrawSum;
    private TradePwdPopUtils pop;
    private LinearLayout lin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw);
        initView();
    }

    public void initView(){
        withdrawSum = (EditText) findViewById(R.id.withdraw_sum);
        pop = new TradePwdPopUtils();
        pop.setCallBackTradePwd(this);
        lin = (LinearLayout)findViewById(R.id.withdraw_lin);
        InputFilter[] filters = {new MoneyFilter()};
        withdrawSum.setFilters(filters);
    }

    public void withdrawCommit(View view){
        String sum =withdrawSum.getText().toString();
        if(!sum.trim().equals(""))
            pop.showPopWindow(this, this, lin);
        else{
            Toast.makeText(this, "Please input valid number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void callBaceTradePwd(String pwd) {
        String sum = withdrawSum.getText().toString();
        double money = Double.parseDouble(sum);
        String paywordMD5 = MD5.EncodeByMd5(pwd);

        RequestData requestData = new RequestData();
        requestData.setAmount(money).setPayword(paywordMD5).setToken(DataUtil.payToken);
        Gson gson = new Gson();
        String param = gson.toJson(requestData);
        String response = MessageSender.sendMessage(Config.getWithdrawUrl(), param);
        ResponseData responseData = gson.fromJson(response, ResponseData.class);
        System.out.println(money);
        System.out.println(response);
        System.out.println(responseData);

        if(responseData.getError() == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Withdraw successfully！");
            builder.setTitle("Tip");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Withdraw.this.finish();
                }
            });
            builder.show();
        }else{
            Toast.makeText(this, responseData.getError().getMessage(), Toast.LENGTH_LONG).show();
            withdrawSum.setText("");
        }
    }


}
