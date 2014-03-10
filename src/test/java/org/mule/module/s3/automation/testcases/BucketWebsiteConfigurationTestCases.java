/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.transport.NullPayload;

import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;

public class BucketWebsiteConfigurationTestCases extends S3TestParent {

	private String bucketName;
	private String indexDocumentSuffix;

	@Before
	public void createRandomBucket() throws Exception {
		bucketName = randomString();
		createBucket(bucketName);
	}

	@Before
	public void initWebsiteConfigData() throws Exception {
		upsertBeanFromContextOnTestRunMessage("bucketWebsiteConfiguration",
				"bucketWebsiteConfigurationTestData");
		BucketWebsiteConfiguration config = getBeanFromContext("bucketWebsiteConfigurationTestData");
		indexDocumentSuffix = config.getIndexDocumentSuffix();
		upsertOnTestRunMessage("indexDocumentSuffix", indexDocumentSuffix);
	}

	@After
	public void deleteCreatedBucket() throws Exception {
		deleteBucket(bucketName);
	}

	@Test
	public void setAndGetBucketWebsiteConfiguration() throws Exception {
		runFlowAndGetPayload("set-bucket-website-configuration");
		BucketWebsiteConfiguration config = runFlowAndGetPayload("get-bucket-website-configuration");
		assertEquals(indexDocumentSuffix, config.getIndexDocumentSuffix());
	}

	@Test
	public void setAndGetBucketWebsiteConfigurationOptionalAttributes()
			throws Exception {
		runFlowAndGetPayload("set-bucket-website-configuration-optional-attributes");
		BucketWebsiteConfiguration config = runFlowAndGetPayload("get-bucket-website-configuration");
		assertEquals(indexDocumentSuffix, config.getIndexDocumentSuffix());
	}

	@Test
	public void deleteBucketWebsiteConfiguration() throws Exception {
		runFlowAndGetPayload("set-bucket-website-configuration");
		runFlowAndGetPayload("delete-bucket-website-configuration");
		Object config = runFlowAndGetPayload("get-bucket-website-configuration");
		assertTrue(config instanceof NullPayload);
	}

}