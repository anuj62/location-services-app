package edu.buffalo.cse.locationapp;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import constants.Constants;

import edu.buffalo.cse.locationapp.business.BusinessManager;
import edu.buffalo.cse.locationapp.entity.Location;
import android.content.Context;
import android.database.MergeCursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.util.Log;
/**
 * @author Gokhan Kul
 * gokhanku@buffalo.edu
 * Added on 09/10/2014
 */
public class ScheduledScan implements Runnable {
	private Context context = null;
	private WifiManager wm = null;
	private BusinessManager bm = null;
	private List<ScanResult> scanResults;
	private Handler handler;
	private int repeatTimeTraining = 1000;
	private int repeatTimePositioning = 15000;
	private int scanLimit = 5;
	private Location location = null;
	private boolean isTrainingMode = true;
	private Location returnResult = null;
	
	public ScheduledScan(WifiManager wm, Handler handler) {
		this.wm = wm;
		this.handler = handler;
		this.bm = bm;
	}
	
	public ScheduledScan(BusinessManager bm, Context context, WifiManager wm, Handler handler,  Location location)  {
		this.context = context;
		this.wm = wm;
		this.handler = handler;
		this.location = location;
		this.isTrainingMode = true;
		
		this.bm = bm;
	}
	
	public ScheduledScan(BusinessManager bm, Context context, WifiManager wm, Handler handler)  {
		this.context = context;
		this.wm = wm;
		this.handler = handler;
		this.isTrainingMode = false;
		
		this.bm = bm;
	}
	
	public void saveData() {
		bm.saveData();
	}
	
	public void loadData() {
		bm.loadData();
	}
	
	@Override
	public void run() {
		if (isTrainingMode) {
			scanResults = scanRSSI();
			if (scanLimit != 0) {
				handler.postDelayed(this, repeatTimeTraining);
				scanLimit--;
				Log.v("ScheduledScan", "In training mode, scanning");
			}
			else 	{
				Bundle bundle = new Bundle();
				Message msg = this.handler.obtainMessage();
				bundle.putString("ListSize", "Scan Complete");
				msg.setData(bundle);
				this.handler.sendMessage(msg);
				
				Log.v("ScheduledScan", "In training mode, scan complete");
			
				handler = null;
			}
		}
		else {
			scanResults = scanRSSI();
			handler.postDelayed(this, repeatTimePositioning);
		}
	}
	
	public List<ScanResult> getScanResults() {
		return scanResults;
	}
	
	private List<ScanResult> scanRSSI() {
		List<ScanResult> scanResult = new ArrayList<ScanResult>();
		Log.e("CSE622", "wifimanager starting: " + (wm == null)); 
		if (wm.startScan()) {
			scanResult = wm.getScanResults();
			
			Location calculatedLocation = null;
			Log.v("ScheduledScan", "In training mode, scanRSSI method");
			
			if ((location != null) && (isTrainingMode)) {
				bm.saveFingerprint(location, scanResult);
				Log.v("ScheduledScan", "In training mode, fingerprint saved");
			}
			else {
				scanResult = bm.mergeSSID(scanResult);
				calculatedLocation = bm.getPosition(scanResult);
			}
			
			Log.v("LocationApp", Integer.toString(scanResult.size()));
			Message msg = this.handler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putInt("MessageType", Constants.MESSAGE_WIFI);
			bundle.putString("ListSize", "Scan Result: "+scanResult.size());
			bundle.putString("ListInfo", "Wifi622 Signal Strenth: " + printInfo(scanResult)); //BugFix: key name same as previous. Was "ListSize"
			//note: sending calculated location to mainactivity
			if (calculatedLocation != null) {
				bundle.putString("Location", calculatedLocation.getTag() + " , " + calculatedLocation.getXLocation()+ " , " + calculatedLocation.getYLocation());
			}
			msg.setData(bundle);
			this.handler.sendMessage(msg);
		}
		return scanResult;
	}
	
	private String printInfo(List<ScanResult> scanInfo) {
		StringBuilder printScan = new StringBuilder();
		
		for (int i = 0; i < scanInfo.size(); i++ ) {
			printScan.append(i + ": " +scanInfo.get(i).SSID + " " + scanInfo.get(i).BSSID +
					" " + scanInfo.get(i).level + "\t");
		}
		
		return printScan.toString();
			
	}
	public int getRepeatTimePositioning() {
		return repeatTimePositioning;
	}
	public int getRepeatTimeTraning() {
		return repeatTimeTraining;
	}
	public void setRepeatTime(int repeatTime) {
		this.repeatTimePositioning = repeatTime;
	}
}
