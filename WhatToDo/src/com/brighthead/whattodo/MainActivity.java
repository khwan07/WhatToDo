package com.brighthead.whattodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.brighthead.whattodo.service.WhatToDoService;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button mStartBtn = null;
	private Button mStopBtn = null;

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

		if (mStartBtn == null) {
			mStartBtn = (Button) findViewById(R.id.start_btn);
		}

		if (mStopBtn == null) {
			mStopBtn = (Button) findViewById(R.id.stop_btn);
		}

		mStartBtn.setOnClickListener(this);
		mStopBtn.setOnClickListener(this);

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mStartBtn != null) {
			mStartBtn.setOnClickListener(null);
			mStartBtn = null;
		}

		if (mStopBtn != null) {
			mStopBtn.setOnClickListener(null);
			mStopBtn = null;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mStartBtn.getId()) {
			startService(new Intent(this, WhatToDoService.class));
		} else if (v.getId() == mStopBtn.getId()) {
			stopService(new Intent(this, WhatToDoService.class));
		}
	}

}
