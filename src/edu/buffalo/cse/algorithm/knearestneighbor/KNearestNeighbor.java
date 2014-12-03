package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.net.wifi.ScanResult;
import android.util.SparseIntArray;

import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;
import edu.buffalo.cse.algorithm.Utils;

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
        m_Location = Utils.CreateLocationList(signalDatabase);
        m_AccessPoint = Utils.CreateAccessPointList(signalDatabase);
        
        if (m_Location != null && m_Location.size() > 0)
        {
            m_Vector = new ArrayList<Vector>();
            Vector tempVector = null;
            for (int i = 0; i < m_Location.size(); i++)
            {
                tempVector = new Vector(m_Location.get(i).getID(), Utils.CreateVectorComponent(m_Location.get(i), m_AccessPoint, signalDatabase));
                m_Vector.add(tempVector);
            }
        }
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