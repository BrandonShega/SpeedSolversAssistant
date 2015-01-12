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

public class PLLFragment extends Fragment {

	GridView gridView;
	Context mContext;
	
	PLLListener listener;
	
	String[] images = {"aa","ab","e","f","ga","gb","gc","gd","h","ja","jb","na","nb","ra","rb","t","ua","ub","v","y","z"};
	String[] algorithms = {
		"x [(R' U R') D2] [(R U' R') D2] R2",
		"x' [(R U' R) D2] [(R' U R) D2] R2",
		"x' (R U' R') D (R U R') D' (R U R') D (R U' R') D'",
		"[R' U2 R' d'] [R' F'] [R2 U' R' U] [R' F R U' F]",
		"R2 u R' U R' U' R u' R2 [y' R' U R]",
		"R2 u R' U R' U' R u' R2 [y' R' U R]",
		"R2 u' R U' R U R' u R2 [y R U' R']",
		"[R U R'] y' R2 u' R U' R' U R' u R2",
		"M2 U M2 U2 M2 U M2",
		"[R' U L'] [U2 R U' R' U2] [R L U']",
		"[R U R' F'] {[R U R' U'] [R' F] [R2 U' R'] U'}",
		"{(L U' R) U2 (L' U R')} {(L U' R) U2 (L' U R')} U",
		"{(R' U L') U2 (R U' L)} {(R' U L') U2 (R U' L)} U'",
		"[L U2' L' U2'] [L F'] [L' U' L U] [L F] L2' U",
		"[R' U2 R U2] [R' F] [R U R' U'] [R' F'] R2 U'",
		"[R U R' U'] [R' F] [R2 U' R'] U' [R U R' F']",
		"R2 U [R U R' U'] (R' U') (R' U R')",
		"[R U'] [R U] [R U] [R U'] R' U' R2",
		"[R' U R' d'] [R' F'] [R2 U' R' U] [R' F R F]",
		"F R U' R' U' [R U R' F'] {[R U R' U'] [R' F R F']}",
		"M2 U M2 U M' U2 M2 U2 M' U2"
	};
	
	Integer[] thumbs = new Integer[images.length];
	Integer filepath;
	
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
				
				listener.changePLLDetails(images[arg2], algorithms[arg2], thumbs[arg2]);
				
			}
		});
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		listener = (PLLListener)activity;
		
	}

	public interface PLLListener {
		
		void changePLLDetails(String name, String algorithm, Integer filepath);
		
	}

	public class ImageAdapter extends BaseAdapter {
		
		public ImageAdapter(Context c) {
			
			mContext = c;
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return thumbs.length;
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
