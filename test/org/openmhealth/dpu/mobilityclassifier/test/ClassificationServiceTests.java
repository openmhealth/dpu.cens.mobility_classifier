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
package org.openmhealth.dpu.mobilityclassifier.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.openmhealth.dpu.mobilityclassifier.domain.ClassifiedPoint;
import org.openmhealth.dpu.mobilityclassifier.domain.SensorDataPoint;
import org.openmhealth.dpu.mobilityclassifier.service.ClassificationService;

import edu.ucla.cens.mobilityclassifier.AccessPoint;
import edu.ucla.cens.mobilityclassifier.Sample;
import edu.ucla.cens.mobilityclassifier.WifiScan;

/**
 * 
 * @author Joshua Selsky
 */
public class ClassificationServiceTests extends TestCase {

	/**
	 * Tests an attempt to classify null.
	 */
	@Test
	public void testClassifyNullList() {
		try {
			ClassificationService.classify(null);
			
			fail("ClassificationService.classify allows a null argument");
		}
		catch(IllegalArgumentException e) {
			// expected behavior
		}
	}
	
	/**
	 * Tests an attempt to classify an empty list
	 */
	@Test
	public void testClassifyEmptyList() {
		List<SensorDataPoint> emptyList = Collections.emptyList();
		List<ClassifiedPoint> emptyClassifiedPointList = ClassificationService.classify(emptyList);
		
		if(emptyClassifiedPointList == null) {
			fail("ClassificationService.classify returned a null instead of an empty list");
		}
		else if(emptyClassifiedPointList.size() > 0) {
			fail("ClassificationService.classify returned a non-empty list");
		}
	}
	
	/**
	 * Tests an attempt to classify a list containing nulls
	 */
	@Test
	public void testClassifyListWithNulls() {
		List<SensorDataPoint> badList = new ArrayList<SensorDataPoint>();
		badList.add(null);
		
		try {
			ClassificationService.classify(badList);
			
			fail("ClassificationService.classify allowed a list containing null items.");
		}
		catch(IllegalArgumentException e) {
			// expected behavior
		}
	}
		
	/**
	 * Tests an attempt to classify a list containing nulls
	 */
	@Test
	public void testSingleAccessPointAndSample() {
		UUID uuid = UUID.randomUUID();
		Double speed = Math.PI;
		DateTimeZone timezone = DateTimeZone.forID("UTC");
		AccessPoint accessPoint = new AccessPoint("test", 6.0D);
		List<AccessPoint> apList = new ArrayList<AccessPoint>();
		apList.add(accessPoint);
		WifiScan wifiScan = new WifiScan(System.currentTimeMillis(), apList);
		Sample sample = new Sample(Math.PI, Math.PI, Math.PI);
		List<Sample> sampleList = new ArrayList<Sample>();
		sampleList.add(sample);
		SensorDataPoint sdp = new SensorDataPoint(uuid, speed, timezone, wifiScan, sampleList);
		List<SensorDataPoint> sensorDataPointList = new ArrayList<SensorDataPoint>();
		sensorDataPointList.add(sdp);
		
		System.out.println(sensorDataPointList.get(0).toString());
		
		List<ClassifiedPoint> classifiedPointList = ClassificationService.classify(sensorDataPointList);
		
		if(classifiedPointList == null || classifiedPointList.isEmpty()) {
			fail("Expected one result and received none.");
		}
		
		if(! classifiedPointList.get(0).getId().equals(sensorDataPointList.get(0).getId())) {
			fail("IDs are out of sync between ClassifiedPoint and SensorDataPoint at index 0");
		}
		
		System.out.println(classifiedPointList.get(0).toString());
	}
}
