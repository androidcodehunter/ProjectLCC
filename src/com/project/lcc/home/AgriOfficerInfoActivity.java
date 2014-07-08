package com.project.lcc.home;

import java.util.ArrayList;

import com.project.lcc.adapter.AgriOfficerProfileAdapter;
import com.project.lcc.base.BaseActionBar;
import com.project.lcc.model.AgriOfficerProfile;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class AgriOfficerInfoActivity extends BaseActionBar {
	
	public AgriOfficerInfoActivity() {
		super(R.id.menu_immergency_services);
	}

	private Bundle extras;
	private int UpozZillaId;
	private ArrayList<AgriOfficerProfile> rowItems;
	private AgriOfficerProfileAdapter adapter;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.officer_profile_info_main);

		listView = (ListView) findViewById(R.id.lv_agri_officer_main);
		extras = this.getIntent().getExtras();
		if (extras != null) {
			UpozZillaId = extras.getInt("UPOZILLA_ID");
		}

		rowItems = new ArrayList<AgriOfficerProfile>();

		adapter = new AgriOfficerProfileAdapter(this, R.layout.officer_profile_info,
				getAgriofficerProfileData());
		listView.setAdapter(adapter);

	}

	private ArrayList<AgriOfficerProfile> getAgriofficerProfileData() {

		SQLiteDatabase db = openOrCreateDatabase("DBLCC", MODE_PRIVATE, null);
		String query = "select d.doctor_name, d.doctor_contact, d.doctor_profile, h.hos_name,d.doctor_id from doctor d inner join hospital h on h.hos_id = d.hos_id where h.upozilla_id ='"
				+ UpozZillaId + "' group by d.doctor_id";

		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				rowItems.add(new AgriOfficerProfile(cursor.getString(0), cursor.getString(2), cursor.getString(1), cursor.getString(3)));
			} while (cursor.moveToNext());
		}

		return rowItems;
	}
}
