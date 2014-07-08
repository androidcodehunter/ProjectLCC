package com.project.lcc.home;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.lcc.base.BaseActionBar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

public class AgriOfficeMapActivity extends BaseActionBar {
	public AgriOfficeMapActivity() {
		super(R.id.menu_immergency_services);
	}

	private float latitude;
	private float longitude;
	private Bundle extras;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.agri_office_map);

		extras = getIntent().getExtras();
		if (extras != null) {
			latitude = extras.getFloat("LAT");
			longitude = extras.getFloat("LONG");
		}

		android.app.FragmentManager fragmentManager = getFragmentManager();
		MapFragment mapFragment = (MapFragment) fragmentManager
				.findFragmentById(R.id.agri_office_map);
		GoogleMap googleMap = mapFragment.getMap();
		Log.e("Map", latitude + "-" + longitude);
		LatLng sfLatLng = new LatLng(latitude, longitude);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(sfLatLng));
		googleMap.addMarker(new MarkerOptions()
				.position(sfLatLng)
				.title("Hospital")
				.snippet("IIT_Knockout")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	}

}
