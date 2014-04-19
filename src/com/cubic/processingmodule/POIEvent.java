package com.cubic.processingmodule;

import android.location.Location;

public class POIEvent {
	String mVideo; //video to be played
	String mAudio; //audio to be played
	String mText; //display text
	POI[] mNextLocations; //New Locations unlocked when this POI is discovered
	POI[] mRetireLocations; //Locations that are no longer available when this POI is found
}
