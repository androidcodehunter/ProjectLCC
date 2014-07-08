package com.project.lcc.utilities;

import com.project.lcc.home.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class PalleteHelper {

	private static PalleteHelper instance = new PalleteHelper();

	private PalleteHelper() {

	}

	public static PalleteHelper getUniqueInstance() {
		return instance;
	}

	public static Bitmap getBitmap(Context context, int palleteNumber) {

		Drawable drawable = null;

		if (palleteNumber == 1) {
			drawable = context.getResources()
					.getDrawable(R.drawable.pallet_one);
		} else if (palleteNumber == 2) {
			drawable = context.getResources().getDrawable(
					R.drawable.pallete_two);
		} else if (palleteNumber == 3) {
			drawable = context.getResources().getDrawable(
					R.drawable.pallete_three);
		} else if (palleteNumber == 4) {
			drawable = context.getResources().getDrawable(
					R.drawable.pallete_four);
		} else if (palleteNumber == 5) {
			drawable = context.getResources().getDrawable(
					R.drawable.pallete_five);
		} else if (palleteNumber == 6) {
			drawable = context.getResources().getDrawable(
					R.drawable.pallete_six);
		}

		Bitmap inputPallete = ((BitmapDrawable) drawable).getBitmap();

		return inputPallete;
	}
}
