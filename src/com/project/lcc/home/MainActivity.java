package com.project.lcc.home;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.lcc.adapter.CropOptionAdapter;
import com.project.lcc.analysis.SimilarityFinder;
import com.project.lcc.base.BaseActionBar;
import com.project.lcc.model.CropOption;
import com.project.lcc.utilities.PalleteHelper;
import com.project.lcc.utilities.Utilities;
import com.project.lcc.utilities.ViewHelper;

public class MainActivity extends BaseActionBar {

	public MainActivity() {
		super(R.id.menu_lcc);
	}

	private int minimumDistance;
	private double minimumIndex;
	private Bitmap outputImage;
	private int counter = 1;
	private double countMinIndex;
	int sameIndex;

	private Uri mImageCaptureUri;
	private ImageView mImageView;
	private SimilarityFinder similarity;
	private ArrayList<Integer> distances;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	private Map<Integer, String> banglaCounters;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mImageView = (ImageView) findViewById(R.id.iv_photo);
		ImageView btnOpenCamera = (ImageView) findViewById(R.id.btn_open_camera);
		ImageView btnOpenGallery = (ImageView) findViewById(R.id.btn_open_gallery);
		Button btnViewResult = (Button) findViewById(R.id.btn_view_result);
		setUpCustomText();
		insertBanglaCounterValues();
		updateTitleCount(0);

		storeSavedDataForConfigurationChange(savedInstanceState);

		btnOpenCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openCamera();
			}

			private void openCamera() {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				mImageCaptureUri = Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "tmp_avatar_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
						mImageCaptureUri);
				try {
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		btnOpenGallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openGallery();
			}

			private void openGallery() {
				Intent intent = new Intent();

				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				startActivityForResult(
						Intent.createChooser(intent, "Complete action using"),
						PICK_FROM_FILE);
			}
		});

		btnViewResult.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (outputImage == null) {
					Utilities.showBanglaSupportedToast(
							MainActivity.this,
							getResources().getString(
									R.string.image_capture_info));
					return;
				}
				Intent intent = new Intent(MainActivity.this,
						ResultActivity.class);
				intent.putExtra("OUTPUT_IMAGE", outputImage);
				intent.putExtra("MIN_DISTANCE", minimumDistance);
				intent.putExtra("MIN_INDEX", countMinIndex);
				startActivity(intent);
			}
		});

	}

	@SuppressLint("UseSparseArrays")
	private void insertBanglaCounterValues() {
		banglaCounters = new HashMap<Integer, String>();
		String[] counters = getResources().getStringArray(R.array.counters);
		if (counters == null || counters.length == 0)
			return;

		for (String string : counters) {
			String[] splittedString = string.split(";");
			if (splittedString.length > 0) {
				int key = Integer.parseInt(splittedString[0]);
				String value = splittedString[1];
				banglaCounters.put(key, value);
			}
		}
	}

	private void setUpCustomText() {
		ViewHelper.setButtonText(this, R.id.btn_view_result,
				getString(R.string.view_lcc_result));
		ViewHelper.setTextViewText(this, new int[] { R.id.camera_pick_text,
				R.id.gallery_pick_text }, new String[] {
				getString(R.string.capture_pic_camera),
				getString(R.string.capture_pic_gallery) });
	}

	private void storeSavedDataForConfigurationChange(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			outputImage = (Bitmap) savedInstanceState
					.getParcelable("OUTPUT_IMAGE");
			mImageView.setImageBitmap(outputImage);
			minimumDistance = savedInstanceState.getInt("MIN_DISTANCE");
			countMinIndex = savedInstanceState.getDouble("COUNT_MIN_INDEX");
			minimumIndex = savedInstanceState.getDouble("MIN_INDEX");
			counter = savedInstanceState.getInt("COUNTER");
			int myCounter = counter;
			if (myCounter == 1) {
				updateTitleCount(0);
			} else if (myCounter > 1) {
				updateTitleCount(myCounter - 1);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelable("OUTPUT_IMAGE", outputImage);
		outState.putInt("MIN_DISTANCE", minimumDistance);
		outState.putDouble("COUNT_MIN_INDEX", countMinIndex);
		outState.putDouble("MIN_INDEX", minimumIndex);
		outState.putInt("COUNTER", counter);
	}

	protected void showToasMessage(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			doCrop();

			break;

		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();

			doCrop();

			break;

		case CROP_FROM_CAMERA:
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				outputImage = photo;

				try {
					if (distances == null) {
						distances = new ArrayList<Integer>();
					} else {
						distances.clear();
					}

					for (int i = 1; i < 7; i++) {
						similarity = new SimilarityFinder(outputImage,
								PalleteHelper.getBitmap(MainActivity.this, i));
						distances.add(similarity.calculateDistance());
					}
					minimumDistance = Collections.min(distances).intValue();
					minimumIndex += distances.indexOf(minimumDistance);
					countMinIndex = minimumIndex / counter;
					updateTitleCount(counter);
					counter++;
				} catch (IOException e) {
					e.printStackTrace();
				}

				mImageView.setImageBitmap(outputImage);
			}
			File f = new File(mImageCaptureUri.getPath());

			if (f.exists())
				f.delete();
			break;

		}
	}

	private void updateTitleCount(int counter) {
		if (counter > 10) {
			Utilities.showBanglaSupportedToast(this,
					getString(R.string.image_captured_above));
			return;
		}
		ViewHelper.setTextViewText(
				this,
				R.id.txt_image_count,
				getString(R.string.test_title_one) + " "
						+ banglaCounters.get(counter) + ""
						+ getString(R.string.test_title_two));
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 75);
			intent.putExtra("outputY", 75);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);
				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}
}