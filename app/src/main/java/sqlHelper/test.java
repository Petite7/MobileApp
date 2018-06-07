package sqlHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.MainActivity;
import com.example.ljw14.tencentadvance.R;

import java.util.List;

public class test implements View.OnClickListener{
//
//    private MySQLiteOpenHelper dbHelper = null;
//    private EditText et_name;
//    private EditText et_number;
//    private EditText et_name1;
//    private EditText et_name2;
//
//    dbHelper = new MySQLiteOpenHelper(this);
//    et_name1 = (EditText) findViewById(R.id.editText_select_name);
//    et_name =  (EditText) findViewById(R.id.editText_delete_name);
//
//    et_name2 = (EditText) findViewById(R.id.editText_update_name);
//    et_number = (EditText) findViewById(R.id.editText_new_phonenumber);
//
//    Button addData =(Button) findViewById(R.id.add_data);
//        addData.setOnClickListener(this);
//    Button querybutton = (Button) findViewById(R.id.select_data);
//        querybutton.setOnClickListener(this);
//    Button deletebutton=(Button) findViewById(R.id.delete_data);
//        deletebutton.setOnClickListener(this);
//    Button updatebutton=(Button) findViewById(R.id.update);
//        updatebutton.setOnClickListener(this);
//
//    @Override
    public void onClick (View v){
//        switch(v.getId()) {
//            case R.id.add_data:
//                SQLiteDatabase db=dbHelper.getWritableDatabase();
//                ContentValues values=new ContentValues();
//                values.put("username","汪昱行");
//                values.put("phonenumber","18292026841");
//                db.insert("tb_mycontacts",null,values);
//                values.clear();
//                values.put("username","陈衍琛");
//                values.put("phonenumber","123456789");
//                db.insert("tb_mycontacts",null,values);
//                values.clear();
//                values.put("username","谢泽帆");
//                values.put("phonenumber","78945621");
//                db.insert("tb_mycontacts",null,values);
//                values.clear();
//                values.put("username","陈宁");
//                values.put("phonenumber","1456451");
//                db.insert("tb_mycontacts",null,values);
//                values.clear();
//                values.put("username","rng");
//                values.put("phonenumber","144555551");
//                db.insert("tb_mycontacts",null,values);
//                toast("插入成功！");
//                break;
//            case R.id.select_data:
//                //String name1 = et_name1.getText() + "";
//                // String[] selectionArgs = new String[]{name1};
//                SQLiteDatabase db1=dbHelper.getWritableDatabase();
//                String[] columns = new String[] {"username", "phonenumber"};
//                //  String selection = "username=?";
//                String orderby="username desc";
//                Cursor cursor =db1.query("tb_mycontacts",columns,null,null,null,null,orderby,null);
//                if(cursor.moveToFirst()){
//                    do{
//                        String result_name=cursor.getString(cursor.getColumnIndex("username"));
//                        String result_number=cursor.getString(cursor.getColumnIndex("phonenumber"));
//                        Log.d("MainActivity","姓名 "+result_name);
//                        Log.d("MainActivity","手机号码 "+result_number);
//                    }while (cursor.moveToNext());
//                }
//                cursor.close();
//                break;
//            case R.id.delete_data:
//                String deletname=et_name.getText()+"";
//                String sql = "delete from tb_mycontacts where username=?";
//                boolean flag = dbHelper.execData(sql, new Object[]{deletname});
//                if (flag) {
//                    toast("删除数据成功！");
//                } else {
//                    toast("删除数据失败！");
//                }
//                break;
//            case R.id.update:
//                String updatename=et_name2.getText()+"";
//                String newphonenumber=et_number.getText()+"";
//                String sql2="update tb_mycontacts set phonenumber=? where username=?";
//                boolean flag1=dbHelper.execData(sql2,new Object[]{newphonenumber,updatename});
//                if (flag1) {
//                    toast("更新数据成功！");
//                } else {
//                    toast("更新数据失败！");
//                }
//                break;
//        }
    }
//    protected void toast(String string) {
//        Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
//    }
}
