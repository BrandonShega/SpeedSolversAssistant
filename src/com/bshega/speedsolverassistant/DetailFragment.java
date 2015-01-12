package com.bshega.speedsolverassistant;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	
	ImageView detailImage;
	
	TextView detailName;
	TextView detailAlg;
	
	String nameString;
	String algorithmString;
	
	Integer filepathInt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_detail, container, false);
		
		detailImage = (ImageView) view.findViewById(R.id.trainerImage);
		
		detailName = (TextView) view.findViewById(R.id.detailName);
		detailAlg = (TextView) view.findViewById(R.id.detailAlg);
		
		detailName.setText(nameString);
		detailAlg.setText(algorithmString);
		
        detailImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        detailImage.setImageResource(filepathInt);
		
		return view;
	}
	
	public void updateDetail(String name, String algorithm, Integer filepath) {
		
		filepathInt = filepath;
		
		nameString = name;
		nameString = nameString.toUpperCase();
		algorithmString = algorithm;
		
	}

}
