
package org.mule.module.s3.processors;

import java.util.List;
import javax.annotation.Generated;
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
 * SetBucketPolicyMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#setBucketPolicy(java.lang.String, java.lang.String)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-27T12:22:35-05:00", comments = "Build M4.1875.17b58a3")
public class SetBucketPolicyMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object policyText;
    protected String _policyTextType;

    public SetBucketPolicyMessageProcessor(String operationName) {
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
     * Sets policyText
     * 
     * @param value Value to set
     */
    public void setPolicyText(Object value) {
        this.policyText = value;
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
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, SetBucketPolicyMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final String _transformedPolicyText = ((String) evaluateAndTransform(getMuleContext(), event, SetBucketPolicyMessageProcessor.class.getDeclaredField("_policyTextType").getGenericType(), null, policyText));
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
                    ((S3Connector) object).setBucketPolicy(_transformedBucketName, _transformedPolicyText);
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
