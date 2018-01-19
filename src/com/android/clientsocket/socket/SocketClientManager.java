package com.android.clientsocket.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.clientsocket.provider.DBOperator;
import com.android.clientsocket.util.Const;
import com.android.clientsocket.util.dataStructure;
import com.android.clientsocket.util.dataStructure.strLeaf;
import com.android.clientsocket.util.dataStructure.strRecordin;
import com.android.clientsocket.util.dataStructure.strTree;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketClientManager {
	/**
	 * 单例对象模式，不同的Activity共享同一个ScoketClientMgr
	 */
	private static SocketClientManager instance = null;
	private Socket clientSocket = null;
	private OutputStream outStream = null;
	private InputStream inStream = null;
	private String serverIP = "";
	private int port = 0;
	public boolean isStop = false;
	private SocketClientCallBack callBack;
	private DBOperator dbOperator;
	private Context context;

	public synchronized static SocketClientManager getInstance(Context context) {
		if (instance == null) {
			instance = new SocketClientManager(context);
		}
		return instance;
	}

	public   SocketClientManager(Context context) {
		this.context = context;
	}
	/**
	 * 通过这方法回调数据
	 * 
	 * @param callBack
	 */
	public void getSocketMessage(SocketClientCallBack callBack) {
		this.callBack = callBack;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			callBack.callBack(msg.what);
		}
	};

	/**
	 * 创建客户端
	 * 
	 * @param serverIP
	 * @param port
	 */
	public void startClientScoket(String serverIP, int port) {

		this.serverIP = serverIP;
		this.port = port;
		isStop = false;
		this.clientSocket = null;
		this.outStream = null;
		this.inStream = null;
		new Thread(runSocket).start();
		Log.d("tag", "clientSocket is create");
	}

	private Runnable runSocket = new Runnable() {

		@Override
		public void run() {
			dbOperator = new DBOperator(context);
			if (clientSocket == null) {
				try {
					clientSocket = new Socket(serverIP, port);
					outStream = clientSocket.getOutputStream();
					inStream = clientSocket.getInputStream();
					
					
					SendMessage(Const.TABLE_User+
				             Const.KEY_DELIMITER+
				             ConnectionManager.getLocalIP()+
				             Const.KEY_DELIMITER_S+
				             dbOperator.getUserNo());
					
					new Thread(runHeartbeat).start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	/**
	 * 心跳测试线程
	 */
	private Runnable runHeartbeat = new Runnable() {
//         String user = null;
		@Override
		public void run() {
//			user = dbOperator.getUserNo();
			while (!isStop) {
//				SendMessage("IAMINTHETEST");
				
				byte[] buf = new byte[10240];
				try {
					inStream.read(buf);// 读取服务器端数据
					String res = new String(buf, 0, buf.length, "utf-8").trim();
					Log.d("tag", "return :" + res);
					if ("".equals(res)) {// 当服务器接收空包时说明断开了
						isStop = true;
						Message msg = mHandler.obtainMessage();
						msg.what = 1;// 与服务器断开
						mHandler.sendMessage(msg);// 知道断开后发送消息
					} 
					
					 String[] operatorStr = res.split(Const.KEY_DELIMITER);
				 	   String FLAG = operatorStr[0];
				 	  if (FLAG.equals("TEL") ) {
				 		   //
				 	   } else if (FLAG.equals("FIND")) {
				 		   //
				 	   } else if (FLAG.endsWith(Const.SOCKET_RECORDIN)) {
				  		  String[] tempStr = operatorStr[1].split(Const.KEY_DELIMITER_S);
				   		strRecordin tmp = new dataStructure.strRecordin();
				   		  tmp.set_id(Integer.valueOf(tempStr[0]));
				   		 tmp.setRecordid(tempStr[1]);
				  			tmp.setPhone(tempStr[2]);
				  			tmp.setNum(Integer.valueOf(tempStr[3]));
				  			tmp.setData1(tempStr[4]);
				  			tmp.setData2(tempStr[5]);
				  			tmp.setData3(tempStr[6]);
				  			tmp.setData4(tempStr[7]);
				  			tmp.setData5(tempStr[8]);
				  			tmp.setDatee(tempStr[9]);
				  			tmp.setData6(tempStr[10]);
				  			tmp.setData7(tempStr[11]);
				  			dbOperator.insertRecordin(tmp);
				   	   } else if (FLAG.endsWith(Const.SOCKET_TREE)) {
				  		  String[] tempStr = operatorStr[1].split(Const.KEY_DELIMITER_S);
				 		  strTree tmp = new dataStructure.strTree();
				 		  tmp.set_id(Integer.valueOf(tempStr[0]));
							tmp.setTypeid(tempStr[1]);
							tmp.setTypename(tempStr[2]);
							tmp.setData1(tempStr[3]);
							tmp.setData2(tempStr[4]);
							dbOperator.insertTrees(tmp);
							
				 	   }else if (FLAG.endsWith(Const.SOCKET_LEAF)) {
				  		  String[] tempStr = operatorStr[1].split(Const.KEY_DELIMITER_S);
				  		  strLeaf tmp = new dataStructure.strLeaf();
				  		  tmp.set_id(Integer.valueOf(tempStr[0]));
				 			tmp.setTypeid(tempStr[1]);
				 			tmp.setContid(tempStr[2]);
				 			tmp.setContname(tempStr[3]);
				 			tmp.setData1(tempStr[4]);
				 			tmp.setData2(tempStr[5]);
				 			dbOperator.insertLeaf(tmp);
				 			
				  	   }
					
					else {
						Thread.sleep(10 * 1000);// 正常
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * 客户端正常退出调用这个方法
	 */
	public void clientQuit() {
		SendMessage("IHAVEQUIT");
	}

	/**
	 * 发送数据
	 * 
	 * @param strMsg
	 * @return
	 */
	public boolean SendMessage(String strMsg) {
		if (clientSocket == null) {
			return false;
		}
		byte[] msgBuffer = null;
		try {
			msgBuffer = strMsg.getBytes("UTF-8");
			outStream.write(msgBuffer);
			outStream.flush();
			Log.d("tag", "send message is:" + strMsg);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void Close() {
		CloseSocket();
	}

	private void CloseSocket() {
		isStop = true;
		try {
			if (clientSocket != null) {
				clientSocket.close();
				clientSocket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface SocketClientCallBack {
		public void callBack(int what);
	}
}
