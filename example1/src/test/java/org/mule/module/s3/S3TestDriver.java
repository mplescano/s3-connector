/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3;

import com.amazonaws.services.s3.model.S3VersionSummary;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.tck.FunctionalTestCase;

import java.util.Collection;

public class S3TestDriver extends FunctionalTestCase
{
    /*
     * This test needs the following System properties to be set: s3.accessKey,
     * s3.secetKey and s3.bucketName.
     */

    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    /**
     * Run this test to upload new versions of 
     * http://www.mulesoft.com:80/images/index/front-esb.jpg 
     * to the bucket. 
     */
    @SuppressWarnings("unchecked")
    public void testUpload() throws Exception
    {
        final MuleEvent event = getTestEvent("");
        final Flow flow = lookupFlowConstruct("UploadFlow");
        final MuleEvent responseEvent = flow.process(event);
        assertTrue(responseEvent.getMessage().getPayload() instanceof Collection<?>);
        for(S3VersionSummary summary : (Collection<S3VersionSummary>)responseEvent.getMessage().getPayload())
        {
            System.out.println(ToStringBuilder.reflectionToString(summary));
        }
    }

    /**
     * Run this test only once in order to create a bucket with versioning 
     * enabled
     */
    public void ignoreTestSetup() throws Exception
    {
        final MuleEvent event = getTestEvent("");
        final Flow flow = lookupFlowConstruct("SetupFlow");
        flow.process(event);
    }


    private Flow lookupFlowConstruct(final String name)
    {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
