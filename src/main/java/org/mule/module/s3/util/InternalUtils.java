/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.util;

/**
 * Hub for some misc class methods
 */
public final class InternalUtils
{
    private InternalUtils()
    {
    }

    public static <T> T coalesce(T o0, T o1)
    {
        return o0 != null ? o0 : o1;
    }
}
