/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.automation.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListBucketsTestCases extends S3TestParent {

	private int bucketAmount;
	private List<String> generatedBucketNames;

	private List<String> createManyBuckets(int nBuckets) throws Exception {
		List<String> bucketList = new ArrayList<String>();
		for (int i = 0; i < nBuckets; i++) {
			bucketList.add(createBucket(randomString()).getName());
		}
		return bucketList;
	}

	@Before
	public void createRandomBuckets() {
		initializeTestRunMessage("listBucketsTestData");
		bucketAmount = Integer.parseInt(getTestRunMessageValue("bucketAmount")
				.toString());
	}

	@After
	public void deleteGeneratedBuckets() {
		for (String bucketName : generatedBucketNames) {
			upsertOnTestRunMessage("bucketName", bucketName);
		}
	}

	@Test
	// Create some buckets in S3, check if they are in the list
	public void listBuckets() throws Exception {
		generatedBucketNames = createManyBuckets(bucketAmount);
		assertTrue(bucketListNames().containsAll(generatedBucketNames));
	}
}
