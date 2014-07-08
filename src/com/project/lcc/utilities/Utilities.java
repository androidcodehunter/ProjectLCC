package com.project.lcc.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;
import com.project.lcc.home.R;

public final class Utilities {

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectionManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectionManager.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static void showBanglaSupportedToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView toastText = (TextView) toastLayout.getChildAt(0);
		toastText.setTypeface(getCustomBanglaFont(context));
		toast.show();
	}

	public static SpannableString getBanlgaSupportText(Context context,
			String message) {
		return AndroidCustomFontSupport.getCorrectedBengaliFormat(message,
				getCustomBanglaFont(context), -1);
	}

	public static Typeface getCustomBanglaFont(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				context.getString(R.string.tpfc_solaimanlipi));
	}

	public static void getFirstTimeCheckPreference(Context context,
			boolean isTrue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isSignUp", isTrue);
		editor.commit();
	}

	public static boolean isFirstTime(Context context) {
		SharedPreferences preference = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preference.getBoolean("isSignUp", false);
	}
	
	public static String fileToBase64String(File file){
		byte[] bytes;
		String encodedString="";
		try {
			bytes = FileUtils.readFileToByteArray(file);
			encodedString = Base64.encodeToString(bytes, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedString;
	}
	
	public static String imageToBase64String(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return Base64.encodeToString(byteArray, Base64.NO_WRAP);
	}
	
}
