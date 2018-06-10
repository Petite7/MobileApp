package callrecord;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.LongDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import relation.People;
import relation.PeopleAdapter;

import static android.content.ContentValues.TAG;


/**
 * 未标注的为从《第一行代码》书中得到
 */
public class CallRecordFragment extends Fragment implements View.OnClickListener{

    private Map<String, String> place = new HashMap<String, String>();


    private int showStyle = 0;
    private int showTime = 0;
    private List<Record> recordListAll = new ArrayList<>();
    private List<Record> recordListMiss = new ArrayList<>();
    private List<Record> recordListAllWeek = new ArrayList<>();
    private List<Record> recordListMissWeek = new ArrayList<>();
    private List<Record> recordListAllMonth = new ArrayList<>();
    private List<Record> recordListMissMonth = new ArrayList<>();
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

        Button buttonAll = (Button) getActivity().findViewById(R.id.callAllButton);
        Button buttonMissed = (Button) getActivity().findViewById(R.id.callMissButton);
        Button buttonAllTime = (Button) getActivity().findViewById(R.id.callAll);
        Button buttonWeek = (Button) getActivity().findViewById(R.id.callWeek);
        Button buttonMonth = (Button) getActivity().findViewById(R.id.callMonth);
        buttonAll.setOnClickListener(this);
        buttonMissed.setOnClickListener(this);
        buttonAllTime.setOnClickListener(this);
        buttonWeek.setOnClickListener(this);
        buttonMonth.setOnClickListener(this);


        placeInitiate();
        //ListView contactsView  = (ListView) findViewById(R.id.contacts_view);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        //contactsView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_CALL_LOG},1);
        }
        else {
            readContacts();
        }

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.callRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recordAdapter = new RecordAdapter(recordListAll);
        recyclerView.setAdapter(recordAdapter);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.callAllButton:
                showStyle = 0;
                break;
            case R.id.callMissButton:
                showStyle = 1;
                break;
            case R.id.callAll:
                showTime = 0;
                break;
            case R.id.callWeek:
                showTime = 1;
                break;
            case R.id.callMonth:
                showTime = 2;
                break;
        }
        showDate(showStyle, showTime);
    }

    private void showDate(int showStyle, int showTime){
        if(showStyle == 0){
            switch(showTime){
                case 0:
                    recordAdapter.setmRecordList(recordListAll);
                    break;
                case 1:
                    recordAdapter.setmRecordList(recordListAllWeek);
                    break;
                case 2:
                    recordAdapter.setmRecordList(recordListAllMonth);
                    break;
            }
        }
        else if(showStyle == 1){
            switch(showTime){
                case 0:
                    recordAdapter.setmRecordList(recordListMiss);
                    break;
                case 1:
                    recordAdapter.setmRecordList(recordListMissWeek);
                    break;
                case 2:
                    recordAdapter.setmRecordList(recordListMissMonth);
                    break;
            }
        }
        recyclerView.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetChanged();
    }

    private void placeInitiate(){
        String file = new String("mobile.txt");
        InputStream is = null;
        try{
            is = getActivity().getAssets().open(file);
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

    private void resolve(String src){
        String pool[] = src.split(",");
        String num = pool[1];
        String toplace = pool[2] + pool[3] + "," + pool[4];
        place.put(num, toplace);
    }

    private void readContacts()
    {
        Cursor cursor = null;
        try{
            cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null,null,null,null);
            if(cursor !=null)
            {
                while(cursor.moveToNext()){
                    String displayName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    if (displayName==null){
                        displayName = "未知号码";
                    }
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String callLogDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String time;
                    String type;
                    int callLogTime;
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(callLogDate));
                    int callLogType =cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    if (callLogType == 1){
                        type ="来电";
                    }else if (callLogType ==2){
                        type = "拨出";
                    }else
                        type = "未接";
                    callLogTime = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    if (type =="未接"){
                        time ="未接";
                    }else {
                        time = timeChange(callLogTime);
                    }
                    String place = getPlace(number);
                    //contactList.add(displayName+"\n" +number+"  "+"\n"+ d + "   " + type  +"\n"+ time+"\n" + place);

                    Record record = new Record();
                    record.setDisplayName(displayName);
                    record.setPhone(number);
                    record.setDate(d.toString());
                    record.setType(type);
                    record.setDurationTime(time);
                    record.setLocation(place);
                    recordListAll.add(record);
                    if(record.getType() == "未接")
                        recordListMiss.add(record);

                    if(compareDay(d, 7)){
                        recordListAllWeek.add(record);
                        if(record.getType() == "未接")
                            recordListMissWeek.add(record);
                    }

                    if(compareDay(d, 30)){
                        recordListAllMonth.add(record);
                        if(record.getType() == "未接")
                            recordListMissMonth.add(record);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null)
            {
                cursor.close();
            }
        }
    }

    /**
     * 判断一个记录的日期离现在是否小于t天
     * @param date
     * @param t
     * @return
     */
    private boolean compareDay(Date date, int t){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        //rightNow.add(Calendar.YEAR,-1);//日期减1年
        //rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR,+t);//日期加t天
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        if(new Date().after(dt1)){
            Log.d(TAG, "compareWeek: " + "new -->"+sdf.format(new Date())+" -last --->"+reStr);
            return false;
        }else{
            Log.d(TAG, "compareWeek: false");
            return true;
        }
    }

    /**
     * 归属地查询，输入一个手机号，返回手机号归属地，格式："省+市+运营商"，如：陕西省西安市，中国移动
     *
     * */
    private String getPlace(String phoneNumber){
        try {
            String tar = phoneNumber.substring(0, 7);
            return new String(place.get(tar));
        } catch (Exception e){
            return new String("");
        }
    }
    public void onRequestPermissionResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    private static String timeChange(int time){
        int h = 0;
        int m = 0;
        int s = 0;
        int temp = time % 3600;
        if (time >3600){
            h = time/3600;
            if(temp!=0){
                if (temp>60){
                    m = temp/60;
                    if (temp%60!= 0 ){
                        s = temp/60;
                    }
                }else {
                    s = temp;
                }
            }
        }else {
            m = time/60;
            if(time%60!=0){
                s = time%60;
            }
        }
        return "通话时长："+ h +"时" + m +"分" + s + "秒";
    }
}
