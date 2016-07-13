package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Gobywind on 2016/7/11.
 */
public class FoundDetail extends Activity
{
    int label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        label = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, -1);
        setContentView(R.layout.activity_found_detail);
        Toast.makeText(FoundDetail.this, String.valueOf(label), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SetPage();
    }

    private void SetPage()
    {

    }

    public void JoinCF(View view)
    {
        Toast.makeText(FoundDetail.this, "Join", Toast.LENGTH_SHORT).show();
    }

    public void CollectCF(View view)
    {
        Toast.makeText(FoundDetail.this, "Collect", Toast.LENGTH_SHORT).show();
    }

}
