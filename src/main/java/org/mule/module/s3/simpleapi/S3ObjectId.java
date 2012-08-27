/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

/**
 * An {@link S3ObjectId} is a unique identifier of an S3Object, with optional
 * versioning support
 */
public class S3ObjectId
{
    private final String bucketName;
    private final String key;
    private final String versionId;

    /**
     * Creates and {@link S3ObjectId} with a null version id. Use this constructor if
     * accessing an object for which versioning was not enabled, or if requesting
     * head version
     * 
     * @param bucketName not empty
     * @param key not empty
     */
    public S3ObjectId(@NotNull String bucketName, @NotNull String key)
    {
        this(bucketName, key, null);
    }

    /**
     * Creates an {@link S3ObjectId} specifying the optional version id. Use this
     * constructor to access a specific version when if accessing objects with
     * versioning support support. Passing a null version is equivalent to
     * {@link #ObjectId(String, String)}
     * 
     * @param bucketName not empty
     * @param key not empty
     * @param versionId optional. not empty
     */
    public S3ObjectId(@NotNull String bucketName, @NotNull String key, String versionId)
    {
        Validate.notEmpty(bucketName);
        Validate.notEmpty(key);
        if (versionId != null)
        {
            Validate.notEmpty(versionId);
        }

        this.bucketName = bucketName;
        this.key = key;
        this.versionId = versionId;
    }

    public String getBucketName()
    {
        return bucketName;
    }

    public String getKey()
    {
        return key;
    }

    public String getVersionId()
    {
        return versionId;
    }

    public boolean isVersioned()
    {
        return versionId != null;
    }
}
