package com.brighthead.whattodo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;

public class WhatToDoService extends Service implements OnTouchListener, OnClickListener{

    private View rootView;
    private Button mBtn;
    
    private boolean isTouchedDown;
    private boolean isBtnMoved;
    
    private int STARTX;
    private int STARTY;
    private int VIEWX;
    private int VIEWY;
    
    WindowManager.LayoutParams mParams = null;
    WindowManager mWindowManager = null;
    
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = li.inflate(R.layout.service_main, null);
        
        
        mParams = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,   //��긽 理��곸쐞���덇쾶
                //WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,       //�곗튂 �몄떇
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT); 
        
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE); //�덈룄 留ㅻ땲��
        mWindowManager.addView(rootView, mParams);  //理쒖긽���덈룄�곗뿉 酉��ｊ린. permission�꾩슂.
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mBtn != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mBtn);
            mBtn.setOnTouchListener(null);
            mBtn.setOnClickListener(null);
            mBtn = null;
        }
        if (rootView != null) {
            rootView = null;
        }
        if (mWindowManager != null) {
        	mWindowManager = null;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("hwankim", "ontouch");
        int id = v.getId();
        int action = event.getAction();
        if (id == mBtn.getId()) {
        	if (action == MotionEvent.ACTION_DOWN) {
        		isTouchedDown = true;
        		STARTX = (int) event.getRawX();
        		STARTY = (int) event.getRawY();
        		VIEWX = mParams.x;
        		VIEWY = mParams.y;
        	}
        	if (isTouchedDown && action == MotionEvent.ACTION_MOVE) {
        		int rawx = (int) event.getRawX();
        		int rawy = (int) event.getRawY();
        		moveBtn(rawx - STARTX, rawy - STARTY);
        	}
        }
        if (action == MotionEvent.ACTION_UP) {
        	isTouchedDown = false;
        }
        return false;
    }
	
	
	private void moveBtn(int movex, int movey) {
		Log.d("hwankim", "moveBtn movex " + movex + " movey " + movey);
		mParams.x = VIEWX + movex;
		mParams.y = VIEWY + movey;
		
		mWindowManager.updateViewLayout(rootView, mParams);
		isBtnMoved = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

