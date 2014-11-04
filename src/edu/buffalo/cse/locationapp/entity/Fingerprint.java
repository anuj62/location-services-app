package edu.buffalo.cse.locationapp.entity;

public class Fingerprint {

	private String macAddress;
	private int level;
	private int frequency;
	private long timestamp;
	private Location location;

	/**
	 * @param location
	 * @param macAddress
	 * @param level
	 * @param frequency
	 * @param timestamp
	 */

	public Fingerprint(Location location, String macAddress, int level, int frequency, long timestamp) {
		this.location = location;
		this.macAddress = macAddress;
		this.level = level;
		this.frequency = frequency;
		this.timestamp = timestamp;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
