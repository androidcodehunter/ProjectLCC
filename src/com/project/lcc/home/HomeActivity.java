package com.project.lcc.home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.project.lcc.db.DbHelper;
import com.project.lcc.utilities.Utilities;
import com.project.lcc.utilities.ViewHelper;

public class HomeActivity extends Activity implements OnClickListener {
	private Button btnLCC;
	private Button btnImmergency;
	private Button btnDisease;
	private Button btnInstanceService;
	private ImageView ivUserManual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		if (!Utilities.isFirstTime(this)) {
			initDatabase();
		}
		initView();

		setButtonText();

		btnLCC.setOnClickListener(this);
		btnImmergency.setOnClickListener(this);
		btnDisease.setOnClickListener(this);
		btnInstanceService.setOnClickListener(this);
		ivUserManual.setOnClickListener(this);

	}

	private void initDatabase() {
		DbHelper dbHelper = new DbHelper(this);
		dbHelper.createDbWithStaticData();
		Utilities.getFirstTimeCheckPreference(this, true);
	}

	private void setButtonText() throws NotFoundException {
		int[] buttonIds = new int[] { R.id.btn_instance_service,
				R.id.btn_immergency, R.id.btn_desease_info };
		ViewHelper.setButtonText(this, buttonIds, getResources()
				.getStringArray(R.array.btns_home_array));
	/*	ViewHelper.setButtonText(this, R.id.btn_instance_service,
				getString(R.string.instant_service));*/
	}

	private void initView() {
		btnLCC = (Button) findViewById(R.id.btn_lcc);
		btnImmergency = (Button) findViewById(R.id.btn_immergency);
		btnInstanceService = (Button) findViewById(R.id.btn_instance_service);
		btnDisease = (Button) findViewById(R.id.btn_desease_info);
		ivUserManual = (ImageView) findViewById(R.id.iv_user_manual);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_lcc:
			startHomeActivity();
			break;
		case R.id.btn_immergency:
			startImmergencyActivity();
			break;
		case R.id.btn_desease_info:
			startDiseaseInfoActivity();
			break;
		case R.id.iv_user_manual:
			startUserManualActivity();
			break;
		case R.id.btn_instance_service:
			startImportantServiceActivity();
			break;
		}
	}

	private void startImportantServiceActivity() {
		startActivity(new Intent(HomeActivity.this,
				InstantServiceActivity.class));
	}

	private void startDiseaseInfoActivity() {
		startActivity(new Intent(HomeActivity.this,
				ScreenSlidePagerActivity.class));
	}

	private void startHomeActivity() {
		startActivity(new Intent(HomeActivity.this, MainActivity.class));
	}

	private void startImmergencyActivity() {
		startActivity(new Intent(HomeActivity.this, EmmergencyActivity.class));
	}

	private void startUserManualActivity() {
		startActivity(new Intent(HomeActivity.this, UserManualActivity.class));
	}
}
