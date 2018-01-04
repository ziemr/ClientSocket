package com.android.clientsocket.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import com.android.clientsocket.R;
import com.android.clientsocket.provider.DBOperator;
import com.android.clientsocket.util.BodyLine;
import com.android.clientsocket.util.Const;
import com.android.clientsocket.util.GroupingListAdapter;
import com.android.clientsocket.util.InitAppData;
import com.android.clientsocket.util.PopupWidows;
import com.android.clientsocket.util.PupWidowsAdapter;
import com.android.clientsocket.util.Utils;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecordInFrgmtActivity extends FragmentActivity {
	private static final String TAG = "RecordListActivity";
	RecentCallsAdapter mAdapter;
	private QueryHandler mQueryHandler;
	private DBOperator mDbOperator;
	private Context mContext;

	private String Bundle_recordid = null;
	private String Bundle_telnumber = null;
	private String Bundle_ID;

	private boolean mScrollToTop;
	private static final int QUERY_TOKEN = 55;

	private ListView mListView;
	private static int PageIndex = 0;

	private String _TextArray[];
	private PupWidowsAdapter mPupAdapter;
	// gesture and pupwindow
	// for list
	private GestureDetector mDetector;
	// flag for add/view to limit gesture
	private Boolean Bundle_Gesture = true;
	private Boolean editEnable = true;
	
	
	private Boolean Bundle_readd = false;
	
	private TextView SUM_HEAD = null;
	private TextView NUM_HEAD = null;
	private Button headNewRecord;
	private LinearLayout mLayoutHide;

	private int TypeCount;
	private String NumStr = "0";
	private BodyLine mBodyLine = new BodyLine();
	String[] remarkarr = null;
	
	private Uri RecordinUri = null;
    /** The projection to use when querying the record in table */
    static final String[] RECORD_IN_PROJECTION = new String[] {
    	"_id",      //0
    	"recordid", //1
        "phone",    //2
        "num",      //3
        "data1",    //4
        "data2" ,   //5
        "data3" ,   //6
        "data4" ,   //7
        "data5",    //8
        "date",      //9
        "data6"
    };
    
	// for list
	OnTouchListener mListTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (mDetector != null && Bundle_Gesture || editEnable)
				return mDetector.onTouchEvent(event);
			return false;
		}
	};

	// for no list
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mDetector != null && Bundle_Gesture) {
			return mDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	private final ContentObserver mContentObserver = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			// Toast.makeText(mContext, "observer", Toast.LENGTH_SHORT).show();
			startQuery();
		}
	};
	private Handler popupHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				PopupWindow();
				break;
			}
		}

	};
	private PopupWidows mMenuWindow;

	public void PopupWindow() {
		if (mMenuWindow == null) {
			mMenuWindow = new PopupWidows(this);
			initPupWidowsAdapter();
			mMenuWindow.setPopupWindowListener(PoPupGridViewListener, mPopClearButtonListener, mPopOkButtonListener,
					mPupAdapter, null, null);
		}
		// View layout = RecordListActivity.this.findViewById(R.id.recordbase);
		mMenuWindow.showAtLocation(this.findViewById(R.id.linerbase), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		mMenuWindow.update();
	}

	public void initPupWidowsAdapter() {
		_TextArray = new String[Const.PUPWIN_CONTENT_ARRAY_MAX];
		ArrayList<String> PupContName = mDbOperator.getPupLeafNames(Utils.toTypeID(PageIndex));
		Iterator<String> ite = PupContName.iterator();
		int k = 0;
		while (ite.hasNext()) {
			if (k == Const.PUPWIN_CONTENT_ARRAY_MAX)
				break;
			_TextArray[k++] = ite.next();
		}
		while (k < Const.PUPWIN_CONTENT_ARRAY_MAX) {
			_TextArray[k++] = null;
		}
		mPupAdapter = new PupWidowsAdapter(mContext, _TextArray, false);
	}

	// PopupWindow gridview
	private OnItemClickListener PoPupGridViewListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (PageIndex == TypeCount && PageIndex != 0) {
				calc(position);
			} else {
				String tmpData = _TextArray[position];
				int nextPage = PageIndex + 1;
				if (tmpData != null) {
					String dataLeafID = mDbOperator.getPupLeafID(Const.TABLE_PupLeaf, tmpData);
					tmpData = dataLeafID;
					mBodyLine.setData(PageIndex, tmpData);
					if (nextPage < TypeCount) {
						setNextGirdDate(nextPage);
					} else {
						mMenuWindow.calcVisibility(true);
					}
					PageIndex++;
				}
			}
		}
	};
	// PopupWindow title button
	private OnClickListener mPopOkButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// num
			if (NumStr != null || !NumStr.equals("0")) {
				try {
					mBodyLine.setNum(Integer.parseInt(NumStr));
				} catch (Exception e) {
					Toast.makeText(mContext, "Num Error", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			mBodyLine.setPhone(Bundle_telnumber);
			mBodyLine.setRecordid(Bundle_recordid);
			String times = Utils.systemFrmtTime("yyyy/MM/dd HH:mm:ss");
			mBodyLine.setDate(times);// set timed

			mDbOperator.insertRecordin(mBodyLine);
			mBodyLine.clear();

			// update Today`flag(data2) to show
//			mDbOperator.updateRecordTodayUsed(Bundle_recordid);

			// update TodayIndex(used) to show
//			mDbOperator.updateRecordTodayIndexUsed(Bundle_telnumber);

			NumStr = "0";
			PageIndex = 0;
			mMenuWindow.calcVisibility(false);
			setNextGirdDate(PageIndex);
			mMenuWindow.setNum(NumStr);
			// startQuery();

//			NUM_HEAD.setText(" " + Integer.toString(mDbOperator.queryRecordINNum(Bundle_recordid)) + "Ì¨");
		}
	};
	private OnClickListener mPopClearButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			clearPopWindow();
		}
	};
	public String calc(int position) {
		String[] calcArrays = new String[] {
			//   0    1    2    3
				"1", "2", "3", "C",
				
			//   4    5    6    7
                "4", "5", "6", "PS.",
            //   8    9    10   11
                "7", "0", "8", "9" };
		String[] calcArray = new String[] {
				//   0    1    2 
					"1", "2", "3",
					
				//   3    4    5 
	                "4", "5", "6", 
	            //   6    7    8 
	                "7", "8", "9",
	            //   9    10   11
	                "C", "0", "Ps."};
		switch(position) {
		    case 9 :
		    	NumStr = "0";
		    	mMenuWindow.setNum(NumStr);
			    break;
		    case 11 :
//		    	PSDialog();
			    break;
		    case 12:
		    case 13:
		    case 14:
		    case 15:
		    case 16:
		    case 17:
		    case 18:
		        break;
			default:
				NumStr += calcArray[position];
				mMenuWindow.setNum(NumStr.substring(1,NumStr.length()));
				if (Integer.parseInt(NumStr)>=9999)
					Toast.makeText(mContext, getString(R.string.num_limit), Toast.LENGTH_SHORT).show();
				break;
		}
		return NumStr;
	}
	private void clearPopWindow() {
		//num
		if (mMenuWindow == null ) return;
		mBodyLine.clear();
		
		NumStr = "0";
		PageIndex = 0;
		mMenuWindow.calcVisibility(false);
		setNextGirdDate(PageIndex);
		mMenuWindow.setNum(NumStr);
	}
	private void setNextGirdDate( int index) {
		ArrayList<String> PupContName = mDbOperator.getPupLeafNames(Utils.toTypeID(index));
		Iterator<String> ite = PupContName.iterator();
		int k = 0;
		while(ite.hasNext()) {
			if (k == Const.PUPWIN_CONTENT_ARRAY_MAX) break;
			_TextArray[k++] = ite.next();
		}
		while(k < Const.PUPWIN_CONTENT_ARRAY_MAX) {
			_TextArray[k++]=null;
		}
		mMenuWindow.notifyDataSetChange();
	}

	private void startQuery() {
		mAdapter.setLoading(true);

		// Cancel any pending queries
		mQueryHandler.cancelOperation(QUERY_TOKEN);
		RecordinUri = mDbOperator.getTableUri(Const.TABLE_RecordIN);

		String order = "date ASC";
		if (Bundle_Gesture)
			order = "date DESC";
		mQueryHandler.startQuery(QUERY_TOKEN, null, RecordinUri, RECORD_IN_PROJECTION,
				Const.RECORDIN_COLUMN_RECORDID + "=? and data7 is not -1", new String[] { Bundle_recordid }, order);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	View rootView = View.inflate(this,R.layout.recordin_layout, null);
	setContentView(rootView);
//	init(rootView);
	init();
	new InitAppData(getApplicationContext()).doInitApp();
	mLayoutHide = (LinearLayout)findViewById(R.id.head_hide);
	headNewRecord = (Button)findViewById(R.id.head_btn);

	mListView = (ListView) findViewById(R.id.callslist);
	mListView.setOnCreateContextMenuListener(this);
	mListView.setAdapter(mAdapter);
    mListView.setOnTouchListener(mListTouchListener);

	
	Intent intent = getIntent();
//    Bundle_recordid = intent.getStringExtra(Const.BUNDLE_RECORD_ID);
//    Bundle_telnumber = intent.getStringExtra(Const.BUNDLE_TEL_NUMBER);
//    Bundle_ID = intent.getStringExtra(Const.BUNDLE_ID);
//    
//    
//    Bundle_readd = intent.getBooleanExtra(Const.BUNDLE_READD_RECORD, false);
//    if (Bundle_readd) {
//    	DateDialog(false);
//    }
    setActivityTitle();
    
    Bundle_Gesture = intent.getBooleanExtra(Const.BUNDLE_Gesture, false);
    mLayoutHide.setVisibility(View.VISIBLE);
		headNewRecord.setVisibility(View.VISIBLE);
//		headNewRecord.setOnClickListener(mNewRecordListener);
}

	private void init() {
		mContext = getApplicationContext();
		mAdapter = new RecentCallsAdapter();

		mQueryHandler = new QueryHandler(this);
		mDbOperator = new DBOperator(mContext);

		// --reCall
		mDetector = new GestureDetector(this, new GestureListener());

		int adapterCount = mDbOperator.gettableCount(Const.TABLE_remark);
		remarkarr = new String[adapterCount];
		for (int i = 0; i < adapterCount; i++) {
//			remarkarr[i] = mDbOperator.getRemarkName(i);
		}

	}
	 public class GestureListener extends SimpleOnGestureListener {
	    	
	    	@Override
	    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	    			float velocityY) {
	    		try {
	    		//left --> right
	    		if (e2.getX() - e1.getX() > 40 && Math.abs(velocityX) > 0 && (Math.abs(e2.getY() - e1.getY()) < 100) ) {
	    			 PopupWindow();
	    		}
	    		} catch(Exception e){
	    			
	    		}
	    		return false;
	    	}
	    }
	 
	private void setActivityTitle() {
		String name = null;// mDbOperator.getContactName(Bundle_telnumber);
		TextView txtHead = (TextView) findViewById(R.id.headTV);
		SUM_HEAD = (TextView) findViewById(R.id.txt_num);
		NUM_HEAD = (TextView) findViewById(R.id.txt_sum);
		// String recordDay = mDbOperator.getCallsRecordDay(Bundle_ID);

//		String start = Bundle_recordid.substring(Bundle_recordid.length() - 12);
//		String end = start.substring(8);
//		// views.labelView.setText(start+"-"+end);
//
//		if (Bundle_readd) {
//			txtHead.setText(name + Const.KEY_NEXTLINE + "-" + end + "²¹");
//		} else {
//			txtHead.setText(name + Const.KEY_NEXTLINE + "-" + end);
//		}
		// telView.setText(telnumber)
	}

	private boolean sortflag = false;

	@Override
	protected void onResume() {
		super.onResume();
		if (!sortflag) {
			startQuery();
			mAdapter.mPreDrawListener = null; // Let it restart the thread after
												// next draw

			Uri AUTHORITY_URI = Uri.parse("content://" + Const.AUTHORITY + "/" + Const.TABLE_RecordIN);
			mContext.getContentResolver().registerContentObserver(AUTHORITY_URI, true, mContentObserver);
			// for test
			// TypeCount = mDbOperator.gettableCount(Const.TABLE_PupWinMage);

			TypeCount = 3;
			// this.PopupWindow();
			if (Bundle_Gesture)
				popupHandler.sendEmptyMessageDelayed(0, 100);
//			String sum = Float.toString(mDbOperator.queryRecordINSum(Bundle_recordid));
//			if (sum != null && mDbOperator.isPieceNoZero(Bundle_recordid)) {
//				SUM_HEAD.setText("$" + sum);
//			} else {
//				SUM_HEAD.setText("$0");
//			}
//			NUM_HEAD.setText(" " + Integer.toString(mDbOperator.queryRecordINNum(Bundle_recordid)) + "Ì¨");
		} else {
			ArrayList<String> PupContName = mDbOperator.getPupLeafNames(Utils.toTypeID(PageIndex));
			Iterator<String> ite = PupContName.iterator();
			int k = 0;
			while (ite.hasNext()) {
				if (k == Const.PUPWIN_CONTENT_ARRAY_MAX)
					break;
				_TextArray[k++] = ite.next();
			}
			while (k < Const.PUPWIN_CONTENT_ARRAY_MAX) {
				_TextArray[k++] = null;
			}
			mMenuWindow.notifyDataSetChange();
		}
		sortflag = false;
	}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.changeCursor(null);
    }
    
	//////////////////////////////////////////////////////////////////	   
    public static final class RecentCallsListItemViews {
    TextView line1View;
    TextView remark;
    TextView line2View;
    TextView labelView;
    TextView typeView;
    TextView dateView;
    TextView rownumView;
    TextView callView;
    ImageView groupIndicator;
    TextView groupSize;
}

