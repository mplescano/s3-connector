/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListObjectsTestCases extends S3TestParent {
	
	private String bucketName;
	private HashMap<String,Object> updatedUserMetadata;
	private List<String> objectsKeyValues;
	
	private HashMap<String,Object> initializeByteArrayTestData() {
		
		HashMap<String,Object> initializationValues = new HashMap<String,Object>();
		initializationValues.putAll((HashMap<String,Object>) context.getBean("listObjectsByteArrayObjectTestData"));
		initializationValues.put("bucketName", bucketName);
		
		byte data[] = bucketName.getBytes();
		initializationValues.put("contentRef", data);
		
		return initializationValues;
		
	}
	
	private HashMap<String,Object> initializeStringTestData() {
		
		HashMap<String,Object> initializationValues = new HashMap<String,Object>();
		initializationValues.putAll((HashMap<String,Object>) context.getBean("listObjectsStringObjectTestData"));
		initializationValues.put("bucketName", bucketName);

		return initializationValues;	
		
	}
	
	private void createObject(HashMap<String, Object> initializationData) {
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(initializationData));
			
			objectsKeyValues.add(initializationData.get("key").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Before
	public void setUp(){

		objectsKeyValues = new ArrayList<String>();
		
		bucketName = UUID.randomUUID().toString();
		
		testObjects = new HashMap<String, Object>();
		testObjects.put("bucketName", bucketName);
		testObjects.putAll((HashMap<String,Object>) context.getBean("listObjectsTestData"));
    	
		try {

			MessageProcessor flow = lookupMessageProcessorConstruct("create-bucket");
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
				
			MessageProcessor flow = lookupMessageProcessorConstruct("delete-bucket-optional-attributes");
			flow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
		}
		
	}
    
    private HashMap<String,Object> initializeFileTestData(File tempFile) {
    	
		HashMap<String,Object> initializationValues = new HashMap<String,Object>();
		initializationValues.putAll((HashMap<String,Object>) context.getBean("listObjectsFileObjectTestData"));
		initializationValues.put("bucketName", bucketName);

		try {
			
			tempFile = tempFile.createTempFile("temp-file-name", ".tmp"); 
			initializationValues.put("contentRef", tempFile);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		return initializationValues;
		
	}
    
    private HashMap<String,Object> initializeInputStreamTestData(InputStream inputStream) {

		HashMap<String,Object> initializationValues = new HashMap<String,Object>();
		initializationValues.putAll((HashMap<String,Object>) context.getBean("listObjectsInputStreamObjectTestData"));
		initializationValues.put("bucketName", bucketName);
		
    	String host = initializationValues.get("host").toString();
    	String path = initializationValues.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	initializationValues.put("contentRef", inputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		return initializationValues;
		
	}

    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testListObjects() {
    	
    	int objectCount = 0;
    	
    	File tempFile = null;
    	InputStream inputStream = null;
    	
		try {
			
	    	createObject(initializeByteArrayTestData());
	    	createObject(initializeFileTestData(tempFile));
	    	createObject(initializeInputStreamTestData(inputStream));
	    	createObject(initializeStringTestData());
	    	
	    	MessageProcessor listObjectsFlow = lookupMessageProcessorConstruct("list-objects");
			MuleEvent listObjectsResponse = listObjectsFlow.process(getTestEvent(testObjects));
			Iterable<S3ObjectSummary> s3ObjectsSummaries = (Iterable<S3ObjectSummary>) listObjectsResponse.getMessage().getPayload();
			
			Iterator<S3ObjectSummary> iterator = s3ObjectsSummaries.iterator();  
			
			while (iterator.hasNext()) {
				
				S3ObjectSummary s3ObjectSummary = iterator.next();
				objectCount++;
				
				assertEquals(bucketName, s3ObjectSummary.getBucketName());
				assertTrue(objectsKeyValues.contains(s3ObjectSummary.getKey()));

			}	
			
			assertEquals(Integer.parseInt("4"), objectCount);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();

		} finally {
			if (tempFile != null) {	tempFile.delete(); }
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}		
		}

	}
    
    @Category({RegressionTests.class})
	@Test
	public void testListObjectsOptionalAttributes() {
    	
    	int objectCount = 0;
    	
    	File tempFile = null;
    	InputStream inputStream = null;
    	
		try {
			
	    	createObject(initializeByteArrayTestData());
	    	createObject(initializeFileTestData(tempFile));
	    	createObject(initializeInputStreamTestData(inputStream));
	    	createObject(initializeStringTestData());
	    	
	    	MessageProcessor listObjectsFlow = lookupMessageProcessorConstruct("list-objects-optional-attributes");
			MuleEvent listObjectsResponse = listObjectsFlow.process(getTestEvent(testObjects));
			Iterable<S3ObjectSummary> s3ObjectsSummaries = (Iterable<S3ObjectSummary>) listObjectsResponse.getMessage().getPayload();
			
			Iterator<S3ObjectSummary> iterator = s3ObjectsSummaries.iterator();  
			
			while (iterator.hasNext()) {
				
				S3ObjectSummary s3ObjectSummary = iterator.next();
				objectCount++;
				
				assertEquals(bucketName, s3ObjectSummary.getBucketName());
				assertTrue(objectsKeyValues.contains(s3ObjectSummary.getKey()));

			}	
			
			assertEquals(Integer.parseInt(testObjects.get("expectedResults").toString()), objectCount);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();

		} finally {
			if (tempFile != null) {	tempFile.delete(); }
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}		
		}

	}
    
}