package edu.buffalo.cse.locationapp;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import constants.Constants;
import constants.Locations;
import edu.buffalo.cse.algorithm.pedometer.Pedometer;
import edu.buffalo.cse.algorithm.pedometer.PedometerPathMapper;
import edu.buffalo.cse.algorithm.pedometer.PedometerPathMapperEventListener;
import edu.buffalo.cse.locationapp.business.BusinessManager;
import edu.buffalo.cse.locationapp.entity.Location;
import gps.GPSSensor;
import android.app.Activity;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LocationUI extends Activity implements OnItemSelectedListener, PedometerPathMapperEventListener{
	Spinner mSpinner;
	private static final String TAG = "LocationUI";
	TextView mCurrentLocationView;
	TextView mCurrentWifiLocView;
	TextView mCurrentWifiPmtrView;
	TextView mCurrentWifiNFCView;
	TextView mCurrentSelection;
	String mResolution;
	GPSSensor gpsSensor;
	String fingerprintlocation;
	String gpsLocation;
	NfcAdapter mNfcAdapter;
	int count;
	int mDistance;
	private Pedometer ped;
	private PedometerPathMapper ppm;
	
	public LocationUI() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_locationui);
		mSpinner = (Spinner)findViewById(R.id.location_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dist_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
		mCurrentLocationView = (TextView)findViewById(R.id.currentlocation);
		mCurrentSelection = (TextView)findViewById(R.id.currentselection);
		mCurrentWifiLocView = (TextView)findViewById(R.id.currentWifilocation);
		mCurrentWifiPmtrView = (TextView)findViewById(R.id.currentWifiPedometerlocation);
		mCurrentWifiNFCView = (TextView)findViewById(R.id.currentNFClocation);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if(mNfcAdapter == null){
			Toast.makeText(this, "NFC not suppored", Toast.LENGTH_SHORT).show();
			
		}
		
		ppm = new PedometerPathMapper(this);
		ped = new Pedometer((SensorManager)getSystemService(SENSOR_SERVICE), ppm);
		ped.start();

		gpsSensor = new GPSSensor(handler);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ped.stop();
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
		mResolution = (String)parent.getItemAtPosition(position);
		
		Log.v(TAG, "Item: "+mResolution);
		mCurrentSelection.setText("Resolution: "+mResolution+" m");
		//mCurrentLocationView.setText(mResolution);
		String currentLocation = getCurrentLocation(mResolution);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
		
	}
	
	
	public String getCurrentLocation(String res){
		int distance = Integer.parseInt(res);
		mDistance = distance;
		String detectedlocation;
		if(distance > 25){
			detectedlocation = getGPSLocation();
			mCurrentLocationView.setText(detectedlocation);
		}else if(distance <= 25 && distance > 10){
			//wifi
			//detectedlocation = getWifiLocation();
			setResult(Activity.RESULT_OK);
			finish();
			
			
		}else if(distance >1 && distance <= 10){
			//wifi + pedometer
			//detectedlocation = getFineGrainedLocation();
			
			//setResult(Activity.RESULT_FIRST_USER);
			//finish();
			
		}else if(distance <= 1){
			//detectedlocation = getNFCLocation();
		}else{
			
			
		}
		return null;
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			Bundle ofJoy = msg.getData();
			int messageType = ofJoy.getInt("MessageType");
			switch(messageType){
			case Constants.MESSAGE_GPS:
				
				double latitude = msg.getData().getDouble(Constants.KEY_LATITUDE);
    			double longitude = msg.getData().getDouble(Constants.KEY_LONGITUDE);
    			//Geocoder
    			Geocoder geocoder = new Geocoder(LocationUI.this, Locale.US);  
    			
    			if(Geocoder.isPresent()){
    				Log.v(TAG, "Geocoder is present");
    			}else{
    				Log.v(TAG, "Geocoder is NOT present");
    			}
    			try {
    				List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
    				if(null!=listAddresses && listAddresses.size()>0){
    					gpsLocation =  listAddresses.get(0).getAddressLine(0);
    					
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}

    		
    			gpsLocation = Locations.LOCATION_UNKNOWN;
    			//***
    			//Log.v(TAG, "lat = "+ latitude + " long = "+longitude);
    	       // valX.setText("lat = "+ latitude + " long = "+longitude + " loc = "+locationName);
    			break;
    			
			}
		}
	};
	
	String getGPSLocation(){
		return "Davis Hall";
	}

	@Override
	public void boundaryCrossed() {
		
		LocationUI.this.runOnUiThread(new Runnable() {
			  public void run() {
				  count++;
				 // Toast.makeText(LocationUI.this, "Scanning..."+count, Toast.LENGTH_SHORT).show();
			  }
			});
		if(mDistance == 10){
			//Toast.makeText(LocationUI.this, "Scanning..."+count, Toast.LENGTH_SHORT).show();
			setResult(Activity.RESULT_OK);
			
			finish();
		}
		
	}
	

}
