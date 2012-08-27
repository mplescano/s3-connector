/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mule.module.s3.AccessControlList.PRIVATE;
import static org.mule.module.s3.AccessControlList.PUBLIC_READ;
import static org.mule.module.s3.AccessControlList.PUBLIC_READ_WRITE;

import com.amazonaws.services.s3.model.*;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.module.s3.simpleapi.Region;
import org.mule.module.s3.simpleapi.SimpleAmazonS3AmazonDevKitImpl;
import org.mule.module.s3.simpleapi.VersioningStatus;
import org.mule.module.s3.simpleapi.SimpleAmazonS3.S3ObjectContent;
import org.mule.module.s3.simpleapi.content.FileS3ObjectContent;

import static org.hamcrest.CoreMatchers.*;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.lang.ObjectUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mule.util.StringUtils;

public class S3TestCase
{
    private static final String POLICY_TEXT = "policy1";
    private static final String MY_OBJECT = "myObject";
    private static final String MY_BUCKET = "my-bucket";
    private S3Connector connector;
    private AmazonS3 client;

    @Before
    public void setup()
    {
        client = mock(AmazonS3.class);
        connector = new S3Connector();
        connector.setClient(new SimpleAmazonS3AmazonDevKitImpl(client));
    }

    @Test
    public void setObjectStorageClass()
    {
        connector.setObjectStorageClass(MY_BUCKET, MY_OBJECT, org.mule.module.s3.StorageClass.STANDARD);
        verify(client).changeObjectStorageClass(MY_BUCKET, MY_OBJECT, com.amazonaws.services.s3.model.StorageClass.Standard);
    }

    @Test
    public void createBucketWithAcl()
    {
        connector.createBucket(MY_BUCKET, Region.US_STANDARD, PRIVATE);
        CreateBucketRequest request = new CreateBucketRequest(MY_BUCKET);
        request.setCannedAcl(CannedAccessControlList.Private);
        CreateBucketRequestMatcher matcher = new CreateBucketRequestMatcher(MY_BUCKET, CannedAccessControlList.Private);
        verify(client).createBucket(argThat(matcher));
    }

    @Test
    public void copyObjectNoDestinationBucket()
    {
        CopyObjectResult result = new CopyObjectResult();
        result.setVersionId("12");
        CopyObjectRequest request = new CopyObjectRequest(MY_BUCKET, MY_OBJECT, MY_BUCKET, "myObject2");
        request.setCannedAccessControlList(CannedAccessControlList.PublicRead);
        request.setStorageClass(com.amazonaws.services.s3.model.StorageClass.Standard);

        CopyObjectRequestMatcher matcher = new CopyObjectRequestMatcher(request);
        when(client.copyObject(argThat(matcher))).thenReturn(result);

        assertEquals("12", connector.copyObject(MY_BUCKET, MY_OBJECT, null, null, "myObject2", PUBLIC_READ,
            org.mule.module.s3.StorageClass.STANDARD, null, null, null));
    }

    @Test
    public void copyObjectWithVersion()
    {
        CopyObjectResult result = new CopyObjectResult();
        result.setVersionId("12");

        CopyObjectRequest request = new CopyObjectRequest(MY_BUCKET, MY_OBJECT, "12", MY_BUCKET, "myObject2");
        request.setCannedAccessControlList(CannedAccessControlList.Private);
        request.setStorageClass(com.amazonaws.services.s3.model.StorageClass.Standard);

        CopyObjectRequestMatcher matcher = new CopyObjectRequestMatcher(request);
        when(client.copyObject(argThat(matcher))).thenReturn(result);

        assertEquals("12", connector.copyObject(MY_BUCKET, MY_OBJECT, "12", null, "myObject2", PRIVATE,
                org.mule.module.s3.StorageClass.STANDARD, null, null, null));
    }

