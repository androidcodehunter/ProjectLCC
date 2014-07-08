package com.project.lcc.base;

import java.lang.reflect.Field;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.project.lcc.home.EmmergencyActivity;
import com.project.lcc.home.InstantServiceActivity;
import com.project.lcc.home.MainActivity;
import com.project.lcc.home.R;
import com.project.lcc.home.ScreenSlidePagerActivity;

public abstract class BaseActionBar extends ActionBarActivity {
	private ActionBar actionBar;
	private int id;

	public BaseActionBar(int id) {
		this.id = id;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setIcon(R.drawable.lcc_logo);
		actionBar.show();
		getOverflowMenu();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (id != 0) {
			menu.removeItem(id);
			//supportInvalidateOptionsMenu();
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.menu_lcc) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return true;
		} else if (itemId == R.id.menu_disease_instruction) {
			startActivity(new Intent(this, ScreenSlidePagerActivity.class));
			finish();
			return true;
		} else if (itemId == R.id.menu_immergency_services) {
			startActivity(new Intent(this, EmmergencyActivity.class));
			finish();
			return true;
		} else if (itemId == R.id.menu_instant_services) {
			startActivity(new Intent(this, InstantServiceActivity.class));
			finish();
			return true;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
