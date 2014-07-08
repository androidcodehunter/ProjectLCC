package com.project.lcc.db;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.project.lcc.home.R;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper {

	private static Context context;
	public DbHelper(Context context) {
				this.context = context;
	}
	public void createDbWithStaticData() {
		SQLiteDatabase db = context.openOrCreateDatabase("DBLCC", context.MODE_PRIVATE, null);

		manageZilla(db);
		manageUpozilla(db);
		//manageHotline(db);
		manageHospital(db);
		manageDoctor(db);
		//manageHealthAdvisor(db);

		db.close();

	}

	private void manageZilla(SQLiteDatabase db) throws NotFoundException {
		createZilla(db);
		String zillas = context.getResources().getString(R.string.zillas);
		ArrayList<String> zillaList = new ArrayList<String>();
		zillaList = getListFromString(zillas);
		insertZilla(db, zillaList);
	}

	private void createZilla(SQLiteDatabase db) {
		deleteTable(db, "zilla");
		db.execSQL("CREATE TABLE IF  NOT EXISTS zilla(zilla_id INTEGER PRIMARY KEY ,zilla_name VARCHAR);");
	}
	
	private void insertZilla(SQLiteDatabase db, ArrayList<String> zillaList) {

		for (int i = 0; i < zillaList.size(); i++) {
			db.execSQL("INSERT INTO zilla (zilla_id,zilla_name) VALUES('"
					+ zillaList.get(i) + "','" + zillaList.get(++i) + "')");
		}

	}
	
	
	private void manageUpozilla(SQLiteDatabase db) throws NotFoundException {
		createUpoZilla(db);
		String upozillas = context.getResources().getString(R.string.upozillas);
		ArrayList<String> upozillaList = new ArrayList<String>();
		upozillaList = getListFromString(upozillas);
		insertUpoZilla(db, upozillaList);
	}
	
	private void createUpoZilla(SQLiteDatabase db) {
		deleteTable(db, "upozilla");
		db.execSQL("CREATE TABLE IF  NOT EXISTS upozilla(upozilla_id INTEGER PRIMARY KEY ,upozilla_name VARCHAR, zilla_id INTEGER);");

	}
	
	private void insertUpoZilla(SQLiteDatabase db,
			ArrayList<String> upozillaList) {

		for (int i = 0; i < upozillaList.size(); i++) {
			db.execSQL("INSERT INTO upozilla (upozilla_id,upozilla_name,zilla_id) VALUES('"
					+ upozillaList.get(i)
					+ "','"
					+ upozillaList.get(++i)
					+ "','" + upozillaList.get(++i) + "')");
		}
	}
	
	
	private void manageHospital(SQLiteDatabase db) {

		createHospital(db);
		String hospitals = context.getResources().getString(R.string.hospitals);
		ArrayList<String> hospitalList = new ArrayList<String>();
		hospitalList = getListFromString(hospitals);
		insertHospital(db, hospitalList);
	}

	private void insertHospital(SQLiteDatabase db,
			ArrayList<String> hospitalList) {

		for (int i = 0; i < hospitalList.size(); i++) {
			db.execSQL("INSERT INTO hospital (hos_id ,hos_name ,hos_location, hos_day, hos_time , hos_lat,hos_long, upozilla_id, hos_contact) VALUES('"
					+ hospitalList.get(i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','"
					+ hospitalList.get(++i)
					+ "','" + hospitalList.get(++i) + "')");
		}
	}

	private void createHospital(SQLiteDatabase db) {
		deleteTable(db, "hospital");
		db.execSQL("CREATE TABLE IF  NOT EXISTS hospital(hos_id INTEGER PRIMARY KEY ,hos_name VARCHAR,hos_location VARCHAR, hos_day VARCHAR, hos_time VARCHAR, hos_lat REAL, hos_long REAL, upozilla_id INTEGER, hos_contact VARCHAR);");

	}
	
	
	private void manageDoctor(SQLiteDatabase db) {
		createDoctor(db);
		String doctors = context.getResources().getString(R.string.doctors);
		ArrayList<String> doctorList = new ArrayList<String>();
		doctorList = getListFromString(doctors);
		insertDoctor(db, doctorList);

	}

	private void insertDoctor(SQLiteDatabase db, ArrayList<String> doctorList) {
		for (int i = 0; i < doctorList.size(); i++) {
			db.execSQL("INSERT INTO doctor (doctor_id,doctor_name ,doctor_contact ,doctor_profile, hos_id) VALUES('"
					+ doctorList.get(i)
					+ "','"
					+ doctorList.get(++i)
					+ "','"
					+ doctorList.get(++i)
					+ "','"
					+ doctorList.get(++i)
					+ "','"
					+ doctorList.get(++i) + "')");
		}

	}

	private void createDoctor(SQLiteDatabase db) {
		deleteTable(db, "doctor");
		db.execSQL("CREATE TABLE IF  NOT EXISTS doctor(doctor_id INTEGER PRIMARY KEY ,doctor_name VARCHAR,doctor_contact VARCHAR,doctor_profile VARCHAR, hos_id INTEGER);");

	}

	
	private void deleteTable(SQLiteDatabase db, String tableName) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	private ArrayList<String> getListFromString(String zillas) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(zillas, ",");
		while (st.hasMoreElements()) {
			list.add(st.nextElement().toString());

		}
		return list;
	}
}
