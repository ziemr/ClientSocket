package com.android.clientsocket.provider;

import com.android.clientsocket.util.Const;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

	private final static int DBverson = 1;
	public OpenHelper(Context context) {
		super(context, Const.DATABASE_NAME, null, DBverson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
        		"CREATE TABLE User(" +
        		"_id INTEGER,"+
        		"user STRING," +
                "password STRING," +
                "date STRING," +
                "permission STRING," +
                "data INTEGER" +
        		");");
		
		db.execSQL(
        		"CREATE TABLE Permission(" +
        		"_id INTEGER,"+
        		"permission STRING," +
                "p1 STRING," +
                "p2 STRING," +
                "p3 STRING," +
                "v1 INTEGER" +
        		");");
		
		 //Type
        db.execSQL(
        		"CREATE TABLE Tree(" +
        		"_id INTEGER,"+
        		"typeID STRING,"+
        		"typeName STRING," +
                "data1 STRING," +
                "data2 STRING" +
        		");");
        
        db.execSQL(//app init 
        		"insert into Tree(" +
        		"_id,"+
        		"typeID," +
                "typeName )" +
                "values(1,"+
                "'T0',"+
                "'STEP1'"+
        		");");
        db.execSQL(//app init 
        		"insert into Tree(" +
        		"_id,"+
        		"typeID," +
                "typeName )" +
                "values(1,"+
                "'T1',"+
                "'STEP2'"+
        		");");
        db.execSQL(//app init 
        		"insert into Tree(" +
        		"_id,"+
        		"typeID," +
                "typeName )" +
                "values(1,"+
                "'T3',"+
                "'STEP3'"+
        		");");
        
        //Content
        db.execSQL(
        		"CREATE TABLE Leaf(" +
        		"_id INTEGER,"+
                "typeID STRING," +
                "contID STRING," +
        		"contName TEXT," +
                "data1 STRING," +
                "data2 STRING" +
        		");");
        
        db.execSQL(
        		"CREATE TABLE RecordIN(" +
                "_id INTEGER,"+
                "recordid TEXT,"+
                "phone STRING," +
                "num INTEGER,"+
                "data1 STRING," +
                "data2 STRING," +
                "data3 STRING," +
                "data4 STRING," +
                "data5 STRING,"+  //piece
                "date STRING," +
                "data6 STRING,"+  //no use
                "data7 STRING"+   //no use
        		");");
        db.execSQL(
        		"CREATE TABLE Remark(" +
        		"_id INTEGER,"+
        		"typeID STRING,"+
        		"typeName STRING," +
                "data1 STRING," +
                "data2 STRING" +
        		");");
        
        db.execSQL(
        		"CREATE TABLE SharedPrefs(" +
        		"_id INTEGER,"+
        		"key STRING,"+
        		"value STRING," +
                "data1 STRING," +
                "data2 STRING" +
        		");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
