package com.example.ljw14.tencentadvance;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import relation.People;
import relation.PeopleAdapter;

public class InfoMe extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    PeopleAdapter adapter;
    private List<People> meList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_melayout);


        /**
         * 这一段代码用来影藏android自带的Title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }



        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initInformationList();//从数据库中初始化数据
        recyclerView = (RecyclerView) findViewById(R.id.meRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter(meList);
        recyclerView.setAdapter(adapter);


        /**
         * 这一段代码用来给一个按钮绑定事件： onClick() 函数
         */
        Button buttonEdit = (Button) findViewById(R.id.meButtonEdit);
        buttonEdit.setOnClickListener(this);
    }


    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    /**
     * 从数据库中初始化数据
     */
    private void initInformationList(){
        TextView textViewMeName = (TextView) findViewById(R.id.meName);
        textViewMeName.setText("数据库中我的名字");
        TextView texViewMeTel = (TextView) findViewById(R.id.meTelephone);
        texViewMeTel.setText("如果有电话的话");
        TextView textViewCamp = (TextView) findViewById(R.id.meCamp);
        textViewCamp.setText("如果有公司的话");

        People people = new People("如果有其他信息的话 : ", "陕西西安");
        meList.add(people);
    }



    /**
     * 按钮点击事件
     */
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.meButtonEdit:
                Toast.makeText(this, "EditMe", Toast.LENGTH_SHORT).show();
                Intent intentAdd = new Intent(InfoMe.this, AddActivity.class);
                intentAdd.putExtra("Title", "编辑我的信息");
                intentAdd.putExtra("Flag", 2);
                startActivity(intentAdd);
                break;
        }
    }
}
