package com.example.ljw14.tencentadvance;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import addpeople.InformationAdapter;
import callrecord.Record;
import relation.People;
import relation.PeopleAdapter;

public class RecordDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PeopleAdapter adapter;
    private String name;
    private String telephone;
    private String startTime;
    private String endTime;
    private List<People> mPeopleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_deatillayout);


        /**
         * 这一段代码用来影藏android自带的Title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }



        /**
         * 这一段代码，用来读取前一个活动（或者碎片）传给这个活动的数据
         * 然后根据不同的使用情况，初始化当前活动的初始数据
         */
        Intent intent = getIntent();
        name = intent.getStringExtra("peopleName");
        telephone = intent.getStringExtra("peopleTelephone");
        startTime = intent.getStringExtra("StartTime");
        endTime = intent.getStringExtra("EndTime");
        TextView textViewPersonName = (TextView) findViewById(R.id.detailName);
        textViewPersonName.setText(name);
        TextView textViewPersonTel = (TextView) findViewById(R.id.detailTelephone);
        textViewPersonTel.setText(telephone);
        TextView textViewStartTime = (TextView) findViewById(R.id.detailStart);
        textViewStartTime.setText(startTime);
        TextView textViewEndTime = (TextView) findViewById(R.id.detailEnd);
        textViewEndTime.setText(endTime);



        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initInformationList();//如果数据库中还有其他数据，从这个函数初始化
        recyclerView = (RecyclerView) findViewById(R.id.detailRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter(mPeopleList);
        recyclerView.setAdapter(adapter);
    }


    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    private void initInformationList(){
        People people = new People("地址 : ", "陕西西安");
        mPeopleList.add(people);
    }
}
