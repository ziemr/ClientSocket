package com.android.clientsocket.util;

import java.util.ArrayList;

import com.android.clientsocket.R;
import com.android.clientsocket.provider.DBOperator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class PupWidowsAdapter extends BaseAdapter {
    private Context Context;
//    private int[] _TextView;
    private String[] _TextView;
    private int[] _ImageView;
    ArrayList<String> items;
    private boolean _limtcount = false;
    
    public PupWidowsAdapter(Context ct, int[] text,int[] icons) {
    	Context = ct;
        _ImageView = icons;
//        _TextView = text;
    }
    
    public PupWidowsAdapter(Context ct, String[] text,int[] icons) {
    	Context = ct;
        _ImageView = icons;
//        _TextView2 = text;
    }
    public PupWidowsAdapter(Context ct, String[] text,boolean calc) {
    	Context = ct;
//        _ImageView = icons;
        _TextView = text;
        _limtcount = calc;
    }
    
    public PupWidowsAdapter(Context ct, ArrayList<String> items) {
    	Context = ct;
//        _ImageView = icons;
//        _TextView = text;
        this.items = items;
    }
	@Override
	public int getCount() {
		if (_limtcount) return Const.PUPWIN_CONTENT_NUM_12;
		int limtcount = 0;

		
		String temp = new DBOperator(Context).getSharedDataValue(Const.KEY_POPHEIGHT);
		    String temparr[] = temp.split(Const.KEY_DELIMITER);
		    limtcount = Integer.parseInt(temparr[1]);
		return limtcount;
	}

	@Override
	public Object getItem(int position) {
//		return _TextView[position];
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
        if (convertView == null) {
		convertView = View.inflate(Context, R.layout.pup_adapter, null);
		viewHolder = new ViewHolder();
		viewHolder.image  = (ImageView) convertView.findViewById(R.id.function_icon);
        viewHolder.title = (TextView) convertView.findViewById(R.id.function_text);
        viewHolder.image.setVisibility(View.GONE);
		convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(_TextView[position]);
//        viewHolder.title.setText(items.get(position));
        return convertView;
//		return v;
	}
    class ViewHolder {
        public TextView title;
        public ImageView image;
    }

}