    @Test
    public void copyObjectBucketWithACL()
    {
        CopyObjectRequest request = new CopyObjectRequest(MY_BUCKET, MY_OBJECT, "myBucket2", "myObject2");
        request.setCannedAccessControlList(CannedAccessControlList.Private);
        request.setStorageClass(com.amazonaws.services.s3.model.StorageClass.Standard);

        CopyObjectRequestMatcher matcher = new CopyObjectRequestMatcher(request);
        when(client.copyObject(argThat(matcher))).thenReturn(new CopyObjectResult());

        assertNull(connector.copyObject(MY_BUCKET, MY_OBJECT, null, "myBucket2", "myObject2", PRIVATE,
            org.mule.module.s3.StorageClass.STANDARD, null, null, null));
    }

    @Test
    public void createObjectSimple()
    {
        PutObjectRequest request = new PutObjectRequest(MY_BUCKET, MY_OBJECT, new NullInputStream(0),
            new ObjectMetadata());
        request.setCannedAcl(CannedAccessControlList.Private);
        request.setStorageClass(com.amazonaws.services.s3.model.StorageClass.ReducedRedundancy);

        PutObjectRequestMatcher matcher = new PutObjectRequestMatcher(CannedAccessControlList.Private, com.amazonaws.services.s3.model.StorageClass.ReducedRedundancy);
        when(client.putObject(argThat(matcher))).thenReturn(new PutObjectResult());

        assertNull(connector.createObject(MY_BUCKET, MY_OBJECT, "have a nice release", null, null, null, null,
                PRIVATE, org.mule.module.s3.StorageClass.REDUCED_REDUNDANCY, null));
    }

    @Test
    public void createObjectStringParameter()
    {
        String content = "hello";
        when(
            client.putObject(argThat(new ContentMetadataMatcher(content.length(), "A5B69...", "text/plain")))).thenReturn(
            new PutObjectResult());
        assertNull(connector.createObject(MY_BUCKET, MY_OBJECT, content, null, "A5B69...", "text/plain", null,
            PUBLIC_READ_WRITE, org.mule.module.s3.StorageClass.STANDARD, null));
    }

    @Test
    public void createObjectByteArrayParameter()
    {
        byte[] content = "hello".getBytes();
        when(client.putObject(argThat(new ContentMetadataMatcher(content.length, "A5B69...", null)))).thenReturn(
            new PutObjectResult());
        assertNull(connector.createObject(MY_BUCKET, MY_OBJECT, content, null, "A5B69...", null, null,
            PUBLIC_READ_WRITE, org.mule.module.s3.StorageClass.STANDARD, null));
    }

    @Test
    public void createObjectInputStreamParameter()
    {
        long contentLength = 100L;
        when(client.putObject(argThat(new ContentMetadataMatcher(contentLength, "A5B69...", "text/plain")))).thenReturn(
            new PutObjectResult());
        assertNull(connector.createObject(MY_BUCKET, MY_OBJECT, new NullInputStream(0), contentLength,
            "A5B69...", "text/plain", "attachment; filename=database.dat", PUBLIC_READ_WRITE, org.mule.module.s3.StorageClass.STANDARD, null));
    }

    @Test
    public void createObjectWithFullOptions() throws Exception
    {
        PutObjectRequest request = new PutObjectRequest(MY_BUCKET, MY_OBJECT, new NullInputStream(0),
            new ObjectMetadata());
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        request.setStorageClass(com.amazonaws.services.s3.model.StorageClass.Standard);

        PutObjectRequestMatcher matcher = new PutObjectRequestMatcher(CannedAccessControlList.PublicRead, com.amazonaws.services.s3.model.StorageClass.Standard);
        when(client.putObject(argThat(matcher))).thenReturn(new PutObjectResult());
        assertNull(connector.createObject(MY_BUCKET, MY_OBJECT, "have a nice release", null, null,
                "text/plain", "attachment; filename=database.dat", PUBLIC_READ, org.mule.module.s3.StorageClass.STANDARD, null));
    }

