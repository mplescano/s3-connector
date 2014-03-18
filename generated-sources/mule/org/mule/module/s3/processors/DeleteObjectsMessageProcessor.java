
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
import org.mule.module.s3.simpleapi.KeyVersion;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * DeleteObjectsMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#deleteObjects(java.lang.String, java.util.List<org.mule.module.s3.simpleapi.KeyVersion>)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-18T08:21:51-05:00", comments = "Build M4.1875.17b58a3")
public class DeleteObjectsMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object keys;
    protected List<KeyVersion> _keysType;

    public DeleteObjectsMessageProcessor(String operationName) {
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
     * Sets keys
     * 
     * @param value Value to set
     */
    public void setKeys(Object value) {
        this.keys = value;
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
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, DeleteObjectsMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final List<KeyVersion> _transformedKeys = ((List<KeyVersion> ) evaluateAndTransform(getMuleContext(), event, DeleteObjectsMessageProcessor.class.getDeclaredField("_keysType").getGenericType(), null, keys));
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
                    ((S3Connector) object).deleteObjects(_transformedBucketName, _transformedKeys);
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
