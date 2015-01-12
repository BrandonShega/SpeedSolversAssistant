package com.bshega.speedsolverassistant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeFragment extends Fragment {
	
	ListView listView;
	
	ArrayList<String> timeList;
	
	CustomAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_times, container, false);
		
		listView = (ListView) view.findViewById(R.id.timesList);
		
		timeList = new ArrayList<String>();
		
		adapter = new CustomAdapter(getActivity());
		
		Integer totalTime = 0;
		
		String filename = "cubetimes";
		
		JSONArray timeArray;
		try {
			
			FileInputStream fis = getActivity().openFileInput(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			String timesString = (String) ois.readObject();
			ois.close();
			fis.close();
			
			JSONObject timesList = new JSONObject(timesString);
			
			timeArray = timesList.getJSONArray("times");
			
			Integer fastestTime = 999999999;
			
			Integer minutes, seconds, milliseconds;
			
			adapter.addHeaderItem("Recent Times");
			
			for (int i=0; i < timeArray.length(); i++) {
				
				JSONObject time = timeArray.getJSONObject(i);
				
				String dateString = time.getString("date");
				String timeString = time.getString("time");
				
				String listString = dateString + " - " + timeString;
				
				String[] minuteArray = timeString.split(":");
				String[] secondArray = minuteArray[1].split("\\.");
				
				minutes = Integer.parseInt(minuteArray[0]);
				seconds = Integer.parseInt(secondArray[0]);
				milliseconds = Integer.parseInt(secondArray[1]);
				
				milliseconds = milliseconds + (minutes*60000) + (seconds*1000);
				
				totalTime += milliseconds;
				
				if (milliseconds < fastestTime) {
					fastestTime = milliseconds;
				}
				
				adapter.addItem(listString);
				
			}
			
			
			
			milliseconds = (fastestTime % 1000);
			seconds = (fastestTime / 1000);
			minutes = seconds / 60;
			
			String timeString = "" + minutes + ":" + String.format("%02d", seconds) + "." + String.format("%02d", milliseconds);
			
			adapter.addHeaderItem("Fastest Time");
			adapter.addItem(timeString);
			
			Integer averageTime = totalTime / timeArray.length();
			
			milliseconds = (averageTime % 1000);
			seconds = (averageTime / 1000);
			minutes = seconds / 60;
			
			timeString = "" + minutes + ":" + String.format("%02d", seconds) + "." + String.format("%02d", milliseconds);
			
			adapter.addHeaderItem("Average Time");
			adapter.addItem(timeString);
		
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			//NO TIMES YET SO NOTIFY USER
			adapter.addHeaderItem("Recent Times");
			adapter.addItem("No Recent Times");
			
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
		
		//listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, timeList));
		
		listView.setAdapter(adapter);
		
		return view;
		
	}

}
