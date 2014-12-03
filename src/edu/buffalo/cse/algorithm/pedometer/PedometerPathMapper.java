/*
 * Copyright (C) 2013 The Android Open Source Project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package edu.buffalo.cse.algorithm.pedometer;

import java.util.ArrayList;

import edu.buffalo.cse.locationapp.MapView;
import android.graphics.Matrix;
import android.graphics.PointF;

public class PedometerPathMapper implements PedometerEventListener {
	
	private float mStrideLengthInPixels = 9.25f;
	private MapView map;
	private PedometerPathMapperEventListener ppmel;
	public static float sBoundaryStepSize = 5;

	public PedometerPathMapper(PedometerPathMapperEventListener ppmel, MapView map) {
		this.map = map;
		this.ppmel = ppmel;
	}
	
	private Walk getWalkFromSteps(ArrayList<PedometerEvent> stepList) {
		int steps = stepList.size();
		Walk walk = new Walk(steps);
		walk.stepPoints[0] = 0;
		walk.stepPoints[1] = 0;
		float rotation = stepList.get(0).getRotation();
		float[] newStepPoint = new float[2];
		Matrix rotationMatrix = new Matrix();
		Matrix translationMatrix = new Matrix();
		
		for(int i=0;i<steps;i++) {
			newStepPoint[0] = 0;
			newStepPoint[1] = -mStrideLengthInPixels;
			rotationMatrix.postRotate(rotation);
			rotationMatrix.mapPoints(newStepPoint);
			translationMatrix.mapPoints(newStepPoint);
			walk.path.lineTo(newStepPoint[0], newStepPoint[1]);
			translationMatrix.setTranslate(newStepPoint[0], newStepPoint[1]);
			if(i < steps - 1) {
				rotation = stepList.get(i + 1).getRotation() - stepList.get(i).getRotation();
			}
			walk.stepPoints[2 * i + 2] = newStepPoint[0];
			walk.stepPoints[2 * i + 3] = newStepPoint[1];
		}
		return walk;
	}
	
	ArrayList<PedometerEvent> stepList;

	@Override
	public void onWalkStarted(float directionAccuracy) {
		stepList = new ArrayList<PedometerEvent>();
	}

	Walk walk;
	PointF start = new PointF(285, 807);
	
	public void resetPosition(PointF point) {
		start = point;
	}
	
	public void setStrideLength(float lengthInPixels) {
		mStrideLengthInPixels = lengthInPixels;
	}
	
	Matrix mat = new Matrix();
	
	@Override
	public void onWalkStopped(int totalSteps) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized(this) {
					walk = getWalkFromSteps(stepList);
					mat.setTranslate(start.x, start.y);
					walk.transform(mat);
					int len = walk.stepPoints.length;
					start = new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]);
//					map.drawPath(walk.path, new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
				}
			};
		}).start();
	}
	
	private PointF point;
	private PointF boundaryCenter;
	private double distance;
	
	@Override
	public void onStepOccurred(PedometerEvent pEvent, int stepNo) {
		stepList.add(pEvent);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				synchronized(this) {
					walk = getWalkFromSteps(stepList);
					mat.setTranslate(start.x, start.y);
					mat.mapPoints(walk.stepPoints);
					int len = walk.stepPoints.length;
					point = new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]);
					map.drawLocation(point);
					if(boundaryCenter == null) {
						boundaryCenter = point;
					} else {
						distance += Math.sqrt(Math.pow((point.x - boundaryCenter.x), 2) + Math.pow((point.y - boundaryCenter.y), 2));
						if(distance > sBoundaryStepSize * mStrideLengthInPixels) {
							ppmel.boundaryCrossed();
							boundaryCenter = point;
							distance = 0;
						}
					}
//					map.drawPath(walk.path, new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
				}
			}
		}).start();
	}
}