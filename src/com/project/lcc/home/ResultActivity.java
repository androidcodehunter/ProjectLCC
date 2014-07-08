package com.project.lcc.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.project.lcc.base.BaseActionBar;
import com.project.lcc.utilities.ViewHelper;

public class ResultActivity extends BaseActionBar implements
		OnCheckedChangeListener {

	public ResultActivity() {
		super(R.id.menu_lcc);
	}

	String[] palletMessageBoro;

	private Bundle extras;
	private ImageView ivTickOne, ivTickTwo, ivTickThree, ivTickFour,
			ivTickFive, ivTickSix;
	int index;

	private int result;

	private RadioGroup dhanType;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.similar_pallete);

		initView();
		ViewHelper.setTextViewText(this, R.id.txt_urea_title,
				getString(R.string.urea_title));

		extras = getIntent().getExtras();
		double minIndex = 0;
		Bitmap bitmap = null;
		if (extras != null) {
			minIndex = extras.getDouble("MIN_INDEX");
			bitmap = (Bitmap) this.getIntent().getParcelableExtra(
					"OUTPUT_IMAGE");
			Log.e("value", "" + minIndex);
		}

		index = (int) ((minIndex * 10) % 10);
		result = index >= 5 ? (int) Math.ceil(minIndex) : (int) Math
				.floor(minIndex);

		View[] views = new View[] { ivTickOne, ivTickTwo, ivTickThree,
				ivTickFour, ivTickFive, ivTickSix };
		views[result].setVisibility(View.VISIBLE);

	}

	private void initView() {
		ivTickOne = (ImageView) findViewById(R.id.iv_tick_one);
		ivTickTwo = (ImageView) findViewById(R.id.iv_tick_two);
		ivTickThree = (ImageView) findViewById(R.id.iv_tick_three);
		ivTickFour = (ImageView) findViewById(R.id.iv_tick_four);
		ivTickFive = (ImageView) findViewById(R.id.iv_tick_five);
		ivTickSix = (ImageView) findViewById(R.id.iv_tick_six);
		dhanType = (RadioGroup) findViewById(R.id.dhan_type);

		ViewHelper.setRadioButtonText(this, R.id.radio_boro,
				getString(R.string.boro));
		ViewHelper.setRadioButtonText(this, R.id.radio_ropa,
				getString(R.string.ropa));

		dhanType.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.radio_boro:
			if (result > 2) {
				setUpResultText(getResources()
						.getString(R.string.nitrigen_boro));
			}

			else {
				setUpResultText(getResources().getString(
						R.string.nitrogen_no_used));
			}

			break;

		case R.id.radio_ropa:
			if (result > 2) {
				setUpResultText(getResources()
						.getString(R.string.nitrigen_amon));
			} else {
				setUpResultText(getResources().getString(
						R.string.nitrogen_no_used));
			}
			break;
		}
	}

	public void setUpResultText(String text) {
		ViewHelper.setTextViewText(ResultActivity.this,
				R.id.tv_pallate_message, text);
	}

}
