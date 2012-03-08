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

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Tests the mobility classification DPU. 
 * 
 * @author Joshua Selsky
 */
public class MobilityClassifierTestSuite {
	/**
	 * Sets up the system and then runs each of the tests.
	 * 
	 * @return A TestSuite that contains all of the sub-TestSuites.
	 */
	public static Test suite() {
//		// Point log4j at System.out
//		BasicConfigurator.configure();
		
		TestSuite suite = new TestSuite(MobilityClassifierTestSuite.class.getName());
		suite.addTestSuite(ClassificationServiceTests.class);
		return suite;
	}
}
