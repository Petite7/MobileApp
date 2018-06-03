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

public class PersonActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private PeopleAdapter adapter;
    private List<People> peopleList = new ArrayList<>();
    private String name;
    private String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlayout);



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
        TextView textViewPersonName = (TextView) findViewById(R.id.personName);
        textViewPersonName.setText(name);
        TextView textViewPersonTel = (TextView) findViewById(R.id.personTelephone);
        textViewPersonTel.setText(telephone);




        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initInformationList();//如果数据库中还有其他数据，从这个函数初始化
        recyclerView = (RecyclerView) findViewById(R.id.personRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(adapter);



        /**
         * 绑定按钮点击事件，事件分别定义在下面的 Onclick 函数中
         */
        Button buttonPersonEdit = (Button) findViewById(R.id.personButtonEdit);
        Button buttonPersonDelete = (Button) findViewById(R.id.personButtonDelete);
        buttonPersonEdit.setOnClickListener(this);
        buttonPersonDelete.setOnClickListener(this);
    }



    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    /**
     * 如果数据库中还有其他数据，从这个函数初始化
     */
    private void initInformationList(){
        People people = new People("地址 : ", "陕西西安");
        peopleList.add(people);
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
            case R.id.personButtonEdit:
                Toast.makeText(this, "Edit编辑", Toast.LENGTH_SHORT).show();
                Intent intentAdd = new Intent(PersonActivity.this, AddActivity.class);
                intentAdd.putExtra("Title", "编辑联系人");
                intentAdd.putExtra("Flag", 1);
                intentAdd.putExtra("PeopleName", name);
                intentAdd.putExtra("PeopleTelephone", telephone);
                startActivity(intentAdd);//用于启动活动 AddActivity 并将上面的数据传给它
                break;
            case R.id.personButtonDelete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                //删除数据，更新peopleList，并需要调用dataRefresh()函数
                dataRefresh();
                break;
        }
    }

    /**
     * 刷新RecycleView显示
     */
    private void dataRefresh(){
        //change(peopleList);//更新完成的数据
        adapter.setmPeopleList(peopleList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
