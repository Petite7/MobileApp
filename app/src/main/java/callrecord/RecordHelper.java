package callrecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordHelper extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> contactList = new ArrayList<>();
    String type;
    String time;
    int callLogTime;
    private Map<String, String> place = new HashMap<String, String>();


    public RecordHelper(){
        placeInitiate();
        ListView contactsView  = (ListView) findViewById(R.id.detailRecyclerView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        contactsView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CALL_LOG},1);
        }
        else {
            readContacts();
        }
    }

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
        try {
            String tar = phoneNumber.substring(0, 7);
            return new String(place.get(tar));
        } catch (Exception e){
            return new String("");
        }
    }

    private void readContacts()
    {
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
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
                    contactList.add(displayName+"\n" +number+"  "+"\n"+ d + "   " + type  +"\n"+ time+"\n" + place);
                }
                adapter.notifyDataSetChanged();
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

    public void onRequestPermissionResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
