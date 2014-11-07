package edu.buffalo.cse.locationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MapView extends SurfaceView {

	private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;
    
    int smallCircle = 16;
    int bigCircle = 20;
    
	public MapView(Context context) {
		super(context);
		init();        
	}
	
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public MapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		  surfaceHolder = getHolder();
		  bmpIcon = BitmapFactory.decodeResource(getResources(), 
		    R.drawable.davis01);
		  surfaceHolder.addCallback(new SurfaceHolder.Callback(){

		   @Override
		   public void surfaceCreated(SurfaceHolder holder) {
			   Canvas canvas = surfaceHolder.lockCanvas(null);
			   drawSomething(canvas);
		       holder.unlockCanvasAndPost(canvas);
		   }

		   @Override
		   public void surfaceChanged(SurfaceHolder holder, 
		     int format, int width, int height) {
		    // TODO Auto-generated method stub
		    
		   }

		   @Override
		   public void surfaceDestroyed(SurfaceHolder holder) {
		    // TODO Auto-generated method stub
		    
		   }});
	}
	
	protected void drawSomething(Canvas canvas) {
		canvas.drawBitmap(Bitmap.createScaledBitmap(bmpIcon, canvas.getWidth(), canvas.getHeight(), true), 0, 0, null);
	 }

	public void drawCircle(float x, float y) {
		// TODO Auto-generated method stub
		
		Canvas canvas = surfaceHolder.lockCanvas(null);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bmpIcon, canvas.getWidth(), canvas.getHeight(), true), 0, 0, null);
    	Paint paint1 = new Paint();
        paint1.setColor(Color.rgb(140, 0, 26));
        paint1.setStyle(Style.FILL);
        canvas.drawCircle(x, y, smallCircle, paint1);
        Paint paint2 = new Paint();
        paint2.setColor(Color.rgb(140, 0, 26));
        paint2.setStyle(Style.STROKE);
        canvas.drawCircle(x, y, bigCircle, paint2);
	    surfaceHolder.unlockCanvasAndPost(canvas);
		
	}
}
