package com.bshega.speedsolverassistant;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class OLLFragment extends Fragment {
	
	String[] images = {
			"opposite",
			"adjacent",
			"none",
			"sune",
			"antisune",
			"car",
			"blinker",
			"headlights",
			"chameleon",
			"bowtie"
	};
	
	String[] algorithms = {
			"F (R U R' U' ) F'",
			"f (R U R' U' ) f'",
			"[F (R U R' U' ) F'] [f (R U R' U' ) f']",
			"(R U R') U (R U2 R')",
			"(R' U' R) U' (R' U2 R)",
			"F (R U R' U') (R U R' U') (R U R' U') F'",
			"[f (R U R' U') f'] [F (R U R' U') F']",
			"(R2 D) (R' U2) (R D') (R' U2 R')",
			"(r U R' U') (r' F R F')",
			"F' (r U R' U') (r' F R )"
	};

	GridView gridView;
	Context mContext;
	
	Integer filepath;
	Integer[] thumbs = new Integer[images.length];
	
	OLLListener listener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_grid, container, false);
		
		gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(getActivity()));
		
		for (int i=0; i<images.length; i++) {
			
			String name = images[i];
			
			String filename = name;
			
			filepath = getResources().getIdentifier(filename, "drawable", getActivity().getPackageName());
			
			thumbs[i] = filepath;
			
		}
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				listener.changeOLLDetails(images[arg2], algorithms[arg2], thumbs[arg2]);
				
			}
		});
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		listener = (OLLListener)activity;
		
	}

	public interface OLLListener {
		
		void changeOLLDetails(String name, String algorithm, Integer filepath);
		
	}

	public class ImageAdapter extends BaseAdapter {
		
		public ImageAdapter(Context c) {
			
			mContext = c;
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length;
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
			
			ImageView imageView;
			
			if (convertView == null) {
				
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
				
			} else {
				
				imageView = (ImageView) convertView;
				
			}
			
			imageView.setImageResource(thumbs[position]);
			return imageView;
		}
		
	}
	
}
