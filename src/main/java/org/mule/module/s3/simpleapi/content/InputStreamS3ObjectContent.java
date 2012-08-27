/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi.content;

import static org.mule.module.s3.util.InternalUtils.coalesce;

import org.mule.module.s3.simpleapi.SimpleAmazonS3.S3ObjectContent;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.InputStream;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

public class InputStreamS3ObjectContent implements S3ObjectContent
{
    private final InputStream inputStream;
    private final long length;
    private final String md5base64;

    public InputStreamS3ObjectContent(@NotNull InputStream inputStream, Long length, String md5base64)
    {
        Validate.notNull(inputStream);
        this.inputStream = inputStream;
        this.length = coalesce(length, 0L);
        this.md5base64 = md5base64;
    }

    public PutObjectRequest createPutObjectRequest()
    {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        if (md5base64 != null)
        {
            metadata.setContentMD5(md5base64);
        }
        return new PutObjectRequest(null, null, inputStream, metadata);
    }

}
