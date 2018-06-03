package callrecord;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ljw14.tencentadvance.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import relation.People;
import relation.PeopleAdapter;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class CallRecordFragment extends Fragment implements View.OnClickListener{

    private int showStyle = 1;
    private List<Record> recordListAll = new ArrayList<>();
    private List<Record> recordListMiss = new ArrayList<>();
    private RecordAdapter recordAdapter;
    private RecyclerView recyclerView;
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
        View view = inflater.inflate(R.layout.callrecordlayout, container, false);
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


        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initRecordList();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.callRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recordAdapter = new RecordAdapter(recordListAll);
        recyclerView.setAdapter(recordAdapter);



        /**
         * 绑定按钮点击事件，事件分别定义在下面的 Onclick 函数中
         * 这两个按钮用于所有通话记录和未接来电之间的切换
         * 其中“未接来电”的标志为在类 Record 中的 talked 属性
         */
        Button buttonAll = (Button) getActivity().findViewById(R.id.callAllButton);
        Button buttonMissed = (Button) getActivity().findViewById(R.id.callMissButton);
        buttonAll.setOnClickListener(this);
        buttonMissed.setOnClickListener(this);
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
            case R.id.callAllButton:
                if(showStyle == 0){
                    recordAdapter.setmRecordList(recordListAll);
                    recyclerView.setAdapter(recordAdapter);
                    recordAdapter.notifyDataSetChanged();
                    showStyle = 1;
                }
                break;
            case R.id.callMissButton:
                if(showStyle == 1){
                    recordAdapter.setmRecordList(recordListMiss);
                    recyclerView.setAdapter(recordAdapter);
                    recordAdapter.notifyDataSetChanged();
                    showStyle = 0;
                }
                break;
            default:
                break;
        }
    }

    /**
     * initInformation() 函数
     * 用来初始化 RecycleView 显示的数据
     * 这里需要从数据库中得到
     */
    private void initRecordList() {
        Date startTime = new Date();
        Date endTime = new Date();
        Random random = new Random();
        for(int i = 0; i < 5; i ++){
            boolean talked = random.nextBoolean();
            Record record1 = new Record("小明", "18858588858",
                    startTime.toString(), endTime.toString(), talked);
            recordListAll.add(record1);
            if(talked == false) recordListMiss.add(record1);

            talked = random.nextBoolean();
            Record record2 = new Record("小红", "18851238858",
                    startTime.toString(), endTime.toString(), talked);
            recordListAll.add(record2);
            if(talked == false) recordListMiss.add(record2);

            talked = random.nextBoolean();
            Record record3 = new Record("小黄", "18432588858",
                    startTime.toString(), endTime.toString(), talked);
            recordListAll.add(record3);
            if(talked == false) recordListMiss.add(record3);

            talked = random.nextBoolean();
            Record record4 = new Record("小白", "11111118858",
                    startTime.toString(), endTime.toString(), talked);
            recordListAll.add(record4);
            if(talked == false) recordListMiss.add(record4);

            talked = random.nextBoolean();
            Record record5 = new Record("未知", "7777778858",
                    startTime.toString(), endTime.toString(), talked);
            recordListAll.add(record5);
            if(talked == false) recordListMiss.add(record5);
        }
    }
}
