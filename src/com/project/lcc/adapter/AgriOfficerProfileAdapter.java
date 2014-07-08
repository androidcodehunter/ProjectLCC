package com.project.lcc.adapter;

import java.util.List;

import com.project.lcc.home.R;
import com.project.lcc.model.AgriOfficerProfile;
import com.project.lcc.utilities.Utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AgriOfficerProfileAdapter extends ArrayAdapter<AgriOfficerProfile> {
	Context context;
	AgriOfficerProfile rowItem;

	public AgriOfficerProfileAdapter(Context context, int resource,
			List<AgriOfficerProfile> items) {
		super(context, resource, items);
		this.context = context;
	}

	private class ViewHolder {
		TextView tvDoctoTitle;
		TextView tvDoctorProfile;
		TextView tvDoctorMobile;
		ImageButton btnShowCall;
		TextView tvDoctorHospital;
	}

	@Override
	public int getViewTypeCount() {

		if (getCount() != 0)
			return getCount();

		return 1;
	}

	@Override
	public int getItemViewType(int position) {

		return position;
	}

	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.officer_profile_info, null);
			holder = new ViewHolder();

			holder.tvDoctoTitle = (TextView) convertView
					.findViewById(R.id.tv_officer_profile_title);
			holder.tvDoctorProfile = (TextView) convertView
					.findViewById(R.id.tv_doctor_profile);
			holder.tvDoctorMobile = (TextView) convertView
					.findViewById(R.id.tv_agriofficer_profile_mobile);
			holder.tvDoctorHospital = (TextView) convertView
					.findViewById(R.id.tv_officer_agrioffice);
			holder.btnShowCall = (ImageButton) convertView
					.findViewById(R.id.ib_agriofficer_profile_call);
			convertView.setTag(holder);

		} else
			holder = (ViewHolder) convertView.getTag();

		holder.tvDoctoTitle.setText(Utilities.getBanlgaSupportText(context,
				rowItem.getTitle()));
		holder.tvDoctorProfile.setText(Utilities.getBanlgaSupportText(context,
				rowItem.getProfile()));
		holder.tvDoctorHospital.setText(Utilities.getBanlgaSupportText(context,
				rowItem.getAgriOffice()));
		holder.tvDoctorMobile.setText("Mobile:"
				+ Utilities.getBanlgaSupportText(context, rowItem.getMobile()));

		holder.btnShowCall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + rowItem.getMobile()));
				context.startActivity(intent);
			}
		});

		return convertView;
	}

}
