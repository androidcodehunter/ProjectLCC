package com.project.lcc.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.lcc.home.R;

public class ScreenSlidePagerAdapter extends PagerAdapter {
	private LayoutInflater mInflater;
	private static int[] mLayouts = { R.layout.slide_one, R.layout.slide_two,
			R.layout.slide_three};

	public ScreenSlidePagerAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewGroup pageView = (ViewGroup) mInflater.inflate(mLayouts[position],
				container, false);
		container.addView(pageView);
		getItemPosition(pageView);
		return pageView;
	}

	@Override
	public int getCount() {
		return mLayouts.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}
}