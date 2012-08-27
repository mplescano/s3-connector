/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */


package org.mule.module.s3;

import static com.amazonaws.services.s3.model.StorageClass.ReducedRedundancy;
import static com.amazonaws.services.s3.model.StorageClass.Standard;

/**
 * A wrapper over {@link com.amazonaws.services.s3.model.StorageClass}, as a
 * workaround over mule cloud connector api issue regarding enums processing
 */
public enum StorageClass
{
    STANDARD(Standard),

    REDUCED_REDUNDANCY(ReducedRedundancy);

    private final com.amazonaws.services.s3.model.StorageClass s3Equivalent;

    StorageClass(com.amazonaws.services.s3.model.StorageClass s3Equivalent)
    {
        this.s3Equivalent = s3Equivalent;
    }

    public com.amazonaws.services.s3.model.StorageClass toS3Equivalent()
    {
        return s3Equivalent;
    }

}
