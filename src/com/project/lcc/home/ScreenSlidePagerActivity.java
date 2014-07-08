package com.project.lcc.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;

import com.project.lcc.adapter.ScreenSlidePagerAdapter;
import com.project.lcc.base.BaseActionBar;

public class ScreenSlidePagerActivity extends BaseActionBar {

	public ScreenSlidePagerActivity() {
		super(R.id.menu_disease_instruction);
	}

	private ViewPager mViewPager;
	private ScreenSlidePagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_screen);

		mViewPager = (ViewPager) findViewById(R.id.pager);

		adapter = new ScreenSlidePagerAdapter(this){
		
		};

		mViewPager.setAdapter(adapter);
		
	}

}