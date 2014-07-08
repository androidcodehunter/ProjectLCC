package com.project.lcc.home;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.lcc.base.BaseActionBar;
import com.project.lcc.model.UpoZilla;
import com.project.lcc.model.Zilla;
import com.project.lcc.utilities.Utilities;
import com.project.lcc.utilities.ViewHelper;

public class EmmergencyActivity extends BaseActionBar {
	public EmmergencyActivity() {
		super(R.id.menu_immergency_services);
	}

	private Spinner spZilla;
	private Spinner spUpoZilla;
	private Spinner spType;
	private Button btnMore;
	private ArrayAdapter<Zilla> adapterZilla;
	private ArrayAdapter<UpoZilla> adapterUpoZilla;
	private ArrayAdapter<String> adapterImmergencyType;
	private int selectedItemPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.immergency);

		initView();

		adapterZilla = new setCustomFontInSpinner(this,
				android.R.layout.simple_spinner_item, loadSpinnerDataZilla());
		adapterZilla
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spZilla.setAdapter(adapterZilla);

		spZilla.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedItemPosition = position;
				adapterUpoZilla = new ArrayAdapter<UpoZilla>(
						EmmergencyActivity.this,
						android.R.layout.simple_spinner_item,
						loadSpinnerDataUpoZilla(position)) {

					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						View view = super
								.getView(position, convertView, parent);
						((TextView) view).setTypeface(Utilities
								.getCustomBanglaFont(EmmergencyActivity.this));
						return view;
					}

					@Override
					public View getDropDownView(int position, View convertView,
							ViewGroup parent) {
						View view = super.getDropDownView(position,
								convertView, parent);
						((TextView) view).setTypeface(Utilities
								.getCustomBanglaFont(EmmergencyActivity.this));
						return view;
					}
				};
				adapterUpoZilla
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				spUpoZilla.setAdapter(adapterUpoZilla);
			}

			@Override
			public void onNothingSelected(AdapterView<?> view) {
			}
		});
		
		adapterImmergencyType = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.immergency_info_type_array)) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				((TextView) view).setTypeface(Utilities
						.getCustomBanglaFont(EmmergencyActivity.this));
				return view;
			}

			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View view = super
						.getDropDownView(position, convertView, parent);
				((TextView) view).setTypeface(Utilities
						.getCustomBanglaFont(EmmergencyActivity.this));
				return view;
			}
		};
		adapterImmergencyType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(adapterImmergencyType);

		btnMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedItemPosition == 0) {
					ShowMessage(getResources().getString(
							R.string.zilla_unselected_message));
					return;
				}

				if (isAgriOfficeInfo(spType.getSelectedItemPosition())) {
					startAgriOfficeActivity();
				} else if (isAgriOfficerInfo(spType.getSelectedItemPosition())) {
					startAgriOfficerActivity();
				}
			}

			private int getUpoZillaId() {
				Iterator<UpoZilla> itr = loadSpinnerDataUpoZilla(
						spZilla.getSelectedItemPosition()).iterator();
				int upozillaId = 0;
				while (itr.hasNext()) {
					UpoZilla upozilla = (UpoZilla) itr.next();
					if (isSelectedUpoZilla(upozilla))
						upozillaId = upozilla.getId();
				}
				return upozillaId;
			}

			private boolean isSelectedUpoZilla(UpoZilla upozilla) {
				return spUpoZilla.getSelectedItem().toString()
						.equals(upozilla.getName());
			}

			private void startAgriOfficerActivity() {
				Intent intentAgriOfiicer = new Intent(
						EmmergencyActivity.this,
						AgriOfficerInfoActivity.class);
				intentAgriOfiicer.putExtra("UPOZILLA_ID", getUpoZillaId());
				startActivity(intentAgriOfiicer);
			}

			private void startAgriOfficeActivity() {
				Intent intentAgriOffice = new Intent(EmmergencyActivity.this,
						AgriOfficeInfoActivity.class);
				intentAgriOffice.putExtra("UPOZILLA_ID", getUpoZillaId());
				startActivity(intentAgriOffice);
			}

			private boolean isAgriOfficerInfo(int selectedType) {
				return selectedType == 1 ? true : false;
			}

			private boolean isAgriOfficeInfo(int selectedType) {
				return selectedType == 0 ? true : false;
			}
		});

	}

	public void ShowMessage(String message) {
		Utilities.showBanglaSupportedToast(this, message);
	}

	private void initView() {
		spZilla = (Spinner) findViewById(R.id.sp_zilla);
		spUpoZilla = (Spinner) findViewById(R.id.sp_upozilla);
		spType = (Spinner) findViewById(R.id.sp_type);
		btnMore = (Button) findViewById(R.id.btn_more);

		ViewHelper.setButtonText(this, R.id.btn_more,
				getResources().getString(R.string.see_info_button));
	}

	private ArrayList<Zilla> loadSpinnerDataZilla() {
		SQLiteDatabase db = openOrCreateDatabase("DBLCC", MODE_PRIVATE, null);
		Cursor cursor = db.rawQuery("select * from zilla", null);
		ArrayList<Zilla> zillas = new ArrayList<Zilla>();
		if (cursor.moveToFirst()) {
			do {
				Zilla zilla = new Zilla();
				zilla.setId(Integer.parseInt(cursor.getString(0)));
				zilla.setName(cursor.getString(1));
				zillas.add(zilla);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return zillas;
	}

	private ArrayList<UpoZilla> loadSpinnerDataUpoZilla(int id) {
		SQLiteDatabase db = openOrCreateDatabase("DBLCC", MODE_PRIVATE, null);

		ArrayList<UpoZilla> upozillas = new ArrayList<UpoZilla>();

		Cursor cursor = db.rawQuery("select * from upozilla where zilla_id='"
				+ id + "'", null);
		if (cursor.moveToFirst()) {
			do {
				UpoZilla upoZilla = new UpoZilla();
				upoZilla.setId(Integer.parseInt(cursor.getString(0)));
				upoZilla.setName(cursor.getString(1));
				upoZilla.setZillId(id);
				upozillas.add(upoZilla);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return upozillas;
	}

	private final class setCustomFontInSpinner extends ArrayAdapter<Zilla> {
		private setCustomFontInSpinner(Context context, int resource,
				List<Zilla> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			((TextView) view).setTypeface(Utilities
					.getCustomBanglaFont(EmmergencyActivity.this));
			return view;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			View view = super.getDropDownView(position, convertView, parent);
			((TextView) view).setTypeface(Utilities
					.getCustomBanglaFont(EmmergencyActivity.this));
			return view;
		}
	}

}
