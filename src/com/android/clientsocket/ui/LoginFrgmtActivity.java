package com.android.clientsocket.ui;

import com.android.clientsocket.R;
import com.android.clientsocket.provider.DBOperator;
import com.android.clientsocket.socket.MainActivity;
import com.android.clientsocket.util.Const;
import com.android.clientsocket.util.DataBackUp;
import com.android.clientsocket.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFrgmtActivity extends FragmentActivity implements
		OnClickListener, OnLongClickListener {
	
	private EditText et_name, et_pass;
	private Button mLoginButton, mLoginError, mRegister, ONLYTEST;
	int selectIndex = 1;
	int tempSelect = selectIndex;
	private int SERVER_FLAG = 0;
	
	
    private DBOperator mDbOperator;
    private Context mContext;
	private Button bt_username_clear;
	private Button bt_pwd_clear;
	private Button bt_pwd_eye;
	private TextWatcher username_watcher;
	private TextWatcher password_watcher;
//	private GestureLockView view;
	
    private static LoginFrgmtActivity mLoginFrgmt;
    
    public static LoginFrgmtActivity getInstance(){
        if( mLoginFrgmt == null ){
        	mLoginFrgmt = new LoginFrgmtActivity();
        }
        return mLoginFrgmt;
    }
    
//	public class GestureFinish implements OnGestureFinishListener {
//
//		@Override
//		public void OnGestureFinish(boolean success, String key) {
//			if (success) {
//				startMainAvtivity();
//			}
//				
//		}
		
//	}
	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// //不显示系统的标题栏
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		mContext= getApplicationContext();
		mDbOperator = new DBOperator(mContext);	
		
//		new InitAppData(mContext).doInitApp();
		setContentView(R.layout.activity_login);
		et_name = (EditText) findViewById(R.id.username);
		
		et_name.setText(Utils.getMac());
		et_pass = (EditText) findViewById(R.id.password);
		bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
		bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
		bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
		
		bt_username_clear.setOnClickListener(this);
		bt_pwd_clear.setOnClickListener(this);
		bt_pwd_eye.setOnClickListener(this);
		initWatcher();
		et_name.addTextChangedListener(username_watcher);
		et_pass.addTextChangedListener(password_watcher);

		mLoginButton = (Button) findViewById(R.id.login);
		mLoginError = (Button) findViewById(R.id.login_error);
		mRegister = (Button) findViewById(R.id.register);
		ONLYTEST = (Button) findViewById(R.id.registfer);
		ONLYTEST.setOnClickListener(this);
		ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
		mLoginButton.setOnClickListener(this);
		mLoginError.setOnClickListener(this);
		mRegister.setOnClickListener(this);
		bt_username_clear.setVisibility(View.VISIBLE);
		
//		view = (GestureLockView) findViewById(R.id.gesturelockview);
//		view.setOnGestureFinishListener(new GestureFinish());
//		view.setKey("1");  //Z
	}

	@Override
	protected void onResume() {
		super.onResume();
//		new SharedPrefsData(mContext).saveSharedData(Const.BUNDLE_LOCK_FLAG, false);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		new SharedPrefsData(mContext).saveSharedData(Const.BUNDLE_LOCK_FLAG, true);
	}

	/**
	 * 手机号，密码输入控件公用这一个watcher
	 */
	private void initWatcher() {
		username_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				et_pass.setText("");
				if (s.toString().length() > 0) {
					bt_username_clear.setVisibility(View.VISIBLE);
				} else {
					bt_username_clear.setVisibility(View.INVISIBLE);
				}
			}
		};

		password_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					bt_pwd_clear.setVisibility(View.VISIBLE);
				} else {
					bt_pwd_clear.setVisibility(View.INVISIBLE);
				}
			}
		};
	}
	/**
	 * 登陆
	 */
	private void loginOperator() {
//		String loginName = et_name.getText().toString().trim();
		String loginName = "admin";
		String loginPass = et_pass.getText().toString().trim();
		if (loginPass.equals("") || loginPass == null) {
			Utils.showToast(mContext, getString(R.string.login_msg_psw));
			return;
		}
		//fffffffffffffffffff
//		String result = mDbOperator.LoginCheck(loginName, loginPass);
		String result = null;
		startMainAvtivity();
		if (Const.LOGIN_USER.equals(result)) {
			startMainAvtivity();
		}else if (Const.LOGIN_DANGERWARNING.equals(result)) {
			Utils.showToast(mContext, "正在处理");
//			mDbOperator.DangerWarning();
//			mDbOperator.DeleteUser();
			
		}else if (Const.LOGIN_NOENTER.equals(result)) {
			Utils.showToast(mContext, getString(R.string.login_msg_psw_error));
		}
		
	}
	
	private void startMainAvtivity() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent = new Intent(LoginFrgmtActivity.this, MainActivity.class);
				LoginFrgmtActivity.this.startActivity(intent);
				LoginFrgmtActivity.this.finish();// 结束本Activity
			}
		}, 500);// 设置执行时间
	} 
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.login: // 登陆
			loginOperator();
			break;
		case R.id.login_error: // 无法登陆(忘记密码了吧)
			new DataBackUp(LoginFrgmtActivity.this).doBackup();
//			new DataBackUp(MoreActivity.this).doBackup()
			break;
		case R.id.register: // 注册新的用户
//			mDbOperator.clearCallsAll();
			Utils.showToast(mContext, "无法连接，请检查网络");
			break;

		case R.id.registfer:
			if (SERVER_FLAG > 2) {
				Toast.makeText(this, "[内部测试--谨慎操作]", Toast.LENGTH_SHORT).show();
			}
			SERVER_FLAG++;
//			view.setVisibility(View.INVISIBLE);
			break;
		case R.id.bt_username_clear:
			et_name.setText("");
			et_pass.setText("");
			break;
		case R.id.bt_pwd_clear:
			et_pass.setText("");
			break;
		case R.id.bt_pwd_eye:
			if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
//				bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_s);
				et_pass.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_NORMAL);
			} else {
//				bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_n);
				et_pass.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			et_pass.setSelection(et_pass.getText().toString().length());
			break;
        
		}
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.registfer:
			if (SERVER_FLAG > 1 && SERVER_FLAG < 8) {
//				view.setVisibility(View.VISIBLE);
			}
			 SERVER_FLAG = 0;
			break;
		}
		return true;
	}

	/**
	 * 监听Back键按下事件,方法2: 注意: 返回值表示:是否能完全处理该事件 在此处返回false,所以会继续传播该事件.
	 * 在具体项目中此处的返回值视情况而定.
	 */
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//			if (isReLogin) {
				Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
				mHomeIntent.addCategory(Intent.CATEGORY_HOME);
				mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				LoginFrgmtActivity.this.startActivity(mHomeIntent);
				
//				view.setVisibility(View.INVISIBLE);
//			} else {
//				LoginFrgmtActivity.this.finish();
//			}
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
