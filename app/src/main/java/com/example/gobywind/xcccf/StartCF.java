package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Gobywind on 2016/7/11.
 */
public class StartCF extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//        label = intent.getIntExtra(login.EXTRA_MESSAGE, -1);
        setContentView(R.layout.start_cf);
//        Toast.makeText(.this, String.valueOf(label), Toast.LENGTH_SHORT).show();
    }
}
