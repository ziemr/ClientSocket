package com.android.clientsocket.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * UDP���չ㲥��
 * ʹ�ûص�����
 * @author thinkpad
 * 
 */
public class UDPSocketBroadCast {
	private final String BROADCAST_IP = "224.224.224.225";
	private final int BROADCAST_PORT = 8681;
	private byte[] getData = new byte[1024];
	private boolean isStop = false;
	private MulticastSocket mSocket = null;
	private InetAddress address = null;
	private DatagramPacket dataPacket;
	private Thread mUDPThread = null;
	private UDPDataCallBack mCallBack = null;

	/**
	 * ��ʼ���չ㲥
	 * 
	 * @param ip
	 */
	public void startUDP(UDPDataCallBack mCallBack) {
		this.mCallBack = mCallBack;
		mUDPThread = new Thread(UDPRunning);
		mUDPThread.start();
	}

	/**
	 * ���������������յ�udp���ͣ���㲥���ٴ���Ҫʱʹ��reStartUDp()����
	 * 
	 * @param ip
	 */
	public void reStartUDP() {
		Log.d("tag", "UDP is reStart!");
		mUDPThread = null;
		isStop = false;
		mUDPThread = new Thread(UDPRunning);
		mUDPThread.start();
	}

	/**
	 * ֹͣ�㲥
	 */
	public void stopUDP() {
		isStop = true;
		mUDPThread.interrupt();
	}

	/**
	 * ����udp����
	 */
	private void CreateUDP() {
		try {
			mSocket = new MulticastSocket(BROADCAST_PORT);
			mSocket.setTimeToLive(5);// �㲥����ʱ��0-255
			address = InetAddress.getByName(BROADCAST_IP);
			mSocket.joinGroup(address);
			dataPacket = new DatagramPacket(getData, getData.length, address,
					BROADCAST_PORT);
			Log.d("tag", "udp is create");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Runnable UDPRunning = new Runnable() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String result = (String) msg.obj;
				mCallBack.mCallback(result);
				Log.d("tag", "handler get data:" + result);
			}
		};

		@Override
		public void run() {
			CreateUDP();
			Message msg = handler.obtainMessage();
			while (!isStop) {
				if (mSocket != null) {
					try {
						mSocket.receive(dataPacket);
						String mUDPData = new String(getData, 0,
								dataPacket.getLength());
						/**
						 * ȷ���Ƿ�������ͻ��˷�����������
						 */
						if (mUDPData != null
								&& "IAMZTSERVERSOCKET".equals(mUDPData
										.split("-")[0])) {
							msg.obj = mUDPData;
							handler.sendMessage(msg);
							isStop = true;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					msg.obj = "error";
					handler.sendMessage(msg);
				}
			}
		}
	};

	public interface UDPDataCallBack {
		public void mCallback(String str);
	}
}
