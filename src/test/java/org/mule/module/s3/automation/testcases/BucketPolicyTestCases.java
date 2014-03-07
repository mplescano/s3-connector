package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BucketPolicyTestCases extends S3TestParent {

	private String bucketName;

	@Before
	public void createRandomBucket() throws Exception {
		initializeTestRunMessage("bucketPolicyTestData");
		bucketName = randomString();
		createBucket(bucketName);
		upsertOnTestRunMessage("bucketName", bucketName);
	}

	@After
	public void deleteCreatedBucket() throws Exception {
		deleteBucket(bucketName);
	}

	@Test
	public void setAndGetBucketPolicy() throws Exception {
		String policyText = getTestRunMessageValue("policyTemplate").toString()
				.replace("test_bucket_name", bucketName);
		upsertOnTestRunMessage("policyText", policyText);
		runFlowAndGetPayload("set-bucket-policy");
		String bucketPolicy = runFlowAndGetPayload("get-bucket-policy");
		assertEquals(bucketPolicy, policyText);
	}
	
	@Test
	public void deleteBucketPolicy() throws Exception {
		runFlowAndGetPayload("delete-bucket-policy");
		String policy = runFlowAndGetPayload("get-bucket-policy").toString();
		assertEquals("{NullPayload}", policy);
	}
}
