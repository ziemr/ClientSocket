package com.android.clientsocket.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketClientManager {
	/**
	 * ��������ģʽ����ͬ��Activity����ͬһ��ScoketClientMgr
	 */
	private static SocketClientManager instance = null;
	private Socket clientSocket = null;
	private OutputStream outStream = null;
	private InputStream inStream = null;
	private String serverIP = "";
	private int port = 0;
	public boolean isStop = false;
	private SocketClientCallBack callBack;

	public synchronized static SocketClientManager getInstance() {
		if (instance == null) {
			instance = new SocketClientManager();
		}
		return instance;
	}

	/**
	 * ͨ���ⷽ���ص�����
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
	 * �����ͻ���
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
			if (clientSocket == null) {
				try {
					clientSocket = new Socket(serverIP, port);
					outStream = clientSocket.getOutputStream();
					inStream = clientSocket.getInputStream();
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
	 * ���������߳�
	 */
	private Runnable runHeartbeat = new Runnable() {

		@Override
		public void run() {
			while (!isStop) {
				SendMessage("IAMINTHETEST");
				byte[] buf = new byte[10240];
				try {
					inStream.read(buf);// ��ȡ������������
					String res = new String(buf, 0, buf.length, "utf-8").trim();
					Log.d("tag", "return :" + res);
					if ("".equals(res)) {// �����������տհ�ʱ˵���Ͽ���
						isStop = true;
						Message msg = mHandler.obtainMessage();
						msg.what = 1;// ��������Ͽ�
						mHandler.sendMessage(msg);// ֪���Ͽ�������Ϣ
					} else {
						Thread.sleep(10 * 1000);// ����
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
	 * �ͻ��������˳������������
	 */
	public void clientQuit() {
		SendMessage("IHAVEQUIT");
	}

	/**
	 * ��������
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
