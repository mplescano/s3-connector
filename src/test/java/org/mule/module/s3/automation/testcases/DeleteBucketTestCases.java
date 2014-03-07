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
