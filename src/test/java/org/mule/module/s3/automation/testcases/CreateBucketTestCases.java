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

import com.amazonaws.services.s3.model.Bucket;

public class CreateBucketTestCases extends S3TestParent {
	
	private String bucketName;
	
	@Before
	public void setUpBucketName() {
		// Use the same bucket name for creation and deletion
		bucketName = randomString();
		initializeTestRunMessage("createBucketTestData");
	}
	
	@After
	public void deleteCreatedBucket() throws Exception {
		deleteBucket(bucketName);
	}
	
	@Test
	public void createBucket() throws Exception {
		Bucket createdBucket = createBucket(bucketName);
		assertEquals(getTestRunMessageValue("bucketName"), createdBucket.getName());
	}
	
	@Test
	public void createBucketWithOptionalAttributes() throws Exception {
		upsertOnTestRunMessage("bucketName", bucketName);
		Bucket createdBucket = runFlowAndGetPayload("create-bucket-optional-attributes");
		assertEquals(getTestRunMessageValue("bucketName"), createdBucket.getName());
	}
	
}
