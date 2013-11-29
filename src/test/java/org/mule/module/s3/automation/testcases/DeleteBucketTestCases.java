/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.Bucket;

public class DeleteBucketTestCases extends S3TestParent {
	
	String bucketName;
	
	@Before
	public void setUp(){
		
		bucketName = UUID.randomUUID().toString();
		
		testObjects = (HashMap<String,Object>) context.getBean("deleteBucketTestData");
		testObjects.put("bucketName", bucketName);
    	
		MessageProcessor flow = lookupMessageProcessor("create-bucket");
    	
		try {

			MuleEvent response = flow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	
	}
	
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testDeleteBucket() {
    	
    	boolean bucketNotFound = true;
    	
		try {

			MessageProcessor deleteBucketFlow = lookupMessageProcessor("delete-bucket");
			deleteBucketFlow.process(getTestEvent(testObjects));
			
			MessageProcessor listBucketsFlow = lookupMessageProcessor("list-buckets");
			MuleEvent response = listBucketsFlow.process(getTestEvent(null));
			
			List<Bucket> buckets = (List<Bucket>) response.getMessage().getPayload();
			Iterator<Bucket> iterator = buckets.iterator();  
			
			while (iterator.hasNext() && !bucketNotFound) {
				
				Bucket bucket = iterator.next();
				if (bucket.getName().equals(bucketName)) {
					bucketNotFound = false;		
				}

			}
			
			assertTrue(bucketNotFound);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testDeleteBucketOptionalAttributes() {
    	
    	boolean bucketNotFound = true;
    	
    	HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("deleteBucketUpdatedUserMetadata");
	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		testObjects.put("versioningStatus", "ENABLED");
    	
		try {
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor deleteBucketFlow = lookupMessageProcessor("delete-bucket-optional-attributes");
			deleteBucketFlow.process(getTestEvent(testObjects));
			
			MessageProcessor listBucketsFlow = lookupMessageProcessor("list-buckets");
			MuleEvent listBucketsResponse = listBucketsFlow.process(getTestEvent(null));
			
			List<Bucket> buckets = (List<Bucket>) listBucketsResponse.getMessage().getPayload();
			Iterator<Bucket> iterator = buckets.iterator();  
			
			while (iterator.hasNext() && !bucketNotFound) {
				
				Bucket bucket = iterator.next();
				if (bucket.getName().equals(bucketName)) {
					bucketNotFound = false;		
				}

			}
			
			assertTrue(bucketNotFound);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
}