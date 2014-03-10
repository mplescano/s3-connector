/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DeleteBucketTestCases extends S3TestParent {
	
	private String bucketName;
	
	@Before
	public void createRandomBucket() throws Exception {
		bucketName = randomString();
		initializeTestRunMessage("deleteBucketTestData");
		createBucket(bucketName);
	}
	
	@Test
	public void deleteCreatedBucket() throws Exception {
		deleteBucket(bucketName);
		assertFalse(bucketListNames().contains(bucketName));
	}
	
	@Test
	public void deleteCreatedBucketOptionalAttributes() throws Exception {
		upsertOnTestRunMessage("bucketName", bucketName);
		runFlowAndGetPayload("delete-bucket-optional-attributes");
		assertFalse(bucketListNames().contains(bucketName));
	}
}
