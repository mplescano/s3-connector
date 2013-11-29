/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.BucketVersioningConfiguration;

public class BucketVersioningConfigurationTestCases extends S3TestParent {
	
	String bucketName;
	
	@Before
	public void setUp(){

		bucketName = UUID.randomUUID().toString();
		
		testObjects = new HashMap<String, Object>();
		testObjects.put("bucketName", bucketName);
    	
		try {

			MessageProcessor flow = lookupMessageProcessor("create-bucket");
			flow.process(getTestEvent(testObjects));
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
			
	}
	
	@After
	public void tearDown() {
		
		try {
				
			MessageProcessor flow = lookupMessageProcessor("delete-bucket-optional-attributes");
			flow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
		}
		
	}
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test    
	public void testBucketVersioningConfigurationTestCases() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("bucketVersioningConfigurationTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {

			MessageProcessor getBucketVersioningConfigurationFlow = lookupMessageProcessor("get-bucket-versioning-configuration");
			MuleEvent getBucketVersioningConfiguration = getBucketVersioningConfigurationFlow.process(getTestEvent(testObjects));
			
			BucketVersioningConfiguration bucketVersioningConfiguration = (BucketVersioningConfiguration) getBucketVersioningConfiguration.getMessage().getPayload();

			assertEquals("OFF", bucketVersioningConfiguration.getStatus().toString().toUpperCase());

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			getBucketVersioningConfiguration = getBucketVersioningConfigurationFlow.process(getTestEvent(testObjects));
			
			bucketVersioningConfiguration = (BucketVersioningConfiguration) getBucketVersioningConfiguration.getMessage().getPayload();

			assertEquals("ENABLED", bucketVersioningConfiguration.getStatus().toString().toUpperCase());
			
			testObjects.put("versioningStatus", "SUSPENDED");
			
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			getBucketVersioningConfiguration = getBucketVersioningConfigurationFlow.process(getTestEvent(testObjects));
			
			bucketVersioningConfiguration = (BucketVersioningConfiguration) getBucketVersioningConfiguration.getMessage().getPayload();

			assertEquals("SUSPENDED", bucketVersioningConfiguration.getStatus().toString().toUpperCase());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}

    
}