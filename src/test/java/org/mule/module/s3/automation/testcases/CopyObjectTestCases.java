/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class CopyObjectTestCases extends S3TestParent {
	
	private String bucketName;
	private HashMap<String,Object> updatedUserMetadata;
	
	private void copyObjectChildElementsNoneGetVerifications() {
		
		try {
			
			testObjects.put("key", testObjects.get("destinationKey").toString());
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			ObjectMetadata objectMetadata = s3object.getObjectMetadata();
					
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("destinationKey").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("userMetadata")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void initializeStringTestData() {
		
 		testObjects.putAll((HashMap<String,Object>) context.getBean("copyStringObjectTestData"));	
	}
	
	private void initializeByteArrayTestData() {
		
		testObjects.putAll((HashMap<String,Object>) context.getBean("copyByteArrayObjectTestData"));
    	
    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
		
	}
	
	private void copyObjectVersioningDisabled(String flowName) {
		
		try {
		
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
	
			String sourceKey = testObjects.get("key").toString();
			String destinationKey = sourceKey + "Copy";
			
			testObjects.put("sourceKey", sourceKey);
			testObjects.put("destinationKey", destinationKey);
			
			MessageProcessor copyObjectFlow = lookupMessageProcessor(flowName);
			MuleEvent copyObjectResponse = copyObjectFlow.process(getTestEvent(testObjects));
	
			assertTrue(copyObjectResponse.getMessage().getPayload().toString().equals("{NullPayload}"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void createObject(boolean versioning) {
		
		try {
		
			if (versioning) {
				
				testObjects.put("versioningStatus", "ENABLED");
				
				MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessor("set-bucket-versioning-status");
				setBucketVersioningStatusFlow.process(getTestEvent(testObjects));
				
				MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
				MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
				
				testObjects.put("sourceVersionId", (String) createObjectResponse.getMessage().getPayload());
				
			} else {
				
				MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
				MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
				
			}	
			
			String sourceKey = testObjects.get("key").toString();
			testObjects.put("sourceKey", sourceKey);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void copyObjectChildElementsCreateObjectManuallyGetVerifications() {
		
		HashMap<String,Object> destinationUserMetadata = new HashMap<String,Object>();
		destinationUserMetadata.put("usermetadatakey", "destinationUserMetadataValue");
		
		try {
			
			testObjects.put("key", testObjects.get("destinationKey").toString());
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			ObjectMetadata objectMetadata = s3object.getObjectMetadata();
					
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("destinationKey").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(destinationUserMetadata));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void copyObjectChildElementsFromMessageGetVerifications() {
		
		try {

			testObjects.put("key", testObjects.get("destinationKey").toString());
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			S3Object s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			ObjectMetadata objectMetadata = s3object.getObjectMetadata();
					
			assertEquals(bucketName, s3object.getBucketName());
			assertEquals(testObjects.get("destinationKey").toString(), s3object.getKey());
			assertTrue(objectMetadata.getUserMetadata().equals(testObjects.get("destinationUserMetadata")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void copyObjectOptionalAttributesVersioningEnabledVerifications() {
		
 		MessageProcessor copyObjectOptionalAttributesFlow;
 		MuleEvent copyObjectOptionalAttributesResponse;
 		
 		MessageProcessor createObjectFlow;
 		
 		S3Object s3object;
 		ObjectMetadata objectMetadata;
 		
 		String sourceVersionId = testObjects.get("sourceVersionId").toString();
 		String destinationKey = testObjects.get("key").toString();
		
		try {
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			testObjects.put("modifiedSince", (Date) objectMetadata.getLastModified());
			testObjects.put("unmodifiedSince", (Date) objectMetadata.getLastModified());
			
			// copy-object-optional-attributes-unmodified-since
			
			testObjects.put("destinationKey", destinationKey + "UnmodifiedSinceCopy");
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-unmodified-since");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			String copyByUnmodifiedSinceVersionId = copyObjectOptionalAttributesResponse.getMessage().getPayload().toString();
			
			assertFalse(copyByUnmodifiedSinceVersionId.contains("{NullPayload}"));
			assertFalse(copyByUnmodifiedSinceVersionId.contains(sourceVersionId));
			
			// update the object
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			// copy-object-optional-attributes-modified-since

			testObjects.put("destinationKey", destinationKey + "ModifiedSinceCopy");
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-modified-since");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			String copyByModifiedSinceVersionId = copyObjectOptionalAttributesResponse.getMessage().getPayload().toString();
			
			assertFalse(copyByModifiedSinceVersionId.contains("{NullPayload}"));
			assertFalse(copyByModifiedSinceVersionId.contains(sourceVersionId));

			// copy-object-optional-attributes-version-id

			testObjects.put("destinationKey", destinationKey + "VersionIdCopy");
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-source-version-id");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			String copyBySourceVersionIdVersionId = copyObjectOptionalAttributesResponse.getMessage().getPayload().toString();
			
			assertFalse(copyBySourceVersionIdVersionId.contains("{NullPayload}"));
			assertFalse(copyBySourceVersionIdVersionId.contains(sourceVersionId));	
			
			// copy-object-optional-attributes-destination-bucket

			testObjects.put("destinationKey", destinationKey + "DestinationBucketCopy");
			testObjects.put("destinationBucketName", testObjects.get("sourceBucketName").toString());
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-destination-bucket");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			String copyByDestinationBucketVersionId = copyObjectOptionalAttributesResponse.getMessage().getPayload().toString();
			
			assertFalse(copyByDestinationBucketVersionId.contains("{NullPayload}"));
			assertFalse(copyByDestinationBucketVersionId.contains(sourceVersionId));	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
private void copyObjectOptionalAttributesVersioningDisabledVerifications() {
		
 		MessageProcessor copyObjectOptionalAttributesFlow;
 		MuleEvent copyObjectOptionalAttributesResponse;
 		
 		MessageProcessor createObjectFlow;
 		
 		S3Object s3object;
 		ObjectMetadata objectMetadata;
 		
 		String destinationKey = testObjects.get("key").toString();
		
		try {
			
			MessageProcessor getObjectFlow = lookupMessageProcessor("get-object");
			MuleEvent getObjectResponse = getObjectFlow.process(getTestEvent(testObjects));
			
			s3object = (S3Object) getObjectResponse.getMessage().getPayload();
			objectMetadata = s3object.getObjectMetadata();
			
			testObjects.put("modifiedSince", (Date) objectMetadata.getLastModified());
			testObjects.put("unmodifiedSince", (Date) objectMetadata.getLastModified());
			
			// copy-object-optional-attributes-unmodified-since
			
			testObjects.put("destinationKey", destinationKey + "UnmodifiedSinceCopy");
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-unmodified-since");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", copyObjectOptionalAttributesResponse.getMessage().getPayload().toString());
			
			// update the object
			
			testObjects.put("userMetadata", updatedUserMetadata);
			
			createObjectFlow = lookupMessageProcessor("create-object-child-elements-from-message");
			createObjectFlow.process(getTestEvent(testObjects));
			
			// copy-object-optional-attributes-modified-since

			testObjects.put("destinationKey", destinationKey + "ModifiedSinceCopy");
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-modified-since");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", copyObjectOptionalAttributesResponse.getMessage().getPayload().toString());

			// copy-object-optional-attributes-destination-bucket

			testObjects.put("destinationKey", destinationKey + "DestinationBucketCopy");
			testObjects.put("destinationBucketName", testObjects.get("sourceBucketName").toString());
			
			copyObjectOptionalAttributesFlow = lookupMessageProcessor("copy-object-optional-attributes-destination-bucket");
			copyObjectOptionalAttributesResponse = copyObjectOptionalAttributesFlow.process(getTestEvent(testObjects));
			
			assertEquals("{NullPayload}", copyObjectOptionalAttributesResponse.getMessage().getPayload().toString());	
			
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
		testObjects.put("sourceBucketName", bucketName);
    	
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
	public void testCopyInputStreamObjectChildElementsFromMessage() {
    	
    	InputStream inputStream = null;
    	
		try {

	    	initializeInputStreamTestData(inputStream);
	    	copyObjectVersioningDisabled("copy-object-child-elements-from-message");
	    	copyObjectChildElementsFromMessageGetVerifications();
			
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyInputStreamObjectChildElementsCreateObjectManually() {
    	
    	InputStream inputStream = null;
    	
		try {

	    	initializeInputStreamTestData(inputStream);
	    	copyObjectVersioningDisabled("copy-object-child-elements-create-object-manually");
	    	copyObjectChildElementsCreateObjectManuallyGetVerifications();
			
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyInputStreamObjectChildElementsNone() {
    	
    	InputStream inputStream = null;
    	
		try {

	    	initializeInputStreamTestData(inputStream);
	    	copyObjectVersioningDisabled("copy-object-child-elements-none");
	    	copyObjectChildElementsNoneGetVerifications();
			
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyByteArrayObjectChildElementsNone() {

    	initializeByteArrayTestData();
    	copyObjectVersioningDisabled("copy-object-child-elements-none");
    	copyObjectChildElementsNoneGetVerifications();
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCopyByteArrayObjectChildElementsFromMessage() {

    	initializeByteArrayTestData();
    	copyObjectVersioningDisabled("copy-object-child-elements-from-message");
    	copyObjectChildElementsFromMessageGetVerifications();
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyByteArrayObjectChildElementsCreateObjectManually() {

    	initializeByteArrayTestData();
    	copyObjectVersioningDisabled("copy-object-child-elements-create-object-manually");
    	copyObjectChildElementsCreateObjectManuallyGetVerifications();
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyFileObjectChildElementsNone() {
    	
    	File tempFile = null;
    	
		try {
			
	    	initializeFileTestData(tempFile);
	    	copyObjectVersioningDisabled("copy-object-child-elements-none");
	    	copyObjectChildElementsNoneGetVerifications();
     
		} finally {
			if (tempFile != null) {	tempFile.delete(); }
		}

	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCopyFileObjectChildElementsFromMessage() {
    	
    	File tempFile = null;
    	
		try {
			
	    	initializeFileTestData(tempFile);
	    	copyObjectVersioningDisabled("copy-object-child-elements-from-message");
	    	copyObjectChildElementsFromMessageGetVerifications();
     
		} finally {
			if (tempFile != null) {	tempFile.delete(); }
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyFileObjectChildElementsCreateObjectManually() {
    	
    	File tempFile = null;
    	
		try {
			
	    	initializeFileTestData(tempFile);
	    	copyObjectVersioningDisabled("copy-object-child-elements-create-object-manually");
	    	copyObjectChildElementsCreateObjectManuallyGetVerifications();
     
		} finally {
			if (tempFile != null) {	tempFile.delete(); }
		}
    	
	}
    
    private void initializeFileTestData(File tempFile) {

    	testObjects.putAll((HashMap<String,Object>) context.getBean("copyFileObjectTestData"));
    	
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

		testObjects.putAll((HashMap<String,Object>) context.getBean("copyInputStreamObjectTestData"));

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

    @Category({RegressionTests.class})
 	@Test
 	public void testCopyStringObjectChildElementsNone() {
     	
 		initializeStringTestData();
 		copyObjectVersioningDisabled("copy-object-child-elements-none");
 		copyObjectChildElementsNoneGetVerifications();
      
 	}
    
    @Category({SanityTests.class, RegressionTests.class})
 	@Test
 	public void testCopyStringObjectChildElementsFromMessage() {
     	
 		initializeStringTestData();
 		copyObjectVersioningDisabled("copy-object-child-elements-from-message");
 		copyObjectChildElementsFromMessageGetVerifications();
      
 	}
    
    @Category({RegressionTests.class})
 	@Test
 	public void testCopyStringObjectChildElementsCreateObjectManually() {
     	
 		initializeStringTestData();
 		copyObjectVersioningDisabled("copy-object-child-elements-create-object-manually");
 		copyObjectChildElementsCreateObjectManuallyGetVerifications();
      
 	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyObjectOptionalAttributesVersionioningEnabled() throws InterruptedException {
    	
    	initializeByteArrayTestData();
    	createObject(true);
    	Thread.sleep(5000);
    	copyObjectOptionalAttributesVersioningEnabledVerifications();
    	copyObjectChildElementsFromMessageGetVerifications();
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCopyObjectOptionalAttributesVersionioningDisabled() throws InterruptedException {
    	
    	initializeByteArrayTestData();
    	createObject(false);
    	Thread.sleep(5000);
    	copyObjectOptionalAttributesVersioningDisabledVerifications();
    	copyObjectChildElementsFromMessageGetVerifications();
     
	}
    

}