/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class SetObjectStorageClassTestCases extends S3TestParent {
	
	private String bucketName;
	
	private void initializeStringTestData() {
		
 		testObjects.putAll((HashMap<String,Object>) context.getBean("setStringObjectStorageClassTestData"));	
	}
	
	private void initializeByteArrayTestData() {
		
		testObjects.putAll((HashMap<String,Object>) context.getBean("setByteArrayObjectStorageClassTestData"));
    	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
		
	}
	
	private void enableVersioning() {
		
		testObjects.put("versioningStatus", "ENABLED");
		
		try {
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private void setReducedRedundancyStorageClass() {
		
		try {
		
			testObjects.put("storageClass", "REDUCED_REDUNDANCY");
			MessageProcessor setObjectStorageClassFlow = lookupMessageProcessor("set-object-storage-class");
			setObjectStorageClassFlow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void setStandardStorageClass() {
		
		try {
		
			testObjects.put("storageClass", "STANDARD");
			MessageProcessor setObjectStorageClassFlow = lookupMessageProcessor("set-object-storage-class");
			setObjectStorageClassFlow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void createObject(boolean versioning) {
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
		
			if (versioning) {

				testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
				
			}
			
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
	public void testSetInputStreamObjectStorageClass() {
    	
    	InputStream inputStream = null;
    	
		try {

	    	initializeInputStreamTestData(inputStream);
	    	createObject(false);
	    	setReducedRedundancyStorageClass();
	    	setStandardStorageClass();
			
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testSetInputStreamObjectStorageClassVersioningEnabled() {
    	
    	InputStream inputStream = null;
    	
		try {

	    	initializeInputStreamTestData(inputStream);
	    	enableVersioning();
	       	createObject(true);
	    	setReducedRedundancyStorageClass();
	    	setStandardStorageClass();
			
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testSetByteArrayObjectStorageClass() {

    	initializeByteArrayTestData();
    	createObject(false);
    	setReducedRedundancyStorageClass();
    	setStandardStorageClass();

	}
    
    @Category({RegressionTests.class})
	@Test
	public void testSetByteArrayObjectStorageClassVersioningEnabled() {

    	initializeByteArrayTestData();
    	enableVersioning();
       	createObject(true);
    	setReducedRedundancyStorageClass();
    	setStandardStorageClass();

	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testSetFileObjectStorageClass() {
    	
    	File tempFile = null;
    	
		try {
			
	    	initializeFileTestData(tempFile);
	    	createObject(false);
	    	setReducedRedundancyStorageClass();
	    	setStandardStorageClass();
     
		} finally {
			if (tempFile != null) {	tempFile.delete(); }
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testSetFileObjectStorageClassVersioningEnabled() {
    	
    	File tempFile = null;
    	
		try {
			
	    	initializeFileTestData(tempFile);
	    	enableVersioning();
	       	createObject(true);
	    	setReducedRedundancyStorageClass();
	    	setStandardStorageClass();
     
		} finally {
			if (tempFile != null) {	tempFile.delete(); }
		}
     
	}
    
    private void initializeFileTestData(File tempFile) {

    	testObjects.putAll((HashMap<String,Object>) context.getBean("setFileObjectStorageClassTestData"));
    	
		try {
			
			tempFile = tempFile.createTempFile("temp-file-name", ".tmp"); 
	    	testObjects.put("contentRef", tempFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (tempFile != null) {	tempFile.delete(); }
			e.printStackTrace();
			fail();
		}
		
	}
    
    private void initializeInputStreamTestData(InputStream inputStream) {

		testObjects.putAll((HashMap<String,Object>) context.getBean("setInputStreamObjectStorageClassTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
			e.printStackTrace();
			fail();
		}
		
	}
    
    @Category({SanityTests.class, RegressionTests.class})
 	@Test
 	public void testSetStringObjectStorageClass() {
     	
 		initializeStringTestData();
    	createObject(false);
    	setReducedRedundancyStorageClass();
    	setStandardStorageClass();;
      
 	}  
    
    @Category({RegressionTests.class})
 	@Test
 	public void testSetStringObjectStorageClassVersioningEnabled() {
     	
 		initializeStringTestData();
    	enableVersioning();
    	createObject(true);
    	setReducedRedundancyStorageClass();
    	setStandardStorageClass();
      
 	}  

}