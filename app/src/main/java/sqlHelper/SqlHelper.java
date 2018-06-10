package sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sidebar.SortModel;

import static android.content.ContentValues.TAG;

public class SqlHelper {
    private final String[] relationTable = {"username", "phonenumber"};
    private final String[] noteTable = {"date", "text"};

    private MySQLiteOpenHelper dbHelper = null;

    public SqlHelper(Context context){
        dbHelper = new MySQLiteOpenHelper(context);
    };

    public List traverse(String tableName){
        int len = 0;
        String[] columns = new String[10];//暂时最多10个选项
        if (tableName == "tb_mycontacts") {
            columns = relationTable;
            len = relationTable.length;
        } else if (tableName == "tn_notes") {
            columns = noteTable;
            len = noteTable.length;
        }
        Cursor cursor = dbHelper.getWritableDatabase().query(tableName, columns,null,
                null,null, null,null,null);

        List<ContentValues> dataList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                ContentValues values = new ContentValues();
                for(int i=0; i<len; i++){
                    values.put(columns[i], cursor.getString(cursor.getColumnIndex(columns[i])));
                }
                dataList.add(values);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    public void insert(String tableName, String[] args) {
        int len = 0;
        String[] columns = new String[10];//暂时最多10个选项
        if (tableName == "tb_mycontacts") {
            columns = relationTable;
            len = relationTable.length;
        } else if (tableName == "tn_notes") {
            columns = noteTable;
            len = noteTable.length;
        }
        ContentValues values = new ContentValues();
        for (int i = 0; i < args.length && i < len; i++) {
            values.put(columns[i], args[i]);
        }
        dbInsertData(values, tableName);
    }

    public void dbInsertData(ContentValues values, String tableName){
        dbHelper.getWritableDatabase().insert(tableName,null,values);
    }

    public ContentValues getAllInfoWithCondition(String tableName, String[] items, String[] args){
        int len = 0;
        String[] columns = new String[10];//暂时最多10个选项
        String selection = new String();

        for(int i=0; i<items.length && i <len; i++)
            Log.d(TAG, "getAllInfoWithCondition: " + items[i]);

        if(tableName == "tb_mycontacts"){
            columns = relationTable;
            len = relationTable.length;
        }
        else if(tableName == "tn_notes") {
            columns = noteTable;
            len = noteTable.length;
        }
        for(int i=0; i<items.length && i <len; i++){
            if(i==0)
                selection = selection + items[i] + "=?";
            else//不知道这里对不对
                selection = selection + " AND " + items[i] + "=?";
        }
        Cursor cursor =dbHelper.getWritableDatabase().query(tableName, columns, selection,
                args,null,null,null,null);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            values.put(columns[i], cursor.getString(cursor.getColumnIndex(columns[i])));
        }
        cursor.close();
        return values;
    }
}
