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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import addpeople.AddDialog;
import callrecord.CallRecordFragment;
import relation.People;
import relation.PeopleAdapter;
import relation.RelationFragment;
import remind.RemindFragment;

/*
*
* Test push to Github
*
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Map<String, String> place = new HashMap<String, String>();

    private void resolve(String src){
        String pool[] = src.split(",");
        String num = pool[1];
        String toplace = pool[2] + pool[3] + "," + pool[4];
        place.put(num, toplace);
    }

    private void placeInitiate(){
        String file = new String("mobile.txt");
        InputStream is = null;
        try{
            is = getAssets().open(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String now = null;
            while((now = br.readLine()) != null){
                resolve(now);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 归属地查询，输入一个手机号，返回手机号归属地，格式："省+市+运营商"，如：陕西省西安市，中国移动
     *
     * */
    private String getPlace(String phoneNumber){
        String tar = phoneNumber.substring(0, 7);
        return new String(place.get(tar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 从assets里初始化归属地信息
         * */
        placeInitiate();

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
        Button mainButtonDial = (Button) findViewById(R.id.mainButtonRemind);
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
            case R.id.mainButtonRemind:
                replaceFragment(new RemindFragment());
                break;
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
