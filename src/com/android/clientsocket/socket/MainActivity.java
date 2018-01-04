package com.android.clientsocket.socket;

import java.net.SocketException;

import com.android.clientsocket.R;
import com.android.clientsocket.socket.SocketClientManager.SocketClientCallBack;
import com.android.clientsocket.socket.UDPSocketBroadCast.UDPDataCallBack;
import com.android.clientsocket.util.InitAppData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv_show;
	private UDPSocketBroadCast mBroadCast;
	private SocketClientManager mClientManager;
	private String localIP;
	private TextView tv_showip;
	
	private Button sendmsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		tv_showip = (TextView) findViewById(R.id.tv_showip);
		
		sendmsg = (Button) findViewById(R.id.sendmsg);
		new InitAppData(getApplicationContext()).doInitApp();
		sendmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mClientManager.SendMessage("hello world");
			}
		});
		mBroadCast = new UDPSocketBroadCast();
		try {
			localIP = ConnectionManager.getLocalIP();
			tv_showip.setText("本机IP:" + localIP);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_show.setText("正在自动连接中...");
		mClientManager = SocketClientManager.getInstance();
		// 接收到的udp广播
		mBroadCast.startUDP(new UDPDataCallBack() {

			@Override
			public void mCallback(String str) {
				if (str != null && !"error".equals(str)) {
					String[] strs = str.split("-");
					if (strs.length > 2) {
						Info.SERVER_IP = strs[1];
						Info.SERVER_PORT = Integer.parseInt(strs[2]);
						tv_show.setText("已连接到：" + strs[1]);
						mClientManager.startClientScoket(strs[1],
								Integer.parseInt(strs[2]));
					}
				}
			}
		});
		/**
		 * 获得socket的状态，比如断开
		 */
		mClientManager.getSocketMessage(new SocketClientCallBack() {

			@Override
			public void callBack(int what) {
				// TODO Auto-generated method stub
				switch (what) {
				case 1:// 与服务器断开
					tv_show.setText("与服务器断开，正在重新连接...");
					mBroadCast.reStartUDP();
					break;
				}
			}
		});
	}
}
