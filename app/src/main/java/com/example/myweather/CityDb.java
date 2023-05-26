package com.example.myweather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CityDb {

    private SQLiteDatabase db;
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";

    public CityDb(Context context, String path){
        db = context.openOrCreateDatabase(CITY_DB_NAME,Context.MODE_PRIVATE,null);
    }





    public List<City> getCityList( )
    {

        //SQLiteDatabase db = SQLiteDatabase.openDatabase(sqlPath, null, SQLiteDatabase.OPEN_READONLY);


        List<City> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * from "+CITY_TABLE_NAME,null);
        while(cursor.moveToNext())
        {
            String province = cursor.getString(cursor.getColumnIndex("province"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String allPY = cursor.getString(cursor.getColumnIndex("allpy"));
            String allFirstPY = cursor.getString(cursor.getColumnIndex("allfirstpy"));
            String firstPY = cursor.getString(cursor.getColumnIndex("firstpy"));
            City item = new City(province,city,number,allPY,allFirstPY,firstPY);
            list.add(item);
        }
        return list;
    }
}
