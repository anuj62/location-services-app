package edu.buffalo.cse.algorithm.knearestneighbor;

/**
 * Representative of distance
 * @author gkul
 */
public class VectorComponent implements Comparable<VectorComponent>
{
    private int m_ID;
    private double m_Value;

    public VectorComponent(int id, double value)
    {
        this.m_ID = id;
        this.m_Value = value;
    }

    public int getID() {
        return m_ID;
    }
    
    public void setID(int id) {
        m_ID = id;
    }

    public double getValue() {
        return m_Value;
    }
    
    public void setValue(double value) {
    	m_Value = value;
    }

	@Override
	public int compareTo(VectorComponent another) {
		int returnValue = 0;

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
	     return returnValue;
	}
}