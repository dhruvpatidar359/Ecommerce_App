package com.example.ecommerce_app.database;

// DataBase for storing the list of orders

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;


import com.example.ecommerce_app.model.imageModel;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    final static String sName = "Database.db";
    final static int sDBverion = 2;

    public DBHelper(@Nullable Context context) {
        super(context, sName, null, sDBverion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table orders"+"(id integer ,"+"name text,"+"image int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists orders");
        onCreate(sqLiteDatabase);

    }
    public boolean insertFuction(int image ,int id,String name ){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("id",id);
        values.put("image",image);

        long idd = sqLiteDatabase.insert("orders",null,values);
        return idd > 0;
    }

    public ArrayList<imageModel> getOrders(){
        ArrayList<imageModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from orders",null);

        if(cursor.moveToFirst()){
            while(true){
                imageModel model  = new imageModel();

                model.setImage(cursor.getInt(2));
                model.setImage_name(cursor.getString(1));
                model.setId(cursor.getInt(0));

                orders.add(model);
                if(!cursor.moveToNext()) break;

            }

        }
        cursor.close();
        database.close();
        return orders;
    }


}
