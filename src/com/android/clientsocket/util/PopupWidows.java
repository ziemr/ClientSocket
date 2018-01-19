package com.android.clientsocket.util;

import java.util.List;

import com.android.clientsocket.R;
import com.android.clientsocket.provider.DBOperator;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;



public class PopupWidows extends PopupWindow {
	private View mMenuView;
    
	private GridView mGridView;
    private Button mOkButton ,mSettingsButton,mHotkey;
    private Button mClearButton;
    private GridView mCalcGridView;
    private EditText mTextView;
    private Context _Context;
    
	private int mDisplayHeight = 0;
	private int mDisplayWidth = 0;
	
	
	public PopupWidows(Activity context) {
		super(context);
		_Context = context;
		LayoutInflater inflater = (LayoutInflater) _Context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.pup_window, null);
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(getPopupWindowHeight());
		this.setFocusable(true);
		this.setAnimationStyle(R.style.DIALOG_STYLE);
		ColorDrawable dw = new ColorDrawable(0xb0ffffff);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						// dismiss();
					}
				}
				return true;
			}
		});
		
	}

	private int getPopupWindowHeight() {
       	WindowManager mWManager = (WindowManager)_Context.getSystemService(Context.WINDOW_SERVICE);
    	Display mDisplay = mWManager.getDefaultDisplay();
    	if (mDisplay != null) {
    		mDisplayHeight = mDisplay.getHeight();
    		mDisplayWidth = mDisplay.getWidth();
    	}
    	float height = 1.0f;
    	//for test
//    	new DBOperator(_Context).updateSharedData(Const.KEY_POPHEIGHT, Const.BOTTOM_POPWINDOW_HEIGHT_12);
    	String temp = new DBOperator(_Context).getSharedDataValue(Const.KEY_POPHEIGHT);
//    	String temp = new SharedPrefsData(_Context).getSharedData(Const.BOTTOM_POPWINDOW_HEIGHT);
//		if (Const.NO_DATA.endsWith(temp)) {
//			height = 0.5f;
//		}else {
		    String temparr[] = temp.split(Const.KEY_DELIMITER);
		    height = Float.parseFloat(temparr[0]);
//		}
		return (int)(mDisplayHeight * height);
	}
	
	List<String> items;
	
	
	public void calcVisibility(Boolean iscalc) {
		if (iscalc) {
			mCalcGridView.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		} else {
			mCalcGridView.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}
		mOkButton.setEnabled(iscalc);
	}
	
	public void setNum(String str) {
		mTextView.setText(str);
	}
	
	public void notifyDataSetChange() {
		((PupWidowsAdapter) mGridView.getAdapter()).notifyDataSetChanged();
	}

	public void setPopupWindowListener(
			OnItemClickListener poPupGridViewListener,
			OnClickListener mPopClearButtonListener,
			OnClickListener mPopOkButtonListener, PupWidowsAdapter mPupAdapter,
			OnClickListener mPopSettingButtonListener,
			OnClickListener mHotKeyListener) {

		if (mMenuView != null) {
			mGridView = (GridView) mMenuView.findViewById(R.id.gridview_layout);
			mGridView.setAdapter(mPupAdapter);
			
			mCalcGridView = (GridView) mMenuView.findViewById(R.id.gridview_layout_calc);
			mCalcGridView.setAdapter(new PupWidowsAdapter(_Context, new String[]{"1","2","3",
					"4","5","6",
					"7","8","9",
					"C","0","±¸×¢"},true));
			
			if (mGridView != null) {
				mGridView.setOnItemClickListener(poPupGridViewListener);
//				mGridView.setOnItemDragListener(mItemDragListener);
				
//				//for DragView
//				items = new ArrayList<String>();
//		        for (int i = 0; i < 9; i++) {
//		            items.add("Item" + i);
//		        }
//				mGridView.setItems(items);
			}
			if (mCalcGridView != null)
				mCalcGridView.setOnItemClickListener(poPupGridViewListener);
			
			mClearButton = (Button) mMenuView.findViewById(R.id.title_clear);
			if (mClearButton != null)
				mClearButton.setOnClickListener(mPopClearButtonListener);

			mOkButton = (Button) mMenuView.findViewById(R.id.title_button);
			if (mOkButton != null) {
				mOkButton.setOnClickListener(mPopOkButtonListener);
			    mOkButton.setEnabled(false);
			}
			
			mHotkey = (Button) mMenuView.findViewById(R.id.hot_key);
			if (mHotkey != null) {
				mHotkey.setOnClickListener(mHotKeyListener);
			}
			mSettingsButton = (Button) mMenuView.findViewById(R.id.pup_settings);
			if (mSettingsButton != null) {
				mSettingsButton.setOnClickListener(mPopSettingButtonListener);
			}
			//num text
			mTextView = (EditText) mMenuView.findViewById(R.id.title_num);
			mTextView.setInputType(InputType.TYPE_NULL);
//			mTextView.setTextColor(0xffff0000);
		}

	
		
	}

}
