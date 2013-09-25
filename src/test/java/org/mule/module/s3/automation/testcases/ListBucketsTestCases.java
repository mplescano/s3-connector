/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.Bucket;

public class ListBucketsTestCases extends S3TestParent {
	
	private List<String> generatedBucketNames = new ArrayList<String>();
	private List<String> retrievedBucketNames = new ArrayList<String>();
	private int bucketAmount;
	
	@Before
	public void setUp(){
		
		String bucketName;
		
		testObjects = (HashMap<String,Object>) context.getBean("listBucketsTestData");
		bucketAmount = Integer.parseInt(testObjects.get("bucketAmount").toString());
		
		try {

			for (int i=1; i<=bucketAmount; i++) {
				
				bucketName = UUID.randomUUID().toString();
				generatedBucketNames.add(bucketName);
				
				testObjects.put("bucketName", bucketName);
				
				MessageProcessor flow = lookupMessageProcessor("create-bucket");
				flow.process(getTestEvent(testObjects));
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
			
	}
	
	@After
	public void tearDown() {
		
		try {
			
			for (int i=0; i<generatedBucketNames.size(); i++) {
				
				testObjects.put("bucketName", generatedBucketNames.get(i).toString());
				
				MessageProcessor flow = lookupMessageProcessor("delete-bucket");
				flow.process(getTestEvent(testObjects));
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
		}
		
	}
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testListBuckets() {
    	 	    	
		try {

			MessageProcessor listBucketsFlow = lookupMessageProcessor("list-buckets");
			MuleEvent response = listBucketsFlow.process(getTestEvent(null));
			
			List<Bucket> buckets = (List<Bucket>) response.getMessage().getPayload();
			
			assertTrue(buckets.size() >= generatedBucketNames.size());
			
			Iterator<Bucket> iterator = buckets.iterator();  
			
			while (iterator.hasNext()) {
				
				Bucket bucket = iterator.next();
				retrievedBucketNames.add(bucket.getName());

			}
			
			assertTrue(retrievedBucketNames.containsAll(generatedBucketNames));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
}