package com.android.clientsocket.provider;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.android.clientsocket.util.BodyLine;
import com.android.clientsocket.util.Const;
import com.android.clientsocket.util.Utils;
import com.android.clientsocket.util.dataStructure;
import com.android.clientsocket.util.dataStructure.strLeaf;
import com.android.clientsocket.util.dataStructure.strRecordin;
import com.android.clientsocket.util.dataStructure.strTree;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class DBOperator {
	private ContentResolver mResolver;
	private Uri baseUri;
	private static Uri providerUri;
	private Context mContext;

	static {
		android.net.Uri.Builder builder = new Uri.Builder();
		builder.scheme("content");
		builder.authority(Const.AUTHORITY);
		providerUri = builder.build();
	}

	public DBOperator(Context context) {
		try {
			mContext = context;
			mResolver = mContext.getContentResolver();
			baseUri = providerUri;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Uri getUri(String type, String table, String option) {
		android.net.Uri.Builder builder = baseUri.buildUpon();
		if (type != null)
			builder.appendQueryParameter("type", type);
		builder.appendQueryParameter("table", table);
		if (option != null)
			builder.appendQueryParameter("option", option);
		return builder.build();
	}

	public Uri getTableUri(String table) {
		android.net.Uri.Builder builder = baseUri.buildUpon();
		builder.appendQueryParameter("table", table);
		return builder.build();
	}
	public int gettableCount(String table) {
		Uri uri = getUri(null, table, null);
		String selection = "";
		Cursor c = null;
		try {
			c = mResolver.query(uri, new String[] { "*" }, selection, null,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int loop_cnt = c.getCount();
		return loop_cnt;
	}
	//////////////////////////////////////////////
	public void initAppData(String[] key, String[] value) {

		String option = "_id" + Const.KEY_DELIMITER + "key"
				+ Const.KEY_DELIMITER + "value";

		Uri uri = getUri(Const.TYPE_INSERT_MULTI, Const.TABLE_SharedPrefs, option);
		ContentValues values = new ContentValues();

		for (int cnt = 0; cnt < key.length; cnt++) {
			values.put("_id"+ Integer.toString(cnt), cnt+1);
			values.put("key"+ Integer.toString(cnt), key[cnt]);
			values.put("value"+ Integer.toString(cnt), value[cnt]);
		}

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateSharedData(String key, String value) {

		// check

		Uri uri = getUri(Const.TYPE_UPDATE, Const.TABLE_SharedPrefs, null);
		// String where = "recordid" + "=" + recordid;
		ContentValues values = new ContentValues();
		values.put("value", value);

		try {
			mResolver.update(uri, values, "key =?", new String[] { key });
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getSharedDataValue(String key) {
		Uri uri = getUri(null, Const.TABLE_SharedPrefs, null);

		String selection = "key=?";
		String[] selectionArgs = new String[] { key };
		String typeName = null;
		Cursor c = null;

		try {
			c = mResolver.query(uri, new String[] { "value" }, selection,
					selectionArgs, null);

			if (c == null || c.getCount() == 0)
				return Const.NO_DATA;
			c.moveToFirst();
			typeName = c.getString(0);
		} catch (Exception e) {
			// TODO: handle exception
			typeName = Const.NO_DATA;
		}
		c.close();
		return typeName;
	}
	
	public String getSharedData(String key ,String vol) { //data  for selectd
		Uri uri = getUri(null, Const.TABLE_SharedPrefs, null);

		String selection = "key=?";
		String[] selectionArgs = new String[] { key };
		String typeName = null;
		Cursor c = null;

		try {
			c = mResolver.query(uri, new String[] { vol }, selection,
					selectionArgs, null);

			if (c == null || c.getCount() == 0)
				return Const.NO_DATA;
			c.moveToFirst();
			typeName = c.getString(0);
		} catch (Exception e) {
			// TODO: handle exception
			typeName = Const.NO_DATA;
		}
		c.close();
		return typeName;
	}
	///////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	//
	//
	//
	////////////////////////////////////////////////////////////////////////////////
	public String LoginCheck(String user, String password) {
		String result = Const.LOGIN_NOENTER;
		Uri uri = getUri(null, Const.TABLE_User, null);

		// systime
//		String strtime = Utils.systemFrmtTime("yyyy/MM/dd HH:mm:ss").substring(0, 10);

		String selection = "user=? and password=?";
		String[] selectionArgs = new String[] { user,password};
		Cursor c = null;

		try {
			c = mResolver.query(uri, new String[] { "permission" }, selection,
					selectionArgs, null);

			if (c.getCount() > 0) {
				c.moveToFirst();

				if (Const.LOGIN_DANGERWARNING.equals(c.getString(0))) { // dangerwarning
					result = Const.LOGIN_DANGERWARNING;
				} else {
					result = Const.LOGIN_USER;
				}
			} else {
				result = Const.LOGIN_NOENTER;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public Boolean isUserExist(String phonenum) {
		Uri uri = getUri(null, Const.TABLE_User, null);

		String selection = "user=?";
		int count = 0;
		Cursor c = null;
		Boolean isExist = false;
		try {
			c = mResolver.query(uri, new String[] { "rowid" }, selection,
					new String[] { phonenum }, null);
			count = c.getCount();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (count != 0)
			isExist = true;
		return isExist;
	}

	public void insertUsers(String phone,String No) {

		if (phone == null) return;
		if (phone.trim().length() == 0) return;
		if (isUserExist(phone)) {
			return;
		}
		Uri uri = getUri(Const.TYPE_INSERT, Const.TABLE_User, null);
		ContentValues values = new ContentValues();

		
		String telArray[] = phone.split(" ");
		String telphone = "";
		for (String telStr : telArray) {
			telphone += telStr;
		}

		values.put("password", No);
		values.put("user", telphone);
//		values.put("password", name); // TEL
//		values.put("used", 1);
//		values.put("date", strtime);

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserNo() {
		Uri uri = getUri(null, Const.TABLE_User, null);

//		String selection = "typeID=?";
//		String[] selectionArgs = new String[] { Utils.toTreeID(typeID) };
		String typeName = null;
		Cursor c = null;

		try {
			c = mResolver.query(uri, new String[] { "password" }, null,
					null, null);

			if (c == null || c.getCount() == 0)
				return Const.NO_DATA;
			c.moveToFirst();
			typeName = c.getString(0);
		} catch (Exception e) {
			// TODO: handle exception
			typeName = Const.NO_DATA;
		}
		c.close();
		return typeName;
	}
	public String getUsers() {
		Uri uri = getUri(null, Const.TABLE_User, null);

//		String selection = "password=?";
		int count = 0;
		Cursor c = null;
		String user = null;
		try {
			c = mResolver.query(uri, new String[] { "user" }, null,
					null, null);
			count = c.getCount();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (count != 0){
			c.moveToFirst();
			 user= c.getString(0);
		}
		return user;
	}
	///////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	//
	//
	//
	////////////////////////////////////////////////////////////////////////////////
	
	public void insertRecordin(BodyLine mBodyLine) {

		Uri uri = getUri(Const.TYPE_INSERT, Const.TABLE_RecordIN, null);
		ContentValues values = new ContentValues();

		int count = gettableCount(Const.TABLE_RecordIN);

		values.put("_id", count + 1);
		values.put(Const.RECORDIN_COLUMN_RECORDID, mBodyLine.getRecordid());
		values.put("phone", mBodyLine.getPhone());
		values.put("num", mBodyLine.getNum());
		values.put("data1", mBodyLine.getData1());
		values.put("data2", mBodyLine.getData2());
		values.put("data3", mBodyLine.getData3());
		//0104?
		values.put("data4", "0");
		// data5 ---> piece
		values.put("data5", "0");
		values.put("data6", mBodyLine.getRemark());
		values.put("date", mBodyLine.getDate());

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//for trans
//		updataTransTableFlag(Const.TABLE_RecordIN, Const.KEY_TRANS_IN);
	}
	
	public void insertRecordin(strRecordin struct) {

		Uri uri = getUri(Const.TYPE_INSERT, Const.TABLE_RecordIN, null);
		ContentValues values = new ContentValues();

		int count = gettableCount(Const.TABLE_RecordIN);

		values.put("_id", count + 1);
		values.put(Const.RECORDIN_COLUMN_RECORDID, struct.getRecordid());
		values.put("phone", struct.getPhone());
		values.put("num", struct.getNum());
		values.put("data1", struct.getData1());
		values.put("data2", struct.getData2());
		values.put("data3", struct.getData3());
		//0104?
		values.put("data4", struct.getData4());
		// data5 ---> piece
		values.put("data5", struct.getData5());
		values.put("data6", struct.getData6());
//		values.put("date", struct.getDate());
		
		values.put("modified",9);

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//for trans
//		updataTransTableFlag(Const.TABLE_RecordIN, Const.KEY_TRANS_IN);
	}
	///////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	//
	//
	//
	////////////////////////////////////////////////////////////////////////////////
	public ArrayList<String> getPupLeafNames(String typeID) {
		int PupWin_COLUMN_NAME = 1;
		ArrayList<String> contName = new ArrayList<String>();

		Uri uri = getUri(null, Const.TABLE_PupLeaf, null);
		String selection = "typeID=?";
		Cursor c = null;
		int cnt = 0;
		try {
			c = mResolver.query(uri, new String[] { "contID,contName" },
					selection, new String[] { typeID }, "_id ASC ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		int loop_cnt = c.getCount();
		c.moveToFirst();

		for (cnt = 0; cnt < loop_cnt; cnt++) {
			contName.add(c.getString(PupWin_COLUMN_NAME));
			c.moveToNext();
		}
		c.close();
		return contName;
	}
	
	public String getPupLeafName(String table, String dataLeafID) {
		Uri uri = getUri(null, table, null);

		String selection = "typeID=? and contID =?";
		String[] ID = dataLeafID.split(Const.KEY_DELIMITER_AND);
		// test for mom
		if (ID.length<2) {
			ID = dataLeafID.split(Const.KEY_DELIMITER);
		}
		//test end
		String[] selectionArgs = new String[] {ID[0],ID[1]};
		Cursor c = null;
        String DataLeafName = null;
		try {
			c = mResolver.query(uri, new String[] { "contName" }, selection,
					selectionArgs, null);
			if (c == null || c.getCount() == 0)
				return Const.NO_DATA;
			c.moveToFirst();
			DataLeafName =c.getString(0);
		} catch (Exception e) {
			// TODO: handle exception
			DataLeafName = Const.NO_DATA;
		}
		return DataLeafName;
	}
	
	public String getPupLeafID(String table,String dataLeafName) {
		Uri uri = getUri(null, table, null);

		String selection = "contName=?";
		String[] selectionArgs = new String[] {dataLeafName};
		Cursor c = null;
        String DataLeafID = null;
		try {
			c = mResolver.query(uri, new String[] { "typeID","contID" }, selection,
					selectionArgs, null);
			if (c == null || c.getCount() == 0)
				return Const.NO_DATA;
			c.moveToFirst();
			String typeID = c.getString(0);
			String contID = c.getString(1);
			DataLeafID = typeID +Const.KEY_DELIMITER_AND+ contID;
		} catch (Exception e) {
			// TODO: handle exception
			DataLeafID = Const.NO_DATA;
		}
		return DataLeafID;
	}
	
	public ArrayList<dataStructure.strLeaf> getPupWinLeafCursor() {
		
//		new DBOperator(mContext).DeleteTable(Const.TABLE_PupWinContent);
		Uri uri = getUri(null, Const.TABLE_PupWinContent, null);

		ArrayList<dataStructure.strLeaf> mArrayList = new ArrayList<dataStructure.strLeaf>();
//		String selection = "typeID=?";
//		String[] selectionArgs = new String[] { Utils.toRekTypeID(typeID) };
//		String typeName = null;
		Cursor c = null;

		try {
			c = mResolver.query(uri,new String[]{"_id,typeID,contID,contName,data1,data2"}, null,
					null, null);

			if (c == null || c.getCount() == 0)
//				return Const.NO_DATA;
				c.close();
			c.moveToFirst();
			for (int cnt = 0; cnt < c.getCount(); cnt++) {
				dataStructure.strLeaf tmp = new dataStructure.strLeaf();
				tmp.set_id(c.getInt(0));
				tmp.setTypeid(c.getString(1));
				tmp.setContid(c.getString(2));
				tmp.setContname(c.getString(3));
				tmp.setData1(c.getString(4));
				tmp.setData2(c.getString(5));
				mArrayList.add(tmp);
				c.moveToNext();
			}
		} catch (Exception e) {
//			typeName = Const.NO_DATA;
			e.printStackTrace();
		}
		
		return mArrayList;
	}
	public ArrayList<dataStructure.strTree> getPupWinTreeCursor() {
		
//		new DBOperator(mContext).DeleteTable(Const.TABLE_PupWinMage);
		Uri uri = getUri(null, Const.TABLE_PupWinMage, null);

		ArrayList<dataStructure.strTree> mArrayList = new ArrayList<dataStructure.strTree>();
		Cursor c = null;

		try {
			c = mResolver.query(uri,new String[]{"_id,typeID,typeName,data1,data2"}, null,
					null, null);

			if (c == null || c.getCount() == 0)
//				return Const.NO_DATA;
				c.close();
			c.moveToFirst();
			for (int cnt = 0; cnt < c.getCount(); cnt++) {
				dataStructure.strTree tmp = new dataStructure.strTree();
				tmp.set_id(c.getInt(0));
				tmp.setTypeid(c.getString(1));
				tmp.setTypename(c.getString(2));
				tmp.setData1(c.getString(3));
				tmp.setData2(c.getString(4));
				mArrayList.add(tmp);
				c.moveToNext();
			}
		} catch (Exception e) {
//			typeName = Const.NO_DATA;
			e.printStackTrace();
		}
		
		return mArrayList;
	}
	public void insertTrees(strTree struct) {

		Uri uri = getUri(Const.TYPE_INSERT, Const.TABLE_PupTree, null);
		ContentValues values = new ContentValues();

		// systime
		long times = System.currentTimeMillis();
		Date curdate = new Date(times);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strtime = format.format(curdate);

		int count = gettableCount(Const.TABLE_PupTree);

		values.put("_id", count + 1);
		values.put("typeID", struct.getTypeid());
		values.put("typeName", struct.getTypename());
		values.put("data1", strtime);

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean insertLeaf(strLeaf struct) {

//		if (!chkDataLeafName(Const.TABLE_Leaf,contentName)) return false;
		
		Uri uri = getUri(Const.TYPE_INSERT, Const.TABLE_PupLeaf, null);
		ContentValues values = new ContentValues();

		// systime
		String strtime =  Utils.systemFrmtTime("yyyy/MM/dd HH:mm:ss");

//		int count = gettableCount(Const.TABLE_Leaf, "typeId",
//				Utils.toTreeID(typeId));

		values.put("_id",struct.get_id());
		values.put("typeID", struct.getTypeid());
		values.put("contID", struct.getContid());
		values.put("contName", struct.getContname());
		values.put("data1", strtime);

		try {
			mResolver.insert(uri, values);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
