/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import java.util.ArrayList;
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
		return runFlowAndGetPayload("list-buckets");
	}
	
	protected List<String> bucketListNames() throws Exception {
		List<String> bucketListNames = new ArrayList<String>();
		for (Bucket b : bucketList()) {
			bucketListNames.add(b.getName());
		}
		return bucketListNames;
	}
	
	protected void deleteBucket(String bucketName) throws Exception {
		upsertOnTestRunMessage("bucketName", bucketName);
		runFlowAndGetPayload("delete-bucket");
	}

}
