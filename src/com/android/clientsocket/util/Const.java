package com.android.clientsocket.util;

public class Const {
	public final static String ACT_ECO_START_BOOT_COMPLETED = "com.android.ISM.action.START_BOOT_COMPLED";
	public final static String ACT_ECO_BLUETOOTH_STATE_CHANGED= "com.android.ISM.action.BLUETOOTH_STATE_CHANGED";
	public final static String ACT_ECO_START_RESET = "com.android.gastove.action.RESET";
	public final static String ACT_ECO_START_DEVICELISTACTIVITY = "com.android.gastove.action.DEVICELISTACTIVITY";
//	public final static String ACT_ECO_START_DEVICE_ACTIVITY = "com.android.ISM.action.DeviceListActivity";
	public final static String ACT_ECO_NOTHING_TO_DO = "com.android.ISM.action.nothingtodo";
	public final static String ACT_ECO_CONTECT_TO_DEVICE = "com.android.ISM.action.contectdevice";
	public final static String ACT_GAS_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
	public final static String ACT_GAS_LOCK = "com.android.gas.action.lock";
	
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    public static final int ECOMODE_NOTIFICATION_ALERT = 1001;
    public static final String ECOMODEAPP_PACKAGE = "jp.co.sharp.android.ecomode";
    public static final String ECOMODEAPP_CLASS = "jp.co.sharp.android.ecomode.EcoModeApp";
    
    public static final String SHAREDPREFERENCE_NAME = "ismshareddata";
    public static final String SHARED_DEVICE_MAC = "device_mac";
    public static final String SHARED_DEVICE_IP = "IP";
    public static final String SHARED_IN_OUT = "inorout";
    public static final String SHARED_IN = "in";
    public static final String SHARED_OUT = "out";
    
    public static final String SHARED_TRANS_START = "data1";
    public static final String SHARED_TRANS_LAST = "data2";
    
    public static final int not_server = 1;
    public static final int not_server_bluetooth = 2;
    public static final int not_server_device = 3;
    public static final int not_server_connect = 4;
    public static final int not_server_found = 5;
    
    public static final int net_connect_ng = 1;
    public static final int net_connect_ok = 2;
    public static final String COLUMN_RECORDIN_REMARK ="data6";
    
    //2013/11/3 add
    public static final String DATABASE_NAME = "clientmp.db";   //==
    public static final String TABLE_User = "User";
	public static final String TABLE_Contacts = "ContactsInfo";
	public static final String TABLE_RecordIN = "RecordIN";
	public static final String TABLE_RecordToday = "RecordToday";
	public static final String TABLE_RecordTodayIndex = "RecordTodayIndex";
	public static final String TABLE_ContactGroup = "ContactsGroup";
	public static final String TABLE_PupWin = "PupWin";
	public static final String TABLE_PupWinIN = "PupWinIN";
	
	public static final String TABLE_SharedPrefs = "SharedPrefs";
	public static final String TABLE_PupTree = "Tree";  //PoPupWidowsDataTree 's table
	public static final String TABLE_PupLeaf = "Leaf"; //PoPupWidowsDataLeaf 's talbe
	
	public static final String TABLE_warhostree = "whtree";
	public static final String TABLE_warhosleaf = "whleaf";
	public static final String TABLE_WHRecordIN = "WHRecordIN";
	public static final String TABLE_WHRecordIndex = "whrecordindex";
	
	public static final String TABLE_remark = "remark";
	public static final String TABLE_Calls = "Calls";
	public static final String USED = "1";
	public static final String USED_NEW_RECORD = "2";
	
	public static final String LOGIN_DANGERWARNING = "danger";
	public static final String LOGIN_USER = "user";
	public static final String LOGIN_NOENTER = "noenter";
	public static final String BUNDLE_RECORD_ID = "recordid";
	public static final String BUNDLE_TEL_NUMBER = "telnumber";
	public static final String BUNDLE_NAME = "name";
	public static final String BUNDLE_ID = "_id";
	public static final String BUNDLE_Gesture = "gesture";
	public static final String BUNDLE_TOADY = "datetoday";
	public static final String BUNDLE_Person = "person";
	public static final String BUNDLE_STORE = "store";
	
	public static final String BUNDLE_READD_RECORD = "readd_reocrd";
	
