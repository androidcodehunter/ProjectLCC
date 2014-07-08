package com.project.lcc.home;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.project.lcc.utilities.Utilities;

public class AudioRecordActivity extends Activity implements OnClickListener {
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;
	private MediaRecorder mRecorder = null;
	boolean mStartRecording = true;
	boolean mStartPlaying = true;
	private MediaPlayer mPlayer = null;
	private Chronometer chronometer;
	private ImageView btnPlayAudio;

	public AudioRecordActivity() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/audiorecordtest.mp4";
	}

	public static String getmFileName() {
		return mFileName;
	}

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	@SuppressLint("InlinedApi")
	private void startRecording() {
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setOutputFile(mFileName);
		// mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private void stopRecording() {
		chronometer.stop();
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.audio_record);

		chronometer = (Chronometer) findViewById(R.id.chronometer);
		ImageView btnRecordAudio = (ImageView) findViewById(R.id.record_audio);
		btnPlayAudio = (ImageView) findViewById(R.id.play_audio);
		ImageView btnDoneAudio = (ImageView) findViewById(R.id.done_audio);

		btnRecordAudio.setImageResource(R.drawable.start);
		btnPlayAudio.setImageResource(R.drawable.play);
		btnPlayAudio.setEnabled(false);

		btnRecordAudio.setOnClickListener(this);
		btnPlayAudio.setOnClickListener(this);
		btnDoneAudio.setOnClickListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.record_audio:
			onRecord(mStartRecording);
			if (mStartRecording) {
				((ImageView) v).setImageResource(R.drawable.stop);
				btnPlayAudio.setEnabled(false);
			} else {
				((ImageView) v).setImageResource(R.drawable.start);
				btnPlayAudio.setEnabled(true);
			}
			mStartRecording = !mStartRecording;

			break;
		case R.id.play_audio:
			onPlay(mStartPlaying);
			if (mStartPlaying) {
				((ImageView) v).setImageResource(R.drawable.stop);
			} else {
				((ImageView) v).setImageResource(R.drawable.play);
			}
			mStartPlaying = !mStartPlaying;
			break;
		case R.id.done_audio:
			if (mStartPlaying && mStartRecording) {
				File audioFile = new File(mFileName);
				if (isAudioFileEmpty(audioFile)) {
					Utilities.showBanglaSupportedToast(this,
							getString(R.string.no_audio_recorded));
					return;
				}

				Intent returnIntent = new Intent();
				returnIntent.putExtra("AUDIO_STRING",
						Utilities.fileToBase64String(audioFile));
				setResult(RESULT_OK, returnIntent);
				if (audioFile != null && audioFile.exists()) {
					audioFile.delete();
				}
				finish();
			} else {
				Utilities.showBanglaSupportedToast(this,
						getString(R.string.record_continuing));
			}
			break;
		}
	}

	private boolean isAudioFileEmpty(File audioFile) {
		if (audioFile.length() == 0) {
			return true;
		}
		return false;
	}
}