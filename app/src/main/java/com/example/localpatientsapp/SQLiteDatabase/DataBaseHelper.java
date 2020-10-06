package com.example.localpatientsapp.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * created by ketan 29-9-2020
 */
public class DataBaseHelper {

    public Context context;
    private SQLiteHelper sqLiteHelper;
    public static SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(Context context) {
        this.context = context;
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    /**
     * store the data to the database
     * insertion operation is performed using this function
     */
    public Long saveToLocalTable(String table, ContentValues contentValues) {
        long count = 0;
        try {
            count = sqLiteDatabase.insert(table, null, contentValues);

            if (count != -1) {
                Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Data not stored properly", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * this function is used to get the patient list from the database
     * returns json array of patient list
     */
    public JSONArray getPatientListFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
        Log.e("query_log", "SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
        JSONObject json = null;

        if (cursor.getCount() != 0) {
            try {
                while (cursor.moveToNext()) {
                    json = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        json.put(cursor.getColumnName(i), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
                    }
                    jArray.put(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jArray;
    }

    /**
     * this function returns the single record of patient against the selected id
     */
    public JSONArray getPatientByID(String id) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.ID + " ='" + id + "'", null);

        JSONObject json = null;

        if (cursor.getCount() != 0) {
            try {
                while (cursor.moveToNext()) {
                    json = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        json.put(cursor.getColumnName(i), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
                    }
                    jArray.put(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jArray;
    }
}