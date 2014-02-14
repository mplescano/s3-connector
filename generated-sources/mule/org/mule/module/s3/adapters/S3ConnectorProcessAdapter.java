
package org.mule.module.s3.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.module.s3.S3Connector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>S3ConnectorProcessAdapter</code> is a wrapper around {@link S3Connector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:48:37-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class S3ConnectorProcessAdapter
    extends S3ConnectorLifecycleAdapter
    implements ProcessAdapter<S3ConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, S3ConnectorCapabilitiesAdapter> getProcessTemplate() {
        final S3ConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,S3ConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, S3ConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, S3ConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
