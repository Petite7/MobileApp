package remind;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.ljw14.tencentadvance.R;

import java.util.ArrayList;
import java.util.List;

import addpeople.AddDialog;
import sqlHelper.MySQLiteOpenHelper;

import static android.content.ContentValues.TAG;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class RemindFragment extends Fragment implements View.OnClickListener{

    private List<Remind> remindList = new ArrayList<>();
    private RemindAdapter recordAdapter;
    private RecyclerView recyclerView;
    private Button remindButtonAdd;

    private MySQLiteOpenHelper dbHelper = null;

    private remind.AddDialog addDialog;

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

        Log.d(TAG, "onActivityCreated: RemindFragment ok here 1");

        refreshShow();

        Log.d(TAG, "onActivityCreated: RemindFragment ok here 2");

        remindButtonAdd = (Button) getActivity().findViewById(R.id.remindButtonAdd);
        remindButtonAdd.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.remindButtonAdd:
                showEditDialog(getView());
            default:
                break;
        }
    }

    private void refreshShow(){
        refreshData();
        Log.d(TAG, "onActivityCreated: RemindFragment ok here 3");
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.remindRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recordAdapter = new RemindAdapter(remindList);

        Log.d(TAG, "onActivityCreated: RemindFragment ok here 5");

        recyclerView.setAdapter(recordAdapter);
    }

    private void refreshData(){
        remindList.clear();
        String[] columns = new String[] {"date", "text"};
        String orderby="date desc";
        Cursor cursor =dbHelper.getWritableDatabase().query("tb_notes",columns,null,
                null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Remind remind = new Remind();
                remind.setRemindTime(cursor.getString(cursor.getColumnIndex("date")));
                remind.setRemindEvent(cursor.getString(cursor.getColumnIndex("text")));
                remindList.add(remind);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void showEditDialog(View view) {
        addDialog = new remind.AddDialog(getActivity(), R.layout.add_dialog, onClickListener);
        addDialog.show();
    }

    private void dbInsertData(ContentValues values, String tableName){
        dbHelper.getWritableDatabase().insert(tableName,null,values);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addButtonOkAAA:

                    String event = addDialog.addRemindTextEvent.getText().toString().trim();
                    String time = addDialog.addRemindTextTime.getText().toString().trim();
                    String info = addDialog.addRemindAdditionInfo.getText().toString().trim();

                    ContentValues values=new ContentValues();
                    values.put("date", time);
                    values.put("text", event);
                    dbInsertData(values, "tb_notes");

                    Toast.makeText(getContext(), "Add Success", Toast.LENGTH_SHORT).show();
                    refreshShow();
                    addDialog.dismiss();
                    break;
            }
        }
    };
}
