/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

public class CreateObjectURITestCases extends S3TestParent {
	
	String bucketName;
	
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
	public void testCreateInputStreamObjectURI() {
    	
    	InputStream inputStream = null;

		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectURITestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
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
	public void testCreateInputStreamObjectURIOptionalAttributes() {
    
    	InputStream inputStream = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createInputStreamObjectURITestData"));

    	String host = testObjects.get("host").toString();
    	String path = testObjects.get("path").toString();
    	String urlString = String.format("http://%s/%s",host, path);
    	
		try {

	    	URL url = new URL(urlString);
	    	URLConnection connection = url.openConnection();
	    	inputStream = connection.getInputStream();	    
	    	
	    	testObjects.put("contentRef", inputStream);

			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri-optional-attributes");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			URI uri = (URI) response.getMessage().getPayload();
			
			assertTrue(uri.getScheme().equals("https"));
			assertNotNull(uri.toURL());
	
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
	public void testCreateByteArrayObjectURI() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectURITestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateStringObjectURI() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectURITestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testCreateFileObjectURI() {
    	
    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectURITestData"));
		
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			assertNotNull(((URI) response.getMessage().getPayload()).toURL());

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
	public void testCreateByteArrayObjectURIOptionalAttributes() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createByteArrayObjectURITestData"));

    	byte data[] = bucketName.getBytes();
    	testObjects.put("contentRef", data);
    	
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri-optional-attributes");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			URI uri = (URI) response.getMessage().getPayload();
			
			assertTrue(uri.getScheme().equals("https"));
			assertNotNull(uri.toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateStringObjectURIOptionalAttributes() {
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createStringObjectURITestData"));
		
		try {
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri-optional-attributes");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			URI uri = (URI) response.getMessage().getPayload();
			
			assertTrue(uri.getScheme().equals("https"));
			assertNotNull(uri.toURL());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}
    
    @Category({RegressionTests.class})
	@Test
	public void testCreateFileObjectURIOptionalAttributes() {

    	File temp = null;
    	
		testObjects.putAll((HashMap<String,Object>) context.getBean("createFileObjectURITestData"));
		
		try {
			
			temp = File.createTempFile("temp-file-name", ".tmp"); 
			
	    	testObjects.put("contentRef", temp);
			
			MessageProcessor createObjectFlow = lookupMessageProcessor("create-object-child-elements-none");
			createObjectFlow.process(getTestEvent(testObjects));
			
			MessageProcessor createObjectURIFlow = lookupMessageProcessor("create-object-uri-optional-attributes");
			MuleEvent response = createObjectURIFlow.process(getTestEvent(testObjects));
			
			URI uri = (URI) response.getMessage().getPayload();
			
			assertTrue(uri.getScheme().equals("https"));
			assertNotNull(uri.toURL());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} finally {
			if (temp != null) {	temp.delete(); }
		}
     
	}
    
}