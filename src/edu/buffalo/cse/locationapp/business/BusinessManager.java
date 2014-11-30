package edu.buffalo.cse.locationapp.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.provider.OpenableColumns;
import android.util.Log;
import edu.buffalo.cse.algorithm.knearestneighbor.KNearestNeighbor;
import edu.buffalo.cse.locationapp.dataaccess.PMAccessPoint;
import edu.buffalo.cse.locationapp.dataaccess.PMFactory;
import edu.buffalo.cse.locationapp.dataaccess.PMLocation;
import edu.buffalo.cse.locationapp.dataaccess.PMSignalStrength;
import edu.buffalo.cse.locationapp.dataaccess.PersistencyManager;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Fingerprint;
import edu.buffalo.cse.locationapp.entity.Location;

public class BusinessManager {
	
	private JSONArray jsonarray = null;
	
	//private PMAccessPoint pmAccessPoint = null;
	//private PMSignalStrength pmFingerprint = null;
	//private PMLocation pmLocation = null;
	
	private List<ScanResult> fingerPrintList = null;
	
	private Context context = null;
	
	public BusinessManager(Context context) {
		jsonarray = new JSONArray();
	}

	public void saveFingerprint(Location location, List<ScanResult> scanResult) {
		/*
		pmAccessPoint = PMFactory.PMAccessPoint();
		pmLocation = PMFactory.PMLocation();
		pmFingerprint = PMFactory.PMSignalStrength();
		for (int i = 0; i < scanResult.size(); i++) {
			pmAccessPoint.AddAccessPoint(
					new AccessPoint(scanResult.get(i).BSSID, scanResult.get(i).SSID, "", 1));
			pmLocation.AddLocation(new Location(location));
			pmFingerprint.addFingerprint(new Fingerprint(location, scanResult.get(i).BSSID, scanResult.get(i).level, scanResult.get(i).frequency, scanResult.get(i).timestamp));
		}
		*/
		
		if (fingerPrintList == null) {
			fingerPrintList = new ArrayList<ScanResult>();
		}
		
		scanResult = MergeSSID(scanResult);
		
		for (int i = 0; i < scanResult.size(); i++) {
			fingerPrintList.add(scanResult.get(i));
		}
		
		JSONArray scanArray = new JSONArray();
		
		for (int i = 0; i < scanResult.size(); i++) {
			JSONObject tempJsonObject = new JSONObject();
			try{
				tempJsonObject.put("SSID", scanResult.get(i).SSID);
				tempJsonObject.put("BSSID", scanResult.get(i).BSSID);
				tempJsonObject.put("Frequency", scanResult.get(i).frequency);
				tempJsonObject.put("TimeStamp", scanResult.get(i).timestamp);
				tempJsonObject.put("Capabilities", scanResult.get(i).capabilities);
				tempJsonObject.put("SignalStrength", scanResult.get(i).level);
			}
			catch (Exception ex) {
				Log.e("JSON Exception", "Error creating JSON Object");
			}
			
			scanArray.put(tempJsonObject);
		}
		
		JSONObject locationObject = new JSONObject();
		
		try {
			locationObject.put("Tag", location.getTag());
			locationObject.put("X", location.getXLocation());
			locationObject.put("Y", location.getYLocation());
			locationObject.put("MapID", location.getMapID());
			locationObject.put("Scan", scanArray);
		} catch (Exception ex) {
			Log.e("JSON Exception", "Error creating Location JSON Object");
		}
		
		jsonarray.put(locationObject);
		
		if (isExternalStorageWritable()) {
			try {
				File extFile = new File(Environment.getExternalStorageDirectory(), "/android/data/localizationApp/fingerprint.json");
            	FileOutputStream out = new FileOutputStream(extFile);

            	out.write(jsonarray.toString().getBytes());
            	out.flush();
            	out.close();
			}
			catch (Exception ex) {
				Log.e("LocalizationApp", ex.toString());
			}
		}
		else {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        	Editor editor = prefs.edit();
        	editor.putString("key", jsonarray.toString());
        	editor.commit();
		}
        
	}
	
	//todo
	public JSONArray loadExternal(){
		JSONObject storyObj = null;
		
		try{

	        FileInputStream fileInput = new FileInputStream(new File("/android/data/localizationApp/fingerprint.json"));

	        BufferedReader inputReader = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"), 8);
	        StringBuilder strBuilder = new StringBuilder();
	            String line = null;
	            while ((line = inputReader.readLine()) != null) {
	                strBuilder.append(line + "\n");
	            }
	            fileInput.close();
	            storyObj = null;//todo strBuilder.toString();

	    }catch(IOException e){
	         Log.e("log_tag", "Error building string "+e.toString());
	    }

	    try{
	        JSONArray jArray = new JSONArray(storyObj);
	        String storyNames = "";
	        for(int i = 0;i<jArray.length();i++){
	            storyNames += jArray.getJSONObject(i).getString("story_name") +"\n";
	        }

	    }catch(JSONException e) {
	         Log.e("log_tag", "Error returning string "+e.toString());
	    }
	    return null;
	//and of openJson() 
	}
	
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
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
	
	private List<ScanResult> MergeSSID(List<ScanResult> result) {
		
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
		
		KNearestNeighbor algo = new KNearestNeighbor();
		
		//return algo.PositionData(scanResult, fingerPrintList);;
		return null;
	}
	
	

}
