/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi;

import static com.amazonaws.services.s3.model.Region.AP_Singapore;
import static com.amazonaws.services.s3.model.Region.EU_Ireland;
import static com.amazonaws.services.s3.model.Region.US_Standard;
import static com.amazonaws.services.s3.model.Region.US_West;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.ObjectUtils;

public enum Region
{

    US_STANDARD(US_Standard, "s3.amazonaws.com"),

    US_WEST(US_West, "s3-us-west-1.amazonaws.com"),

    EU_IRELAND(EU_Ireland, "s3-external-1.amazonaws.com"),

    AP_SINGAPORE(AP_Singapore, "s3-ap-southeast-1.amazonaws.com");

    private final com.amazonaws.services.s3.model.Region s3Equivalent;

    private final String domain;

    private Region(com.amazonaws.services.s3.model.Region s3Equivalent, String domain)
    {
        this.s3Equivalent = s3Equivalent;
        this.domain = domain;
    }

    public com.amazonaws.services.s3.model.Region toS3Equivalent()
    {
        return s3Equivalent;
    }

    public URI getObjectUri(S3ObjectId objectId, boolean secure)
    {
    	String scheme = secure?"https":"http";
        try
        {
            return new URI(String.format("%s://%s.%s/%s", scheme, objectId.getBucketName(), domain,
                objectId.getKey()));
        }
        catch (URISyntaxException e)
        {
            throw new AssertionError(e);
        }
    }

    public static Region getDefaultRegion()
    {
        return US_STANDARD;
    }

    public static Region from(String location)
    {
        for (Region region : Region.values())
        {
            if (ObjectUtils.equals(location, region.toS3Equivalent().toString()))
            {
                return region;
            }
        }
        return US_STANDARD;
    }
}
