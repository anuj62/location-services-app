package edu.buffalo.cse.algorithm.knearestneighbor;

import android.graphics.Bitmap;

public class Map
{
    private int m_mapID;
    private double m_pixelToMeter;
    private String m_description;
    private int m_projectID;
    private Bitmap m_MapImage;
    private int m_height;
    private int m_width;

    public Map(int mapID, double pixelToMeter)
    {
        this.m_mapID = mapID;
        this.m_pixelToMeter = pixelToMeter;
    }

    public Map()
    {

    }

	public int getMapID() {
		return m_mapID;
	}

	public void setMapID(int m_mapID) {
		this.m_mapID = m_mapID;
	}

	public double getPixelToMeter() {
		return m_pixelToMeter;
	}

	public void setPixelToMeter(double m_pixelToMeter) {
		this.m_pixelToMeter = m_pixelToMeter;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String m_description) {
		this.m_description = m_description;
	}

	public int getProjectID() {
		return m_projectID;
	}

	public void setProjectID(int m_projectID) {
		this.m_projectID = m_projectID;
	}

	public Bitmap getMapImage() {
		return m_MapImage;
	}

	public void setMapImage(Bitmap m_MapImage) {
		this.m_MapImage = m_MapImage;
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int m_height) {
		this.m_height = m_height;
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int m_width) {
		this.m_width = m_width;
	}
    
    
}    