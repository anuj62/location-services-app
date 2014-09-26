package edu.buffalo.cse.locationapp;

import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity implements LocationListener, SensorEventListener, OnTaskCompleted{
LocationManager lm;
Sensor mAccelerometerSensor;
SensorManager sm;
WifiManager wm;
ScheduledScan wifiScan;
OnTaskCompleted listener;

int TIMER_PERIOD = 2000; 

TextView tvGps, tvAccX, tvAccY, tvAccZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGps = (TextView)this.findViewById(R.id.displaytext);
        tvAccX = (TextView)this.findViewById(R.id.xcoor);
        tvAccY = (TextView)this.findViewById(R.id.ycoor);
        tvAccZ = (TextView)this.findViewById(R.id.zcoor);
        
        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, this);
        
        sm = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        mAccelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
        sm.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        
        Handler handler = new Handler();
        wm = (WifiManager)this.getSystemService(WIFI_SERVICE);
        wifiScan = new ScheduledScan(wm, handler, listener);
        handler.postDelayed(wifiScan, wifiScan.getRepeatTime());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.v("LocationApp","Location updated");
		tvGps.setText(location.getLatitude()+", "+location.getLongitude());
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Log.v("LocationApp", "Sensor changed");
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			tvAccX.setText("Acc. in X: " + event.values[0]);
			tvAccY.setText("Acc. in Y: " + event.values[1]);
			tvAccZ.setText("Acc. in Z: " + event.values[2]);
		}
	}
	
	


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTaskCompleted() {
		List<ScanResult> scanResults = wifiScan.getScanResults();
		Log.v("LocationApp", Integer.toString(scanResults.size()) + " should be printed now.");
	}
}
