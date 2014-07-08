package com.project.lcc.home;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.lcc.base.BaseActionBar;
import com.project.lcc.model.Model;
import com.project.lcc.utilities.Utilities;
import com.project.lcc.utilities.ViewHelper;

public class InstantServiceActivity extends BaseActionBar implements
		OnClickListener {

	private static final int IMAGE_CAPTURE_REQ_CODE = 0;
	private static final int AUDIO_CAPTURE_REQ_CODE = 1;
	private ImageView recordButton;
	private Button sendToServer;
	private ImageView buttonOpenCamery;
	private ProgressDialog progressDialog;
	private static final String URL = "http://appsomehow.com.wbm2.my-hosting-panel.com/api/uploaddata/upload";
	private Bitmap resultImage;
	private String audioBase64String;
	private String imageBase64String;
	private RequestQueue queue;
	private EditText etRichAge;

	public InstantServiceActivity() {
		super(R.id.menu_instant_services);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instant_service);

		initView();

		queue = Volley.newRequestQueue(this);

		recordButton.setOnClickListener(this);
		buttonOpenCamery.setOnClickListener(this);
		sendToServer.setOnClickListener(this);
		setUpBanglaText();
	}

	private void setUpBanglaText() {
		ViewHelper.setTextViewText(this, R.id.txt_capture_image,
				getString(R.string.capture_pic_camera));
		ViewHelper.setTextViewText(this, R.id.txt_record_audio,
				getString(R.string.record_audio));

		ViewHelper.setButtonText(this, R.id.btn_view_result,
				getString(R.string.send_to_server));
		etRichAge.setHint(Utilities.getBanlgaSupportText(this,
				getString(R.string.hint_rich_age)));
	}

	private void initView() {
		etRichAge = (EditText) findViewById(R.id.et_rice_age);
		buttonOpenCamery = (ImageView) findViewById(R.id.btn_open_camera);
		recordButton = (ImageView) findViewById(R.id.btn_open_audio_recorder);
		sendToServer = (Button) findViewById(R.id.btn_view_result);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_open_audio_recorder:
			startAudioRecordActivity();
			break;
		case R.id.btn_view_result:
			if (!Utilities.isNetworkAvailable(this)) {
				Utilities.showBanglaSupportedToast(this,
						getString(R.string.no_interne_connection));
				return;
			}

			if (imageBase64String != null && audioBase64String != null) {
				showMobileNumberDialogue();
			} else {
				Utilities.showBanglaSupportedToast(this,
						getString(R.string.no_audio_image));
				return;
			}
			break;
		case R.id.btn_open_camera:
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(intent, IMAGE_CAPTURE_REQ_CODE);
			break;
		}
	}

	private void startAudioRecordActivity() {
		Intent audioIntent = new Intent(InstantServiceActivity.this,
				AudioRecordActivity.class);
		startActivityForResult(audioIntent, AUDIO_CAPTURE_REQ_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IMAGE_CAPTURE_REQ_CODE && resultCode == RESULT_OK) {
			resultImage = (Bitmap) data.getExtras().get("data");
			if (resultImage != null) {
				imageBase64String = Utilities.imageToBase64String(resultImage);
			}
		} else if (requestCode == AUDIO_CAPTURE_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				audioBase64String = data.getStringExtra("AUDIO_STRING");
			}
		}
	}

	private void uploadFile(final String imageString, final String audioString,
			final String mobileNumber) {

		StringRequest request = new StringRequest(
				com.android.volley.Request.Method.POST, URL,
				new Response.Listener<String>() {

					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						progressDialog.dismiss();
						Utilities.showBanglaSupportedToast(
								InstantServiceActivity.this,
								getString(R.string.server_success));
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressDialog.dismiss();
						Utilities.showBanglaSupportedToast(
								InstantServiceActivity.this,
								getString(R.string.server_error));
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Model model = new Model();
				model.setPaddyTreeAge(etRichAge.getText().toString());
				model.setMobile(mobileNumber);
				model.setImage(imageString);
				model.setVoice(audioString);

				Map<String, String> params = new HashMap<String, String>();
				params.put("paddytreeage", model.getPaddyTreeAge());
				params.put("mobile", model.getMobile());
				params.put("image", model.getImage());
				params.put("voice", model.getVoice());
				return params;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(50000, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	public void showMobileNumberDialogue() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(Utilities.getBanlgaSupportText(this,
				getString(R.string.mobile_input_title)));
		alert.setMessage(Utilities.getBanlgaSupportText(this,
				getString(R.string.mobile_input_message)));
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		alert.setView(input);

		alert.setPositiveButton(Utilities.getBanlgaSupportText(
				InstantServiceActivity.this, getString(R.string.input_ok)),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						progressDialog = ProgressDialog.show(
								InstantServiceActivity.this,
								"",
								Utilities
										.getBanlgaSupportText(
												InstantServiceActivity.this,
												getString(R.string.uploading_to_server)),
								true);
						uploadFile(imageBase64String, audioBase64String, input
								.getText().toString());
					}
				});

		alert.setNegativeButton(Utilities.getBanlgaSupportText(
				InstantServiceActivity.this, getString(R.string.input_cancel)),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();

	}

}
