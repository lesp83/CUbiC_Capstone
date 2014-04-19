package com.cubic.processingmodule;

import java.util.Vector;

import com.cubic.sensingmodule.SensorModule;

import android.app.Activity;

public class Processor {
	private Vector<POI> mTooFarPOI = new Vector<POI>(100); //points that are not visible
	private Vector<POI> mRetiredPOI = new Vector<POI>(100); //points that have been deleted
	private Vector<POI> mClosePOI = new Vector<POI>(100); //points that are "close"
	private Vector<POI> mMediumPOI = new Vector<POI>(100); //points that are "medium"
	private Vector<POI> mFarPOI = new Vector<POI>(100); //points that are "far"
	
	SensorModule mSensor;
	
	//thresholds in meters
	int mCloseThreshold; //for the largest icon 
	int mMediumThreshold; //for the mid-sized icon
	int mFarThreshold; //for the smallest icon
	
	public Processor(Activity activity) { 
		//set defaults
		mCloseThreshold = 30;
		mMediumThreshold = 60;
		mFarThreshold = 100;
		mSensor = new SensorModule(activity);
	}
	
	//could be used to view history
	public Vector<POI> getRetired()
	{
		return mRetiredPOI;
	}
	
	public Vector<POI> getTooFar()
	{
		return mTooFarPOI;
	}
	
	public Vector<POI> getFar()
	{
		return mFarPOI;
	}
	
	public Vector<POI> getMedium()
	{
		return mMediumPOI;
	}
	
	public Vector<POI> getClose()
	{
		return mClosePOI;
	}
	
	//add 1 POI
	public void addPOI(POI poi)
	{
		//set a default discovery threshold
		if(poi.mDiscoveryThreshold == 0)
			poi.mDiscoveryThreshold = 5;
		
		mTooFarPOI.add(poi);
	}
	
	//add array of POIs
	public void addPOIs(POI[] POIs)
	{
		for(int i = 0; i < POIs.length; i++)
		{
			//set a default discovery threshold
			if(POIs[i].mDiscoveryThreshold == 0)
				POIs[i].mDiscoveryThreshold = 5;			
			
			mTooFarPOI.add(POIs[i]);
		}	
	}	
	
	//delete a POI - used if the location should no longer be available
	//this should only need to be used locally since all dependancies
	//are given beforehand
	private void delPOI(POI poi)
	{
		int index = 0;
		boolean found = false;
		
		for (POI thisPOI : mTooFarPOI)
		{
		    if(thisPOI.mLocation.getLatitude() == poi.mLocation.getLatitude() &&
		       thisPOI.mLocation.getLongitude() == poi.mLocation.getLongitude())
		    {
		    	mTooFarPOI.removeElementAt(index);
		    	found = true;
		    }
		    index++;
		}
		
		index = 0;
		
		//nested these for loops to reduce the amount of times we check the found variable
		if(!found)
		{
			for (POI thisPOI : mFarPOI)
			{
			    if(thisPOI.mLocation.getLatitude() == poi.mLocation.getLatitude() &&
			       thisPOI.mLocation.getLongitude() == poi.mLocation.getLongitude())
			    {
			    	mFarPOI.removeElementAt(index);
			    	found = true;
			    }
			    index++;
			}		
		
			index = 0;
			if(!found)
			{
				for (POI thisPOI : mMediumPOI)
				{
				    if(thisPOI.mLocation.getLatitude() == poi.mLocation.getLatitude() &&
				       thisPOI.mLocation.getLongitude() == poi.mLocation.getLongitude())
				    {
				    	mMediumPOI.removeElementAt(index);
				    	found = true;
				    }
				    index++;
				}	
			
				index = 0;
				if(!found)
					for (POI thisPOI : mClosePOI)
					{
					    if(thisPOI.mLocation.getLatitude() == poi.mLocation.getLatitude() &&
					       thisPOI.mLocation.getLongitude() == poi.mLocation.getLongitude())
					    {
					    	mClosePOI.removeElementAt(index);
					    }
					    index++;
					}
			}
		}
	}	
	
