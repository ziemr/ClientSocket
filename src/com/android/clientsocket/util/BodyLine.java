package com.android.clientsocket.util;

public class BodyLine {
//	private Integer _id;
	private String recordid;
	private String phone;
	private Integer num;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String date;
	private String data6; //remark
	
	
	public void setData(int index , String data) {
		isClear = false;
		switch (index) {
		case 0:
			//04/05
			setData1(data);
			break;
		case 1:
			//name
			setData2(data);
			break;
		case 2:
			//single/double
			setData3(data);
			break;
		case 3:
			//type
			setData4(data);
			break;
		case 5:
			break;
		default :
//			setnum(data);
		}
	}
	
	public String getData(int index) {
		switch (index) {
		case 0:
			//04/05
			return getData1();
		case 1:
			//name
			return getData2();
		case 2:
			//single/double
			return getData3();
		case 3:
			//type
			return getData4();
		case 5:
			break;
		default :
			return getDate();
		}
		return "nothing";
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void clear() {
		num = null;
		data1 = null;
		data2 = null;
		data3 = null;
		data4 = null;
		data5 = null;
		date = null;
		data6 = null;
		isClear = true;
	}
	
	private boolean isClear = false;
	public boolean isClear() {
		return isClear;
	}
   //remark
	public String getRemark() {
		return data6;
	}

	public void setRemark(String data6) {
		this.data6 = data6;
	}
}
