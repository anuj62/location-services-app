package constants;

public class Locations {
	public static final String LOCATION_DAVIS = "Davis Hall";
	public static final String LOCATION_TAK = "Tak laboratory";
	public static final String LOCATION_PHONELAB = "Phonelab-Davis 301b";
	public static final String LOCATION_UNKNOWN = "Unknown Location";
	public Locations() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCurrentLocation(String detectedLocation){
		if(detectedLocation == "x")
			return LOCATION_DAVIS;
		else if(detectedLocation == "y")
			return LOCATION_PHONELAB;
		else
			return LOCATION_UNKNOWN;
		
	}

}
