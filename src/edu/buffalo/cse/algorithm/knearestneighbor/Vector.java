package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.buffalo.cse.locationapp.entity.AccessPoint;

/// <summary>
/// Representative of Location
/// </summary>
public class Vector
{
    private int m_ID;
    private List<APRSSIPair> m_apSignalStrengthList = null;

    public Vector(int locationID, List<APRSSIPair> apSignalStrengthList)
    {
        m_ID = locationID;
        m_apSignalStrengthList = apSignalStrengthList;
    }
    
    public int GetLocationID()
    {
        return m_ID;
    }
    
    public List<APRSSIPair> GetSignalStrengthList()
    {
        return m_apSignalStrengthList;
    }
    
    //public Vector(int locationID)
    //{
    //    m_ID = locationID;

        //LoadCurrentComponents();
    //}

    /*
    public void LoadCurrentComponents()
    {
        //Retrieves all access points in the current project
        List<AccessPoint> tempAPList = new ArrayList<AccessPoint>();// todo add a m_BMAccessPoint.GetAllAccessPoints();

        if (tempAPList != null && tempAPList.size() > 0)
        {
            m_AccessPointList = new Hashtable();

            for (int i = 0; i < tempAPList.size(); i++)
            {
            	//todo if in positioning mode
                m_AccessPointList.put(tempAPList.get(i).getID(), this.FindSignalStrength(tempAPList.get(i).getID()));
            }
        }
    }

    

    public Hashtable GetSignalDataList()
    {
        return m_AccessPointList;
    }

    private double FindSignalStrength(int accesspointID)
    {
        List<SignalData> tempSignalData = new ArrayList<SignalData>(); //todo add m_BMSignalStrength.GetAllSignalData(m_ID, accesspointID);

        if (tempSignalData != null && tempSignalData.size() > 0)
        {
            int average = 0;
            for (int i = 0; i < tempSignalData.size(); i++)
            {
                average += tempSignalData.get(i).getRSSI();
            }
            return average / tempSignalData.size();
        }
        else
            return -100; //todo assumed -100 no signal
    }
    */
}