    @Test
    public void getBucketPolicy()
    {
        BucketPolicy policy = new BucketPolicy();
        policy.setPolicyText(POLICY_TEXT);
        when(client.getBucketPolicy(MY_BUCKET)).thenReturn(policy);
        assertSame(POLICY_TEXT, connector.getBucketPolicy(MY_BUCKET));
    }

    @Test
    public void createPresignedUri() throws Exception
    {
        when(client.generatePresignedUrl(MY_BUCKET, MY_OBJECT, null, HttpMethod.GET)).thenReturn(
            new URL("http://www.foo.com"));
        assertEquals(new URI("http://www.foo.com"), connector.createObjectPresignedUri(MY_BUCKET, MY_OBJECT,
            null, null, "GET"));
    }

    @Test
    public void getObjectContent() throws Exception
    {
        S3Object s3Object = mock(S3Object.class);
        S3ObjectInputStream content = new S3ObjectInputStream(new NullInputStream(0), null);
        when(s3Object.getObjectContent()).thenReturn(content);

        GetObjectRequestMatcher matcher = new GetObjectRequestMatcher(MY_BUCKET, MY_OBJECT);
        when(client.getObject(argThat(matcher))).thenReturn(s3Object);
        assertSame(content, connector.getObjectContent(MY_BUCKET, MY_OBJECT, null, null, null));
    }

    @Test
    public void getObjectContentWithVersion() throws Exception
    {
        S3Object s3Object = mock(S3Object.class);
        S3ObjectInputStream content = new S3ObjectInputStream(new NullInputStream(0), null);
        when(s3Object.getObjectContent()).thenReturn(content);

        GetObjectRequestMatcher matcher = new GetObjectRequestMatcher(MY_BUCKET, MY_OBJECT, "9");
        when(client.getObject(argThat(matcher))).thenReturn(s3Object);
        assertSame(content, connector.getObjectContent(MY_BUCKET, MY_OBJECT, "9", null, null));
    }

    @Test
    public void getObjectMetadata() throws Exception
    {
        ObjectMetadata meta = new ObjectMetadata();
        GetObjectMetadataRequestMatcher matcher = new GetObjectMetadataRequestMatcher(MY_BUCKET, MY_OBJECT);
        when(client.getObjectMetadata(argThat(matcher))).thenReturn(meta);
        assertSame(meta, connector.getObjectMetadata(MY_BUCKET, MY_OBJECT, null));
    }

    @Test
    public void getObject() throws Exception
    {
        S3Object s3Object = new S3Object();
        GetObjectRequestMatcher matcher = new GetObjectRequestMatcher(MY_BUCKET, MY_OBJECT);
        when(client.getObject(argThat(matcher))).thenReturn(s3Object);
        assertSame(s3Object, connector.getObject(MY_BUCKET, MY_OBJECT, null, null, null));
    }

