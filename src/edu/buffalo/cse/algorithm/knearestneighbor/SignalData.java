package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.Calendar;
import java.util.Date;

public class SignalData
{
    public SignalData()
    {
        m_rssi = 0;
        m_signalQuality = 0;
        m_locationID = 0;
        m_accessPointID = 0;
        m_channel = 0;
        m_dateTime = Calendar.getInstance().getTime();
    }

    private int m_rssi;
    private int m_signalQuality;
    private int m_locationID;
    private int m_accessPointID;
    private int m_channel;
    private Date m_dateTime;
    private int m_projectID;
    
	public int getRSSI() {
		return m_rssi;
	}
	public void setRSSI(int m_rssi) {
		this.m_rssi = m_rssi;
	}
	public int getSignalQuality() {
		return m_signalQuality;
	}
	public void setSignalQuality(int m_signalQuality) {
		this.m_signalQuality = m_signalQuality;
	}
	public int getLocationID() {
		return m_locationID;
	}
	public void setLocationID(int m_locationID) {
		this.m_locationID = m_locationID;
	}
	public int getAccessPointID() {
		return m_accessPointID;
	}
	public void setAccessPointID(int m_accessPointID) {
		this.m_accessPointID = m_accessPointID;
	}
	public int getChannel() {
		return m_channel;
	}
	public void setChannel(int m_channel) {
		this.m_channel = m_channel;
	}
	public Date getDateTime() {
		return m_dateTime;
	}
	public void setDateTime(Date m_dateTime) {
		this.m_dateTime = m_dateTime;
	}
	public int getProjectID() {
		return m_projectID;
	}
	public void setProjectID(int m_projectID) {
		this.m_projectID = m_projectID;
	}    
}