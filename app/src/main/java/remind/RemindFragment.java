package remind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ljw14.tencentadvance.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class RemindFragment extends Fragment implements View.OnClickListener{

    private List<Remind> remindList = new ArrayList<>();
    private RemindAdapter recordAdapter;
    private RecyclerView recyclerView;
    private Button remindButtonAdd;

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
        View view = inflater.inflate(R.layout.remindlayout, container, false);
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
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.remindRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recordAdapter = new RemindAdapter(remindList);
        recyclerView.setAdapter(recordAdapter);

        remindButtonAdd = (Button) getActivity().findViewById(R.id.remindButtonAdd);
        remindButtonAdd.setOnClickListener(this);
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
            case R.id.remindButtonAdd:

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
        String time;
        String event;
        String finished;
        for(int i = 0; i < 1; i ++){
            time = "2019年1月1日";
            event = "给父母拜年";
            finished = "未完成";
            remindList.add(new Remind(time, event, finished));
            time = "2018年12月24日";
            event = "考研第一天，加油";
            finished = "未完成";
            remindList.add(new Remind(time, event, finished));
            time = "2018年9月1日";
            event = "大四开始了，写新学期计划";
            finished = "未完成";
            remindList.add(new Remind(time, event, finished));
        }
    }
}
