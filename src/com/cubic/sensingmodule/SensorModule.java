package com.cubic.sensingmodule;

import com.cubic.processingmodule.Processor;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;

public class SensorModule {

	SensorManager mSensorManager;
	Accelerometer mAccelerometer;
	AudioFeed mAudioFeed;
	Activity mActivity;
	Location mLocation; //store current location
	Processor mProcessor; //our processing module!
	
	
	public SensorModule(Activity mActivity) { //passes in a context
		
		this.mActivity = mActivity;
		mSensorManager = (SensorManager) mActivity.getSystemService(Context.SENSOR_SERVICE); 
		mAccelerometer = new Accelerometer(mSensorManager);
		mAudioFeed = new AudioFeed();
		mProcessor = new Processor(); //create a processing module so we can process the world
	}

	//add 1 POI
	public void addPOI(Location POI)
	{
		mProcessor.addPOI(POI);
	}
	
	//add array of POIs
	public void addPOIs(Location[] POIs)
	{
		mProcessor.addPOIs(POIs);
	}	
	
	//delete a POI - used if the location should no longer be available
	public void delPOI(Location POI)
	{
		mProcessor.delPOI(POI);
	}
	public String getAccelerationX()
	{
		return Float.toString(mAccelerometer.getAccelerationX());
	}
	
	public String getAccelerationY()
	{
		return Float.toString(mAccelerometer.getAccelerationY());
	}
	
	public String getAccelerationZ()
	{
		return Float.toString(mAccelerometer.getAccelerationZ());
	}
	
	public void getAudioFeed()
	{
		mAudioFeed.getAudio(mActivity);
	}
	
	public void takePicture()
	{
		PhotoActivity photo = new PhotoActivity();
		if(mActivity != null)
			photo.takePhoto(mActivity);
	}

	public void recordVideo()
	{
		PhotoActivity video = new PhotoActivity();
		if(mActivity != null)
			video.recordVideo(mActivity);
	}
}
