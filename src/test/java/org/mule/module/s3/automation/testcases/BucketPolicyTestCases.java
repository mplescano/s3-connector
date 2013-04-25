/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class BucketPolicyTestCases extends S3TestParent {
	
	private String bucketName;
	
	@Before
	public void setUp(){
		
		bucketName = UUID.randomUUID().toString();
		
		testObjects = (HashMap<String,Object>) context.getBean("bucketPolicyTestData");
		testObjects.put("bucketName", bucketName);
    	
		try {

			MessageProcessor flow = lookupFlowConstruct("create-bucket");
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
				
			MessageProcessor flow = lookupFlowConstruct("delete-bucket");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
		}
		
	}
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testSetAndGetBucketPolicy() {
    	
    	testObjects.put("policyText", testObjects.get("policyTemplate").toString().replace("test_bucket_name", bucketName));
        	    	
		try {

			MessageProcessor setBucketPolicyFlow = lookupFlowConstruct("set-bucket-policy");
			setBucketPolicyFlow.process(getTestEvent(testObjects));
			
			MessageProcessor getBucketPolicyFlow = lookupFlowConstruct("get-bucket-policy");
			MuleEvent response = getBucketPolicyFlow.process(getTestEvent(testObjects));
			
			String bucketPolicy = response.getMessage().getPayload().toString();

			assertEquals(testObjects.get("policyText").toString(), bucketPolicy);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testDeleteBucketPolicy() {
    	
    	testObjects.put("policyText", testObjects.get("policyTemplate").toString().replace("test_bucket_name", bucketName));
        	    	
		try {

			MessageProcessor setBucketPolicyFlow = lookupFlowConstruct("set-bucket-policy");
			setBucketPolicyFlow.process(getTestEvent(testObjects));
			
			MessageProcessor deleteBucketPolicyFlow = lookupFlowConstruct("delete-bucket-policy");
			deleteBucketPolicyFlow.process(getTestEvent(testObjects));
			
			MessageProcessor getBucketPolicyFlow = lookupFlowConstruct("get-bucket-policy");
			MuleEvent response = getBucketPolicyFlow.process(getTestEvent(testObjects));
			
			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
}