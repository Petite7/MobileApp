package com.example.ljw14.tencentadvance;

import relation.People;
import relation.PeopleAdapter;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RelationActivity extends AppCompatActivity{

    private List<People> peopleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationlayout);


        /**
         * 这一段代码用来影藏android自带的Title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }



        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initPeopleList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.relationRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PeopleAdapter adapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(adapter);
    }





    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    private void initPeopleList(){
        for(int i = 0; i < 4; i ++){
            People people1 = new People("小张", "18858588858");
            peopleList.add(people1);
            People people2 = new People("小王", "18851234458");
            peopleList.add(people2);
            People people3 = new People("小黄", "18854328858");
            peopleList.add(people3);
            People people4 = new People("小红", "18858585435");
            peopleList.add(people4);
        }
    }
}
