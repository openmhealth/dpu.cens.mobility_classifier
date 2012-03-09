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
package org.openmhealth.dpu.mobilityclassifier.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmhealth.dpu.mobilityclassifier.domain.ClassifiedPoint;
import org.openmhealth.dpu.mobilityclassifier.domain.SensorDataPoint;
import org.openmhealth.dpu.mobilityclassifier.service.ClassificationService;

import edu.ucla.cens.mobilityclassifier.AccessPoint;
import edu.ucla.cens.mobilityclassifier.Sample;
import edu.ucla.cens.mobilityclassifier.WifiScan;

/**
 * Example of using the ClassificationService via Java. To run the example:
 * 
 * <ul>
 * <li>cd to the root of this project.</li>
 * <li>Run ant clean dist-jar.</li>
 * <li>Run java -cp dist:lib/joda-time-2.1.jar:lib/json.org-2011-11-03.jar:lib/mobility-classifier-1.2.9.jar:build/classes org.openmhealth.dpu.mobilityclassifier.example.Example</li>
 * </ul>
 * 
 * This example classifies mobility points stored as JSON in ./example.json. It
 * outputs a ClassifiedPoint to System.out for each point in example.json.
 * 
 * @author Joshua Selsky
 */
public class Example {
	
	public static void main(String[] args) throws IOException, JSONException {

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("example.json")));
		String line = in.readLine();
		StringBuilder jsonStringBuilder = new StringBuilder();
		
		while(line != null) {
			jsonStringBuilder.append(line);
			line = in.readLine();
		}
		
		in.close();
		
		JSONArray array = new JSONArray(jsonStringBuilder.toString());
		List<SensorDataPoint> sensorDataPoints = new ArrayList<SensorDataPoint>();
		
		int length = array.length();
		for(int i = 0; i < length; i++) {
			JSONObject object = array.getJSONObject(i);
			
			List<Sample> samples = new ArrayList<Sample>();
			double speed = 0.0D;
			long time = 0;
			DateTimeZone timezone = null;
			List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
			
			JSONArray accelDataArray = object.getJSONArray("accel_data");
			int numberOfAccelPoints = accelDataArray.length();
			
			for(int j = 0; j < numberOfAccelPoints; j++) {
				samples.add(new Sample(
				    accelDataArray.getJSONObject(j).getDouble("x"),
				    accelDataArray.getJSONObject(j).getDouble("y"),
				    accelDataArray.getJSONObject(j).getDouble("z")
				));
			}
			
			speed = object.getDouble("speed");
			JSONObject wifiObject = object.getJSONObject("wifi_data");
			timezone = DateTimeZone.forID(wifiObject.getString("timezone"));
			time = wifiObject.getLong("time");
			JSONArray scanArray = wifiObject.getJSONArray("scan");
			int numberOfScans = scanArray.length();
			
			for(int k = 0; k < numberOfScans; k++) {
				accessPoints.add(new AccessPoint(
					scanArray.getJSONObject(k).getString("ssid"),
					scanArray.getJSONObject(k).getInt("strength")
				));
			}
			
			SensorDataPoint point = new SensorDataPoint(UUID.randomUUID(), speed, timezone, new WifiScan(time, accessPoints), samples);
			sensorDataPoints.add(point);
		}
		
		List<ClassifiedPoint> classifiedPointList = ClassificationService.classify(sensorDataPoints);
		
		for(ClassifiedPoint cp : classifiedPointList) {
			System.out.println(cp);
		}
	}
}
