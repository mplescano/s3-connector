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

import org.mule.module.s3.simpleapi.KeyVersion;
import org.mule.modules.tests.ConnectorTestCase;
import org.mule.transport.NullPayload;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

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
	
	protected List<KeyVersion> objectListNames(String bucketName) throws Exception {
		List<KeyVersion> objectListNames = new ArrayList<KeyVersion>();
		initializeTestRunMessage("bucketName", bucketName);
		Iterable<S3ObjectSummary> objects = runFlowAndGetPayload("list-objects");
		for (S3ObjectSummary o : objects) {
			objectListNames.add(new KeyVersion(o.getKey()));
		}
		return objectListNames;
	}
	
	protected void deleteBucket(String bucketName) throws Exception {
		List<KeyVersion> objects = objectListNames(bucketName);
		if (!objects.isEmpty()) {
			upsertOnTestRunMessage("keysReference", objectListNames(bucketName));
			runFlowAndGetPayload("delete-objects");
		}
		runFlowAndGetPayload("delete-bucket-optional-attributes");
	}

	protected void enableVersioning() throws Exception {
		upsertOnTestRunMessage("versioningStatus", "ENABLED");
		runFlowAndGetPayload("set-bucket-versioning-status");
	}
	
	protected boolean isNullPayload(Object payload) {
		return payload instanceof NullPayload;
	}

}
