package com.project.lcc.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lcc.home.R;
import com.project.lcc.utilities.Utilities;

public abstract class BaseSlideLayout extends LinearLayout {

	public TextView diseaseName;
	public TextView diseaseDescription;
	public TextView vaccinOne;
	public TextView vaccinTwo;
	public TextView vaccinThree;
	public TextView diseaseProtection;

	public BaseSlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		diseaseName = (TextView) findViewById(R.id.tv_diseas_name);
		diseaseDescription = (TextView) findViewById(R.id.tv_diseas_description);
		vaccinOne = (TextView) findViewById(R.id.vaccin_one);
		vaccinTwo = (TextView) findViewById(R.id.vaccin_two);
		vaccinThree = (TextView) findViewById(R.id.vaccin_three);
		diseaseProtection = (TextView) findViewById(R.id.disease_protection);
		diseaseProtection.setText(Utilities.getBanlgaSupportText(getContext(),
				getContext().getString(R.string.disease_protection)));
		setBanglaFont();
	}

	public abstract void setBanglaFont();
}
