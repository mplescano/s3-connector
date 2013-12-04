/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.integration;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3VersionSummary;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.module.s3.AccessControlList;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.StorageClass;
import org.mule.module.s3.simpleapi.Region;
import org.mule.module.s3.simpleapi.VersioningStatus;

import java.net.URI;

import static org.junit.Assert.*;
import static org.mule.module.s3.AccessControlList.PRIVATE;

public class S3TestDriver
{
    private S3Connector connector;
    private String bucketName;

    @Before
    public void setup() throws Exception
    {
        connector = new S3Connector();
        connector.connect(System.getenv("user.key.access"), System.getenv("user.key.secret"));
        bucketName = System.getenv("bucket.name");
    }

    @After
    public void deleteTestBucket()
    {
        connector.deleteBucket(bucketName, true);
    }

    @Test
    @Ignore
    public void testCreatePresignedUri() throws Exception
    {
        connector.createBucket(bucketName, Region.US_STANDARD, AccessControlList.PRIVATE);
        connector.createObject(bucketName, "myObject", "hello world", null, null, "text/plain",
            null, AccessControlList.PUBLIC_READ, StorageClass.STANDARD, null, null);
        URI uri = connector.createObjectPresignedUri(bucketName, "myObject", null, null, "GET");
        assertTrue(uri.toString().startsWith(
                String.format("https://%s.s3.amazonaws.com/%s", bucketName, "myObject")));
    }

    @Test(expected = AmazonServiceException.class)
    @Ignore
    public void testDeleteNoForce() throws Exception
    {
        connector.createBucket(bucketName, Region.US_STANDARD, PRIVATE);
        connector.createObject(bucketName, "anObject", "hello world", null, null, null, null, PRIVATE,
            StorageClass.STANDARD, null, null);
        connector.deleteBucket(bucketName, false);
    }

    /**
     * Creates a bucket, and asserts that buckets count has now increased in 1, and
     * that it is empty of objects. Then adds a new object and asserts that its
     * version is null (versioning disabled), and that the bucket is not empty
     * anymore
     */
    @Test
    @Ignore
    public void testCreateBucketAndObjects() throws Exception
    {
        // pre1
        int bucketsCount = connector.listBuckets().size();

        // op1
        Bucket bucket = connector.createBucket(bucketName, Region.US_STANDARD, PRIVATE);

        // pos1
        assertNotNull(bucket);
        assertEquals(bucketName, bucket.getName());
        assertEquals(bucketsCount + 1, connector.listBuckets().size());
        assertFalse(connector.listObjects(bucketName, "").iterator().hasNext());

        // op2
        String objectVersion = connector.createObject(bucketName, "anObject", "hello world!", null, null,
            "text/plain", null, PRIVATE, StorageClass.STANDARD, null, null);

        // op3



        // pos2
        assertNull(objectVersion);
        assertTrue(connector.listObjects(bucketName, "").iterator().hasNext());
    }

    /**
     * Creates a bucket, enables versioning, adds an object and overrides it with a
     * new content. Asserts that both returned version ids are not null and not equal
     */
    @Test
    @Ignore
    public void testCreateBucketAndObjectsWithVersions() throws Exception
    {
        connector.createBucket(bucketName, Region.US_STANDARD, PRIVATE);
        connector.setBucketVersioningStatus(bucketName, VersioningStatus.ENABLED);
        String versionId1 = connector.createObject(bucketName, "anObject", "hello", null, null, null, null,
            PRIVATE, StorageClass.STANDARD, null, null);
        assertNotNull(versionId1);
        String versionId2 = connector.createObject(bucketName, "anObject", "hello world", null, null, null, null,
            PRIVATE, StorageClass.STANDARD, null, null);
        assertNotNull(versionId2);
        assertFalse(versionId1.equals(versionId2));
        
        Iterable<S3VersionSummary> version = connector.listObjectVersions(bucketName);
        assertTrue(version.iterator().hasNext());
    }

    /**
     * Creates a new Bucket, copies an object from an other bucket to it, and sets
     * the bucket configuration
     */
    @Test
    @Ignore
    public void testCopyAndSetWebsiteConfiguration() throws Exception
    {
        connector.createBucket(bucketName, Region.US_STANDARD, PRIVATE);
        connector.copyObject("camaraenclaromeco", "axis.jpg", null, bucketName, "axis.jpg",
            AccessControlList.PRIVATE, StorageClass.STANDARD, null, null, null, null);
        BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration();
        configuration.setErrorDocument("axis.jpg");
        connector.setBucketWebsiteConfiguration(bucketName, configuration);
    }


    @Test
    public void testCreateEncryptedObject() {

        final String key = "myObject3";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        connector.createBucket(bucketName, Region.US_STANDARD, AccessControlList.PRIVATE);
        String result = connector.createObject(bucketName, key, "hello world", null, null, "text/plain",
        null, AccessControlList.PUBLIC_READ, StorageClass.STANDARD, "AES256", null);

        ObjectMetadata objectMetadata1 = connector.getObjectMetadata(bucketName, key, null);
        assertTrue(objectMetadata1.getServerSideEncryption() != null);
        assertTrue(objectMetadata1.getServerSideEncryption().equals(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION));
    }
}
