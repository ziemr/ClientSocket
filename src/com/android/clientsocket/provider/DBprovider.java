package com.android.clientsocket.provider;


import java.util.ArrayList;
import java.util.StringTokenizer;

import com.android.clientsocket.util.Const;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DBprovider extends ContentProvider {
	private  OpenHelper mHelper;
	private SQLiteDatabase mDatabase;
	
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	@Override
	public boolean onCreate() {
		mHelper = new OpenHelper(getContext());
		mDatabase = mHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
			
	String tbl = uri.getQueryParameter(Const.TBLNAME);
	String type = uri.getQueryParameter(Const.TYPE);
	
	if (Const.TYPE_Query_SUM.equals(type)) {
		String groupby = uri.getQueryParameter(Const.OPTION);
		Cursor c = mDatabase.query(tbl, projection, selection, selectionArgs, groupby, null, sortOrder);
	}
	
	Cursor c = mDatabase.query(tbl, projection, selection, selectionArgs, null, null, sortOrder);
	return c;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
	      String tbl = uri.getQueryParameter(Const.TBLNAME);
	        String type = uri.getQueryParameter(Const.TYPE);

	        if (uri.equals(null)) {
	            return null;
	        }

	        mDatabase.beginTransaction();

	        // ﾍｨｳ｣INSERT
	        if (Const.TYPE_INSERT.equals(type)){
	            try {
	                mDatabase.insert(tbl, null, values);
	                mDatabase.setTransactionSuccessful();
	            } finally {
	                mDatabase.endTransaction();
	            }
	            return uri;
	        }

	        // ･ﾞ･�ﾁINSERT
	        ContentValues ins_value = new ContentValues();

	        String keys = uri.getQueryParameter(Const.OPTION);
	        ArrayList<String> keylist = analysisStrToList(keys, Const.KEY_DELIMITER);
	        int keys_size = keylist.size();
	        String search_key = null;
	        String key_value = null;
	        int cnt_rec = 0;
	        int cnt_item = 0;

	        // ﾑ}ﾊ��ｳｩ`･ﾉINSERT
	        for(cnt_rec = 0; cnt_rec < Const.SETTING_MAX_VALUE; cnt_rec++){
	            // ･�ｳｩ`･ﾉﾒｪﾋﾘﾊ�
	            for(cnt_item = 0; cnt_item < keys_size; cnt_item++){
	                search_key = keylist.get(cnt_item) + Integer.toString(cnt_rec);
	                key_value = values.getAsString(search_key);

	                if (key_value == null){
	                    break;
	                }

	                ins_value.put(keylist.get(cnt_item), key_value);
	            }

	            // ﾈｫ､ﾆ､ﾎ嵭ﾄｿ､Oｶｨ､ｷ､ﾊ､､､ﾈINSERT､ｷ､ﾊ､､
	            if (cnt_item >= keys_size){
	                try {
	                    mDatabase.insert(tbl, null, ins_value);
	                } catch (Exception e) {
	                    mDatabase.endTransaction();
	                }
	                ins_value.clear();
	            }
	        }

	        // ･ｳ･ﾟ･ﾃ･ﾈ
	        mDatabase.setTransactionSuccessful();
	        mDatabase.endTransaction();
	        return uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		   String tbl = uri.getQueryParameter(Const.TBLNAME);
	        String type = uri.getQueryParameter(Const.TYPE);

	        if ((uri == null) || (!Const.TYPE_DELETE.equals(type))) {
	            return -1;
	        }

	        int deleteCount = 0;

	        mDatabase.beginTransaction();

	        try {
	            deleteCount = mDatabase.delete(tbl, selection, selectionArgs);
	            mDatabase.setTransactionSuccessful();
	        } finally {
	            mDatabase.endTransaction();
	        }
	        return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
	        String tbl = uri.getQueryParameter(Const.TBLNAME);
	        String type = uri.getQueryParameter(Const.TYPE);

	        if ((uri == null) || (!Const.TYPE_UPDATE.equals(type))) {
	            return -1;
	         }

	        int updateCount = 0;

	        mDatabase.beginTransaction();

	        try {
	            updateCount = mDatabase.update(tbl, values, selection, selectionArgs);
	            mDatabase.setTransactionSuccessful();
	        } finally {
	            mDatabase.endTransaction();
	         }

	        return updateCount;
	}
    private static ArrayList<String> analysisStrToList(String str, String sepa) {
        if (str == null || sepa == null) {
            return null;
        }

        ArrayList<String> keylist = new ArrayList<String>();
        StringTokenizer strtok = new StringTokenizer(str, sepa);
        int iTokenCount = strtok.countTokens();

        if (iTokenCount < 1) {
            return null;
        }

        for (int i = 0; i < iTokenCount; i++) {
            keylist.add(strtok.nextToken());
        }

        return keylist;
    }
}
