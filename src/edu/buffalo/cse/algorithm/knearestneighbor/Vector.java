package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/// <summary>
/// Representative of Location
/// </summary>
public class Vector
{
    private int m_ID;
    private Hashtable m_AccessPointList = null;

    //todo create private BMAccessPoint m_BMAccessPoint = null;
    //todo create private BMSignalStrength m_BMSignalStrength = null;

    public Vector(int locationID)
    {
        m_ID = locationID;
        //todo bring m_BMAccessPoint = BMFactory.BMAccessPoint();
        //todo bring m_BMSignalStrength = BMFactory.BMSignalStrength();

        LoadCurrentComponents();
    }

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

    public int GetLocationID()
    {
        return m_ID;
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
}