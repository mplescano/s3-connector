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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class SetBucketVersioningStatusTestCases extends S3TestParent {
	
	String bucketName;
	
	@Before
	public void setUp(){

		bucketName = UUID.randomUUID().toString();
		
		testObjects = new HashMap<String, Object>();
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
				
			MessageProcessor flow = lookupFlowConstruct("delete-bucket-optional-attributes");
			flow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
		}
		
	}
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test    
	public void testSetBucketVersioningStatus() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("setBucketVersioningStatusObjectTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
			
			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}

    
}