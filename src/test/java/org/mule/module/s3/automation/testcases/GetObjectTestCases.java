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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class GetObjectTestCases extends S3TestParent {
	
	String bucketName;
	
	private void getObjectVerifications(Map<String, Object> testObjects) {
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent response = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) response.getMessage().getPayload();
			ObjectMetadata objectMetadata = s3object.getObjectMetadata();
					
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("key").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("userMetadata")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void getObjectOptionalAttributesVerifications(Map<String, Object> testObjects, HashMap<String,Object> updatedUserMetadata){
		
 		MessageProcessor getObjectOptionalAttributesFlow;
 		MuleEvent getObjectOptionalAttributesResponse;
 		
	 	S3Object s3object;
	 	ObjectMetadata objectMetadata;
		
		testObjects.put("versioningStatus", "ENABLED");
		
		try {
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			HashMap<String,Object> firstVersionMetadata = (HashMap<String,Object>) testObjects.get("userMetadata");
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			testObjects.put("modifiedSince", (Date) objectMetadata.getLastModified());
			testObjects.put("unmodifiedSince", (Date) objectMetadata.getLastModified());
			
			// get-object-optional-attributes-unmodified-since
			
			getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-optional-attributes-unmodified-since");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectOptionalAttributesResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("key").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("userMetadata")));
			
			// update the object
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			// get-object-optional-attributes-version-id
			
				getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-optional-attributes-version-id");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectOptionalAttributesResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("key").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(firstVersionMetadata));

			// get-object-optional-attributes-modified-since
			
			getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-optional-attributes-modified-since");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectOptionalAttributesResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("key").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("userMetadata")));		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
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
	
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testGetInputStreamObject() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

	    	getObjectVerifications(testObjects);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testGetByteArrayObject() {

		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectTestData"));
    	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
    	getObjectVerifications(testObjects);
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testGetFileObject() {
    	
    	File temp = null;
    	
    	testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

	    	getObjectVerifications(testObjects);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
 	@Test
 	public void testGetStringObject() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectContentTestData"));
 		
 		getObjectVerifications(testObjects);
      
 	}
    
    @Category({RegressionTests.class})
 	@Test
 	public void testGetByteArrayObjectOptionalAttributes() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getByteArrayObjectUpdatedUserMetadata");

     	byte data[] = bucketName.getBytes();
     	testObjects.put("contentRef", data);
     	
     	getObjectOptionalAttributesVerifications(testObjects, updatedUserMetadata);
      
 	}

    @Category({RegressionTests.class})
 	@Test
 	public void testGetFileObjectOptionalAttributes() {

 	    File temp = null;
	   
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getFileObjectUpdatedUserMetadata");
 		
 		try {
 			
 			temp = File.createTempFile("temp-file-name", ".tmp"); 
 			
 	    	testObjects.put("contentRef", temp);

 	    	getObjectOptionalAttributesVerifications(testObjects, updatedUserMetadata);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
      
 	}

    @Category({RegressionTests.class})
 	@Test
 	public void testGetInputStreamObjectOptionalAttributes() {
     
 		InputStream inputStream = null;
 		
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getInputStreamObjectUpdatedUserMetadata");

     	String host = testObjects.get("host").toString();
     	String path = testObjects.get("path").toString();
     	String urlString = String.format("http://%s/%s",host, path);
     	
 		try {
 			
 			URL url = new URL(urlString);
 	    	URLConnection connection = url.openConnection();
 	    	inputStream = connection.getInputStream();	    
 	    	
 	    	testObjects.put("contentRef", inputStream);

 	    	getObjectOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
      
 	}
 
    @Category({RegressionTests.class})
	@Test
	public void testGetStringObjectOptionalAttributes() {
  	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getStringObjectUpdatedUserMetadata");
		
 		getObjectOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 		
	}
    
}