package edu.buffalo.cse.locationapp;

import java.util.List;
import java.util.Timer;

import constants.Constants;

import edu.buffalo.cse.locationapp.business.BusinessManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Region;
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
import android.os.Message;
import android.text.Editable;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewDebug.FlagToString;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener, OnTaskCompleted {

	private LocationManager lm;
	private WifiManager wm;
	private ScheduledScan wifiScan;
	private OnTaskCompleted listener;

	private SensorManager mSensorManager;
	
	private int TIMER_PERIOD = 2000;
	private boolean isTrainingMode = true;


	private TextView tvGps, tvAccX, tvAccY, tvAccZ, tvWifi;
	private ImageView ivMap;
	private MapView mapMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //inertial sensor code
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		 
        tvGps = (TextView)this.findViewById(R.id.displaytext);
        tvAccX = (TextView)this.findViewById(R.id.xcoor);
        //tvAccY = (TextView)this.findViewById(R.id.ycoor);
        //tvAccZ = (TextView)this.findViewById(R.id.zcoor);
        tvWifi = (TextView)this.findViewById(R.id.wifidata);
        
        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, this);        
        
        wm = (WifiManager)this.getSystemService(WIFI_SERVICE);
        
        
        mapMap = (MapView) this.findViewById(R.id.dummymap);
        mapMap.setOnTouchListener(clickListener);
       
    }
    
    Handler handler = new Handler(){
    	public void handleMessage(Message msg){
    		int messageType = msg.getData().getInt("MessageType");
    		switch (messageType){
    		case Constants.MESSAGE_WIFI:
    			tvWifi.setText(msg.getData().getString("ListSize"));
    			break;
    		case Constants.MESSAGE_INPUTTEXT:
    			Toast.makeText(MainActivity.this, msg.getData().getString(Constants.DATA_INPUTTEXT), Toast.LENGTH_SHORT).show();
    			break;
    			default:
    			
    		}
    		
    	}
    };
    
    private OnTouchListener clickListener = new OnTouchListener() {
    	
		public boolean onTouch(View v, MotionEvent event) {
            if (true) { //mode == training_mode) {
            	if (event.getAction() == MotionEvent.ACTION_DOWN){
            		MapTextInput mapText = new MapTextInput(handler);
            		mapMap.drawCircle(event.getX(), event.getY());
                	handler.post(mapText);
				
            		wifiScan = new ScheduledScan(getApplicationContext(), wm, handler, new edu.buffalo.cse.locationapp.entity.Location(mapText.getInput(), (int)event.getX(), (int)event.getY(), null));
                	handler.postDelayed(wifiScan, wifiScan.getRepeatTime());
                            	
            		Log.i("CSE622:", "Scan Started: " +
            			String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
            	}
            }
            else {
            	// if the ScheduledScan is created without a location, then it means the program is in positioning mode
            	wifiScan = new ScheduledScan(getApplicationContext(), wm, handler);
            	handler.postDelayed(wifiScan, wifiScan.getRepeatTime());
            }
            return true;
            
        }
    	
    };
    
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
        else if(id == R.id.training_mode) {
        	if(item.isChecked()) {
        		isTrainingMode = true;
        	}
        	else {
        		isTrainingMode = false;
        	}
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//we should carry this into another thread
		//Log.v("LocationApp","Location updated");
		//tvGps.setText("Location:"+location.getLatitude()+", "+location.getLongitude());
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	public void onTaskCompleted() {
		List<ScanResult> scanResults = wifiScan.getScanResults();
		Log.v("LocationApp", Integer.toString(scanResults.size()) + " should be printed now.");
	}
	
	private class MapTextInput implements Runnable {
		private Handler handler;
		private String location;
		
		public MapTextInput(Handler aHandler){
			this.handler = aHandler;
		}
		
		public String getInput() {
			return location;
		}
		
		public void setInput(String locationName) {
			this.location = locationName;
		}

		@Override
		public void run() {
			AlertDialog.Builder textInputDialog = new AlertDialog.Builder(MainActivity.this);
            final EditText textInputField = new EditText(MainActivity.this);
            textInputDialog.setTitle("Enter Location Name")
            .setView(textInputField)
            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Bundle locationName = new Bundle();
					locationName.putInt("MessageType", Constants.MESSAGE_INPUTTEXT);
					String locationText = "LOCATION";
					Editable inputText  = textInputField.getText();
					if(inputText == null){
						locationText = "UNKNOWN";
					}else{
						locationText = inputText.toString();
					}
					locationName.putString(Constants.DATA_INPUTTEXT, locationText);
					Message msg = MapTextInput.this.handler.obtainMessage();
					msg.setData(locationName);
					location = locationText;
					MapTextInput.this.handler.sendMessage(msg);
				}
			});
            
            textInputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
            textInputDialog.show();
			
		}
		
	}
}
