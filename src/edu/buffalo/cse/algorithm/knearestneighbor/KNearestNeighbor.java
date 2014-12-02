package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.net.wifi.ScanResult;
import android.util.SparseIntArray;

import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;

public class KNearestNeighbor
{
    private int k = 3;

    //Each training location is a vector
    private List<Vector> m_Vector = null;
    private List<Location> m_Location = null;
    private List<AccessPoint> m_AccessPoint = null;

    
    public KNearestNeighbor(List<SignalSample> signalDatabase)
    {
        PrepareVectors(signalDatabase);
    }

    public int getK() {
    	return k;
    }
    
    public void setK(int k) {
    	this.k = k;
    }

    private void PrepareVectors(List<SignalSample> signalDatabase)
    {         
        m_Location = CreateLocationList(signalDatabase);
        m_AccessPoint = CreateAccessPointList(signalDatabase);
        
        if (m_Location != null && m_Location.size() > 0)
        {
            m_Vector = new ArrayList<Vector>();
            Vector tempVector = null;
            for (int i = 0; i < m_Location.size(); i++)
            {
                tempVector = new Vector(m_Location.get(i).getID(), CreateVectorComponent(m_Location.get(i), m_AccessPoint, signalDatabase));
                m_Vector.add(tempVector);
            }
        }
    }

    private List<APRSSIPair> CreateVectorComponent(Location location, List<AccessPoint> accessPointList, List<SignalSample> signalDatabase) {
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
    			for (int j = 0; i < pairList.size(); j++) {
    				if (accessPointList.get(i).getMacAddress().equals(pairList.get(j).getAPMac())) {
    					isAdded = true;
    				}
    			}
    			
    			if (!isAdded) {
    				pairList.add(new APRSSIPair(accessPointList.get(i).getMacAddress(), -100));
    			}
    		}
    		
    		return pairList;
        }
    	
		return null;
	}

	private List<Location> CreateLocationList(List<SignalSample> signalDatabase) {
		
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
    
    private List<AccessPoint> CreateAccessPointList(List<SignalSample> signalDatabase) {
		
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

	public Location PositionData(List<ScanResult> signalList)
    {	
        if (signalList != null && signalList.size() > 0 && m_Vector != null && m_Vector.size() > 0)
        {

            //todo calculate and sort distances list here
        	
        	Location tempLocation = GetClosestLocation(signalList);

        	/*
            Location[] tempLocationList = new Location[k];
            int x = 0, y = 0, z = 0;

            
            for (int i = 0; i < k; i++)
            {
                tempLocationList[i] = new Location(); //todo bring m_BMLocation.GetLocation(distances[distances.Count - k + i ].ID);
            }

            for (int i = 0; i < tempLocationList.length; i++)
            {
                x += tempLocationList[i].getXLocation();
                y += tempLocationList[i].getYLocation();
                //z += tempLocationList[i].getMapID();
            }

            Location retVal = new Location();
            retVal.setXLocation(x / k);
            retVal.setYLocation(y / k);
            //retVal.setMapID(z / k);

            return retVal;
            */
        	
        	return tempLocation;
        }

        return null;
        
    }
	
	private Location GetClosestLocation(List<ScanResult> signalList) {
		SparseIntArray distances = CalculateDistances(signalList);
		
		int tempKey = 0;
		int minKey = 0;
		int tempValue = Integer.MAX_VALUE;
		int minValue = Integer.MAX_VALUE;

		
		
		for(int i = 0; i < distances.size(); i++) {
			tempKey = distances.keyAt(i);
			// get the object by the key.
			tempValue = distances.get(tempKey);
			   
			if (tempValue < minValue) {
				minValue = tempValue;
				minKey = tempKey;
			}
		}
		
		for(int i = 0; i < m_Location.size(); i++) {
			if (m_Location.get(i).getID() == minKey) {
				return m_Location.get(i);
			}
		}
		
		return null;
	}

    private SparseIntArray CalculateDistances(List<ScanResult> signalList)
    {
    	//first integer is locationID and second is the distance
        SparseIntArray distances = new SparseIntArray();
        
        for (int i = 0; i < m_Vector.size(); i++) {
        	distances.put(m_Vector.get(i).GetLocationID(), calculateDifference(signalList, m_Vector.get(i).GetSignalStrengthList()));
        }
        
        return distances;
    }

    private int calculateDifference(List<ScanResult> signalList, List<APRSSIPair> signalStrengthList) {
    	double distance = 0;
        for (int i = 0; i < signalStrengthList.size(); i++) {
        	
        	boolean isFound = false;
        	
        	for (int j = 0; j < signalList.size(); j++) {
        		if (signalStrengthList.get(i).getAPMac().equals(signalList.get(j).BSSID)) {
        			isFound = true;
        			distance += Substract((double)signalStrengthList.get(i).getValue(), (double) signalList.get(j).level);
        			break;
        		}
        	}
        	
        	if (!isFound) {
        		//note : minimum signal strength value is assumed -100
        		distance += Substract(signalStrengthList.get(i).getValue(), -100);
        	}
        }

        return (int) distance;
    }

    private double Substract(double value1, double value2)
    {
        return Math.abs(Math.abs(value1) - Math.abs(value2));
    }
    
}