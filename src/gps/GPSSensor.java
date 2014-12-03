package gps;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import constants.Constants;
import constants.Locations;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GPSSensor implements LocationListener{
	
	private Handler locationHandler;
	
	public GPSSensor(Handler serviceHandler) {
		
		locationHandler = serviceHandler;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Bundle bundle = new Bundle(5);
		bundle.putInt("MessageType", Constants.MESSAGE_GPS );
		bundle.putDouble(Constants.KEY_LATITUDE, location.getLatitude());
		bundle.putDouble(Constants.KEY_LATITUDE, location.getLongitude());
		bundle.putDouble(Constants.KEY_ALTITUDE, location.getAltitude());
		bundle.putFloat(Constants.KEY_ACCURACY, location.getAccuracy());
		msg.setData(bundle);
		locationHandler.sendMessage(msg);
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	LocationManager mLocationManager;
	LocationListener mLocationListener;
	Context mContext;
	String mProvider;
	Location currentLocation;
	private static final String TAG = "GPSLocation";
	public GPSLocation(Context context, LocationListener locationListener) {
		mContext = context;
		mLocationListener = locationListener;
		mLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
	}
	
	//public String getLastLocation();

	public String getGPSLocation(float distance){
		Criteria criteria = new Criteria();
		if(distance > 25.0f){
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		}else if(distance > 0.0f && distance <= 25.0f){
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
		}
		mProvider = mLocationManager.getBestProvider(criteria, true);
		Log.v(TAG, "Provider chosen: "+mProvider);
		Location locations = mLocationManager.getLastKnownLocation(mProvider);
		
		List<String>  providerList = mLocationManager.getAllProviders();
		if(locations==null){
			Log.v(TAG, "Locations is NULL");
			
		}
		if(providerList==null){
			Log.v(TAG, "Provider is null");
			
			
		}else{
			if(providerList.size()<=0)
				Log.v(TAG, "Providerlist is zero");
		}
		if(null!=locations && null!=providerList && providerList.size()>0){                 
			double longitude = locations.getLongitude();
			double latitude = locations.getLatitude();
			Geocoder geocoder = new Geocoder(mContext, Locale.US);        
			if(Geocoder.isPresent()){
				Log.v(TAG, "Geocoder is present");
			}else{
				Log.v(TAG, "Geocoder is NOT present");
			}
			try {
				List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
				if(null!=listAddresses && listAddresses.size()>0){
					return listAddresses.get(0).getAddressLine(0);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return Locations.LOCATION_UNKNOWN;
	}
	
	public String getCurrrentLocation(){
		Location location = currentLocation;
		if(currentLocation == null){
			Log.e(TAG, "Current location is null\n");
			return Locations.LOCATION_UNKNOWN;
		}
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		Geocoder geocoder = new Geocoder(mContext, Locale.US);        
		if(Geocoder.isPresent()){
			Log.v(TAG, "Geocoder is present");
		}else{
			Log.v(TAG, "Geocoder is NOT present");
		}
		try {
			List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
			if(null!=listAddresses && listAddresses.size()>0){
				return listAddresses.get(0).getAddressLine(0);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Locations.LOCATION_UNKNOWN;
	}

	
	*/

	
}
