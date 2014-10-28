package edu.buffalo.cse.locationapp.dataaccess;

import android.content.Context;

public class PMFactory {
    private static PMAccessPoint m_PMAccessPoint = null;
    private static PMLocation m_PMLocation = null;
    private static PMMap m_PMMap = null;
    private static PMProject m_PMProject = null;
    private static PMSignalStrength m_PMSignalStrength = null;
    private static PersistencyManager m_PersistencyManager = null;
    
    //TODO implement a method to get Application Context
    private static Context context = null; 
    
    public static PersistencyManager PersistencyManager() {
    	if (m_PersistencyManager == null)
        {
    		m_PersistencyManager = new PersistencyManager(context);
        }

        return m_PersistencyManager;
    }

    public static PMAccessPoint PMAccessPoint()
    {
        if (m_PMAccessPoint == null)
        {
            m_PMAccessPoint = new PMAccessPoint(context);
        }

        return m_PMAccessPoint;
    }

    public static PMLocation PMLocation()
    {
        if (m_PMLocation == null)
        {
            m_PMLocation = new PMLocation();
        }

        return m_PMLocation;
    }

    public static PMMap PMMap()
    {
        if (m_PMMap == null)
        {
            m_PMMap = new PMMap();
        }

        return m_PMMap;
    }

    public static PMProject PMProject()
    {
        if (m_PMProject == null)
        {
            m_PMProject = new PMProject();
        }

        return m_PMProject;
    }

    public static PMSignalStrength PMSignalStrength()
    {
        if (m_PMSignalStrength == null)
        {
            m_PMSignalStrength = new PMSignalStrength();
        }

        return m_PMSignalStrength;
    }


}