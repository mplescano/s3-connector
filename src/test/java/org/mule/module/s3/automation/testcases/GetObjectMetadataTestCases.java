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

public class GetObjectMetadataTestCases extends S3TestParent {
	
	String bucketName;
	
	private void getObjectMetadataVerifications(Map<String, Object> testObjects) {
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor getObjectFlow = lookupMessageProcessorConstruct("get-object-metadata");
			MuleEvent response = getObjectFlow.process(getTestEvent(testObjects));
			
			ObjectMetadata objectMetadata = (ObjectMetadata) response.getMessage().getPayload();

			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("userMetadata")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void getObjectMetadataOptionalAttributesVerifications(Map<String, Object> testObjects, HashMap<String,Object> updatedUserMetadata){
		
 		MessageProcessor getObjectOptionalAttributesFlow;
 		MuleEvent getObjectOptionalAttributesResponse;
		
		testObjects.put("versioningStatus", "ENABLED");
		
		try {
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessorConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-from-message");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			HashMap<String,Object> firstVersionMetadata = (HashMap<String,Object>) testObjects.get("userMetadata");
			
			// update the object
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			// get-object-optional-attributes-version-id
			
			getObjectOptionalAttributesFlow = lookupMessageProcessorConstruct("get-object-metadata-optional-attributes-version-id");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			ObjectMetadata objectMetadata = (ObjectMetadata) getObjectOptionalAttributesResponse.getMessage().getPayload();
			
			assertTrue(objectMetadata.getUserMetadata().equals(firstVersionMetadata));
	
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
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetInputStreamObjectMetadata() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectMetadataTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

	    	getObjectMetadataVerifications(testObjects);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetByteArrayObjectMetadata() {

		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectMetadataTestData"));
    	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
    	getObjectMetadataVerifications(testObjects);
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetFileObjectMetadata() {
    	
    	File temp = null;
    	
    	testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectMetadataTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

	    	getObjectMetadataVerifications(testObjects);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
 	@Test
 	public void testGetStringObjectMetadata() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectMetadataTestData"));
 		
 		getObjectMetadataVerifications(testObjects);
      
 	}
    
    @Category({RegressionTests.class})
 	@Test
 	public void testGetByteArrayObjectMetadataOptionalAttributes() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectMetadataTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getByteArrayObjectMetadataUpdatedUserMetadata");

     	byte data[] = bucketName.getBytes();
     	testObjects.put("contentRef", data);
     	
     	getObjectMetadataOptionalAttributesVerifications(testObjects, updatedUserMetadata);
      
 	}

    @Category({RegressionTests.class})
 	@Test
 	public void testGetFileObjectMetadataOptionalAttributes() {

 	    File temp = null;
	   
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectMetadataTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getFileObjectMetadataUpdatedUserMetadata");
 		
 		try {
 			
 			temp = File.createTempFile("temp-file-name", ".tmp"); 
 			
 	    	testObjects.put("contentRef", temp);

 	    	getObjectMetadataOptionalAttributesVerifications(testObjects, updatedUserMetadata);

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
 	public void testGetInputStreamObjectMetadataOptionalAttributes() {
     
 		InputStream inputStream = null;
 		
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectMetadataTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getInputStreamObjectMetadataUpdatedUserMetadata");

     	String host = testObjects.get("host").toString();
     	String path = testObjects.get("path").toString();
     	String urlString = String.format("http://%s/%s",host, path);
     	
 		try {
 			
 			URL url = new URL(urlString);
 	    	URLConnection connection = url.openConnection();
 	    	inputStream = connection.getInputStream();	    
 	    	
 	    	testObjects.put("contentRef", inputStream);

 	    	getObjectMetadataOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 	    	
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
	public void testGetStringObjectMetadataOptionalAttributes() {
  	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectMetadataTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getStringObjectUpdatedMetadataUserMetadata");
		
 		getObjectMetadataOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 		
	}
    
}