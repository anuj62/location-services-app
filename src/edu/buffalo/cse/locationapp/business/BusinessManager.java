package edu.buffalo.cse.locationapp.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.buffalo.cse.algorithm.knearestneighbor.KNearestNeighbor;
import edu.buffalo.cse.algorithm.knearestneighbor.SignalSample;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Fingerprint;
import edu.buffalo.cse.locationapp.entity.Location;

public class BusinessManager {
	
	private JSONArray jsonarray = null;
	private List<SignalSample> fingerPrintList = null;
	private Context context = null;
	private KNearestNeighbor algo = null;
	
	public BusinessManager(Context context) {
		jsonarray = new JSONArray();
	}
	
	public void setFingerprintList(List<SignalSample> fingerPrintList) {
		this.fingerPrintList = fingerPrintList;
	}
	
	public List<SignalSample> getFingerprintList() {
		return fingerPrintList;
	}
	

	public void loadData() {
		try{  
	        File localizationFile = new File(Environment.getExternalStorageDirectory(), "/android/data/localizationApp/fingerprint.json");
            FileInputStream stream = new FileInputStream(localizationFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
              }
              finally {
                stream.close();
              }
            
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray data  = jsonObj.getJSONArray("ScanData");
            
            fingerPrintList = new ArrayList<SignalSample>();
            SignalSample tempSample = null;

            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                
                tempSample = new SignalSample();
                tempSample.setXCoordinate(c.getInt("X"));
                tempSample.setYCoordinate(c.getInt("Y"));
                tempSample.setMapID(c.getInt("MapID"));
                tempSample.setTag(c.getString("Tag"));
                tempSample.setSSID(c.getString("SSID"));
                tempSample.setMacAddress(c.getString("BSSID"));
                tempSample.setFrequency(c.getInt("Frequency"));
                tempSample.setScanDateTime(c.getLong("TimeStamp"));
                tempSample.setCapabilities(c.getString("Capabilities"));
                tempSample.setRSSI(c.getInt("SignalStrength"));
                
                fingerPrintList.add(tempSample);
              }


       } catch (Exception e) {
       e.printStackTrace();
      }
    }
	
	public void saveData() {
		if (fingerPrintList != null && fingerPrintList.size() > 0) {
			JSONObject mainJsonObject = new JSONObject();
			JSONObject tempJsonObject = null;
			
			JSONArray scanArray = new JSONArray();
			
			for (int i = 0; i < fingerPrintList.size(); i++) {
				tempJsonObject = new JSONObject();
				try{
					tempJsonObject.put("X", fingerPrintList.get(i).getXCoordinate());
					tempJsonObject.put("Y", fingerPrintList.get(i).getYCoordinate());
					tempJsonObject.put("MapID", fingerPrintList.get(i).getMapID());
					tempJsonObject.put("Tag", fingerPrintList.get(i).getTag());
					tempJsonObject.put("SSID", fingerPrintList.get(i).getSSID());
					tempJsonObject.put("BSSID", fingerPrintList.get(i).getMacAddress());
					tempJsonObject.put("Frequency", fingerPrintList.get(i).getFrequency());
					tempJsonObject.put("TimeStamp", fingerPrintList.get(i).getScanDateTime());
					tempJsonObject.put("Capabilities", fingerPrintList.get(i).getCapabilities());
					tempJsonObject.put("SignalStrength", fingerPrintList.get(i).getRSSI());
					Log.v("saveData", "Object created");
				}
				catch (Exception ex) {
					Log.e("JSON Exception", "Error creating JSON Object");
				}
				
				scanArray.put(tempJsonObject);
			}
			
			try {
				mainJsonObject.put("ScanData", scanArray);
				Log.v("saveData", "Array created");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.v("saveData", "Entering External");
			
			if (isExternalStorageWritable()) {
				Log.v("saveData", "Entered External");
				try {
					File extFile = null;
					FileOutputStream out = null;
					try {
						extFile = new File(Environment.getExternalStorageDirectory(), "/android/data/localizationApp/");
	            		out = new FileOutputStream(extFile);
					}
					catch (Exception ex) {
						extFile = new File("fingerprint.json");
						//extFile = new File(Environment.getExternalStorageDirectory(), "/android/data/localizationApp/fingerprint.json");
						out = new FileOutputStream(extFile);
					}
	            	out.write(jsonarray.toString().getBytes());
	            	out.flush();
	            	out.close();
	            	Log.v("saveData", "File Created in External");
				}
				catch (Exception ex) {
					Log.e("LocalizationApp", ex.toString());
				}
			}
			else {
				Log.v("saveData", "External unavailable, entered SharedPreferences");
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	        	Editor editor = prefs.edit();
	        	editor.putString("key", jsonarray.toString());
	        	editor.commit();
	        	Log.v("saveData", "Successfull Committed to SharedPreferences");
			}

			
			
			
		}
	}

	public void saveFingerprint(Location location, List<ScanResult> scanResult) {
		if (fingerPrintList == null) {
			fingerPrintList = new ArrayList<SignalSample>();
		}
		
		scanResult = mergeSSID(scanResult);
		
		SignalSample tempSignalSample = null;
		for (int i = 0; i < scanResult.size(); i++) {
			tempSignalSample = new SignalSample();
			
			tempSignalSample.setSSID(scanResult.get(i).SSID);
			tempSignalSample.setMacAddress(scanResult.get(i).BSSID);
			tempSignalSample.setFrequency(scanResult.get(i).frequency);
			tempSignalSample.setScanDateTime(scanResult.get(i).timestamp);
			tempSignalSample.setCapabilities(scanResult.get(i).capabilities);
			tempSignalSample.setRSSI(scanResult.get(i).level);
			tempSignalSample.setMapID(location.getID());
			tempSignalSample.setXCoordinate(location.getXLocation());
			tempSignalSample.setYCoordinate(location.getYLocation());
			tempSignalSample.setMapID(location.getMapID());
			tempSignalSample.setTag(location.getTag());
			
			fingerPrintList.add(tempSignalSample);
			
			Log.v("saveFingerprint", "Fingerprint save success");
		}
		
		        
	}
	

	
	
	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public List<ScanResult> mergeSSID(List<ScanResult> result) {
		
		HashMap<String,ScanResult> tempMap = new HashMap<String, ScanResult>();
		
		for (int i=0; i < result.size(); i++){
			if (!tempMap.containsKey(result.get(i).BSSID)){
				tempMap.put(result.get(i).BSSID, result.get(i));
			}
			else {
				if (tempMap.get(result.get(i).BSSID).level < result.get(i).level) {
					tempMap.put(result.get(i).BSSID, result.get(i));
				}
			}
		}
		
		Collection<ScanResult> tempVal = tempMap.values();
		ArrayList<ScanResult> retVal = new ArrayList<ScanResult>();
		
		
		for (Iterator<ScanResult> iterator = tempVal.iterator(); iterator.hasNext();) {
	        ScanResult value = (ScanResult) iterator.next();
	        retVal.add(value);
	    }
		
		return retVal;
	}

	public Location getPosition(List<ScanResult> scanResult) {
		
		// note first localization will take so much time
		if (algo == null) {
			algo = new KNearestNeighbor(fingerPrintList);
		}
		
		return algo.PositionData(scanResult);
	}
}