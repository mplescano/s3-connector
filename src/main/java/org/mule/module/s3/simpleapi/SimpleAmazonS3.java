/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * A Amazon S3 facade roughly based on {@link com.amazonaws.services.s3.AmazonS3}
 * interface, but that simplifies it by adding consistent versioning support.
 * Otherwise stated, all messages that take {@link S3ObjectId} are aware of
 * versioning, that is, if {@link S3ObjectId#isVersioned()}, then operations try to
 * affect the specified version.
 * <p>
 * Not all messages of {@link com.amazonaws.services.s3.AmazonS3} interface are
 * exposed here. However, those exposed share the same semantics of that interface.
 * </p>
 * Exception handling:
 * <ul>
 * <li>All operations will throw {@link IllegalArgumentException} if a non null or
 * non empty constraint is violated</li>
 * <li>All operations will throw {@link com.amazonaws.AmazonServiceException} if a s3
 * restriction is violated, like for example, trying to create a bucket without
 * permissions</li>
 * <li>All operation will throw {@link com.amazonaws.AmazonClientException} if any
 * communication or unexpected error occurs</li>
 * </ul>
 */
public interface SimpleAmazonS3
{
    @NotNull
    List<Bucket> listBuckets();

    /**
     * Creates a {@link Bucket}.
     * 
     * @see AmazonS3#createBucket(com.amazonaws.services.s3.model.CreateBucketRequest)
     * @param bucketName mandatory
     * @param region optional
     * @param acl optional
     * @return the new Bucket
     * @throws com.amazonaws.AmazonServiceException
     */
    @NotNull
    Bucket createBucket(@NotNull String bucketName, Region region, CannedAccessControlList acl);

    /**
     * Deletes a Bucket
     * 
     * @see AmazonS3#deleteBucket(com.amazonaws.services.s3.model.DeleteBucketRequest)
     * @param bucketName
     */
    void deleteBucket(@NotNull String bucketName);

    /**
     * Deletes a Bucket, deleting also all its contents if necessary
     * 
     * @see AmazonS3#deleteBucket(com.amazonaws.services.s3.model.DeleteBucketRequest)
     * @param bucketName
     */
    void deleteBucketAndObjects(@NotNull String bucketName);

    @NotNull
    Iterable<S3ObjectSummary> listObjects(@NotNull String bucketName, String prefix);
    
    @NotNull
    Iterable<S3VersionSummary> listObjectVersions(@NotNull String bucketName);
    
    void deleteBucketPolicy(@NotNull String bucketName);

    /**
     * Answers the bucket policy, or null, if not set .
     * 
     * @param bucketName
     * @return the nullable bucket policy text
     */
    String getBucketPolicy(@NotNull String bucketName);

    void setBucketPolicy(@NotNull String bucketName, @NotNull String policyText);

    void deleteBucketWebsiteConfiguration(@NotNull String bucketName);

    /**
     * Returns the versioning configuration for the specified bucket.
     * 
     * @param bucketName The bucket whose versioning configuration will be retrieved.
     * @return The bucket versioning configuration for the specified bucket.
     */
    @NotNull
    BucketWebsiteConfiguration getBucketWebsiteConfiguration(@NotNull String bucketName);

    /**
     * Sets the website configuration for the specified bucket.
     * 
     * @param bucketName
     * @param configuration
     * @see AmazonS3#setBucketWebsiteConfiguration(String,
     *      BucketWebsiteConfiguration)
     */
    void setBucketWebsiteConfiguration(@NotNull String bucketName,
                                       @NotNull BucketWebsiteConfiguration configuration);

    //4.1
    String createObject(@NotNull S3ObjectId objectId,
                        @NotNull S3ObjectContent content,
                        String contentType,
                        String contentDisposition,
                        CannedAccessControlList acl,
                        StorageClass storageClass,
                        ObjectMetadata objectMetadata,
                        Map<String, String> userMetadata);

    /**
     * Deletes an object.
     * 
     * @param objectId
     */
    void deleteObject(@NotNull S3ObjectId objectId);

    /**
     * Deletes all objects
     *
     * @param bucketName mandatory bucket name
     * @param keys keys to delete, with optional version
     *
     * @throws com.amazonaws.services.s3.model.MultiObjectDeleteException
     *             if one or more of the objects couldn't be deleted.
     * @throws com.amazonaws.AmazonClientException
     *             If any errors are encountered in the client while making the
     *             request or handling the response.
     * @throws com.amazonaws.AmazonServiceException
     *             If any errors occurred in Amazon S3 while processing the
     *             request.
     */
    void deleteObjects(@NotNull String bucketName, List<KeyVersion> keys);

    void setObjectStorageClass(@NotNull S3ObjectId objectId, @NotNull StorageClass newStorageClass);

    /**
     * Copies a source object, with optional version, to a destination, with optional
     * destination object acl.
     *
     * @param source
     * @param destination the destination object. If this id is versioned, its
     *            version is ignored
     * @param conditionalConstraints the constraints to be matched in order to proceed with copy.
     *          If not matched, no copy is performed
     * @param acl
     * @param objectMetadata
     * @param destinationUserMetadata
     * @return the version id of the destination object, if versioning is enabled
     * @see AmazonS3#copyObject(com.amazonaws.services.s3.model.CopyObjectRequest)
     */
    String copyObject(@NotNull S3ObjectId source,
                      @NotNull S3ObjectId destination,
                      @NotNull ConditionalConstraints conditionalConstraints,
                      CannedAccessControlList acl, 
                      StorageClass storageClass,
                      ObjectMetadata objectMetadata,
                      Map<String, String> destinationUserMetadata);

    /**
     * Creates a presigned URL for accessing the object of the given id, with an
     * optional http method and date expiration.
     * 
     * @param objectId
     * @param expiration if no expiration is supplied, a default expiration provided
     *            by AmazonS3 will be used
     * @param method if no method is supplied, PUT method is assumed
     * @see AmazonS3#generatePresignedUrl(com.amazonaws.services.s3.model.
     *      GeneratePresignedUrlRequest)
     */
    @NotNull
    URI createObjectPresignedUri(@NotNull S3ObjectId objectId, Date expiration, HttpMethod method);

    /**
     * Answers the object content a given {@link S3ObjectId}.
     * 
     * @param objectId
     * @param modifiedSince
     * @param unmodifiedSince
     * @return an input stream to the contents of the object, or null, if there
     *         conditional request contraints were not met
     * @see AmazonS3#getObject(com.amazonaws.services.s3.model.GetObjectMetadataRequest)
     */
    S3ObjectInputStream getObjectContent(@NotNull S3ObjectId objectId, @NotNull ConditionalConstraints conditionalConstraints);

    /**
     * Retrieves an object from S3 given its id. <strong>Warning: use this method
     * with caution</strong>, as the retrieved object has an already open inputStream
     * to the object contents. It should be closed quickly.
     * 
     * @param objectId
     * @param unmodifiedSince
     * @param modifiedSince
     * @return the object, or null, if conditional request constraints were not met
     * @see AmazonS3#getObject(com.amazonaws.services.s3.model.GetObjectRequest)
     */
    S3Object getObject(@NotNull S3ObjectId objectId, @NotNull ConditionalConstraints conditionalConstraints);

    /**
     * Answers the ObjectMetadata for a given {@link S3ObjectId}
     * 
     * @param objectId
     * @return
     * @see AmazonS3#getObjectMetadata(com.amazonaws.services.s3.model.GetObjectMetadataRequest)
     */
    @NotNull
    ObjectMetadata getObjectMetadata(@NotNull S3ObjectId objectId);

    void setBucketVersioningStatus(@NotNull String bucketName, @NotNull VersioningStatus versioningStatus);

    @NotNull
    URI createObjectUriUsingDefaultServer(@NotNull S3ObjectId objectId, boolean secure);

    @NotNull
    URI createObjectUri(@NotNull S3ObjectId objectId, boolean secure);

    /**
     * The content to be uploaded to S3, capable of creating a
     * {@link PutObjectRequest}.
     */
    public interface S3ObjectContent
    {
        /**
         * Creates a {@link PutObjectRequest}, which grants to have its metadata
         * object and its inputstream (or file), populated.
         * 
         * @return a new {@link PutObjectRequest}
         */
        PutObjectRequest createPutObjectRequest();
    }
    /**
     * Returns the bucket versioning configuration for the specified bucket.
     * 
     * @param bucketName The bucket versioning configuration for the specified bucket.
     * @return The bucket versioning configuration for the specified bucket.
     */
	public BucketVersioningConfiguration getBucketVersioningConfiguration(
			@NotNull String bucketName);

}
