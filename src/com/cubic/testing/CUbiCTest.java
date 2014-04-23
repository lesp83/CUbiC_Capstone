package com.cubic.testing;

import java.util.Timer;
import java.util.TimerTask;

import com.cubic.sensingmodule.SensorModule;
import com.cubic.testing.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class CUbiCTest extends Activity {

	
	private Handler handler = new Handler();
	
	private TextView xAView;
	private TextView yAView;
	private TextView zAView;
	
	//GPS and Compass 
	private TextView txtvHeading;
	private TextView txtLat; 
	private TextView txtLong;
	
	private SensorModule mModule;

    MediaPlayer mp = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 mModule = new SensorModule(this);
		
		 xAView = (TextView) findViewById(R.id.xAcceleration);
		 yAView = (TextView) findViewById(R.id.yAcceleration);
		 zAView = (TextView) findViewById(R.id.zAcceleration);
		 
        txtvHeading = (TextView) findViewById(R.id.Direction);
        txtLat = (TextView) findViewById(R.id.latitude);
        txtLong = (TextView) findViewById(R.id.longitude);

        mp = MediaPlayer.create(CUbiCTest.this,R.raw.whish);
        
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask()
        {
        	public void run() {
        		timerMethod();
        	}
        }, 0, 1000);
        
        
	}

	private void timerMethod()
	{
		this.runOnUiThread(runnable);
	}
	
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			txtvHeading.setText("Heading: " + mModule.getHeading());
			float mV = Float.valueOf(mModule.getHeading());
			if(mV > 0 && mV < 10 || mV < 360 && mV > 350)
				mp.start();
			try{
			txtLat.setText("Lat: " + mModule.getLatitude());
			txtLong.setText("Long: " + mModule.getLongitude());			   
			}
			catch(Exception e)
			{
			}
			xAView.setText("X Acceleration: " + mModule.getAccelerationX());
			yAView.setText("Y Acceleration: " + mModule.getAccelerationY());
			zAView.setText("Z Acceleration: " + mModule.getAccelerationZ());
		}
	};
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.cubi_ctest, menu);
		return true;
	}
	  
	public void getAudioFeed(View view){
		mModule.getAudioFeed();
	}
	
	public void takePhoto(View view)
	{
		mModule.takePicture();
	}
		  
	public void recordVideo(View view)
	{
		mModule.recordVideo();
	}
			
	@Override
	protected void onResume() {
		super.onResume();
	}  
			  
	@Override
	protected void onPause() {
		super.onPause();
	}
			
	@Override
	public void onDestroy() {
		super.onDestroy();
	}	  
}
