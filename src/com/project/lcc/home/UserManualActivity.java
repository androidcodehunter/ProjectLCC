package com.project.lcc.home;

import android.os.Bundle;

import com.project.lcc.base.BaseActionBar;
import com.project.lcc.utilities.ViewHelper;

public class UserManualActivity extends BaseActionBar {
	public UserManualActivity() {
		super(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_manual);

		int[] textViewIds = new int[] { R.id.user_manual_1, R.id.user_manual_2,
				R.id.user_manual_3, R.id.user_manual_4, R.id.user_manual_5,
				R.id.user_manual_6, R.id.user_manual_7, R.id.user_manual_8,
				R.id.user_manual_9, R.id.user_manual_10, R.id.user_manual_11 };

		ViewHelper.setTextViewText(this, textViewIds, getResources()
				.getStringArray(R.array.user_manuals));

	}

}
