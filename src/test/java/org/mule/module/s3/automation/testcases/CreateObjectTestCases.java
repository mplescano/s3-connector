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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class CreateObjectTestCases extends S3TestParent {
	
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
	
    @Category({RegressionTests.class})
	@Test
	public void testCreateInputStreamObjectChildElementsNone() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateInputStreamObjectChildElementsNoneVersioningEnabled() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects));
			
	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
			
			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
 	@Test
 	public void testCreateInputStreamObjectChildElementsFromMessageVersioningEnabled() {
     	
		InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

     	String host = testObjects.get("host").toString();
     	String path = testObjects.get("path").toString();
     	String urlString = String.format("http://%s/%s",host, path);
     	
 		try {
 			
 			testObjects.put("versioningStatus", "ENABLED");
 			
 			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
 			setBucketVersioningStatusFlow.process(getTestEvent(testObjects));
 			
 	    	URL url = new URL(urlString);
 	    	URLConnection connection = url.openConnection();
 	    	inputStream = connection.getInputStream();	    
 	    	
 	    	testObjects.put("contentRef", inputStream);

 			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
 			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
 			
 			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
 		
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			fail();
 		} finally {
 			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
 		}
      
 	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateInputStreamObjectChildElementsFromMessage() {
    	
		InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {
			
	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
			
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
	public void testCreateInputStreamObjectChildElementsCreateObjectManually() {
    	
		InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {
			
	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-create-object-manually");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
			
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
	public void testCreateByteArrayObjectChildElementsNone() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectChildElementsNone() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateStringObjectChildElementsFromMessage() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectChildElementsCreateObjectManually() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-create-object-manually");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectChildElementsNoneVersioningEnabled() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
    	
		try {

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
			
			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test    
	public void testCreateStringObjectChildElementsFromMessageVersioningEnabled() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
    	
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
      
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateByteArrayObjectChildElementsFromMessage() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateByteArrayObjectChildElementsNoneVersioningEnabled() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
			
			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test    
	public void testCreateByteArrayObjectChildElementsFromMessageVersioningEnabled() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectTestData"));

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
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateByteArrayObjectChildElementsCreateObjectsManually() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectTestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-create-object-manually");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateFileObjectChildElementsNone() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectTestData"));
		
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateFileObjectChildElementsNoneVersioningEnabled() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

			testObjects.put("versioningStatus", "ENABLED");
			
			MessageProcessor setBucketVersioningStatusFlow = lookupFlowConstruct("set-bucket-versioning-status");
			setBucketVersioningStatusFlow.process(getTestEvent(testObjects)); 

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-none");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));
			
			assertFalse(response.getMessage().getPayload().toString().equals("{NullPayload}"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateFileObjectChildElementsFromMessage() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-from-message");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
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
	public void testCreateFileObjectChildElementsCreateObjectManually() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-child-elements-create-object-manually");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();	
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test    
	public void testCreateFileObjectChildElementsFromMessageVersioningEnabled() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectTestData"));
    	
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);

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
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
   
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectOptionalAttributes() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectTestData"));
		
		String content = testObjects.get("contentRef").toString();
		
		try {
			
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(content.getBytes("UTF8"));   
			byte[] encodedByteData = Base64.encodeBase64(messageDigest.digest());
		    
			testObjects.put("contentMd5", new String(encodedByteData, "UTF-8"));
			testObjects.put("contentLength", Long.valueOf(content.getBytes("UTF-8").length));
			
			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-optional-attributes");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Ignore
    @Category({RegressionTests.class})
	@Test
	public void testCreateInputStreamObjectOptionalAttributes() {
    	
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectTestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	    	DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);

			byte[] encodedByteData = Base64.encodeBase64(digestInputStream.getMessageDigest().digest());
    
			testObjects.put("contentMd5", new String(encodedByteData, "UTF-8"));
			testObjects.put("contentLength", Long.valueOf(IOUtils.toByteArray(inputStream).length));

			MessageProcessor createObjectFlow = lookupFlowConstruct("create-object-optional-attributes");
			MuleEvent response = createObjectFlow.process(getTestEvent(testObjects));

			assertEquals("{NullPayload}", response.getMessage().getPayload().toString());
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {			
			if (inputStream != null) try { inputStream.close(); } catch (IOException logOrIgnore) {}
		}
     
	}
    
}