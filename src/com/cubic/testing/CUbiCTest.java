package com.cubic.testing;

import com.cubic.sensingmodule.SensorModule;
import com.cubic.testing.*;

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
	private SensorModule mModule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 mModule = new SensorModule(this);
		
		 xAView = (TextView) findViewById(R.id.xAcceleration);
		 yAView = (TextView) findViewById(R.id.yAcceleration);
		 zAView = (TextView) findViewById(R.id.zAcceleration);

		 handler.postDelayed(runnable, 1000);	
	}

	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() { 
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
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	
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
}
