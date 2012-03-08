/*******************************************************************************
 * Copyright 2012 The Regents of the University of California
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.openmhealth.dpu.mobilityclassifier.service;

import java.util.ArrayList;
import java.util.List;

import org.openmhealth.dpu.mobilityclassifier.domain.ClassifiedPoint;
import org.openmhealth.dpu.mobilityclassifier.domain.SensorDataPoint;

import edu.ucla.cens.mobilityclassifier.Classification;
import edu.ucla.cens.mobilityclassifier.MobilityClassifier;
import edu.ucla.cens.mobilityclassifier.WifiScan;

/**
 * Singleton mobility classification service.
 * 
 * @author Joshua Selsky
 */
public class ClassificationService {
	
	/**
	 * Prevent direct instantiation as this class has no instance state.
	 */
	private ClassificationService() {
		
	}
	
	/**
	 * Iterates through the provided list and classifies the mobility mode for 
	 * each list item.
	 * 
	 * @param sensorDataPoints the data points to classify
	 * 
	 * @return a list of ClassifiedPoints. The list will be in the same order
	 * as the provided list.
	 * 
	 * @throws IllegalArgumentException if the provided list is null 
	 */
	public static List<ClassifiedPoint> classify(List<SensorDataPoint> sensorDataPoints) {
		if(sensorDataPoints == null) {
			throw new IllegalArgumentException("sensorDataPoints is required");
		}
		
		List<ClassifiedPoint> classifiedPoints = new ArrayList<ClassifiedPoint>();

		// This could be an instance variable because it is stateless
		MobilityClassifier mobilityClassifier = new MobilityClassifier();
		
		WifiScan previousWifiScan = null;
		String previousMode = null;
		
		for(SensorDataPoint sensorDataPoint : sensorDataPoints) {
			Classification classification = mobilityClassifier.classify(sensorDataPoint.getAccelerometerSamples(),
					                                                    sensorDataPoint.getSpeed(),
					                                                    sensorDataPoint.getWifiScan(),
					                                                    previousWifiScan,
					                                                    previousMode);
			
			previousWifiScan = sensorDataPoint.getWifiScan();
			previousMode = classification.getMode();
			
			classifiedPoints.add(new ClassifiedPoint(sensorDataPoint.getId(), classification));
		}
		
		return classifiedPoints;
	}
}
