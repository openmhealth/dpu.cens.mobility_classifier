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

import java.util.UUID;

import edu.ucla.cens.mobilityclassifier.Classification;

/**
 * An immutable classified mobility point. A classification contains a mobility
 * mode of still, walk, run, or drive and a set of features extracted from
 * sensor data.
 * 
 * @author Joshua Selsky
 * @see org.openmhealth.dpu.mobilityclassifier.SensorData
 */
public class ClassifiedPoint {
	private final UUID id;
	private final Classification classification;
	
	/**
	 * Creates a classified mobility point.
	 * 
	 * @param id a UUID identifying this point
	 * 
	 * @param classification a Classification containing the mobility mode 
	 * and classifier output
	 */
	public ClassifiedPoint(final UUID id, final Classification classification) {
		if(id == null) {
			throw new IllegalArgumentException("id is required");
		}
		if(classification == null) {
			throw new IllegalArgumentException("classification is required");
		}
		
		this.id = id;
		this.classification = classification;
	}

	public UUID getId() {
		return id;
	}

	public Classification getClassification() {
		return classification;
	}
}
