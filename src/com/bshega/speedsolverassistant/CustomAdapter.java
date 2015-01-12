package com.bshega.speedsolverassistant;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<Integer> headerList = new ArrayList<Integer>();
	private LayoutInflater mInflater;
	
	public CustomAdapter(Context context) {
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	public void addItem(String item) {
		
		list.add(item);
		notifyDataSetChanged();
		
	}
	
	public void addHeaderItem(String item) {
		
		list.add(item);
		headerList.add(list.size()-1);
		notifyDataSetChanged();
		
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return headerList.contains(position) ? 1 : 0;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		int rowType = getItemViewType(position);
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			
			switch(rowType) {
			
			case 0:
				
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder.textView = (TextView) convertView.findViewById(R.id.item_text);
				break;
				
			case 1:
				
				convertView = mInflater.inflate(R.layout.list_header, null);
				holder.textView = (TextView) convertView.findViewById(R.id.item_header);
				break;
			
			}
			convertView.setTag(holder);
			
		} else {
			
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		holder.textView.setText(list.get(position));
		
		return convertView;
	}
	
	public static class ViewHolder {
		
		public TextView textView;
		
	}

}
