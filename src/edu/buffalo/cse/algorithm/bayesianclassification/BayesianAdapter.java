package edu.buffalo.cse.algorithm.bayesianclassification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.net.wifi.ScanResult;

import edu.buffalo.cse.algorithm.knearestneighbor.APRSSIPair;
import edu.buffalo.cse.algorithm.knearestneighbor.SignalSample;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;
import edu.buffalo.cse.algorithm.Utils;

/**
 * 
 * All edu.buffalo.cse.algorithm.bayesianclassification is taken from https://github.com/ptnplanet/Java-Naive-Bayes-Classifier/
 * except BayesianAdapter class
 */

public class BayesianAdapter {
	
	private Classifier<APRSSIPair, Location> bayes = null;
	private List<Location> m_Location = null;
    private List<AccessPoint> m_AccessPoint = null;
	
	public BayesianAdapter(List<SignalSample> signalDatabase) {
		bayes = new BayesClassifier<APRSSIPair, Location>();
		
		PrepareClassifiers(signalDatabase);
		
	}
	
	private void PrepareClassifiers(List<SignalSample> signalDatabase) {
		
		m_Location = Utils.CreateLocationList(signalDatabase);
		m_AccessPoint = Utils.CreateAccessPointList(signalDatabase);
		
		for (int i = 0; i < m_Location.size(); i++) {
			bayes.learn(m_Location.get(i), Utils.CreateVectorComponent(m_Location.get(i), m_AccessPoint, signalDatabase));
		}
	}
	
	public Location PositionData(List<ScanResult> signalList) {
		
		return bayes.classify(Utils.CreateVectorComponent(m_AccessPoint, signalList)).getCategory();
	}
	

}
