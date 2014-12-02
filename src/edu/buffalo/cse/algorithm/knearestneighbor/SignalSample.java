package edu.buffalo.cse.algorithm.knearestneighbor;

import java.util.Date;

/**
 * This is a simple data class that holds some basic
 * data retrieved from the wireless interface
 */
public class SignalSample
{        
    public SignalSample() {}

    /**
     * Creates a new instance.
     * @param macAddress raw mac address byte array
     */
    public SignalSample(String macAddress) {
        _macAddress = new MacAddress(macAddress);
    }
    
    public SignalSample(MacAddress macAddress) {
        _macAddress = macAddress;
    }

    /// <summary>
    /// Gets or sets the network capability information
    /// </summary>
    public String getCapabilities() {
    	return _capabilities;
    }
    
    public void setCapabilities(String capabilities) {
        _capabilities = capabilities;
    }
    
    private String _capabilities;

    /// <summary>
    /// Gets the MAC address associated with this class
    /// </summary>   
    public MacAddress getMacAddress() {
    	return _macAddress;
    }
    
    public void setMacAddress(String macAddress) {
    	_macAddress = new MacAddress(macAddress);
    }
    
    public void setMacAddress(MacAddress macAddress) {
    	_macAddress = macAddress;
    }
    
    private MacAddress _macAddress;

    /// <summary>
    /// Gets or sets the network SSID
    /// </summary>  
    public String getSSID() {
    	return _ssid;
    }
    
    public void setSSID(String Ssid) {
    	_ssid = Ssid;
    }
    
    private String _ssid;

    /// <summary>
    /// Gets or sets the location name tag
    /// </summary>  
    public String getTag() {
    	return _tag;
    }
    
    public void setTag(String tag) {
    	_tag = tag;
    }
    
    private String _tag;

    /// <summary>
    /// Gets or sets the wifi network channel
    /// </summary>
    public int getChannel() {
    	return _channel;
    }
    
    public void setChannel(int channel) {
    	_channel = channel;
    }
    
    private int _channel;
    
  /// <summary>
    /// Gets or sets the wifi network frequency
    /// </summary>
    public int getFrequency() {
    	return _frequency;
    }
    
    public void setFrequency(int frequency) {
    	_frequency = frequency;
    }
    
    private int _frequency;

    /// <summary>
    /// Gets or sets the RSSI (receive signal strength indication).
    /// </summary>
    public int getRSSI() {
    	return _rssi;
    }
    
    public void setRSSI(int rssi) {
    	_rssi = rssi;
    }
    
    private int _rssi;

    /// <summary>
    /// Gets or sets the string representation of the
    /// Network Card Name
    /// </summary>
    public long getScanDateTime() {
    	return _ScanDateTime;
    }
    
    public void setScanDateTime(long timestamp) {
    	_ScanDateTime = timestamp;
    }
    
    private long _ScanDateTime;

    /// <summary>
    /// Gets or sets the string representation of the
    /// Network Card Name
    /// </summary>
    public int getXCoordinate() {
    	return _XCoordinate;
    }
    
    public void setXCoordinate(int xCoordinate) {
    	_XCoordinate = xCoordinate;
    }
    
    private int _XCoordinate;

    /// <summary>
    /// Gets or sets the string representation of the
    /// Network Card Name
    /// </summary>
    public int getYCoordinate() {
    	return _YCoordinate;
    }
    
    public void setYCoordinate(int yCoordinate) {
    	_YCoordinate = yCoordinate;
    }
    
    private int _YCoordinate;

    /// <summary>
    /// Gets or sets the string representation of the
    /// Network Card Name
    /// </summary>
    public int getMapID() {
    	return _MapID;
    }
    
    public void setMapID(int mapID) {
    	_MapID = mapID;
    }
    
    private int _MapID;

    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(this._ssid + " \t " +
                          this._macAddress.toString() + " \t " +
                          this._ScanDateTime + " \t " +
                          this._rssi);
        return result.toString();
    }

}