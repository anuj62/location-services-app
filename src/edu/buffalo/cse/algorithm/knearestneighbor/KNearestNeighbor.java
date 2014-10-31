package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;

public class KNearestNeighbor
{
    private int k = 3;

    //Each training location is a vector
    private List<Vector> m_Vector = null;

    //todo create private BMLocation m_BMLocation = null;
    //todo create private BMAccessPoint m_BMAccessPoint = null;
    
    public KNearestNeighbor()
    {
        //todo retrieve m_BMLocation = BMFactory.BMLocation();
        //todo retrieve m_BMAccessPoint = BMFactory.BMAccessPoint();
        PrepareVectors();
    }

    public int getK() {
    	return k;
    }
    
    public void setK(int k) {
    	this.k = k;
    }

    private void PrepareVectors()
    {
        List<Location> tempLocationList = new ArrayList<Location>(); //todo retrieve m_BMLocation.GetLocationList();
        if (tempLocationList != null && tempLocationList.size() > 0)
        {
            m_Vector = new ArrayList<Vector>();
            Vector tempVector = null;
            for (int i = 0; i < tempLocationList.size(); i++)
            {
                tempVector = new Vector(tempLocationList.get(i).getID());
                m_Vector.add(tempVector);
            }
        }
    }

    public Location PositionData(List<SignalSample> signalList)
    {
        List<SignalData> signalDataList = ConvertDataToSignalData(signalList);
        if (signalDataList != null && signalDataList.size() > 0)
        {
            Hashtable signalTable = PrepareData(signalDataList);

            List<VectorComponent> distances = CalculateDistances(signalTable);

            //todo sort distances list here

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
                z += tempLocationList[i].getMapID();
            }

            Location retVal = new Location();
            retVal.setXLocation(x / k);
            retVal.setYLocation(y / k);
            retVal.setMapID(z / k);

            return retVal;
        }

        return null;
        
    }

    private List<VectorComponent> CalculateDistances(Hashtable signalTable)
    {
        List<VectorComponent> distances = new ArrayList<VectorComponent>();
        VectorComponent distance = null;

        for (int i = 0; i < m_Vector.size(); i++)
        {
            distance = new VectorComponent(m_Vector.get(i).GetLocationID(), CalculateDifference(m_Vector.get(i).GetSignalDataList(), signalTable));
            distances.add(distance);
        }

        return distances;
    }

    private double CalculateDifference(Hashtable recordedData, Hashtable signalTable)
    {
        double distance = 0;

        // todo calculate distance
        //foreach (item in recordedData)
        //{
        //    try
        //    {
        //        distance += Substract((double)item.Value, (double)signalTable[item.Key]);
        //    }
        //    catch
        //    {
        //        distance += Substract((double)item.Value, Global.NO_SIGNAL);
        //    }
        //}

        return distance;
    }

    private double Substract(double value1, double value2)
    {
        return Math.abs(Math.abs(value1) - Math.abs(value2));
    }

    private Hashtable<Integer, Integer> PrepareData(List<SignalData> signalDataList)
    {
        //Active project'teki bütün accesspointleri getirir
        List<AccessPoint> tempAPList = new ArrayList<AccessPoint>(); //todo bring access point list m_BMAccessPoint.GetAllAccessPoints();
        Hashtable<Integer, Integer> retVal = null;

        if (tempAPList != null && tempAPList.size() > 0)
        {
            retVal = new Hashtable<Integer, Integer>();

            for (int i = 0; i < tempAPList.size(); i++)
            {
                retVal.put(tempAPList.get(i).getID(), this.FindSignalStrength(tempAPList.get(i).getID(), signalDataList));
            }
        }

        return retVal;
    }

    private int FindSignalStrength(int accesspointID, List<SignalData> signalDataList)
    {
       for (int i = 0; i < signalDataList.size(); i++)
        {
            if (signalDataList.get(i).getAccessPointID() == accesspointID)
                return signalDataList.get(i).getRSSI();
        }
        return -100; //todo default no signal value
    }

    

    private List<SignalData> ConvertDataToSignalData(List<SignalSample> signalList)
    {
        if (signalList != null && signalList.size() > 0)
        {
            List<SignalData> m_tempSignalList = new ArrayList<SignalData>();
            SignalData m_tempSignal = null;
            for (int i = 0; i < signalList.size(); i++)
            {
                //if (CheckIfExists(signalList.get(i).getMacAddress()))
                //{
                //    m_tempSignal = new SignalData();
                //    m_tempSignal.RSSI = signalList[i].Rssi;
                //    m_tempSignal.SignalQuality = (int)signalList[i].SignalQuality;
                //    m_tempSignal.ProjectID = Global.m_activeProject;
                //    m_tempSignal.LocationID = 0;
                //    m_tempSignal.Channel = (int)signalList[i].Channel;
                //    m_tempSignal.Time = signalList[i].ScanDateTime;
                //    m_tempSignal.AccessPointID = m_BMAccessPoint.GetAccessPointData(signalList[i].MacAddress).ID;
                //    m_tempSignalList.Add(m_tempSignal);
                //}
            }
            return m_tempSignalList;
        }
        return null;
    }

    
}