	public static final String BUNDLE_LOCK_FLAG = "lockflag";
	
	public static final String RECORD_MULTY = "not only record";
//	public static final String USED_NEW_RECORD_2 = "2";
	
	public static final String BUNDLE_TYPEID = "typeid";
	public static final String COLOUM_TYPEID = "typeID";
	public static final String AUTHORITY = "com.provider.clientmpDBprovider";  //==
	
	public static final long SLEEPTIEM = 200l;
	public static final String BUNDLE_DRAG = "dragorclick";
	public static final String KEY_DELIMITER = ",";
	public static final String KEY_DELIMITER_ = "/";
	public static final String KEY_DELIMITER_AND = ":";
	public static final String KEY_DELIMITER_S = "~";
	public static final String KEY_NEXTLINE = "\r\n";
	
	public static final String SOCKET_TRANS_TABLECOUNT = "tbct";
	
	public static final String BOTTOM_POPWINDOW_HEIGHT = "bottom_pop_height";
	//pupwindows limit
	public static final int PUPWIN_CONTENT_NUM = 9;
	public static final int PUPWIN_CONTENT_NUM_12 = 12;
	public static final int PUPWIN_CONTENT_NUM_15 = 15;
	public static final int PUPWIN_CONTENT_ARRAY_MAX = 18;
	
//	public static String BOTTOM_POPWINDOW_HEIGHT_9 = 0.4f;
	public static String BOTTOM_POPWINDOW_HEIGHT_12 = "0.5f" + KEY_DELIMITER + PUPWIN_CONTENT_NUM_12;
	public static String BOTTOM_POPWINDOW_HEIGHT_15 = "0.6f" + KEY_DELIMITER + PUPWIN_CONTENT_NUM_15;
	public static String BOTTOM_POPWINDOW_HEIGHT_18 = "0.7f" + KEY_DELIMITER + PUPWIN_CONTENT_ARRAY_MAX;
	public static final int DB_PUPPART_TYPE = 2;

	
	public static final int DB_DATATREE_LIMIT = 3;
	public static final String TBLNAME = "table";
	public static final String TYPE = "type";
	public static final String TYPE_INSERT = "insert";
	public static final String TYPE_DELETE = "delete";
	public static final String TYPE_UPDATE = "update";
	public static final String OPTION = "option";

	public static final String TYPE_INSERT_MULTI = "insert_multi";
	public static final String TYPE_Query_SUM = "query_sum";
	
	public static final int SETTING_MAX_VALUE = 35;
	public static final String PAS_DEL_DB = "0000";
	//
	public static final String RECORDIN_COLUMN_RECORDID = "recordid";
	
	public static final String NO_DATA ="Î´Öª";
	public static final String NO_DATA_ ="-1";
	public static final String HINT ="ÌáÊ¾  ";
	public static final int WinMageTypeCount = 3;
	public static final int WinMageContentCount = 6;
	
	public static final String SHAREPREFS_DATABUCKUP_TIME = "databuckptime";
    public static final String PARAMS_X = "paramsx";
    public static final String PARAMS_Y = "paramsy";
    
    public static final String BUND_HOTKEY = "hotkey";
    
    public static final String KEY_VALUE_TRUE = "0";
    public static final String KEY_VALUE_FALSE = "-1";
    
    public static final String KEY_TRANS_ZERO = "0";
    public static final String KEY_TRANS_IN = "1";
    public static final String KEY_TRANS_UP = "2";
    
    
    public static final String KEY_INIT = "init";
    public static final String KEY_CALLS = "calls"; //1 send  false    2 home  true
    public static final String KEY_POPHEIGHT = "popheight"; //1 send  false    2 home  true
    
    public static final String FLAG_DATAIN= "0"; 
    public static final String FLAG_DATAOUT= "1"; 
    
    //status for recordindex
    public static final String STATUS_Record= "0"; 
    public static final String STATUS_NOTE = "1"; 
    public static final String STATUS_DONING_MAKE = "2";
    public static final String STATUS_DOING_FIRE = "3";
    public static final String STATUS_DOING_DELIVERY = "4";
    public static final String STATUS_PAYING = "5";
    public static final String STATUS_END = "6";
    public static final String STATUS_scrap = "-1";
    public static final String STATUS_REMARK = "7";
}
