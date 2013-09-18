/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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

public class CreateObjectPresignedURITestCases extends S3TestParent {
	
	String bucketName;
	
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
	public void testCreateInputStreamObjectPresignedURI() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectPresignedURITestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri");
			MuleEvent response = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
			
		} finally {
			
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
	    
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateInputStreamObjectPresignedURIOptionalAttributes() {
    
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectPresignedURITestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessorConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);
	    	
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			
			createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri-optional-attributes");
			MuleEvent createObjectPresignedURIResponse = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) createObjectPresignedURIResponse.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
			
		} finally {
			
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
	    
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateByteArrayObjectPresignedURI() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectPresignedURITestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri");
			MuleEvent response = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateStringObjectPresignedURI() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectPresignedURITestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri");
			MuleEvent response = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateFileObjectPresignedURI() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectPresignedURITestData"));
		
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri");
			MuleEvent response = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
			
		} finally {
			
			if (temp != null) {	temp.delete(); }

		}
		 
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateByteArrayObjectPresignedURIOptionalAttributes() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectPresignedURITestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {
			
			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessorConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			
			createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri-optional-attributes");
			MuleEvent createObjectPresignedURIResponse = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) createObjectPresignedURIResponse.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectPresignedURIOptionalAttributes() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectPresignedURITestData"));
		
		try {
			
			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessorConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			
			createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));

			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri-optional-attributes");
			MuleEvent createObjectPresignedURIResponse = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) createObjectPresignedURIResponse.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateFileObjectPresignedURIOptionalAttributes() {

		File temp = null;
		
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectPresignedURITestData"));
		
		try {
			
			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupMessageProcessorConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

			MessageProcessor createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			MuleEvent createObjectResponse = createObjectFlow.process(getTestEvent(testObjects));
			
			testObjects.put("versionId", (String) createObjectResponse.getMessage().getPayload());
			
			createObjectFlow = lookupMessageProcessorConstruct("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectPresignedURIFlow = lookupMessageProcessorConstruct("create-object-presigned-uri-optional-attributes");
			MuleEvent createObjectPresignedURIResponse = createObjectPresignedURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) createObjectPresignedURIResponse.getMessage().getPayload()).toURL());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
			
		} finally {
			
			if (temp != null) {	temp.delete(); }

		}
     
	}
    
}