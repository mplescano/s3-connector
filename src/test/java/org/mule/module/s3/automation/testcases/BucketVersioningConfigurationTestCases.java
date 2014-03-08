/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.s3.model.BucketVersioningConfiguration;

public class BucketVersioningConfigurationTestCases extends S3TestParent {
	
	String bucketName;
	
	@Before
	public void setUp() throws Exception{
		bucketName = randomString();
		createBucket(bucketName);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteBucket(bucketName);
	}
	
	private String getVersioningConfiguration(String bucketName) throws Exception {
		BucketVersioningConfiguration config = runFlowAndGetPayload("get-bucket-versioning-configuration");
		return config.getStatus().toString().toUpperCase();
	}
	
	private void setVersioningConfiguration(String status) throws Exception {
		upsertOnTestRunMessage("versioningStatus", status);
		runFlowAndGetPayload("set-bucket-versioning-status");
	}
	
	@Test    
	public void testBucketVersioningConfigurationTestCases() throws Exception {
    	assertEquals("OFF", getVersioningConfiguration(bucketName));
    	setVersioningConfiguration("ENABLED");
    	assertEquals("ENABLED", getVersioningConfiguration(bucketName));
    	setVersioningConfiguration("SUSPENDED");
    	assertEquals("SUSPENDED", getVersioningConfiguration(bucketName));
	}
    
}