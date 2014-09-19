package edu.buffalo.cse.locationapp;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author Gokhan Kul
 * gokhanku@buffalo.edu
 * Added on 09/10/2014
 */

public class ScheduledScan implements Runnable {

	private WifiManager wm;
	private List<ScanResult> scanResults;
	private Handler handler;
	
	private int repeatTime = 2000;
	
	public ScheduledScan(WifiManager wm, Handler handler) {
		this.wm = wm;
		this.handler = handler;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		scanResults = scanRSSI();
		
		handler.postDelayed(this, repeatTime);
	}
	
	public List<ScanResult> getScanResults() {
		return scanResults;
	}
	
	private List<ScanResult> scanRSSI() {
		List<ScanResult> scanResult = new ArrayList<ScanResult>();
		
		if (wm.startScan()) {
			
			scanResult = wm.getScanResults();
			Log.v("LocationApp", Integer.toString(scanResult.size()));
			Message msg = this.handler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putString("ListSize", "Scan Result: "+scanResult.size());
			msg.setData(bundle);
			this.handler.sendMessage(msg);
		}
		
		return scanResult;
	}
	
	public int getRepeatTime() {
		return repeatTime;
	}
	
	public void setRepeatTime(int repeatTime) {
		this.repeatTime = repeatTime;
	}

}
