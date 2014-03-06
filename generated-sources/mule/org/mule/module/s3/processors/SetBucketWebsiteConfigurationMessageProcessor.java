
package org.mule.module.s3.processors;

import java.util.List;
import javax.annotation.Generated;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.connectivity.S3ConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * SetBucketWebsiteConfigurationMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#setBucketWebsiteConfiguration(java.lang.String, com.amazonaws.services.s3.model.BucketWebsiteConfiguration)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T11:28:06-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SetBucketWebsiteConfigurationMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object bucketWebsiteConfiguration;
    protected BucketWebsiteConfiguration _bucketWebsiteConfigurationType;

    public SetBucketWebsiteConfigurationMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets bucketName
     * 
     * @param value Value to set
     */
    public void setBucketName(Object value) {
        this.bucketName = value;
    }

    /**
     * Sets bucketWebsiteConfiguration
     * 
     * @param value Value to set
     */
    public void setBucketWebsiteConfiguration(Object value) {
        this.bucketWebsiteConfiguration = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(S3ConnectorConnectionManager.class, true, event);
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, SetBucketWebsiteConfigurationMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final BucketWebsiteConfiguration _transformedBucketWebsiteConfiguration = ((BucketWebsiteConfiguration) evaluateAndTransform(getMuleContext(), event, SetBucketWebsiteConfigurationMessageProcessor.class.getDeclaredField("_bucketWebsiteConfigurationType").getGenericType(), null, bucketWebsiteConfiguration));
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((S3Connector) object).setBucketWebsiteConfiguration(_transformedBucketName, _transformedBucketWebsiteConfiguration);
                    return null;
                }

            }
            , this, event);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

}
