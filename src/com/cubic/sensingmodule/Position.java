package com.cubic.sensingmodule;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.Criteria;
import android.os.Bundle;
import android.hardware.GeomagneticField;

public class Position implements LocationListener{
	private LocationManager locationmanager;
	private String provider;
	private Location location;
	private GeomagneticField geoMagneticField;

	public Position(LocationManager locManager){
		locationmanager = locManager;
		Criteria criteria = new Criteria();
		provider = locationmanager.getBestProvider(criteria, true);
		locationmanager.requestLocationUpdates(provider, 1000, .1f, this);
		location = locationmanager.getLastKnownLocation(provider);
	}

	public Location getLocation(){
		return location;
	}

	@Override
	public void onLocationChanged(Location newLocation){
		location = newLocation;
		updateGeomagneticField();
	}

	public double getLatitude(){
		return location.getLatitude();
	}

	public double getLongitude(){
		return location.getLongitude();
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

