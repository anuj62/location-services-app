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

import android.graphics.Matrix;
import android.graphics.PointF;

public class PedometerPathMapper implements PedometerEventListener {
	
	private PedometerPathMapperEventListener mEvent;
	private float mStrideLengthInPixels = 9.25f;

	public PedometerPathMapper(PedometerPathMapperEventListener event) {
		mEvent = event;
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
	
	Thread walkStopped = new Thread(new Runnable() {
		
		@Override
		public synchronized void run() {
			walk = getWalkFromSteps(stepList);
			Matrix mat = new Matrix();
			mat.postTranslate(start.x, start.y);
			walk.transform(mat);
			int len = walk.stepPoints.length;
			start = new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]);
//			map.drawPath(walk.path, new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
		};
	});
	
	@Override
	public void onWalkStopped(int totalSteps) {
		walkStopped.start();
	}
	
	Thread stepOccurred = new Thread(new Runnable() {

		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			walk = getWalkFromSteps(stepList);
			Matrix mat = new Matrix();
			mat.postTranslate(start.x, start.y);
			walk.transform(mat);
			int len = walk.stepPoints.length;
			mEvent.getLocation(new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
//			map.drawLocation(new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
//			map.drawPath(walk.path, new PointF(walk.stepPoints[len - 2], walk.stepPoints[len - 1]));
		}
	});
	
	@Override
	public void onStepOccurred(PedometerEvent pEvent, int stepNo) {
		stepList.add(pEvent);
		stepOccurred.start();
	}
}