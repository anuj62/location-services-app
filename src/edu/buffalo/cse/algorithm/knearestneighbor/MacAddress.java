package edu.buffalo.cse.algorithm.knearestneighbor;

public class MacAddress {
	
	private String _macAddress;
	
	public MacAddress(String macAddress) {
		_macAddress = macAddress;
	}
	
	public String toString() {
		return _macAddress;
	}

	public boolean equals(MacAddress another) {
		if (this.toString().equals(another.toString())) {
			return true;
		}
		else 
			return false;
	}
	

}
