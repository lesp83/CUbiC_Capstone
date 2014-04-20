package com.cubic.testing;

import java.util.List;

import com.cubic.sensingmodule.SensorModule;

import android.location.Location;
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

		 handler.postDelayed(runnable, 1000);	
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() { 
			txtvHeading.setText("Heading: " + mModule.getHeading());
			
			txtLat.setText("Lat: " + mModule.getLatitude());
			txtLong.setText("Long: " + mModule.getLongitude());	
			
			 
			xAView.setText("X Acceleration: " + mModule.getAccelerationX());
			yAView.setText("Y Acceleration: " + mModule.getAccelerationY());
			zAView.setText("Z Acceleration: " + mModule.getAccelerationZ());
		    handler.postDelayed(this, 1000);
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
