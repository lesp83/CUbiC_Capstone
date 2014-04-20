package com.cubic.sensingmodule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cubic.processingmodule.Processor;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.Criteria;
import android.os.Bundle;
import android.hardware.GeomagneticField;

public class Position implements LocationListener{
	private LocationManager locationmanager;
	private List<String> providers;
	private Location location;
	private GeomagneticField geoMagneticField;
	private Processor mProcessor;

	public Position(LocationManager locManager, Processor processor){
		locationmanager = locManager;
		mProcessor = processor;
		Location lastlocation = locationmanager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		if (lastlocation != null) {
            long locationAge = lastlocation.getTime() - System.currentTimeMillis();
            if (locationAge < TimeUnit.MINUTES.toMillis(30)) {
                location = lastlocation;
                updateGeomagneticField();
            }
        }
		
		Criteria criteria = new Criteria();
		providers = locationmanager.getProviders(criteria, true);
		for (String provider : providers) {
			locationmanager.requestLocationUpdates(provider, 1000, .1f, this);
		}
	}

	public Location getLocation(){
		return location;
	}

	@Override
	public void onLocationChanged(Location newLocation){
		//only call the processing module if the distance traveled is 5 meters
		if(location.distanceTo(newLocation) >= 5)
		{
			location = newLocation;
			mProcessor.checkPOIs(location);
		}
		updateGeomagneticField();
	}

	public double getLatitude(){
		try{
			return location.getLatitude();
		}catch(NullPointerException ex){
			return 0.0;
		}
		
	}

	public double getLongitude(){
		try{
			return location.getLongitude();
		}catch(NullPointerException ex){
			return 0.0;
		}
	}

	public GeomagneticField getGeomagneticField(){
		return geoMagneticField;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {
//		 Toast.makeText(this, "Enabled new provider " + provider,
//		 Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
//		 Toast.makeText(this, "Disabled provider " + provider,
//		 Toast.LENGTH_SHORT).show();
	}

	private void updateGeomagneticField() {
        geoMagneticField = new GeomagneticField((float) location.getLatitude(),
                (float) location.getLongitude(), (float) location.getAltitude(),
                location.getTime());
    }

}

