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

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class Pedometer implements SensorEventListener {

	private PedometerEventListener mPedometerEvent;
	private SensorManager mSensorManager;
	private Sensor mStepDetector, mRotVectGyro;
	
	private final int mLagSamples = 100;
	private final long mResetTimePeriod = 10000000000l;
	private final long mOneStepWalkResetTime = 3000000000l;
	
	private int mSteps;
	private boolean mIsWalking;
	private float mAccuracy;
	private double mX, mY, mZ, mW;
	private long mLastStepTime, mMaxPeriod;
	
	private Yaw mGyroYaws[] = new Yaw[mLagSamples];
	private int mGyroCount;
	private long mClosestGyro;
	private float mGyroYaw;
	private float mFirstGyroVal;
	private long mLastResetTime;
	private float mLastResetAngle;
	
	public Pedometer(SensorManager sm, PedometerEventListener pel) {
		mSensorManager = sm;
		mPedometerEvent = pel;
		mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		mRotVectGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		for(int i=0;i<mLagSamples;i++) {
			mGyroYaws[i] = new Yaw();
		}
	}
	
	public boolean start() {
		mSteps = 0;
		mIsWalking = false;
		mAccuracy = 0;
		mMaxPeriod = 0;
		mFirstGyroVal = 0;
		return  mSensorManager.registerListener(this, mRotVectGyro, SensorManager.SENSOR_DELAY_GAME) &&
				mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void stop() {
		mSensorManager.unregisterListener(this, mRotVectGyro);
	}
	
	public void resetAngle() {
		stop();
		mLastResetAngle = 0;
		start();
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
			mSteps++;
			if((mLastStepTime != 0) && (event.timestamp - mLastStepTime > mMaxPeriod)) {
				mMaxPeriod = event.timestamp - mLastStepTime;
			}
			mLastStepTime = event.timestamp;
			mClosestGyro = Math.abs(event.timestamp - mGyroYaws[0].timestamp);
			mGyroYaw = mGyroYaws[0].yaw;
			// get rotation when the step actually occurred
			for(int i=1;i<mLagSamples;i++) {
				if(Math.abs(event.timestamp - mGyroYaws[i].timestamp) < mClosestGyro) {
					mClosestGyro = Math.abs(event.timestamp - mGyroYaws[i].timestamp);
					mGyroYaw = mGyroYaws[i].yaw;
				}
			}
			if(mSteps == 1) {
				mIsWalking = true;
				mPedometerEvent.onWalkStarted(mAccuracy);
			}
			mPedometerEvent.onStepOccurred(new PedometerEvent(event.timestamp, mGyroYaw), mSteps);
		} else if(event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
			setYaw(event);
			if(mFirstGyroVal == 0) {
				mFirstGyroVal = mGyroYaws[(mGyroCount + (mLagSamples - 1)) % mLagSamples].yaw - mLastResetAngle;
				mGyroYaws[(mGyroCount + (mLagSamples - 1)) % mLagSamples].yaw = mLastResetAngle;
				mLastResetTime = event.timestamp;
			}
			resetGyroIfTimeUp(event);
		}
	}
	
	private void resetGyroIfTimeUp(SensorEvent event) {
		// Walking is assumed to stop when no step is detected for
		// three periods of the maximum step interval
		if(mIsWalking && (((mMaxPeriod != 0) && ((event.timestamp - mLastStepTime) > (3 * mMaxPeriod))) || ((event.timestamp - mLastStepTime) > mOneStepWalkResetTime))) {
			mPedometerEvent.onWalkStopped(mSteps);
			mSteps = 0;
			mIsWalking = false;
			mAccuracy = 0;
			mMaxPeriod = 0;
		}
		// Reset game_rotation_vector every 20 seconds
		// the person is idle/not walking
		if((!mIsWalking) && (event.timestamp - mLastResetTime > mResetTimePeriod)) {
			int j = 0;
			float min = 361, max = 0;
			for(int i=1;i<=50;i++) {
				j = (mLagSamples + mGyroCount - i) % mLagSamples;
				if(mGyroYaws[j].yaw < min) {
					min = mGyroYaws[j].yaw;
				}
				if(mGyroYaws[j].yaw > max) {
					max = mGyroYaws[j].yaw;
				}
			}
			if(max - min <= 0.5f) {
				mLastResetAngle = mGyroYaws[(mGyroCount + (mLagSamples - 1)) % mLagSamples].yaw;
				stop();
				start();
			}
		}
	}
	
	private void setYaw(SensorEvent event) {
		mX = event.values[0];
		mY = event.values[1];
		mZ = event.values[2];
		mW = event.values[3];
		mGyroYaws[mGyroCount].timestamp = event.timestamp;
		mGyroYaws[mGyroCount].yaw = (float)(((Math.atan2(2*mZ*mW + 2*mY*mX, 1 - 2*mY*mY - 2*mZ*mZ) / Math.PI * 180) + 360) % 360);
		mGyroYaws[mGyroCount].yaw = (720 - mGyroYaws[mGyroCount].yaw - mFirstGyroVal) % 360;
		mGyroCount++;
		mGyroCount %= mLagSamples;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Nothing TODO here
	}
	
	private class Yaw {
		private long timestamp;
		private float yaw;
	}
}