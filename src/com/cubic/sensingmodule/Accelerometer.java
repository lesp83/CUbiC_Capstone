package com.cubic.sensingmodule;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

public class Accelerometer implements SensorEventListener {

	private Sensor mAccMeter = null;
	private float[] accValues;
	TextView mTestView;
	
	
	public Accelerometer(SensorManager mSM) { //turns on Accelerometer
		
		mAccMeter = mSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSM.registerListener(this,mAccMeter, SensorManager.SENSOR_DELAY_NORMAL);	
		
	}
	
	public void turnOn()
	{
		//code for turning on Sensor;
	}

	public float getAccelerationX() //returns X acceleration
	{
		return accValues[0];
	}
	
	public float getAccelerationY() //return Y acceleration
	{
		return accValues[1];
	}
	
	public float getAccelerationZ() //return Z acceleration
	{
		return accValues[2];
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// needs to expand
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
	accValues= event.values;
	}

	
	public void turnOff()
	{
			//needs to be be done
	}
}
