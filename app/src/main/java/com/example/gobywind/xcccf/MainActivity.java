package com.example.gobywind.xcccf;

        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.Matrix;
        import android.os.Bundle;

        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v4.view.ViewPager.OnPageChangeListener;
        import android.util.DisplayMetrics;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.animation.Animation;
        import android.view.animation.TranslateAnimation;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.gobywind.xcccf.model.CountView;
        import com.example.gobywind.xcccf.model.RequestData;
        import com.example.gobywind.xcccf.model.ResponseData;
        import com.example.gobywind.xcccf.util.Config;
        import com.example.gobywind.xcccf.util.DataUtil;
        import com.example.gobywind.xcccf.util.MessageSender;
        import com.google.gson.Gson;

public class MainActivity extends Activity {

    private ViewPager viewPager;//页卡内容
    private ImageView imageView;// 动画图片
    private TextView textView1, textView2, textView3;
    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    static public int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1, view2, view3;//各个页卡

    private CountView money;
    private int label;

    private int page2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        label = intent.getIntExtra(Login.EXTRA_MESSAGE, -1);
        setContentView(R.layout.activity_main);
        InitImageView();
        InitViewPager();
        InitTextView();
        textView1.setTextColor(Color.YELLOW);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestData requestData = new RequestData();
        requestData.setToken(DataUtil.payToken);
        Gson gson = new Gson();
        String response = MessageSender.sendMessage(Config.getSelfInfoUrl(), gson.toJson(requestData));
        ResponseData responseData = gson.fromJson(response, ResponseData.class);

        if (responseData.getError() == null) {
            double amount = responseData.getInfo().getMoney();
            money.setNumber((float) amount);
            money.showNumberWithAnimation((float) amount);
        } else {
            Toast.makeText(this, responseData.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.lay1, null);
        view2 = inflater.inflate(R.layout.lay2, null);
        view3 = inflater.inflate(R.layout.lay3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
        money = (CountView) view2.findViewById(R.id.balance);
    }

    /**
     * 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     * 3
     */
    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.b1).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 头标点击监听 3
     */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i)
        {
            index = i;
        }

        public void onClick(View v)
        {
            viewPager.setCurrentItem(index);

            textView1.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);

            switch (index)
            {
                case 0:
                    textView1.setTextColor(Color.YELLOW);
                    break;
                case 1:
                    textView2.setTextColor(Color.YELLOW);
                    setPage2();
                    break;
                case 2:
                    textView3.setTextColor(Color.YELLOW);
                    setPage3();
                    break;
            }
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        public void onPageScrollStateChanged(int arg0) {
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        public void onPageSelected(int arg0) {
            Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);//显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            textView1.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);
            switch (currIndex) {
                case 0:
                    textView1.setTextColor(Color.YELLOW);
                    break;
                case 1:
                    textView2.setTextColor(Color.YELLOW);
                    setPage2();
                    break;
                case 2:
                    textView3.setTextColor(Color.YELLOW);
                    setPage3();
                    break;
            }
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
         //   Toast.makeText(MainActivity.this, "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews)
        {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount()
        {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
    }


    public boolean setPage2() {
        RequestData requestData = new RequestData();
        requestData.setToken(DataUtil.payToken);
        Gson gson = new Gson();
        String response = MessageSender.sendMessage(Config.getSelfInfoUrl(), gson.toJson(requestData));
        ResponseData responseData = gson.fromJson(response, ResponseData.class);

        if (responseData.getError() == null) {
            double amount = responseData.getInfo().getMoney();
            money.setNumber((float) amount);
            money.showNumberWithAnimation((float) amount);
        } else {
            Toast.makeText(this, responseData.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void setPage3() {

    }


    public void QRCode(View view){
        Intent intent = new Intent(this, MipcaActivityCapture.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void transfer(View view){
        Intent intent = new Intent(this, Transfer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void recharge(View view){
        Intent intent = new Intent(this, Recharge.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void withdrawCash(View view){
        Intent intent = new Intent(this, Withdraw.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void changePayword(View view){
        Intent intent = new Intent(this, ChangePayword.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void logout(View view){
        RequestData requestData = new RequestData();
        requestData.setToken(DataUtil.payToken);
        MessageSender.sendMessage(Config.getLogoutUrl(), (new Gson()).toJson(requestData));
        DataUtil.payToken = null;
        DataUtil.userId = null;
        MainActivity.this.finish();
    }
}

