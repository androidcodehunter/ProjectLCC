package com.project.lcc.utilities;

import android.app.Activity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ViewHelper {

	public static void setTextViewText(Activity activity, int id, String text) {
		((TextView) activity.findViewById(id)).setText(Utilities
				.getBanlgaSupportText(activity, text));
	}	
	
	
	public static void setTextViewText(Activity activity, int[] ids,
			String[] texts) {
		for (int i = 0; i < texts.length; i++) {
			((TextView) activity.findViewById(ids[i])).setText(Utilities
					.getBanlgaSupportText(activity, texts[i]));
		}
	}

	public static void setButtonText(Activity activity, int id, String text) {
		((Button) activity.findViewById(id)).setText(Utilities
				.getBanlgaSupportText(activity, text));
	}

	public static void setButtonText(Activity activity, int[] ids,
			String[] texts) {
		for (int i = 0; i < texts.length; i++) {
			((Button) activity.findViewById(ids[i])).setText(Utilities
					.getBanlgaSupportText(activity, texts[i]));
		}

	}

	public static void setRadioButtonText(Activity activity, int id, String text) {
		((RadioButton) activity.findViewById(id)).setText(Utilities
				.getBanlgaSupportText(activity, text));
	}
	
	

}
