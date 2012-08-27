/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi.content;

import org.mule.module.s3.simpleapi.SimpleAmazonS3.S3ObjectContent;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

public class FileS3ObjectContent implements S3ObjectContent
{
    private final File file;

    public FileS3ObjectContent(@NotNull File file)
    {
        Validate.notNull(file);
        this.file = file;
    }

    public PutObjectRequest createPutObjectRequest()
    {
        PutObjectRequest request = new PutObjectRequest(null, null, file);
        request.setMetadata(new ObjectMetadata());
        return request;
    }
}
