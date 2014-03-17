/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3;

import org.mule.module.s3.simpleapi.SimpleAmazonS3.S3ObjectContent;
import org.mule.module.s3.simpleapi.content.FileS3ObjectContent;
import org.mule.module.s3.simpleapi.content.InputStreamS3ObjectContent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.commons.httpclient.Header;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;

public final class S3ContentUtils
{
    private S3ContentUtils()
    {
    }
    
    /**
     * Creates the {@link S3ObjectContent}. If content is a String or file, the
     * content length parameter is ignored. Also contentMD5 is ignored if content is
     * a file, too.
     */
    public static S3ObjectContent createContent(Object content, Long contentLength, String contentMd5)
    {
        if (content instanceof InputStream)
        {
            InputStream streamContent = (InputStream) content;
            if (contentLength != null)
            {
                return createContent(streamContent, contentLength, contentMd5);
            }
            Header contentLengthHeader = getContentLengthHeader(content);
            if (contentLengthHeader != null)
            {
                return createContent(streamContent, Long.parseLong(contentLengthHeader.getValue()),
                    contentMd5);
            }
            File tempFile = toTempFile(streamContent);
            long streamLength = tempFile.length();
            tempFile.delete();
            return createContent(streamContent, streamLength, contentMd5);
            
        }
        if (content instanceof String)
        {
            return createContent(contentMd5, (String) content);
        }
        if (content instanceof byte[])
        {
            return createContent((byte[]) content, contentMd5);
        }
        if (content instanceof File)
        {
            return createContent((File) content);
        }
        throw new IllegalArgumentException("Wrong input");
    }

    private static File toTempFile(InputStream streamContent)
    {
        try
        {
            File tempFile = File.createTempFile("mules3", ".tmp");
            IOUtils.copy(streamContent, new FileOutputStream(tempFile));
            return tempFile;
        }
        catch (IOException e)
        {
            throw new UnhandledException(e);
        }
    }

    private static S3ObjectContent createContent(File content)
    {
        return new FileS3ObjectContent(content);
    }

    private static Header getContentLengthHeader(Object content)
    {
        if (content.getClass().getName().equals("org.mule.transport.http.ReleasingInputStream"))
        {
            return getHttpMethod(content).getResponseHeader("Content-Length");
        }
        return null;
    }

    private static S3ObjectContent createContent(byte[] content, String contentMd5)
    {
        return new InputStreamS3ObjectContent(new ByteArrayInputStream((byte[]) content),
            (long) content.length, contentMd5);
    }

    private static S3ObjectContent createContent(String contentMd5, String stringContent)
    {
        return new InputStreamS3ObjectContent(new ByteArrayInputStream(stringContent.getBytes()),
            (long) stringContent.length(), contentMd5);
    }

    private static S3ObjectContent createContent(InputStream content, Long contentLength, String contentMd5)
    {
        return new InputStreamS3ObjectContent((InputStream) content, contentLength, contentMd5);
    }

    private static org.apache.commons.httpclient.HttpMethod getHttpMethod(Object inputStream)
    {
        try
        {
            Field field = Class.forName("org.mule.transport.http.ReleasingInputStream").getDeclaredField(
                "method");
            field.setAccessible(true);
            return (org.apache.commons.httpclient.HttpMethod) field.get(inputStream);
        }
        catch (Exception e)
        {
            throw new UnhandledException(e);
        }
    }

}
