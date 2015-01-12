package com.bshega.speedsolverassistant;

import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TrainerFragment extends Fragment {
	
	String[] images = {
			"aa",
			"ab",
			"e",
			"f",
			"ga",
			"gb",
			"gc",
			"gd",
			"h",
			"ja",
			"jb",
			"na",
			"nb",
			"ra",
			"rb",
			"t",
			"ua",
			"ub",
			"v",
			"y",
			"z",
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
	
	Button resetButton;
	
	ImageView trainerImage;
	ImageView trainerPad;
	
	TextView timeTextView;
	
	Handler customHandler;
	
	boolean timerRunning = false;
	
	long time = 0L;
	long elapsedTime = 0L;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_trainer, container, false);
		
		resetButton = (Button) view.findViewById(R.id.newTrainerButton);
		
		trainerImage = (ImageView) view.findViewById(R.id.trainerImage);
		trainerPad = (ImageView) view.findViewById(R.id.trainerPad);
		
		timeTextView = (TextView) view.findViewById(R.id.timeTextView);
		
		customHandler = new Handler();
		
		trainerPad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (timerRunning) {
					
					customHandler.removeCallbacks(updateTimerText);
					
					
				} else {
					
					time = SystemClock.uptimeMillis();
					customHandler.postDelayed(updateTimerText, 0);
					//Toast.makeText(getActivity(), "Timer Started!", Toast.LENGTH_LONG).show();
					
					timerRunning = true;
					
				}
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				customHandler.removeCallbacks(updateTimerText);
				timeTextView.setText("00:00.00");
				timerRunning = false;
				getRandomCase();
			}
		});
		
		getRandomCase();
		
		return view;
	}
	
	private Runnable updateTimerText = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			elapsedTime = SystemClock.uptimeMillis() - time;
			
			int milliseconds = (int) (elapsedTime % 1000);
			int seconds = (int) (elapsedTime / 1000);
			int minutes = (int) seconds / 60;
			
			seconds = seconds % 60;
			
			String timerTextString = "" + minutes + ":" + String.format("%02d", seconds) + "." + String.format("%02d", milliseconds);
			
			timeTextView.setText(timerTextString);
			customHandler.postDelayed(this, 0);
			
		}
		
	};
	
	public void getRandomCase() {
		
		Random random = new Random();
		
		int randomCase = random.nextInt(images.length);
		
		String caseName = images[randomCase];
		Integer caseImg = getResources().getIdentifier(caseName, "drawable", getActivity().getPackageName());
		
		trainerImage.setImageResource(caseImg);
		
	}
	
}
