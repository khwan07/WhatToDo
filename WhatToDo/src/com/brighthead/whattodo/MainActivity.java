package com.brighthead.whattodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brighthead.whattodo.preferences.SharedPreference;
import com.brighthead.whattodo.service.WhatToDoService;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button mStartBtn = null;
	private Button mStopBtn = null;
	private Button mConfirmBtn = null;
	private EditText mWorkEdit = null;
	private TextView mWorkText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		initializeLayout();
		initializeListener();

	}

	private void initializeLayout() {
		if (mStartBtn == null) {
			mStartBtn = (Button) findViewById(R.id.start_btn);
		}

		if (mStopBtn == null) {
			mStopBtn = (Button) findViewById(R.id.stop_btn);
		}

		if (mConfirmBtn == null) {
			mConfirmBtn = (Button) findViewById(R.id.confirm_btn);
		}

		if (mWorkEdit == null) {
			mWorkEdit = (EditText) findViewById(R.id.work_edit);
		}

		if (mWorkText == null) {
			mWorkText = (TextView) findViewById(R.id.main_text);
			String main = SharedPreference.getSharedPreference(getApplicationContext(),
					getString(R.string.pref_key_work));
			if (main != null) {
				mWorkText.setText(main);
			}
		}
	}

	private void initializeListener() {
		if (mStartBtn != null) {
			mStartBtn.setOnClickListener(this);
		}
		if (mStopBtn != null) {
			mStopBtn.setOnClickListener(this);
		}
		if (mConfirmBtn != null) {
			mConfirmBtn.setOnClickListener(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseLayout();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mStartBtn.getId()) {
			startService(new Intent(this, WhatToDoService.class));
		} else if (v.getId() == mStopBtn.getId()) {
			stopService(new Intent(this, WhatToDoService.class));
		} else if (v.getId() == mConfirmBtn.getId()) {
			String s = mWorkEdit.getText().toString();
			if (s != null) {
				updatePreference(s);
			}
		}
	}

	private void updatePreference(String s) {
		SharedPreference.putSharedPreference(getApplicationContext(),
				getString(R.string.pref_key_work), s);
	}

	private void releaseLayout() {
		if (mStartBtn != null) {
			mStartBtn.setOnClickListener(null);
			mStartBtn = null;
		}

		if (mStopBtn != null) {
			mStopBtn.setOnClickListener(null);
			mStopBtn = null;
		}

		if (mConfirmBtn != null) {
			mConfirmBtn.setOnClickListener(null);
			mConfirmBtn = null;
		}

		if (mWorkEdit != null) {
			mWorkEdit = null;
		}

		if (mWorkText != null) {
			mWorkText = null;
		}
	}

}
