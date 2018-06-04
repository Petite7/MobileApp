package com.example.ljw14.tencentadvance;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import callrecord.CallRecordFragment;
import relation.People;
import relation.PeopleAdapter;
import relation.RelationFragment;

/*
*
* Test push to Github
* 刘剑文加的一行注释
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 这一段代码用来影藏android自带的Title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        /**
         * 首先默认载入 “所有联系人” 的碎片（RelationFragment()）
         */
        replaceFragment(new RelationFragment());

        /**
         * 给三个按钮 ”联系人“，”拨号“， ”通话记录“
         * 绑定点击事件，事件分别定义在下面的 Onclick 函数中
         */
        Button mainButtonRelation = (Button) findViewById(R.id.mainButtonRelation);
        Button mainButtonDial = (Button) findViewById(R.id.mainButtonDial);
        Button mainButtonCallRecord = (Button) findViewById(R.id.mainButtonCallRecord);
        mainButtonRelation.setOnClickListener(this);
        mainButtonDial.setOnClickListener(this);
        mainButtonCallRecord.setOnClickListener(this);

    }

    /**
     * onClick 函数
     * 绑定按钮点击事件。
     * ”联系人  “按钮：触发载入“所有联系人” 的碎片（RelationFragment()）
     * ”拨号    “按钮：触发打开电话App
     * ”通话记录“按钮：触发载入“通话记录” 的碎片（CallRecordFragment()）
     */
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainButtonRelation:
                replaceFragment(new RelationFragment());
                break;
            case R.id.mainButtonCallRecord:
                replaceFragment(new CallRecordFragment());
                break;
            case R.id.mainButtonDial:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"));//表示不输入号码
                //intent.setData(Uri.parse("tel:10086"));//表示输入号码：10086
                startActivity(intent);
            default:
                break;
        }
    }

    /**
     * replaceFragment 函数
     * 用于切换碎片
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment);
        transaction.commit();
    }
}
