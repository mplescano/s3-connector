/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi;

import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

/**
 * Conditional Get Constraints
 */
public final class ConditionalConstraints
{
    /**A constant constraint for a common scenario where no constraints are specified at all*/
    private static final ConditionalConstraints EMPTY_CONSTRAINTS = new ConditionalConstraints(null, null);
    private final Date modifiedSince;
    private final Date unmodifiedSince;

    private ConditionalConstraints(Date modifiedSince, Date unmodifiedSince)
    {
        Validate.isTrue(modifiedSince == null || unmodifiedSince == null,
            "Specify either modifiedSince or umodifiedSince");
        this.modifiedSince = modifiedSince;
        this.unmodifiedSince = unmodifiedSince;
    }

    public void populate(@NotNull GetObjectRequest request)
    {
        request.setModifiedSinceConstraint(modifiedSince);
        request.setUnmodifiedSinceConstraint(unmodifiedSince);
    }

    public void populate(@NotNull CopyObjectRequest request)
    {
        request.setModifiedSinceConstraint(modifiedSince);
        request.setUnmodifiedSinceConstraint(unmodifiedSince);
    }

    public static ConditionalConstraints from(Date modifiedSince, Date unmodifiedSince)
    {
        if (modifiedSince == null && unmodifiedSince == null)
        {
            return EMPTY_CONSTRAINTS;
        }
        return new ConditionalConstraints(modifiedSince, unmodifiedSince);
    }

}
