/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */


package org.mule.module.s3.simpleapi;

/**
 * An {@link KeyVersion} is a unique identifier of an S3Object inside of a bucket, with optional
 * versioning support
 */
public class KeyVersion
{
    private String value;
    private String version;

    public KeyVersion()
    {}

    /**
     * Constructs a key without a version.
     */
    public KeyVersion(String value)
    {
        this(value, null);
    }

    /**
     * Constructs a key-version pair.
     */
    public KeyVersion(String value, String version)
    {
        this.value = value;
        this.version = version;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
