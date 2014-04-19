package com.cubic.sensingmodule;

import com.cubic.processingmodule.POI;
import com.cubic.processingmodule.Processor;
import com.cubic.sensingmodule.Orientation;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;

public class SensorModule {
	//instances of sensors
	SensorManager mSensorManager;
	Accelerometer mAccelerometer;
	AudioFeed mAudioFeed;
	LocationManager mLocationManager;
	Orientation mCompass;
	Position mPosition;
	
	Activity mActivity;
	Location mLocation; //store current location
	Processor mProcessor; //our processing module!
	
	public SensorModule(Activity mActivity) { //passes in a context
		
		this.mActivity = mActivity;
		mSensorManager = (SensorManager) mActivity.getSystemService(Context.SENSOR_SERVICE); 
		mAccelerometer = new Accelerometer(mSensorManager);
		mAudioFeed = new AudioFeed();
		mProcessor = new Processor(); //create a processing module so we can process the world
		mPosition = new Position(mLocationManager, mProcessor);
		mCompass = new Orientation(mSensorManager, mPosition);
	}

	//Accelerations
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
	
	//audio
	public void getAudioFeed()
	{
		mAudioFeed.getAudio(mActivity);
	}
	
	//photo
	public void takePicture()
	{
		PhotoActivity photo = new PhotoActivity();
		if(mActivity != null)
			photo.takePhoto(mActivity);
	}
	
	//video
	public void recordVideo()
	{
		PhotoActivity video = new PhotoActivity();
		if(mActivity != null)
			video.recordVideo(mActivity);
	}
	
	
	//Compass Heading
	public String getHeading(){
		return Float.toString(mCompass.getHeading());
	}
	
	//Lat and Long
	public double getLatitude(){
		return mPosition.getLatitude();
	}
	
	public double getLongitude(){
		return mPosition.getLongitude();
	}
	
	//add POIs to our sensing module
	public void addPOI(POI poi)
	{
		mProcessor.addPOI(poi);
	}
	
	public void addPOI(POI[] poi)
	{
		mProcessor.addPOIs(poi);
	}
}
