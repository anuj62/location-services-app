package edu.buffalo.cse.locationapp;
import java.util.ArrayList;
import java.util.List;
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
	private WifiManager wm;
	private List<ScanResult> scanResults;
	private Handler handler;
	private int repeatTime = 500;
	private int scanLimit = 4;
	public ScheduledScan(WifiManager wm, Handler handler) {
		this.wm = wm;
		this.handler = handler;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		scanResults = scanRSSI();
		if (scanLimit != 0) {
			handler.postDelayed(this, repeatTime);
			scanLimit--;
		}
		else {
			Bundle bundle = new Bundle();
			Message msg = this.handler.obtainMessage();
			bundle.putString("ListSize", "Scan Complete");
			msg.setData(bundle);
			this.handler.sendMessage(msg);
			
			handler = null;
		}
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
			bundle.putString("ListSize", "Wifi622 Signal Strenth: " + printInfo(scanResult));
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
	public int getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(int repeatTime) {
		this.repeatTime = repeatTime;
	}
}
