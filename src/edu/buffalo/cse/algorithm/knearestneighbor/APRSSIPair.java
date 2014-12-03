package edu.buffalo.cse.algorithm.knearestneighbor;

/**
 * Representative of distance
 * @author gkul
 */
public class APRSSIPair implements Comparable<APRSSIPair>
{
    private String m_ID;
    private double m_Value;

    public APRSSIPair(String accessPointMac, int value)
    {
        this.m_ID = accessPointMac;
        this.m_Value = value;
    }
    
    public APRSSIPair(String accessPointMac, double value)
    {
        this.m_ID = accessPointMac;
        this.m_Value = value;
    }

    public String getAPMac() {
        return m_ID;
    }
    
    public void setAPMac(String accessPointMac) {
        m_ID = accessPointMac;
    }

    public double getValue() {
        return m_Value;
    }
    
    public void setValue(double value) {
    	m_Value = value;
    }

	@Override
	public int compareTo(APRSSIPair another) {
		int returnValue = 0;
		/*
	     if (this != null && another == null)
	     {
	        returnValue = 1;
	     }
	     else if (this == null && another != null)
	     {
	        returnValue = -1;
	     }
	     else if (this != null && another != null)
	     {
	        if (this.getValue() == another.getValue())
	        {
	           returnValue = 0;
	        }
	        else
	        {
	        	if (this.getValue() > another.getValue()) {
	        		returnValue = 1;
	        	}
	        	else {
	        		returnValue = -1;
	        	}
	        }
	     }
	     */
		
		returnValue = this.getAPMac().compareTo(another.getAPMac());
	    return returnValue;
	}
}