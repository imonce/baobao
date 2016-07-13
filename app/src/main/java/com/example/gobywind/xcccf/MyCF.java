package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gobywind on 2016/7/11.
 */
public class MyCF extends Activity {

    public static final String EXTRA_MESSAGE = "com.example.gobywind.xcccf.MESSAGE";

    private ViewPager Pager;
    private List<View> viewlist;// Tab页面列表
    private View view1, view2, view3;//各个页卡
    private int nowpage;

    private ListView listView1, listView2, listView3;
    SimpleAdapter adapter;
    private int[] Zcid = new int[100];

    ImageView i1, i2, i3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        label = intent.getIntExtra(login.EXTRA_MESSAGE, -1);
        setContentView(R.layout.my_cf);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        InitImageView();
        InitViewPager();
        InitListView();
        Set_start();
    }

    private void InitViewPager()
    {
        Pager = (ViewPager) findViewById(R.id.vPager_mycf);
        viewlist = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.mystart, null);
        view2 = inflater.inflate(R.layout.myjoin, null);
        view3 = inflater.inflate(R.layout.mycollect, null);
        viewlist.add(view1);
        viewlist.add(view2);
        viewlist.add(view3);
        Pager.setAdapter(new MycfPageAdapter(viewlist));
        Pager.setOnPageChangeListener(new MycfPageChangeListener());
    }

    private void InitImageView() {
        i1 = (ImageView) findViewById(R.id.cf_icon_start);
        i2 = (ImageView) findViewById(R.id.cf_icon_join);
        i3 = (ImageView) findViewById(R.id.cf_icon_collect);
    }

    private void InitListView() {
        listView1 = (ListView) view1.findViewById(R.id.list_mystart);
        listView1.setOnItemClickListener(new MyOnItemClickListener());
        listView2 = (ListView) view2.findViewById(R.id.list_myjoin);
        listView2.setOnItemClickListener(new MyOnItemClickListener());
        listView3 = (ListView) view3.findViewById(R.id.list_mycollect);
        listView3.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(MainActivity.this, String.valueOf(Zcid[position]), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyCF.this, FoundDetail.class);
            intent.putExtra(EXTRA_MESSAGE, Zcid[position]);
            startActivity(intent);
        }
    }

    public class MycfPageAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MycfPageAdapter(List<View> mListViews) {
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
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public class MycfPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            nowpage = arg0;
            switch (nowpage) {
                case 0:
                    Set_start();
                    break;
                case 1:
                    Set_join();
                    break;
                case 2:
                    Set_collect();
                    break;
            }
        }
    }

    public void set_start(View view) {
        Set_start();
    }

    public void Set_start() {
        i1.setImageDrawable(getResources().getDrawable(R.drawable.start_black));
        i2.setImageDrawable(getResources().getDrawable(R.drawable.join_grey));
        i3.setImageDrawable(getResources().getDrawable(R.drawable.collect_grey));
        Pager.setCurrentItem(0);
        nowpage = 0;
//        Toast.makeText(MyCF.this, "我发起的", Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) findViewById(R.id.cf_type);
        tv.setText("我发起的");

        adapter = new SimpleAdapter(this, Get_start_ListData(), R.layout.listview, new String[]{"title", "info", "crash"}, new int[]{R.id.title, R.id.info, R.id.crash});
        listView1.setAdapter(adapter);
    }

    public void set_join(View view) {
        Set_join();
    }

    public void Set_join() {
        i1.setImageDrawable(getResources().getDrawable(R.drawable.start_grey));
        i2.setImageDrawable(getResources().getDrawable(R.drawable.join_black));
        i3.setImageDrawable(getResources().getDrawable(R.drawable.collect_grey));
        Pager.setCurrentItem(1);
        nowpage = 1;
//        Toast.makeText(MyCF.this, "我参加的", Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) findViewById(R.id.cf_type);
        tv.setText("我参加的");
        adapter = new SimpleAdapter(this, Get_join_ListData(), R.layout.listview, new String[]{"title", "info", "crash"}, new int[]{R.id.title, R.id.info, R.id.crash});
        listView2.setAdapter(adapter);
    }

    public void set_collect(View view) {
        Set_collect();
    }

    public void Set_collect() {
        i1.setImageDrawable(getResources().getDrawable(R.drawable.start_grey));
        i2.setImageDrawable(getResources().getDrawable(R.drawable.join_grey));
        i3.setImageDrawable(getResources().getDrawable(R.drawable.collect_black));
        Pager.setCurrentItem(2);
        nowpage = 2;
//        Toast.makeText(MyCF.this, "我收藏的", Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) findViewById(R.id.cf_type);
        tv.setText("我收藏的");
        adapter = new SimpleAdapter(this, Get_collect_ListData(), R.layout.listview, new String[]{"title", "info", "crash"}, new int[]{R.id.title, R.id.info, R.id.crash});
        listView3.setAdapter(adapter);
    }

    private List<Map<String, Object>> Get_start_ListData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        //Database--------------------------------------------------------------------------------------

        Zcid[0] = 1;

        map.put("title", "start1");
        map.put("info", "google 1");
        map.put("crash", "10000");
        list.add(map);

        return list;
    }

    private List<Map<String, Object>> Get_join_ListData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        //Database--------------------------------------------------------------------------------------

        Zcid[0] = 2;

        map.put("title", "join1");
        map.put("info", "google 1");
        map.put("crash", "10000");
        list.add(map);

        return list;
    }

    private List<Map<String, Object>> Get_collect_ListData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        //Database--------------------------------------------------------------------------------------

        Zcid[0] = 3;

        map.put("title", "collect1");
        map.put("info", "google 1");
        map.put("crash", "10000");
        list.add(map);

        return list;
    }

}