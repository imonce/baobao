package com.example.gobywind.xcccf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gobywind.xcccf.model.Info;
import com.example.gobywind.xcccf.model.RequestData;
import com.example.gobywind.xcccf.model.ResponseData;
import com.example.gobywind.xcccf.util.Config;
import com.example.gobywind.xcccf.util.DataUtil;
import com.example.gobywind.xcccf.util.MessageSender;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/7/10.
 */
public class ChangePayword extends Activity {

    private EditText oldPay;
    private EditText newPay;
    private EditText confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_paycode);
        initView();
    }
    public void initView(){
        oldPay = (EditText) findViewById(R.id.oldPay);
        newPay = (EditText) findViewById(R.id.newPay);
        confirm = (EditText) findViewById(R.id.confirmPay);
    }

    public void changePaywordCommit(View view){
        String old = oldPay.getText().toString();
        String newWord = newPay.getText().toString();
        String confirmWord = confirm.getText().toString();

        if(!newWord.trim().equals(confirmWord.trim())){
            Toast.makeText(this, "Please repeat new pay word correctly!", Toast.LENGTH_SHORT).show();
        }else{
            RequestData requestData = new RequestData();
            Info info = new Info();
            Gson gson = new Gson();
            info.setPayword(old).setNewPassword(newWord);
            requestData.setToken(DataUtil.payToken).setInfo(info);

            String response = MessageSender.sendMessage(Config.getChangePayUrl(), gson.toJson(requestData));
            ResponseData responseData = gson.fromJson(response, ResponseData.class);

            if(responseData.getError() == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Change payword successfully！");
                builder.setTitle("Tip");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ChangePayword.this.finish();
                    }
                });
                builder.show();
            }else{
                Toast.makeText(this, responseData.getError().getMessage(), Toast.LENGTH_LONG).show();
                oldPay.setText("");
                confirm.setText("");
            }
        }
    }
}
