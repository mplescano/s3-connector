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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateObjectTestCases extends S3TestParent {

	String bucketName;
	InputStream inputStream;

	@Before
	public void createRandomBucket() throws Exception {
		bucketName = randomString();
		createBucket(bucketName);
	}

	@After
	public void deleteCreatedBucket() throws Exception {
		deleteBucket(bucketName);
		if (inputStream != null) {
			inputStream.close();
		}
	}

	private void runFlowCheckNullPayload(String flow) throws Exception {
		Object payload = null;
		try {
			payload = runFlowAndGetPayload(flow);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception when running flow");
		}
		// If versioning is enabled, returned payload is not null
		assertTrue(isNullPayload(payload) != versioningEnabled);
	}

	private InputStream createInputStream() throws MalformedURLException,
			IOException {
		upsertBeanFromContextOnTestRunMessage("createInputStreamObjectTestData");
		String urlString = inputStreamUrlString();
		InputStream inputStream = new URL(urlString).openConnection()
				.getInputStream();
		upsertOnTestRunMessage("contentRef", inputStream);
		return inputStream;
	}

	private String inputStreamUrlString() {
		String host = getTestRunMessageValue("host");
		String path = getTestRunMessageValue("path");
		return String.format("http://%s/%s", host, path);
	}
	
	private void testInputStream(String flow) throws Exception {
		inputStream = createInputStream();
		runFlowCheckNullPayload(flow);
	}
	
	private void testInputStreamOptionalAttributes(String flowName)
			throws Exception {
		createInputStream();
		addOptionalInputStreamAttributesToMessage();
		runFlowCheckNullPayload(flowName);
	}

	private void addOptionalInputStreamAttributesToMessage()
			throws NoSuchAlgorithmException, IOException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		DigestInputStream digestInputStream = new DigestInputStream(
				inputStream, messageDigest);
		byte[] encodedByteData = Base64.encodeBase64(digestInputStream
				.getMessageDigest().digest());
		upsertOnTestRunMessage("contentMd5", new String(encodedByteData,
				"UTF-8"));
		upsertOnTestRunMessage("contentLength",
				Long.valueOf(IOUtils.toByteArray(inputStream).length));
	}
	
	private void addByteArrayToMessage() {
		upsertBeanFromContextOnTestRunMessage("createByteArrayObjectTestData");
	}

	private void testByteArray(String flow) throws Exception {
		addByteArrayToMessage();
		runFlowCheckNullPayload(flow);
	}
	
	private void addStringToMessage() {
		upsertBeanFromContextOnTestRunMessage("createStringObjectTestData");
	}

	private void addOptionalStringAttributesToMessage()
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String content = getTestRunMessageValue("contentRef");
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(content.getBytes("UTF8"));
		byte[] encodedByteData = Base64.encodeBase64(messageDigest.digest());
		upsertOnTestRunMessage("contentMd5", new String(encodedByteData,
				"UTF-8"));
		upsertOnTestRunMessage("contentLength",
				Long.valueOf(content.getBytes("UTF-8").length));
	}
	
	private void testString(String flow) throws Exception {
		addStringToMessage();
		runFlowCheckNullPayload(flow);
	}
	
	private void testStringOptionalAttributes(String flowName) throws Exception {
		addStringToMessage();
		addOptionalStringAttributesToMessage();
		runFlowCheckNullPayload(flowName);
	}
	private void testFile(String flowName) throws Exception {
		upsertBeanFromContextOnTestRunMessage("createFileObjectTestData");
		File temp = File.createTempFile("temp-file-name", ".tmp");
		upsertOnTestRunMessage("contentRef", temp);
		runFlowCheckNullPayload(flowName);
	}

	@Test
	public void createInputStreamObjectChildElementsNone() throws Exception {
		testInputStream("create-object-child-elements-none");
	}

	@Test
	public void createInputStreamObjectChildElementsNoneVersioningEnabled()
			throws Exception {
		enableVersioning();
		testInputStream("create-object-child-elements-none");
	}

	@Test
	public void createInputStreamObjectChildElementsFromMessageVersioningEnabled()
			throws Exception {
		enableVersioning();
		testInputStream("create-object-child-elements-from-message");
	}

	@Test
	public void createInputStreamObjectChildElementsFromMessage()
			throws Exception {
		testInputStream("create-object-child-elements-from-message");
	}

	@Test
	public void createInputStreamObjectChildElementsCreateObjectManually()
			throws Exception {
		testInputStream("create-object-child-elements-create-object-manually");
	}
	
	@Test
	public void createByteArrayObjectChildElementsNone() throws Exception {
		testByteArray("create-object-child-elements-none");
	}
	
	@Test
	public void createByteArrayObjectChildElementsNoneVersioningEnabled() {
		
	}
	
	@Test
	public void createByteArrayObjectChildElementsFromMessageVersioningEnabled() {
		
	}
	
	@Test
	public void createByteArrayObjectChildElementsCreateObjectsManually() {
		
	}
	
	@Test
	public void createByteArrayObjectChildElementsFromMessage()
			throws Exception {
		testByteArray("create-object-child-elements-from-message");
	}

	@Test
	public void createStringObjectChildElementsNone() throws Exception {
		testString("create-object-child-elements-none");
	}

	@Test
	public void createStringObjectChildElementsFromMessage() throws Exception {
		testString("create-object-child-elements-from-message");
	}

	@Test
	public void createStringObjectChildElementsCreateObjectManually()
			throws Exception {
		testString("create-object-child-elements-create-object-manually");
	}

	@Test
	public void createStringObjectChildElementsNoneVersioningEnabled()
			throws Exception {
		enableVersioning();
		testString("create-object-child-elements-none");
	}

	@Test
	public void createStringObjectChildElementsFromMessageVersioningEnabled()
			throws Exception {
		enableVersioning();
		testString("create-object-child-elements-from-message");
	}

	@Test
	public void createFileObjectChildElementsNone() throws Exception {
		testFile("create-object-child-elements-none");
	}

	@Test
	public void createFileObjectChildElementsNoneVersioningEnabled()
			throws Exception {
		enableVersioning();
		testFile("create-object-child-elements-none");
	}

	@Test
	public void createFileObjectChildElementsFromMessage() throws Exception {
		testFile("create-object-child-elements-from-message");
	}

	@Test
	public void createFileObjectChildElementsCreateObjectManually()
			throws Exception {
		testFile("create-object-child-elements-create-object-manually");
	}

	@Test
	public void createFileObjectChildElementsFromMessageVersioningEnabled()
			throws Exception {
		enableVersioning();
		testFile("create-object-child-elements-from-message");
	}

	@Test
	public void testCreateStringObjectOptionalAttributes() throws Exception {
		testStringOptionalAttributes("create-object-optional-attributes");
	}

	@Test
	public void testCreateInputStreamObjectOptionalAttributes() throws Exception {
		testInputStreamOptionalAttributes("create-object-optional-attributes");
	}
	
}