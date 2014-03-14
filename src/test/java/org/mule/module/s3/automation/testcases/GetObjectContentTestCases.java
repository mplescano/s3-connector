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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.common.io.ByteSource;

public class GetObjectContentTestCases extends S3TestParent {
	
	String bucketName;
	
	private void getObjectContentVerifications(Map<String, Object> testObjects){
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			S3ObjectInputStream expectedObjectContent = s3object.getObjectContent();

			MessageProcessor getObjectContentFlow = lookupMessageProcessor("get-object-content");
			MuleEvent getObjectContentResponse = getObjectContentFlow.process(getTestEvent(testObjects));

			S3ObjectInputStream actualObjectContent = (S3ObjectInputStream) getObjectContentResponse.getMessage().getPayload();

			assertTrue(IOUtils.contentEquals(expectedObjectContent, actualObjectContent));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	private void getObjectContentOptionalAttributesVerifications(Map<String, Object> testObjects, HashMap<String,Object> updatedUserMetadata){
		
		MessageProcessor createObjectFlow;
		MuleEvent createObjectResponse;
		
 		MessageProcessor getObjectOptionalAttributesFlow;
 		MuleEvent getObjectOptionalAttributesResponse;

 		S3ObjectInputStream expectedObjectContent;
 		S3ObjectInputStream actualObjectContent;
 		
 		ByteSource expectedBytes;
 		ByteSource actualBytes;
		
		testObjects.put("versioningStatus", "ENABLED");
		
		try {
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			expectedObjectContent = s3object.getObjectContent();
			expectedBytes = ByteSource.wrap(IOUtils.toByteArray(expectedObjectContent));
		 	ObjectMetadata objectMetadata = s3object.getObjectMetadata();
			
			testObjects.put("modifiedSince", (Date) objectMetadata.getLastModified());
			testObjects.put("unmodifiedSince", (Date) objectMetadata.getLastModified());
			
			// get-object-content-optional-attributes-unmodified-since
			
			getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-content-optional-attributes-unmodified-since");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			actualObjectContent = (S3ObjectInputStream) getObjectOptionalAttributesResponse.getMessage().getPayload();
			actualBytes = ByteSource.wrap(IOUtils.toByteArray(actualObjectContent));
			
	
			assertTrue(expectedBytes.contentEquals(actualBytes));
			
			// update the object
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			// get-object-content-optional-attributes-version-id
			
			getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-content-optional-attributes-version-id");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			actualObjectContent = (S3ObjectInputStream) getObjectOptionalAttributesResponse.getMessage().getPayload();
			actualBytes = ByteSource.wrap(IOUtils.toByteArray(actualObjectContent));
			
			assertTrue(expectedBytes.contentEquals(actualBytes));

			// get-object-content-optional-attributes-modified-since
			
			getObjectOptionalAttributesFlow = lookupMessageProcessor("get-object-content-optional-attributes-modified-since");
			getObjectOptionalAttributesResponse = getObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			actualObjectContent = (S3ObjectInputStream) getObjectOptionalAttributesResponse.getMessage().getPayload();
			actualBytes = ByteSource.wrap(IOUtils.toByteArray(actualObjectContent));
			
			assertTrue(expectedBytes.contentEquals(actualBytes));
			
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
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetInputStreamObjectContent() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectContentTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

	    	getObjectContentVerifications(testObjects);
		
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
	public void testGetByteArrayObjectContent() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectContentTestData"));
    	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
    	getObjectContentVerifications(testObjects);
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetFileObjectContent() {
    	
    	File temp = null;
    	
    	testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectContentTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

	    	getObjectContentVerifications(testObjects);
	    	
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
 	public void testGetStringObjectContent() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectContentTestData"));
 		
 		getObjectContentVerifications(testObjects);
 		
 	}
    
    @Category({RegressionTests.class})
 	@Test
 	public void testGetByteArrayObjectContentOptionalAttributes() {
     	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getByteArrayObjectContentTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getByteArrayObjectContentUpdatedUserMetadata");

     	byte data[] = bucketName.getBytes();
     	testObjects.put("contentRef", data);
     	
     	getObjectContentOptionalAttributesVerifications(testObjects, updatedUserMetadata);
      
 	}



    @Category({RegressionTests.class})
 	@Test
 	public void testGetFileObjectContentOptionalAttributes() {

 	    File temp = null;
 		   
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getFileObjectContentTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getFileObjectContentUpdatedUserMetadata");
 		
 		try {
 			
 			temp = File.createTempFile("temp-file-name", ".tmp"); 
 			
 	    	testObjects.put("contentRef", temp);

 	    	getObjectContentOptionalAttributesVerifications(testObjects, updatedUserMetadata);

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
 	public void testGetInputStreamObjectContentOptionalAttributes() {
     
    	InputStream inputStream = null;
 		
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getInputStreamObjectContentTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getInputStreamObjectContentUpdatedUserMetadata");

     	String host = testObjects.get("host").toString();
     	String path = testObjects.get("path").toString();
     	String urlString = String.format("http://%s/%s",host, path);
     	
 		try {
 			
 			URL url = new URL(urlString);
 	    	URLConnection connection = url.openConnection();
 	    	inputStream = connection.getInputStream();	    
 	    	
 	    	testObjects.put("contentRef", IOUtils.toByteArray(inputStream));

 	    	getObjectContentOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 	
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
	public void testGetStringObjectContentOptionalAttributes() {
  	
 		testObjects.putAll((HashMap<String,Object>) context.getBean("getStringObjectContentTestData"));
 		HashMap<String,Object> updatedUserMetadata = (HashMap<String,Object>) context.getBean("getStringObjectContentUpdatedUserMetadata");
		
 		getObjectContentOptionalAttributesVerifications(testObjects, updatedUserMetadata);
 		
	}
    
}