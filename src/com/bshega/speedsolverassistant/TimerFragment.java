package com.bshega.speedsolverassistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class TimerFragment extends Fragment {
	
	Button saveTimeButton;
	Button resetTimeButton;
	
	ImageView timerPad;
	
	TextView timerText;
	TextView algorithmText;
	
	boolean timerRunning = false;
	
	long time = 0L;
	long elapsedTime = 0L;
	
	String scrambleString;
	String currentTime;
	
	static Handler customHandler;
	
	String[] moves = {"D","U","F","B","L","R","d","u","f","b","l","r"};
	String[] modifiers = {" ","2","'"};
	
	ShareActionProvider sap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_timer, container, false);
		
		saveTimeButton = (Button) view.findViewById(R.id.saveTimeButton);
		resetTimeButton = (Button) view.findViewById(R.id.newTrainerButton);
		
		timerPad = (ImageView) view.findViewById(R.id.trainerPad);
		
		timerText = (TextView) view.findViewById(R.id.detailAlg);
		algorithmText = (TextView) view.findViewById(R.id.detailName);
		
		customHandler = new Handler();
		
		setHasOptionsMenu(true);
		
		saveTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String filename = "cubetimes";
				
				currentTime = timerText.getText().toString();
				
				Calendar c = Calendar.getInstance();
				String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
				String month = String.valueOf(c.get(Calendar.MONTH)+1);
				String year = String.valueOf(c.get(Calendar.YEAR));
				
				String currentDate = month + "/" + day + "/" + year;
				
				JSONObject time = new JSONObject();
				
				try {
					time.put("date", currentDate);
					time.put("time", currentTime);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				File file = new File(getActivity().getFilesDir(), filename);
				
				if (!file.exists()) {
					
					JSONArray timeArray = new JSONArray();
					timeArray.put(time);
					
					JSONObject timesList = new JSONObject();
					try {
						timesList.put("times", timeArray);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String timeString = timesList.toString();
					
					try {
						
						FileOutputStream fos = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(timeString);
						oos.close();
						fos.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					
					//file.delete();
					
					try {
						
						FileInputStream fis = getActivity().openFileInput(filename);
						ObjectInputStream ois = new ObjectInputStream(fis);
						String timeString = (String) ois.readObject();
						ois.close();
						fis.close();
						
						JSONObject timesList = new JSONObject(timeString);
						
						JSONArray timeArray = timesList.getJSONArray("times");
						
						timeArray.put(time);
						
						timesList.put("times", timeArray);
						
						timeString = timesList.toString();
						
						FileOutputStream fos = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(timeString);
						oos.close();
						fos.close();
						
					} catch (StreamCorruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OptionalDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}
		});
		
		resetTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				timerText.setText("00:00.00");
				timerRunning = false;
				customHandler.removeCallbacks(updateTimerText);
				scrambleString = generateScramble();
				algorithmText.setText(scrambleString);
				
			}
		});
		
		timerPad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (timerRunning) {
					
					customHandler.removeCallbacks(updateTimerText);
					
					
				} else {
					
					time = SystemClock.uptimeMillis();
					customHandler.postDelayed(updateTimerText, 0);
					timerRunning = true;
					
				}
				
			}
		});
		
		scrambleString = generateScramble();
		
		algorithmText.setText(scrambleString);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.timer_menu, menu);
		
		MenuItem share = menu.findItem(R.id.action_share);
		sap = (ShareActionProvider) share.getActionProvider();
		
		sap.setShareIntent(shareIntent());
		
	}

	public Intent shareIntent() {
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		currentTime = timerText.getText().toString();
		shareIntent.putExtra(Intent.EXTRA_TEXT, "I just solved the Rubik's cube in " + currentTime + "!");
		shareIntent.setType("text/plain");
		
		return shareIntent;
		
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
			
			timerText.setText(timerTextString);
			customHandler.postDelayed(this, 0);
			
		}
		
	};
	
	public String generateScramble() {
		
		String scramble = "";
		
		int movesLength = moves.length;
		int modifiersLength = modifiers.length;
		
		Random r = new Random();
		
		for (int k=0; k < 25; k++) {
			
			int i = r.nextInt(movesLength);
			int j = r.nextInt(modifiersLength);
			
			scramble += moves[i] + modifiers[j] + " ";
			
		}
		
		return scramble;
		
	};
	
}
