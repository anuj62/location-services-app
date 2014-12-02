package edu.buffalo.cse.locationapp.entity;

public class AccessPoint
{
    private int m_ID;
    private String m_macAddress;
    private String m_SSID;
    private String m_Description;
    private int m_projectID;
    
	public AccessPoint(String m_macAddress, String m_SSID,
			String m_Description, int m_projectID) {
		this.m_macAddress = m_macAddress;
		this.m_SSID = m_SSID;
		this.m_Description = m_Description;
		this.m_projectID = m_projectID;
	}
	
	public AccessPoint(String m_macAddress, String m_SSID) {
		this.m_macAddress = m_macAddress;
		this.m_SSID = m_SSID;
	}
	
	public int getID() {
		return m_ID;
	}
	public void setID(int m_ID) {
		this.m_ID = m_ID;
	}
	public String getMacAddress() {
		return m_macAddress;
	}
	public void setMacAddress(String m_macAddress) {
		this.m_macAddress = m_macAddress;
	}
	public String getSSID() {
		return m_SSID;
	}
	public void setSSID(String m_SSID) {
		this.m_SSID = m_SSID;
	}
	public String getDescription() {
		return m_Description;
	}
	public void setDescription(String m_Description) {
		this.m_Description = m_Description;
	}
	public int getProjectID() {
		return m_projectID;
	}
	public void setProjectID(int m_projectID) {
		this.m_projectID = m_projectID;
	}

}
