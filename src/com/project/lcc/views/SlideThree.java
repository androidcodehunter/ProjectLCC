package com.project.lcc.views;

import android.content.Context;
import android.util.AttributeSet;

import com.project.lcc.base.BaseSlideLayout;
import com.project.lcc.home.R;
import com.project.lcc.utilities.Utilities;

public class SlideThree extends BaseSlideLayout {
	public SlideThree(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setBanglaFont() {
		String[] vaccines = getContext().getResources().getStringArray(
				R.array.blast_vaccin);
		diseaseName.setText(Utilities.getBanlgaSupportText(getContext(),
				getContext().getString(R.string.disease_blast)));
		diseaseDescription.setText(Utilities.getBanlgaSupportText(getContext(),
				getContext().getString(R.string.disease_blast_description)));
		vaccinOne.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[0]));
		vaccinTwo.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[1]));
		vaccinThree.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[2]));

	}

}
