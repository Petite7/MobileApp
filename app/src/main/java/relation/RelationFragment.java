package relation;

import addpeople.AddDialog;
import sidebar.*;
import sqlHelper.MySQLiteOpenHelper;
import sqlHelper.SqlHelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.InfoMe;
import com.example.ljw14.tencentadvance.PersonActivity;
import com.example.ljw14.tencentadvance.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class RelationFragment extends Fragment implements View.OnClickListener{

    private final String[] relationTable = {"username", "phonenumber"};
    private final String[] noteTable = {"date", "text"};

    private RecyclerView mRecyclerView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private LinearLayoutManager manager;
    private List<SortModel> SourceDateList = new ArrayList<>();
    private SqlHelper sqlHelper = null;
    private AddDialog addDialog;

    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relationlayout, container, false);
        return view;
    }
    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button buttonAdd = (Button) getActivity().findViewById(R.id.relationAddButton);
        Button buttonMe = (Button) getActivity().findViewById(R.id.relationMeButton);
        Button buttonEdit = (Button) getActivity().findViewById(R.id.relationEditButton);
        buttonAdd.setOnClickListener(this);
        buttonMe.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);

        sqlHelper = new SqlHelper(getContext());

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) getActivity().findViewById(R.id.relationSideBar);
        dialog = (TextView) getActivity().findViewById(R.id.relationDialog);
        sideBar.setTextView(dialog);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.relationRecyclerView);

        initViews();//初始化显示
    }

    //通过联系人姓名，从数据库查询数据
    private ContentValues getAllInfoWithName(String tableName, String Name){
        String items[] = new String[]{"username"};
        String args[] = new String[]{Name};
        return sqlHelper.getAllInfoWithCondition(tableName, items, args);
    }


    //用于初始化这个界面
    private void initViews() {
        showRecycleData();

        //设置右侧SideBar触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }

            }
        });

        adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), ((SortModel)adapter.getItem(position)).getName(),
                        Toast.LENGTH_SHORT).show();
                ContentValues contentValues = getAllInfoWithName("tb_mycontacts",
                        ((SortModel)adapter.getItem(position)).getName());//通过姓名得到这个人的所有信息
                Intent intentPerson = new Intent(getContext(), PersonActivity.class);
                intentPerson.putExtra("Name", (String) contentValues.get("username"));
                intentPerson.putExtra("Telephone", (String) contentValues.get("phonenumber"));

                Log.d(TAG, "onItemClick: " + (String) contentValues.get("phonenumber"));

                startActivity(intentPerson);
            }
        });

        mClearEditText = (ClearEditText) getActivity().findViewById(R.id.relationClearEditText);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.relationAddButton:
//                Intent intentAdd = new Intent(getContext(), AddActivity.class);
//                intentAdd.putExtra("Title", "新建联系人");
//                intentAdd.putExtra("Flag", 3);
//                startActivity(intentAdd);
                showEditDialog(getView());
                break;
            case R.id.relationMeButton:
                Intent intentMe = new Intent(getContext(), InfoMe.class);
                startActivity(intentMe);
                break;
        }
    }

    //根据填充的信息显示，每次插入后重新显示
    private void showRecycleData(){
        initRelation();
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        //RecyclerView社置manager
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new SortAdapter(getContext(), SourceDateList);
        mRecyclerView.setAdapter(adapter);
    }

    //用于初始化显示的数据
    private void initRelation() {
        SourceDateList.clear();
        List<ContentValues> dataList = sqlHelper.traverse("tb_mycontacts");
        for(int i=0; i<dataList.size(); i++){
            ContentValues values = dataList.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setName((String) values.get("username"));
            sortModel.setTelephone((String) values.get("phonenumber"));
            SourceDateList.add(sortModel);
        }
    }
    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    //用于填充联系人的首字母
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(
                                filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(
                                filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }


    //点击添加联系人出现的Dialog
    private void showEditDialog(View view) {
        addDialog = new AddDialog(getActivity(), R.layout.add_dialog, onClickListener);
        addDialog.show();
    }
    //给Dialog的完成按钮添加事件，插入添加的数据到数据库
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addButtonOkAAA:

                    String name = addDialog.addTextName.getText().toString();
                    String telephone = addDialog.addTextTelephone.getText().toString();
                    String info = addDialog.addAdditionInfo.getText().toString();

                    String[] args = new String[]{name, telephone};
                    sqlHelper.insert("tb_mycontacts", args);

                    Toast.makeText(getContext(), "Add Success", Toast.LENGTH_SHORT).show();

                    showRecycleData();

                    addDialog.dismiss();
                    break;
            }
        }
    };

    //用于测试插入数据
    private void tempInsertDates(){
        String[] args = new String[2];
        for(int i=0; i<1; i++) {
            args[0] = "汪昱行";
            args[1] = "18292026841";
            sqlHelper.insert("tb_mycontacts", args);

            args[0] = "陈衍琛";
            args[1] = "123456789";
            sqlHelper.insert("tb_mycontacts", args);

            args[0] = "谢泽帆";
            args[1] = "123456789";
            sqlHelper.insert("tb_mycontacts", args);

            args[0] = "陈宁";
            args[1] = "123456789";
            sqlHelper.insert("tb_mycontacts", args);

            args[0] = "rng";
            args[1] = "123456789";
            sqlHelper.insert("tb_mycontacts", args);
        }
    }
    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private void filledData(SortModel[] date) {
        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = date[i];
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(sortModel.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }
        }
    }
}