    @Test
    public void getObjectWithConstraints() throws Exception
    {
        S3Object s3Object = new S3Object();

        Date date = new Date();
        GetObjectRequestMatcher matcher = new GetObjectRequestMatcher(MY_BUCKET, MY_OBJECT, date);
        when(
            client.getObject(argThat(matcher))).thenReturn(s3Object);
        assertSame(s3Object, connector.getObject(MY_BUCKET, MY_OBJECT, null, null, date));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getObjectWithInconsistentConstraints() throws Exception
    {
        connector.getObject(MY_BUCKET, MY_OBJECT, null, new Date(), new Date());
    }

    @Test
    public void deleteObject() throws Exception
    {
        connector.deleteObject(MY_BUCKET, MY_OBJECT, null);
        verify(client).deleteObject(MY_BUCKET, MY_OBJECT);
    }

    @Test
    public void deleteObjectWithVersion() throws Exception
    {
        connector.deleteObject(MY_BUCKET, MY_OBJECT, "25");
        verify(client).deleteVersion(MY_BUCKET, MY_OBJECT, "25");
    }

    @Test
    public void setBucketPolicy() throws Exception
    {
        connector.setBucketPolicy(MY_BUCKET, POLICY_TEXT);
        verify(client).setBucketPolicy(MY_BUCKET, POLICY_TEXT);
    }

    @Test
    public void listBuckets() throws Exception
    {
        connector.listBuckets();
        verify(client).listBuckets();
    }

    @Test
    public void setBucketWebsiteConfiguration() throws Exception
    {
        connector.setBucketWebsiteConfiguration(MY_BUCKET, "suffix1", "error.do");
        verify(client).setBucketWebsiteConfiguration(eq(MY_BUCKET),
            refEq(new BucketWebsiteConfiguration("suffix1", "error.do")));
    }

    @Test
    public void getBucketWebsiteConfiguration() throws Exception
    {
        BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("suffix");
        when(client.getBucketWebsiteConfiguration(MY_BUCKET)).thenReturn(config);
        assertSame(config, connector.getBucketWebsiteConfiguration(MY_BUCKET));
    }

    @Test
    public void deleteBucketWebsiteConfiguration() throws Exception
    {
        connector.deleteBucketWebsiteConfiguration(MY_BUCKET);
        verify(client).deleteBucketWebsiteConfiguration(MY_BUCKET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBucketWebsiteConfigurationNoSuffix() throws Exception
    {
        connector.setBucketWebsiteConfiguration(MY_BUCKET, null, "error.jsp");
    }

    @Test
    public void deleteBucketPolicy() throws Exception
    {
        connector.deleteBucketPolicy(MY_BUCKET);
        verify(client).deleteBucketPolicy(MY_BUCKET);
    }

    @Test
    public void listObjects() throws Exception
    {
        Iterable<S3ObjectSummary> listObjects = connector.listObjects(MY_BUCKET, "mk");

        ObjectListing firstListing = new ObjectListing();
        firstListing.getObjectSummaries().add(newObjectSummary("key1"));
        firstListing.getObjectSummaries().add(newObjectSummary("key2"));
        firstListing.setTruncated(true);

        ObjectListing secondListing = new ObjectListing();
        secondListing.getObjectSummaries().add(newObjectSummary("key3"));
        secondListing.setTruncated(false);

        when(client.listObjects(eq(MY_BUCKET), eq("mk"))).thenReturn(firstListing);
        when(client.listNextBatchOfObjects(eq(firstListing))).thenReturn(secondListing);

        Iterator<S3ObjectSummary> iter = listObjects.iterator();
        assertTrue(iter.hasNext());
        assertEquals("key1", iter.next().getKey());
        assertEquals("key2", iter.next().getKey());
        assertEquals("key3", iter.next().getKey());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void listVersions() throws Exception
    {
        Iterable<S3VersionSummary> listObjects = connector.listObjectVersions(MY_BUCKET);

        VersionListing firstListing = new VersionListing();
        firstListing.setVersionSummaries(Arrays.asList(newVersionSummary("key1"), newVersionSummary("key2")));
        firstListing.setTruncated(true);

        VersionListing secondListing = new VersionListing();
        secondListing.setVersionSummaries(Collections.singletonList(newVersionSummary("key3")));
        secondListing.setTruncated(false);

        when(client.listVersions(eq(MY_BUCKET), (String) Matchers.isNull())).thenReturn(firstListing);
        when(client.listNextBatchOfVersions(eq(firstListing))).thenReturn(secondListing);

        Iterator<S3VersionSummary> iter = listObjects.iterator();
        assertTrue(iter.hasNext());
        assertEquals("key1", iter.next().getKey());
        assertEquals("key2", iter.next().getKey());
        assertEquals("key3", iter.next().getKey());
        assertFalse(iter.hasNext());
    }

    @Test
    public void setBucketVersioningStatus() throws Exception
    {
        connector.setBucketVersioningStatus(MY_BUCKET, VersioningStatus.ENABLED);

        verify(client).setBucketVersioningConfiguration(
            argThat(new BaseMatcher<SetBucketVersioningConfigurationRequest>()
            {
                public boolean matches(Object item)
                {
                    SetBucketVersioningConfigurationRequest request = (SetBucketVersioningConfigurationRequest) item;
                    return request.getBucketName().equals(MY_BUCKET)
                           & request.getVersioningConfiguration().getStatus().equals("Enabled");
                }

                public void describeTo(Description description)
                {
                }
            }));
    }

    @Test
    public void deleteBucketNoForce() throws Exception
    {
        connector.deleteBucket(MY_BUCKET, false);
        verify(client).deleteBucket(MY_BUCKET);
    }

    @Test
    public void createUriUseDefaultServer() throws Exception
    {
        assertEquals("http://my-bucket.s3.amazonaws.com/myObject", connector.createObjectUri(MY_BUCKET,
            MY_OBJECT, true,false).toString());
    }

    @Test
    public void createObjectUri() throws Exception
    {
        when(client.getBucketLocation(MY_BUCKET)).thenReturn(Region.EU_IRELAND.toS3Equivalent().toString());
        assertEquals("http://my-bucket.s3-external-1.amazonaws.com/myObject", connector.createObjectUri(
            MY_BUCKET, MY_OBJECT, false, false).toString());
    }
    
    @Test
    public void createSecureUri() throws Exception
    {
        when(client.getBucketLocation(MY_BUCKET)).thenReturn(Region.EU_IRELAND.toS3Equivalent().toString());
        assertEquals("https://my-bucket.s3-external-1.amazonaws.com/myObject", connector.createObjectUri(
            MY_BUCKET, MY_OBJECT, false, true).toString());
    }
    
    @Test
    public void testContent() throws Exception
    {
        S3ObjectContent content = S3ContentUtils.createContent(
            new ByteArrayInputStream(new byte[]{20, 30, 6}), null, null);
        assertThat(content, instanceOf(FileS3ObjectContent.class));
        assertNotNull(content.createPutObjectRequest().getFile());
    }

    private S3ObjectSummary newObjectSummary(String key)
    {
        S3ObjectSummary summary = new S3ObjectSummary();
        summary.setKey(key);
        return summary;
    }

    private S3VersionSummary newVersionSummary(String key)
    {
        S3VersionSummary summary = new S3VersionSummary();
        summary.setKey(key);
        return summary;
    }

    
    private final class ContentMetadataMatcher extends BaseMatcher<PutObjectRequest>
    {

        private final long contentLength;
        private final String contentType;
        private final String contentMd5;

        public ContentMetadataMatcher(long contentLength, String contentMd5, String contentType)
        {
            this.contentLength = contentLength;
            this.contentType = contentType;
            this.contentMd5 = contentMd5;
        }

        public boolean matches(Object item)
        {
            PutObjectRequest request = (PutObjectRequest) item;
            ObjectMetadata metadata = request.getMetadata();
            return metadata.getContentLength() == contentLength
                   && ObjectUtils.equals(metadata.getContentType(), contentType)
                   && metadata.getContentMD5().equals(contentMd5);
        }

        public void describeTo(Description description)
        {
        }
    }

    private final class GetObjectRequestMatcher extends TypeSafeMatcher<GetObjectRequest>
    {
        private final String bucketName;
        private final String key;
        private final String versionId;
        private final Date unmodifiedSinceConstraint;

        public GetObjectRequestMatcher(String bucketName, String key)
        {
            this(bucketName, key, null, null);
        }

        public GetObjectRequestMatcher(String bucketName, String key, String versionId)
        {
            this(bucketName, key, versionId, null);
        }

        public GetObjectRequestMatcher(String bucketName, String key, Date unmodifiedSinceConstraint)
        {
            this(bucketName, key, null, unmodifiedSinceConstraint);
        }

        public GetObjectRequestMatcher(String bucketName, String key, String versionId, Date unmodifiedSinceConstraint)
        {
            this.bucketName = bucketName;
            this.key = key;
            this.versionId = versionId;
            this.unmodifiedSinceConstraint = unmodifiedSinceConstraint;
        }

        @Override
        public boolean matchesSafely(GetObjectRequest item)
        {
            return item != null &&
                    bucketName.equals(item.getBucketName()) &&
                    key.equals(item.getKey()) &&
                    (versionId == null || versionId.equals(item.getVersionId())) &&
                    (unmodifiedSinceConstraint == null || unmodifiedSinceConstraint.equals(item.getUnmodifiedSinceConstraint()));
        }

        @Override
        public void describeTo(Description description)
        {
        }
    }

    public final class CreateBucketRequestMatcher extends TypeSafeMatcher<CreateBucketRequest>
    {
        private final String bucketName;
        private final CannedAccessControlList cannedAcl;


        public CreateBucketRequestMatcher(String bucketName, CannedAccessControlList cannedAcl)
        {
            this.bucketName = bucketName;
            this.cannedAcl = cannedAcl;
        }

        @Override
        public boolean matchesSafely(CreateBucketRequest item)
        {
            return cannedAcl.equals(item.getCannedAcl()) && bucketName.equals(item.getBucketName());
        }

        @Override
        public void describeTo(Description description)
        {
        }
    }

    public final class GetObjectMetadataRequestMatcher extends TypeSafeMatcher<GetObjectMetadataRequest>
    {
        private final String bucketName;
        private final String key;


        public GetObjectMetadataRequestMatcher(String bucketName, String key)
        {
            this.bucketName = bucketName;
            this.key = key;
        }

        @Override
        public boolean matchesSafely(GetObjectMetadataRequest item)
        {
            return key.equals(item.getKey()) && bucketName.equals(item.getBucketName());
        }

        @Override
        public void describeTo(Description description)
        {
        }
    }

    public final class PutObjectRequestMatcher extends TypeSafeMatcher<PutObjectRequest>
    {
        private final CannedAccessControlList cannedAcl;
        private final com.amazonaws.services.s3.model.StorageClass storageClass;

        public PutObjectRequestMatcher(CannedAccessControlList cannedAcl, com.amazonaws.services.s3.model.StorageClass storageClass)
        {
            this.cannedAcl = cannedAcl;
            this.storageClass = storageClass;
        }

        @Override
        public boolean matchesSafely(PutObjectRequest item)
        {
            return cannedAcl.equals(item.getCannedAcl()) &&
                    storageClass.equals(com.amazonaws.services.s3.model.StorageClass.fromValue(item.getStorageClass()));
        }

        @Override
        public void describeTo(Description description)
        {
        }
    }

    public final class CopyObjectRequestMatcher extends TypeSafeMatcher<CopyObjectRequest>
    {
        private final CopyObjectRequest request;

        public CopyObjectRequestMatcher(CopyObjectRequest request)
        {
            this.request = request;
        }

        @Override
        public boolean matchesSafely(CopyObjectRequest item)
        {
            return request.getSourceBucketName().equals(item.getSourceBucketName()) &&
                    request.getSourceKey().equals(item.getSourceKey()) &&
                    request.getDestinationBucketName().equals(item.getDestinationBucketName()) &&
                    request.getDestinationKey().equals(item.getDestinationKey()) &&
                    request.getCannedAccessControlList().equals(item.getCannedAccessControlList()) &&
                    request.getStorageClass().equals(item.getStorageClass()) &&
                    StringUtils.equals(request.getSourceVersionId(), item.getSourceVersionId());
        }

        @Override
        public void describeTo(Description description)
        {
        }
    }

}
