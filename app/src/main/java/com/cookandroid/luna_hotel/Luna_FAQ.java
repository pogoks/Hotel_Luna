package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Luna_FAQ extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;
    TabHost tabHost1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_faq);

        expandableListView = findViewById(R.id.exapandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        Button btn_back, btn_lunalogo;


        tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);
        tabHost1.setup();





        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("FAQ");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("연락처");
        tabHost1.addTab(ts2);


        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 로고를 누르면 홈화면으로 이동하는 코드
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(Home_Intent);
            }
        });
    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));
        listGroup.add(getString(R.string.group5));
        listGroup.add(getString(R.string.group6));
        listGroup.add(getString(R.string.group7));
        listGroup.add(getString(R.string.group8));
        listGroup.add(getString(R.string.group9));
        listGroup.add(getString(R.string.group10));
        listGroup.add(getString(R.string.group11));
        listGroup.add(getString(R.string.group12));
        listGroup.add(getString(R.string.group13));
        listGroup.add(getString(R.string.group14));
        listGroup.add(getString(R.string.group15));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
        for (String item : array) {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group2);
        for (String item : array) {
            list2.add(item);
        }
        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group3);
        for (String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group4);
        for (String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group5);
        for (String item : array) {
            list5.add(item);
        }

        List<String> list6 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group6);
        for (String item : array) {
            list6.add(item);
        }
        List<String> list7 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group7);
        for (String item : array) {
            list7.add(item);
        }
        List<String> list8 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group8);
        for (String item : array) {
            list8.add(item);
        }
        List<String> list9 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group9);
        for (String item : array) {
            list9.add(item);
        }
        List<String> list10 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group10);
        for (String item : array) {
            list10.add(item);
        }
        List<String> list11 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group11);
        for (String item : array) {
            list11.add(item);
        }
        List<String> list12 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group12);
        for (String item : array) {
            list12.add(item);
        }
        List<String> list13 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group13);
        for (String item : array) {
            list13.add(item);
        }
        List<String> list14 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group14);
        for (String item : array) {
            list14.add(item);
        }
        List<String> list15 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group15);
        for (String item : array) {
            list15.add(item);
        }


        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        listItem.put(listGroup.get(5),list6);
        listItem.put(listGroup.get(6),list7);
        listItem.put(listGroup.get(7),list8);
        listItem.put(listGroup.get(8),list9);
        listItem.put(listGroup.get(9),list10);
        listItem.put(listGroup.get(10),list11);
        listItem.put(listGroup.get(11),list12);
        listItem.put(listGroup.get(12),list13);
        listItem.put(listGroup.get(13),list14);
        listItem.put(listGroup.get(14),list15);
        adapter.notifyDataSetChanged();
    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}
