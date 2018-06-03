package com.example.ljw14.tencentadvance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import addpeople.Information;
import addpeople.InformationAdapter;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private InformationAdapter adapter;
    private List<Information> mInformationList = new ArrayList<>();
    private List<Information> otherInformationList = new ArrayList<>();
    private List<Information> allInformationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlayout);

        /**
         * 这一段代码用来影藏android自带的Title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }




        /**
         * 这一段代码，用来读取前一个活动（或者碎片）传给这个活动的数据
         * 首先读取 标题数据 title
         * 然后根据不同的使用情况，初始化当前活动的初始数据
         */
        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        int flag = intent.getIntExtra("Flag", 0);

        TextView title = (TextView) findViewById(R.id.addTitle);
        title.setText(Title);

        if(flag == 1) {//编辑联系人
            EditText editTextName = (EditText) findViewById(R.id.addName);
            String peopleName = intent.getStringExtra("PeopleName");
            editTextName.setText(peopleName);

            EditText editTextTel = (EditText) findViewById(R.id.addTelephone);
            String peopleTel = intent.getStringExtra("PeopleTelephone");
            editTextTel.setText(peopleTel);
        }
        else if(flag == 2){//编辑我的信息
            //初始化我的信息
        }
        else if(flag == 3){//新增联系人
            //初始化默认信息
        }




        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initInformation();
        recyclerView = (RecyclerView) findViewById(R.id.addRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InformationAdapter(mInformationList);
        recyclerView.setAdapter(adapter);



        /**
         * 这一段代码用来给两个按钮绑定事件： onClick() 函数
         */
        Button buttonAddItem = (Button) findViewById(R.id.addButtonAdd);
        Button buttonAddOk = (Button) findViewById(R.id.addButtonOk);
        buttonAddItem.setOnClickListener(this);
        buttonAddOk.setOnClickListener(this);
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
            case R.id.addButtonAdd:
                addItem(getCurrentFocus());
                break;
            case R.id.addButtonOk:
                Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    private void initInformation(){
        Information information1 = new Information("电话", "点击输入");
        mInformationList.add(information1);
        Information information2 = new Information("地址", "点击输入");
        mInformationList.add(information2);
    }


    /**
     * 这一段代码用于给 RecycleView 额外增加条目
     * 最好通过运行查看其功能
     */
    private void addItem(View view){
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入条目")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        Information information = new Information(et.getText().toString(), "点击输入");
                        otherInformationList.add(information);
                        dataRefresh();
                        //Toast.makeText(getApplicationContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消",null).show();
    }


    /**
     * dataRefresh() 函数
     * 用于刷新RecycleView 显示的内容
     * 主要用于点击了“完成”按钮后数据库修改和显示修改
     */
    private void dataRefresh(){
        allInformationList.clear();
        allInformationList.addAll(mInformationList);
        allInformationList.addAll(otherInformationList);

        adapter.setmInformationList(allInformationList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}


