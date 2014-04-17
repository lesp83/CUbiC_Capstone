package com.cubic.processingmodule;

import android.location.Location;

public class Processor {
	POI[] mPOIs; //store all POIs 
	int locationCount; //count of POIs
	int metersThreshold;
	
	public Processor() { 
		locationCount = 0;	
		mPOIs = new POI[100]; //allow 100 POIs for now
		metersThreshold = 10; //default to 10 meters
	}	
	
	//add 1 POI
	public void addPOI(Location POI)
	{
		if(locationCount<100)
			mPOIs[locationCount].mLocation = POI;
	}
	
	//add array of POIs
	public void addPOIs(Location[] POIs)
	{
		for(int i = 0; i < POIs.length; i++)
		{
			if(locationCount<100)
			{
				mPOIs[locationCount].mLocation = POIs[i];
				locationCount++;
			}
			else
			{
				break; //no sense in looping more
			}
		}	
	}	
	
	//delete a POI - used if the location should no longer be available
	public void delPOI(Location POI)
	{
		for(int i = 0; i < mPOIs.length; i++)
		{
			if(mPOIs[i].mLocation == POI)
			{
				//move last element into the new empty spot
				//since order doesn't matter =)
				mPOIs[i] = mPOIs[locationCount];
				locationCount--;
			}
		}
	}	
	
	//This function will be called when the GPS coords change
	//It will simply loop through the given POIs
	//and return an array of POIs that are within the threshold
	public Location[] checkPOIs(Location myLocation)
	{
		Location[] closeLocations = new Location[100];
		int closeCount = 0;
		int index = 0;
		while(mPOIs[index] != null && index < 100)
		{
			//not sure how accurate "distanceTo" is
			if(myLocation.distanceTo(mPOIs[index].mLocation) < metersThreshold)
			{
				closeLocations[closeCount] = mPOIs[index].mLocation;
				closeCount++;
			}
			index++;
		}
		
		return closeLocations;
	}
}
