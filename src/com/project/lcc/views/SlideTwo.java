package com.project.lcc.views;

import android.content.Context;
import android.util.AttributeSet;

import com.project.lcc.base.BaseSlideLayout;
import com.project.lcc.home.R;
import com.project.lcc.utilities.Utilities;

public class SlideTwo extends BaseSlideLayout {
	public SlideTwo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setBanglaFont() {
		String[] vaccines = getContext().getResources().getStringArray(
				R.array.pata_pora_vaccin);
		diseaseName.setText(Utilities.getBanlgaSupportText(getContext(),
				getContext().getString(R.string.disease_pata_pora)));
		diseaseDescription
				.setText(Utilities.getBanlgaSupportText(
						getContext(),
						getContext().getString(
								R.string.disease_pata_pora_description)));
		vaccinOne.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[0]));
		vaccinTwo.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[1]));
		vaccinThree.setText(Utilities.getBanlgaSupportText(getContext(),
				vaccines[2]));
	}

}