static final int COLUMN_INDEX_ID = 0;
static final int COLUMN_INDEX_PHONENUMBER = 1;
static final int COLUMN_INDEX_NUM = 3;
static final int COLUMN_INDEX_DATA1 = 4;
static final int COLUMN_INDEX_DATA2 = 5;
static final int COLUMN_INDEX_DATA3 = 6;
static final int COLUMN_INDEX_SUM = 7;
static final int COLUMN_INDEX_PIECE = 8;
static final int COLUMN_INDEX_DATE = 9;
static final int COLUMN_INDEX_REMARK = 10;
	final class RecentCallsAdapter extends GroupingListAdapter
			implements ViewTreeObserver.OnPreDrawListener, View.OnClickListener {
		private boolean mLoading = true;
		ViewTreeObserver.OnPreDrawListener mPreDrawListener;
		private static final int REDRAW = 1;
		private static final int START_THREAD = 2;
		private boolean mFirst;

		/**
		 * Reusable char array buffers.
		 */

		public void onClick(View view) {
			// String[] data =
			// view.getTag().toString().split(Const.KEY_DELIMITER);
			// form recordid _id
			// AlertDialog dialog = (AlertDialog) CreateDialog(data[0],data[1]);
			String data = view.getTag().toString();
			// String hintPiece = mDbOperator.queryRecordINhintPiece(data);
			// AlertDialog dialog = (AlertDialog) PieceDialog(data,hintPiece);
			// dialog.show();
			// if(Const.NO_DATA == hintPiece)
			// dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
		}

		public boolean onPreDraw() {
			if (mFirst) {
				mHandler.sendEmptyMessageDelayed(START_THREAD, 1000);
				mFirst = false;
			}
			return true;
		}

		private Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case REDRAW:
					notifyDataSetChanged();
					break;
				case START_THREAD:
					// startRequestProcessing();
					break;
				}
			}
		};

		public RecentCallsAdapter() {
			super(RecordInFrgmtActivity.this);
			mPreDrawListener = null;
		}

		/**
		 * Requery on background thread when {@link Cursor} changes.
		 */
		@Override
		protected void onContentChanged() {
			// Start async requery
			startQuery();
		}

		void setLoading(boolean loading) {
			mLoading = loading;
		}

		@Override
		public boolean isEmpty() {
			if (mLoading) {
				// We don't want the empty state to show when loading.
				return false;
			} else {
				return super.isEmpty();
			}
		}

		@Override
		protected View newStandAloneView(Context context, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.recordin_list, parent, false);
			findAndCacheViews(view);
			return view;
		}

		@Override
		protected void bindStandAloneView(View view, Context context, Cursor cursor) {
			bindView(context, view, cursor);
		}

		private void findAndCacheViews(View view) {

			// Get the views to bind to
			RecentCallsListItemViews views = new RecentCallsListItemViews();
			views.line1View = (TextView) view.findViewById(R.id.line1);
			views.remark = (TextView) view.findViewById(R.id.remark);
			views.line2View = (TextView) view.findViewById(R.id.line2);
			views.labelView = (TextView) view.findViewById(R.id.label);
			views.typeView = (TextView) view.findViewById(R.id.type);
			views.dateView = (TextView) view.findViewById(R.id.date);
			views.rownumView = (TextView) view.findViewById(R.id.rownum);
			views.callView = (TextView) view.findViewById(R.id.call_icon);
			views.callView.setOnClickListener(this);
			view.setTag(views);
		}

		public void bindView(Context context, View view, Cursor c) {
			final RecentCallsListItemViews views = (RecentCallsListItemViews) view.getTag();
			//
			int rownumber = c.getInt(COLUMN_INDEX_ID); // rownum 0
			views.callView.setTag(rownumber);

			// No.
			int No = c.getCount() - c.getPosition();
			if (!Bundle_Gesture) {
				No = c.getPosition() + 1;
			}
			views.rownumView.setText(Integer.toString(No));

			// piece
			String mPiece = c.getString(COLUMN_INDEX_PIECE); // data5 8
			mPiece = mPiece.trim();
			views.callView.setText(mPiece);

			String data2 = mDbOperator.getPupLeafName(Const.TABLE_PupLeaf, c.getString(COLUMN_INDEX_DATA2)); // data1
																													// 4
			String data3 = mDbOperator.getPupLeafName(Const.TABLE_PupLeaf, c.getString(COLUMN_INDEX_DATA3));
			views.labelView.setText(data2 + "  " + data3);

			String remark = c.getString(COLUMN_INDEX_REMARK);
			views.remark.setText(remark);

			String data1 = mDbOperator.getPupLeafName(Const.TABLE_PupLeaf, c.getString(COLUMN_INDEX_DATA1)); // name
																													// 5
			if (!TextUtils.isEmpty(data1)) {
				views.line1View.setText(data1);

				int num = c.getInt(COLUMN_INDEX_NUM); // num 3
				views.line2View.setText(Integer.toString(num));

				// if (mPiece != null && !mPiece.equals("")) {
				// float tmpMoney = num * Float.parseFloat(mPiece);
				// TOTALMONEY +=tmpMoney;
				// }

				// "type" and "label" are currently unused for SIP addresses.
				CharSequence numberLabel = null;
				String product = c.getString(COLUMN_INDEX_SUM);
				views.dateView.setText("$ " + product);

				if (!TextUtils.isEmpty(numberLabel)) {
					views.labelView.setText(numberLabel);
					views.labelView.setVisibility(View.VISIBLE);

					// Zero out the numberView's left margin (see below)
					ViewGroup.MarginLayoutParams numberLP = (ViewGroup.MarginLayoutParams) views.typeView
							.getLayoutParams();
					numberLP.leftMargin = 0;
					views.typeView.setLayoutParams(numberLP);
				} else {

					ViewGroup.MarginLayoutParams labelLP = (ViewGroup.MarginLayoutParams) views.labelView
							.getLayoutParams();
					ViewGroup.MarginLayoutParams numberLP = (ViewGroup.MarginLayoutParams) views.typeView
							.getLayoutParams();
					// Equivalent to setting android:layout_marginLeft in XML
					numberLP.leftMargin = -labelLP.rightMargin;
					views.typeView.setLayoutParams(numberLP);
				}
			} else {
				views.line1View.setText("any");
			}

			// Listen for the first draw
			if (mPreDrawListener == null) {
				mFirst = true;
				mPreDrawListener = this;
				view.getViewTreeObserver().addOnPreDrawListener(this);
			}
		}

	}
	///////////////////////////////////////////////////////////////

	private static final class QueryHandler extends AsyncQueryHandler {
		private final WeakReference<RecordInFrgmtActivity> mActivity;

		/**
		 * Simple handler that wraps background calls to catch
		 * {@link SQLiteException}, such as when the disk is full.
		 */
		protected class CatchingWorkerHandler extends AsyncQueryHandler.WorkerHandler {
			public CatchingWorkerHandler(Looper looper) {
				super(looper);
			}

			@Override
			public void handleMessage(Message msg) {
				try {
					// Perform same query while catching any exceptions
					super.handleMessage(msg);
				} catch (SQLiteDiskIOException e) {
					Log.w(TAG, "Exception on background worker thread", e);
				} catch (SQLiteFullException e) {
					Log.w(TAG, "Exception on background worker thread", e);
				} catch (SQLiteDatabaseCorruptException e) {
					Log.w(TAG, "Exception on background worker thread", e);
				}
			}
		}

		@Override
		protected Handler createHandler(Looper looper) {
			// Provide our special handler that catches exceptions
			return new CatchingWorkerHandler(looper);
		}

		public QueryHandler(Context context) {
			super(context.getContentResolver());
			mActivity = new WeakReference<RecordInFrgmtActivity>((RecordInFrgmtActivity) context);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			final RecordInFrgmtActivity activity = mActivity.get();
			if (activity != null && !activity.isFinishing()) {
				final RecordInFrgmtActivity.RecentCallsAdapter callsAdapter = activity.mAdapter;
				callsAdapter.setLoading(false);
				callsAdapter.changeCursor(cursor);
				if (activity.mScrollToTop) {
					activity.mScrollToTop = false;
				}
			} else {
				cursor.close();
			}
		}
	}
}
