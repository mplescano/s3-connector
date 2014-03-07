package org.mule.module.s3.automation.testcases;

import java.util.List;
import java.util.UUID;

import org.mule.modules.tests.ConnectorTestCase;

import com.amazonaws.services.s3.model.Bucket;

public class S3TestParent extends ConnectorTestCase {

	protected String randomString() {
		return UUID.randomUUID().toString();
	}

	protected Bucket createBucket(String bucketName) throws Exception {
		upsertOnTestRunMessage("bucketName", bucketName);
		return runFlowAndGetPayload("create-bucket");
	}
	
	protected List<Bucket> bucketList() throws Exception {
		return runFlowAndGetPayload("list-bucket");
	}
	

}
