/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3;

import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.tck.FunctionalTestCase;

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

    public void testUpload() throws Exception
    {
        final MuleEvent event = getTestEvent("test");
        final Flow flow = lookupFlowConstruct("PublishFlow");
        flow.process(event);
    }

    private Flow lookupFlowConstruct(final String name)
    {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
