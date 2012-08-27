/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi;

import com.amazonaws.services.s3.model.BucketVersioningConfiguration;

/**
 * The posible versioning status.
 */
public enum VersioningStatus
{
    OFF(BucketVersioningConfiguration.OFF), ENABLED(BucketVersioningConfiguration.ENABLED), SUSPENDED(
                    BucketVersioningConfiguration.SUSPENDED);

    private String versioningStatusString;

    private VersioningStatus(String s3Equivalent)
    {
        this.versioningStatusString = s3Equivalent;
    }

    public String toString()
    {
        return versioningStatusString;
    }

}
