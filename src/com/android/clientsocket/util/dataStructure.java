package com.android.clientsocket.util;


public class dataStructure {
	
	public static class strRemark {
		private int dtype;
		private int _id;
		private String typeID;
		private String typeName;
		private String data1;
		private String data2;
		
		public int get_id() {
			return _id;
		}

		public void set_id(int _id) {
			this._id = _id;
		}

		public int getDtype() {
			return dtype;
		}

		public void setDtype(int dtype) {
			this.dtype = dtype;
		}

		/**
		 * @return the typeID
		 */
		public String getTypeID() {
			return typeID;
		}
		/**
		 * @param typeID the typeID to set
		 */
		public void setTypeID(String typeID) {
			this.typeID = typeID;
		}
		/**
		 * @return the typeName
		 */
		public String getTypeName() {
			return typeName;
		}
		/**
		 * @param typeName the typeName to set
		 */
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		/**
		 * @return the data1
		 */
		public String getData1() {
			return data1;
		}
		/**
		 * @param data1 the data1 to set
		 */
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
	}

	public static class strContactsInfo {
		private int dtype;
		private int _id;
		private String coname;
		
		private String telphone;
		private int used;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		private String datee;
		private int contracId;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getConame() {
			return coname;
		}
		public void setConame(String coname) {
			this.coname = coname;
		}
		public String getTelphone() {
			return telphone;
		}
		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
		public int getUsed() {
			return used;
		}
		public void setUsed(int used) {
			this.used = used;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
		public String getDatee() {
			return datee;
		}
		public void setDatee(String datee) {
			this.datee = datee;
		}
		public int getContracId() {
			return contracId;
		}
		public void setContracId(int contracId) {
			this.contracId = contracId;
		}
	}

    public  static class strTree {
		private int dtype;
		private int _id;
		private String typeid;
		private String typename;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getTypeid() {
			return typeid;
		}
		public void setTypeid(String typeid) {
			this.typeid = typeid;
		}
		public String getTypename() {
			return typename;
		}
		public void setTypename(String typename) {
			this.typename = typename;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
    }

    public static class strLeaf {
		private int dtype;
    	private int _id;
    	private String typeid;
    	private String contid;
    	private String contname;
    	private String data1;
    	private String data2;
    	private String data3;
    	private String data4;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getTypeid() {
			return typeid;
		}
		public void setTypeid(String typeid) {
			this.typeid = typeid;
		}
		public String getContid() {
			return contid;
		}
		public void setContid(String contid) {
			this.contid = contid;
		}
		public String getContname() {
			return contname;
		}
		public void setContname(String contname) {
			this.contname = contname;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
    	
    }

//    public class str

    public static class strRecordtoday {
		private int dtype;
		private int _id;
		private String recordid;
		private String telphone;
		private String coname;
		private String today;
		private String used;
		private String pay;
		private String datee;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		private String data5;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getRecordid() {
			return recordid;
		}
		public void setRecordid(String recordid) {
			this.recordid = recordid;
		}
		public String getTelphone() {
			return telphone;
		}
		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
		public String getConame() {
			return coname;
		}
		public void setConame(String coname) {
			this.coname = coname;
		}
		public String getToday() {
			return today;
		}
		public void setToday(String today) {
			this.today = today;
		}
		public String getUsed() {
			return used;
		}
		public void setUsed(String used) {
			this.used = used;
		}
		public String getPay() {
			return pay;
		}
		public void setPay(String pay) {
			this.pay = pay;
		}
		public String getDatee() {
			return datee;
		}
		public void setDatee(String datee) {
			this.datee = datee;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
		public String getData5() {
			return data5;
		}
		public void setData5(String data5) {
			this.data5 = data5;
		}
		
		
    }
    
    public static class strRecordtodayindex {
		private int dtype;
		private int _id;
		private String telphone;
		private String today;
		private String used;
		private String datee;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getTelphone() {
			return telphone;
		}
		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
		public String getToday() {
			return today;
		}
		public void setToday(String today) {
			this.today = today;
		}
		public String getUsed() {
			return used;
		}
		public void setUsed(String used) {
			this.used = used;
		}
		public String getDatee() {
			return datee;
		}
		public void setDatee(String datee) {
			this.datee = datee;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
		
    }
    
    public static class strRecordin {
		private int dtype;
		private int _id;
		private String recordid;
		private String phone;
		private int num;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		private String data5;
		private String datee;
		private String data6;
		private String data7;
		private String data8;
		private String data9;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getRecordid() {
			return recordid;
		}
		public void setRecordid(String recordid) {
			this.recordid = recordid;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
		public String getData5() {
			return data5;
		}
		public void setData5(String data5) {
			this.data5 = data5;
		}
		public String getDatee() {
			return datee;
		}
		public void setDatee(String datee) {
			this.datee = datee;
		}
		public String getData6() {
			if(data6==null) return "";
			if(data6.length() > 0)
			return data6;
			else return "";
		}
		public void setData6(String data6) {
			this.data6 = data6;
		}
		public String getData7() {
			return data7;
		}
		public void setData7(String data7) {
			this.data7 = data7;
		}
		public String getData8() {
			return data8;
		}
		public void setData8(String data8) {
			this.data8 = data8;
		}
		public String getData9() {
			return data9;
		}
		public void setData9(String data9) {
			this.data9 = data9;
		}
		
    }
    
    public static class calls {
		private int dtype;
		private int _id;
		private String number;
		private String datee;
		private int used;
		private String data1;
		private String data2;
		private String data3;
		private String data4;
		private String coname;
		public int getDtype() {
			return dtype;
		}
		public void setDtype(int dtype) {
			this.dtype = dtype;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getDatee() {
			return datee;
		}
		public void setDatee(String datee) {
			this.datee = datee;
		}
		public int getUsed() {
			return used;
		}
		public void setUsed(int used) {
			this.used = used;
		}
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public String getData2() {
			return data2;
		}
		public void setData2(String data2) {
			this.data2 = data2;
		}
		public String getData3() {
			return data3;
		}
		public void setData3(String data3) {
			this.data3 = data3;
		}
		public String getData4() {
			return data4;
		}
		public void setData4(String data4) {
			this.data4 = data4;
		}
		public String getConame() {
			return coname;
		}
		public void setConame(String coname) {
			this.coname = coname;
		}
		
    } 
    public static class strUser {
		private int _id;
		private String mac;
		private String name;
		private String password;
		private String date;
		private String permission;
		private String data;
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getPermission() {
			return permission;
		}
		public void setPermission(String permission) {
			this.permission = permission;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		
    }
}


