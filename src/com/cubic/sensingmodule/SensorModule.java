package com.cubic.sensingmodule;

import com.cubic.sensingmodule.Orientation;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;

public class SensorModule {
	//instances of sensors
	private SensorManager mSensorManager;
	private Accelerometer mAccelerometer;
	private AudioFeed mAudioFeed;
	private LocationManager mLocationManager;
	private Orientation mCompass;
	private Position mPosition;
	private PhotoActivity mPhotoActivity;
	private Activity mActivity;
	
	public SensorModule(Activity mActivity) { //passes in a context
		this.mActivity = mActivity;
		mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
		mSensorManager = (SensorManager) mActivity.getSystemService(Context.SENSOR_SERVICE); 
		mAccelerometer = new Accelerometer(mSensorManager);
		mAudioFeed = new AudioFeed();
		mPosition = new Position(mLocationManager);
		mCompass = new Orientation(mSensorManager, mPosition);
		mPhotoActivity = new PhotoActivity();
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
		if(mActivity != null)
			mPhotoActivity.takePhoto(mActivity);
	}
	
	//video
	public void recordVideo()
	{
		if(mActivity != null)
			mPhotoActivity.recordVideo(mActivity);
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
	
	public Location getLocation()
	{
		return mPosition.getLocation();
	}
}
