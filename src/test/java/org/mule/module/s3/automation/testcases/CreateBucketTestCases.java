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
		upsertOnTestRunMessage("bucketName", bucketName);
	}
	
	@After
	public void deleteCreatedBucket() throws Exception {
		// TODO Rename when framework is updated
		runFlowAndGetPayload("delete-bucket");
	}
	
	@Test
	public void createBucket() throws Exception {
		Bucket createdBucket = runFlowAndGetPayload("create-bucket");
		assertEquals(getTestRunMessageValue("bucketName"), createdBucket.getName());
	}
	
	@Test
	public void createBucketWithOptionalAttributes() throws Exception {
		Bucket createdBucket = runFlowAndGetPayload("create-bucket-optional-attributes");
		assertEquals(getTestRunMessageValue("bucketName"), createdBucket.getName());
	}
	
}
