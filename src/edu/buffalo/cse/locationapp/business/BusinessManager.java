package edu.buffalo.cse.locationapp.business;

import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.wifi.ScanResult;
import edu.buffalo.cse.locationapp.dataaccess.PMAccessPoint;
import edu.buffalo.cse.locationapp.dataaccess.PMFactory;
import edu.buffalo.cse.locationapp.dataaccess.PMLocation;
import edu.buffalo.cse.locationapp.dataaccess.PMSignalStrength;
import edu.buffalo.cse.locationapp.dataaccess.PersistencyManager;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Fingerprint;
import edu.buffalo.cse.locationapp.entity.Location;

public class BusinessManager {
	
	private PMAccessPoint pmAccessPoint = null;
	private PMSignalStrength pmFingerprint = null;
	private PMLocation pmLocation = null;
		
	
	private Context context = null;
	
	public BusinessManager(Context context) {
		
		
	}

	public void saveFingerprint(Location location, List<ScanResult> scanResult) {
		pmAccessPoint = PMFactory.PMAccessPoint();
		pmLocation = PMFactory.PMLocation();
		pmFingerprint = PMFactory.PMSignalStrength();
		for (int i = 0; i < scanResult.size(); i++) {
			pmAccessPoint.AddAccessPoint(
					new AccessPoint(scanResult.get(i).BSSID, scanResult.get(i).SSID, "", 1));
			pmLocation.AddLocation(new Location(location));
			pmFingerprint.addFingerprint(new Fingerprint(location, scanResult.get(i).BSSID, scanResult.get(i).level, scanResult.get(i).frequency, scanResult.get(i).timestamp));
		}
		
	}
	
	

}
