package com.project.lcc.home;

import java.util.ArrayList;

import com.project.lcc.adapter.AgriOfficeAdapter;
import com.project.lcc.base.BaseActionBar;
import com.project.lcc.model.AgriOffice;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class AgriOfficeInfoActivity extends BaseActionBar {
	
	public AgriOfficeInfoActivity() {
		super(R.id.menu_immergency_services);
	}

	private Bundle extras;
	private int UpozZillaId;
	private ArrayList<AgriOffice> rowItems;
	private AgriOfficeAdapter adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agri_office_info_main);
		listView = (ListView) findViewById(R.id.lv_agri_office_main);
		extras = this.getIntent().getExtras();
		if (extras != null) {
			UpozZillaId = extras.getInt("UPOZILLA_ID");
		}

		rowItems = new ArrayList<AgriOffice>();

		adapter = new AgriOfficeAdapter(this, R.layout.agri_office_info,
				getAgriofficeData());
		listView.setAdapter(adapter);

	}

	private ArrayList<AgriOffice> getAgriofficeData() {

		SQLiteDatabase db = openOrCreateDatabase("DBLCC", MODE_PRIVATE, null);
		String query = "select * from hospital"
				+ " where hos_id in(select hos_id from hospital where upozilla_id='"
				+ UpozZillaId + "' ); ";

		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				rowItems.add(new AgriOffice(cursor.getString(1), cursor
						.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(8), cursor
								.getFloat(5), cursor.getFloat(6)));
			} while (cursor.moveToNext());
		}

		return rowItems;
	}

}
