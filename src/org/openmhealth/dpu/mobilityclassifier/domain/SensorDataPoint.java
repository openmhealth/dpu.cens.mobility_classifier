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
package org.openmhealth.dpu.mobilityclassifier.domain;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTimeZone;

import edu.ucla.cens.mobilityclassifier.Sample;
import edu.ucla.cens.mobilityclassifier.WifiScan;

/**
 * Immutable wrapper for sensor data.
 * 
 * @author Joshua Selsky
 */
public class SensorDataPoint {
	private final UUID id;
	private final Double speed;
	private final DateTimeZone timezone;
	private final WifiScan wifiScan;
	private final List<Sample> accelerometerSamples;
	
	/**
	 * Creates an immutable representation of sensor data at a particular 
	 * instance in time.
	 * 
	 * @param id a UUID identifying this instance
	 * 
	 * @param speed a speed at which a device was moving
	 * 
	 * @param timezone a timezone a device was running in
	 *  
	 * @param wifiScan a scan of wifi access points and the time at which the 
	 * points were collected
	 * 
	 * @param accelerometerSamples a list of triaxial accelerometer points
	 * 
	 * @throws IllegalArgumentException if any of the provided parameters are 
	 * null 
	 */
	public SensorDataPoint(final UUID id, final Double speed, 
			final DateTimeZone timezone, 
			final WifiScan wifiScan,
			final List<Sample> accelerometerSamples) {
		
		if(id == null) {
			throw new IllegalArgumentException("id is required");
		}
		if(speed == null) {
			throw new IllegalArgumentException("speed is required");
		}
		if(timezone == null) {
			throw new IllegalArgumentException("timezone is required");
		}
		if(wifiScan == null) {
			throw new IllegalArgumentException("wifiScan is required");
		}
		if(accelerometerSamples == null) {
			throw new IllegalArgumentException("accelerometerSamples is required");
		}
		
		this.id = id;
		this.speed = speed;
		this.timezone = timezone;
		this.wifiScan = wifiScan;
		this.accelerometerSamples = accelerometerSamples;
	}

	public UUID getId() {
		return id;
	}

	public Double getSpeed() {
		return speed;
	}

	public DateTimeZone getTimezone() {
		return timezone;
	}

	public WifiScan getWifiScan() {
		return wifiScan;
	}

	public List<Sample> getAccelerometerSamples() {
		return accelerometerSamples;
	}
}