	//This function will be called when the GPS coords change
	//It will simply loop through the given POIs
	//and place each POI in it's rightful place
	//returns true if there is a change and false if not
	//NOTE:  not sure how accurate "distanceTo" is --> need to test
	public Vector<POI> checkPOIs()
	{
		boolean change = false;
		Vector<POI> discovered = new Vector<POI>(10);
		
		//loop through the tooFar vector and move POIs accordingly
		for (POI thisPOI : mTooFarPOI)
		{
			float distanceFromPOI = mSensor.getLocation().distanceTo(thisPOI.mLocation);
			
			if(distanceFromPOI <= thisPOI.mDiscoveryThreshold)
			{
				firePOIEvent(thisPOI.mEvent);
				discovered.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI <= mCloseThreshold)
			{
				delPOI(thisPOI);
				mClosePOI.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI <= mMediumThreshold)
			{
				delPOI(thisPOI);
				mMediumPOI.add(thisPOI);
				change = true;
			}	
			else if(distanceFromPOI <= mFarThreshold)
			{
				delPOI(thisPOI);
				mFarPOI.add(thisPOI);
				change = true;
			}
		}
		
		//loop through the Far vector and move POIs accordingly
		for (POI thisPOI : mFarPOI)
		{
			float distanceFromPOI = mSensor.getLocation().distanceTo(thisPOI.mLocation);
			
			if(distanceFromPOI <= thisPOI.mDiscoveryThreshold)
			{
				firePOIEvent(thisPOI.mEvent);
				discovered.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI <= mCloseThreshold)
			{
				delPOI(thisPOI);
				mClosePOI.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI <= mMediumThreshold)
			{
				delPOI(thisPOI);
				mMediumPOI.add(thisPOI);
				change = true;
			}	
			else if(distanceFromPOI > mFarThreshold)
			{
				delPOI(thisPOI);
				mTooFarPOI.add(thisPOI);
				change = true;
			}
		}	
		
		//loop through the Medium vector and move POIs accordingly
		for (POI thisPOI : mMediumPOI)
		{
			float distanceFromPOI = mSensor.getLocation().distanceTo(thisPOI.mLocation);
			
			if(distanceFromPOI <= thisPOI.mDiscoveryThreshold)
			{
				firePOIEvent(thisPOI.mEvent);
				discovered.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI <= mCloseThreshold)
			{
				delPOI(thisPOI);
				mClosePOI.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI > mMediumThreshold &&
					distanceFromPOI <= mFarThreshold)
			{
				delPOI(thisPOI);
				mFarPOI.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI > mFarThreshold)
			{
				delPOI(thisPOI);
				mTooFarPOI.add(thisPOI);
				change = true;
			}
		}
		
		//loop through the Close vector and move POIs accordingly
		for (POI thisPOI : mClosePOI)
		{
			float distanceFromPOI = mSensor.getLocation().distanceTo(thisPOI.mLocation);
			
			if(distanceFromPOI <= thisPOI.mDiscoveryThreshold)
			{
				firePOIEvent(thisPOI.mEvent);
				discovered.add(thisPOI);
				change = true;
			}
			else if(distanceFromPOI > mCloseThreshold)
			{
				if(distanceFromPOI <= mMediumThreshold)
				{
					delPOI(thisPOI);
					mMediumPOI.add(thisPOI);
					change = true;
				}
				else if(distanceFromPOI <= mFarThreshold)
				{
					delPOI(thisPOI);
					mFarPOI.add(thisPOI);
					change = true;
				}
			}	
			else if(distanceFromPOI > mFarThreshold)
			{
				delPOI(thisPOI);
				mTooFarPOI.add(thisPOI);
				change = true;
			}
		}		
		
		if(change)
		{
			//possibly wake up the presentation module somehow
		}
		
		return discovered;
	}
	
	private void firePOIEvent(POIEvent event)
	{
		//add dependencies
		for (POI thisPOI : event.mNextLocations)
		{
			mTooFarPOI.add(thisPOI);
		}
		
		//delete desired POIs and add them to retired vector
		for (POI thisPOI : event.mRetireLocations)
		{
			mRetiredPOI.add(thisPOI);
			delPOI(thisPOI);
		}
		
		//trigger event
		//wake up presentation module
	}
}
