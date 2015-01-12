package com.bshega.speedsolverassistant;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity implements PLLFragment.PLLListener, OLLFragment.OLLListener{
	
	DrawerLayout drawerLayout;
	ListView timesListView;
	ListView drawerList;
	String[] drawerTitles = {"Home", "Timer", "PLL", "OLL", "Trainer", "Competitions"};
	Context mContext;
	ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		mContext = this;
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.nav_drawer);
		
		drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, drawerTitles));
		
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			
			@Override
			public void onDrawerClosed(View view) {
				
				super.onDrawerClosed(view);
				
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				
				super.onDrawerOpened(drawerView);
				
			}
			
		};
		
		drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		drawerLayout.openDrawer(drawerList);
		
		Fragment fragment = new HomeFragment();
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.main_layout, fragment);
		ft.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			selectItem(position);
			
		}
		
	}
	
	private void selectItem(int position) {
		
		Fragment fragment = null;
		FragmentManager fm = getFragmentManager();
		
		switch(position) {
		
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new TimerFragment();
			break;
		case 2:
			fragment = new PLLFragment();
			break;
		case 3:
			fragment = new OLLFragment();
			break;
		case 4:
			fragment = new TrainerFragment();
			break;
		case 5:
			
			Intent competitionActivity = new Intent(mContext, CompetitionActivity.class);
			startActivity(competitionActivity);
			break;
		default:
			break;
		}
		
		if (fragment != null) {
			
			fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.main_layout, fragment);
			transaction.commit();
			
			drawerList.setItemChecked(position, true);
			setTitle("Home");
			drawerLayout.closeDrawer(drawerList);
			
		}
	}
	
	public void setTitle(CharSequence title) {
		
		CharSequence mTitle = title;
		getActionBar().setTitle(mTitle);
		
	}
	
	public static class ViewFragment extends Fragment {
		
		public static final String ARG_FRAGMENT_NUMBER = "fragment_number";
		
		public ViewFragment() {
			
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		
		
		
	}

	@Override
	public void changePLLDetails(String name, String algorithm, Integer filepath) {
		// TODO Auto-generated method stub
		
		DetailFragment detailFragment = new DetailFragment();
		
		FragmentManager manager = this.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_layout, detailFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		detailFragment.updateDetail(name, algorithm, filepath);
		
	}

	@Override
	public void changeOLLDetails(String name, String algorithm, Integer filepath) {
		// TODO Auto-generated method stub
		
		DetailFragment detailFragment = new DetailFragment();
		
		FragmentManager manager = this.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_layout, detailFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		detailFragment.updateDetail(name, algorithm, filepath);
		
	}

}
