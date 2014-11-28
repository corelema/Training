package com.cocorporation.minesweeper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class BouncingBallActivity extends Activity {
	
	private static final String TAG = "BouncingBallActivity";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  BouncingBallView bouncingBallView = new BouncingBallView(this);
	      bouncingBallView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i(TAG, "Event captured");
				
				final int action1 = event.getActionMasked();
				Log.i(TAG, "Action1 = " + action1);
				
				BouncingBallView vCasted = (BouncingBallView) v;
				
				switch (action1){
				case MotionEvent.ACTION_DOWN:
					Log.i(TAG, "ACTION_DOWN");
					vCasted.updateFromTouchFirstFingerDown(event.getAxisValue(MotionEvent.AXIS_X), event.getAxisValue(MotionEvent.AXIS_Y));
					break;
					
				case MotionEvent.ACTION_POINTER_DOWN:
					Log.i(TAG, "ACTION_POINTER_DOWN");
					vCasted.updateFromTouchSecondFingerDown(event.getX(event.getPointerId(event.getActionIndex())), event.getY(event.getPointerId(event.getActionIndex())));
					break;
					
				case MotionEvent.ACTION_MOVE:
					int pointerIndex;
					int pointerId;
					int pointerCount = event.getPointerCount();
			        for(int i = 0; i < pointerCount; ++i)
			        {
			            pointerIndex = i;
			            pointerId = event.getPointerId(pointerIndex);
			            if(pointerId == 0)
			            {
			            	vCasted.updateFromTouchFirstFingerMove(event.getX(pointerIndex), event.getY(pointerIndex));
			            }
			            if(pointerId == 1)
			            {
			            	vCasted.updateFromTouchSecondFingerMove(event.getX(pointerIndex), event.getY(pointerIndex));
			            }
			        }

					break;
					
				case MotionEvent.ACTION_UP:
					Log.i(TAG, "ACTION_UP");
					vCasted.updateFromTouchFirstFingerUp(event.getAxisValue(MotionEvent.AXIS_X), event.getAxisValue(MotionEvent.AXIS_Y));
					break;
					
				case MotionEvent.ACTION_POINTER_UP:
					Log.i(TAG, "ACTION_POINTER_UP");
					vCasted.updateFromTouchSecondFingerUp(event.getX(event.getPointerId(event.getActionIndex())), event.getY(event.getPointerId(event.getActionIndex())));
					break;
				}
				
				return true;
			}
		});
	      setContentView(bouncingBallView);
	      bouncingBallView.setBackgroundColor(Color.BLACK);
	      
	      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
}
