package edu.buffalo.cse.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.net.wifi.ScanResult;

import edu.buffalo.cse.algorithm.knearestneighbor.APRSSIPair;
import edu.buffalo.cse.algorithm.knearestneighbor.SignalSample;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;

public class Utils {
	
	public static List<Location> CreateLocationList(List<SignalSample> signalDatabase) {
		
    	if (signalDatabase != null && signalDatabase.size() > 0)
        {
    		List<Location> locationList = new ArrayList<Location>();
    		Location tempLocation = null;
    		int counter = 1;
    		
    		for (int i = 0; i < signalDatabase.size(); i++) {
    			tempLocation = new Location(signalDatabase.get(i).getTag(), signalDatabase.get(i).getXCoordinate(), signalDatabase.get(i).getYCoordinate(), signalDatabase.get(i).getMapID());
    			boolean isListed = false;
    			if (locationList != null && locationList.size() > 0) {
    				for (int j = 0; j < locationList.size(); j++) {
    					if (locationList.get(j).getMapID() == tempLocation.getMapID()
    						&& locationList.get(j).getXLocation() == tempLocation.getXLocation()
    						&& locationList.get(j).getYLocation() == tempLocation.getYLocation()) {
    					
    						isListed = true;
    						break;
    					}
    				}
    			}
    			
    			if (!isListed && tempLocation != null) {
    				tempLocation.setID(counter);
    				// note: counter is starting from 1 because all objects that is created has 0 as the ID (because it is an integer) 
    				locationList.add(tempLocation);
    				counter++;
    			}
    		}
    		
    		return locationList;
        }
    	
		return null;
	}
	
    public static List<AccessPoint> CreateAccessPointList(List<SignalSample> signalDatabase) {
		
    	if (signalDatabase != null && signalDatabase.size() > 0)
        {
    		List<AccessPoint> accessPointList = new ArrayList<AccessPoint>();
    		AccessPoint tempAccessPoint = null;
    		int counter = 1;
    		
    		for (int i = 0; i < signalDatabase.size(); i++) {
    			tempAccessPoint = new AccessPoint(signalDatabase.get(i).getMacAddress().toString(), signalDatabase.get(i).getSSID());
    			boolean isListed = false;
    			if (accessPointList != null && accessPointList.size() > 0) {
    				for (int j = 0; j < accessPointList.size(); j++) {
    					if (accessPointList.get(j).getMacAddress().equals(tempAccessPoint.getMacAddress())) {    					
    						isListed = true;
    						break;
    					}
    				}
    			}
    			
    			if (!isListed && tempAccessPoint != null) {
    				tempAccessPoint.setID(counter);
    				// note: counter is starting from 1 because all objects that is created has 0 as the ID (because it is an integer) 
    				accessPointList.add(tempAccessPoint);
    				counter++;
    			}
    		}
    		
    		return accessPointList;
        }
    	
		return null;
	}
    
    public static List<APRSSIPair> CreateVectorComponent(Location location, List<AccessPoint> accessPointList, List<SignalSample> signalDatabase) {
    	if (signalDatabase != null && signalDatabase.size() > 0)
        {
    		List<APRSSIPair> pairList = new ArrayList<APRSSIPair>();
    		APRSSIPair tempPair = null;
    		for (int i = 0; i < signalDatabase.size(); i++) {
    			//location = new Location(signalDatabase.get(i).getTag(), signalDatabase.get(i).getXCoordinate(), signalDatabase.get(i).getYCoordinate(), signalDatabase.get(i).getMapID());
    			if (pairList != null && (signalDatabase.get(i).getXCoordinate() == location.getXLocation())
    					&& (signalDatabase.get(i).getYCoordinate() == location.getYLocation())
    					&& (signalDatabase.get(i).getMapID() == location.getMapID())) {
    				tempPair = new APRSSIPair(signalDatabase.get(i).getMacAddress().toString(), signalDatabase.get(i).getRSSI());
    				pairList.add(tempPair);
    			}
    		}
    		
    		for (int i = 0; i < accessPointList.size(); i++) {
    			boolean isAdded = false;
    			for (int j = 0; j < pairList.size(); j++) {
    				if (accessPointList.get(i).getMacAddress().equals(pairList.get(j).getAPMac())) {
    					isAdded = true;
    				}
    			}
    			
    			if (!isAdded) {
    				pairList.add(new APRSSIPair(accessPointList.get(i).getMacAddress(), -100));
    			}
    		}
    		
    		Collections.sort(pairList);
    		
    		return pairList;
        }
    	
		return null;
	}
    
    public static List<APRSSIPair> CreateVectorComponent(List<AccessPoint> accessPointList, List<ScanResult> signalDatabase) {
    	if (signalDatabase != null && signalDatabase.size() > 0)
        {
    		List<APRSSIPair> pairList = new ArrayList<APRSSIPair>();
    		APRSSIPair tempPair = null;
    		for (int i = 0; i < signalDatabase.size(); i++) {
    			//location = new Location(signalDatabase.get(i).getTag(), signalDatabase.get(i).getXCoordinate(), signalDatabase.get(i).getYCoordinate(), signalDatabase.get(i).getMapID());
    			if (pairList != null) {
    				tempPair = new APRSSIPair(signalDatabase.get(i).BSSID.toString(), signalDatabase.get(i).level);
    				pairList.add(tempPair);
    			}
    		}
    		
    		for (int i = 0; i < accessPointList.size(); i++) {
    			boolean isAdded = false;
    			for (int j = 0; i < pairList.size(); j++) {
    				if (accessPointList.get(i).getMacAddress().equals(pairList.get(j).getAPMac())) {
    					isAdded = true;
    				}
    			}
    			
    			if (!isAdded) {
    				pairList.add(new APRSSIPair(accessPointList.get(i).getMacAddress(), -100));
    			}
    		}
    		
    		Collections.sort(pairList);
    		
    		return pairList;
        }
    	
		return null;
	}





}
