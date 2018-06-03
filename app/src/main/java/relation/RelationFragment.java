package relation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.AddActivity;
import com.example.ljw14.tencentadvance.InfoMe;
import com.example.ljw14.tencentadvance.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class RelationFragment extends Fragment implements View.OnClickListener{

    private List<People> peopleList = new ArrayList<>();

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


        /**
         * 这一段代码用来初始化RecycleView的显示信息
         * 主要修改第一行的 initInformation() 函数
         */
        initPeopleList();
        //Log.d("RelationFragment", "ok");
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.relationRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        PeopleAdapter adapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(adapter);


        /**
         * 绑定按钮点击事件，事件分别定义在下面的 Onclick 函数中
         */
        Button buttonAdd = (Button) getActivity().findViewById(R.id.relationAddButton);
        Button buttonMe = (Button) getActivity().findViewById(R.id.relationMeButton);
        Button buttonEdit = (Button) getActivity().findViewById(R.id.relationEditButton);
        buttonAdd.setOnClickListener(this);
        buttonMe.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);

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
        switch(v.getId()){
            case R.id.relationAddButton:
                Intent intentAdd = new Intent(getContext(), AddActivity.class);
                intentAdd.putExtra("Title", "新建联系人");
                intentAdd.putExtra("Flag", 3);
                startActivity(intentAdd);
                break;
            case R.id.relationMeButton:
                Intent intentMe = new Intent(getContext(), InfoMe.class);
                startActivity(intentMe);
                break;

        }
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
