/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;

public class BucketWebsiteConfigurationTestCases extends S3TestParent {
	
	private String bucketName;
	
	@Before
	public void setUp(){
		
		bucketName = UUID.randomUUID().toString();
		
		testObjects = (HashMap<String,Object>) context.getBean("bucketWebsiteConfigurationTestData");
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
	public void testSetAndGetBucketWebsiteConfiguration() {
    	
    	MessageProcessor setBucketWebsiteConfigurationFlow = lookupFlowConstruct("set-bucket-website-configuration");
      	
		try {
	
			setBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			MessageProcessor getBucketWebsiteConfigurationFlow = lookupFlowConstruct("get-bucket-website-configuration");
			MuleEvent response = getBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			BucketWebsiteConfiguration bucketWebsiteConfiguration = (BucketWebsiteConfiguration) response.getMessage().getPayload();
			
			assertEquals(testObjects.get("suffix").toString(), bucketWebsiteConfiguration.getIndexDocumentSuffix());
			assertNull(bucketWebsiteConfiguration.getErrorDocument());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testSetAndGetBucketWebsiteConfigurationOptionalAttributes() {
    	
    	MessageProcessor setBucketWebsiteConfigurationFlow = lookupFlowConstruct("set-bucket-website-configuration-optional-attributes");
      	
		try {
	
			setBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			MessageProcessor getBucketWebsiteConfigurationFlow = lookupFlowConstruct("get-bucket-website-configuration");
			MuleEvent response = getBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			BucketWebsiteConfiguration bucketWebsiteConfiguration = (BucketWebsiteConfiguration) response.getMessage().getPayload();
			
			assertEquals(testObjects.get("suffix").toString(), bucketWebsiteConfiguration.getIndexDocumentSuffix());
			assertEquals(testObjects.get("errorDocument").toString(), bucketWebsiteConfiguration.getErrorDocument());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testDeleteBucketWebsiteConfiguration() {
    	
    	MessageProcessor setBucketWebsiteConfigurationFlow = lookupFlowConstruct("set-bucket-website-configuration");
      	
		try {
	
			setBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			MessageProcessor deleteBucketWebsiteConfigurationFlow = lookupFlowConstruct("delete-bucket-website-configuration");
			deleteBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			MessageProcessor getBucketWebsiteConfigurationFlow = lookupFlowConstruct("get-bucket-website-configuration");
			MuleEvent response = getBucketWebsiteConfigurationFlow.process(getTestEvent(testObjects));
			
			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
}