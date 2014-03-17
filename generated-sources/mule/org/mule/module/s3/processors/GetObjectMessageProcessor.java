
package org.mule.module.s3.processors;

import java.util.Date;
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
 * GetObjectMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#getObject(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T07:15:49-05:00", comments = "Build M4.1875.17b58a3")
public class GetObjectMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object key;
    protected String _keyType;
    protected Object versionId;
    protected String _versionIdType;
    protected Object modifiedSince;
    protected Date _modifiedSinceType;
    protected Object unmodifiedSince;
    protected Date _unmodifiedSinceType;

    public GetObjectMessageProcessor(String operationName) {
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
     * Sets unmodifiedSince
     * 
     * @param value Value to set
     */
    public void setUnmodifiedSince(Object value) {
        this.unmodifiedSince = value;
    }

    /**
     * Sets modifiedSince
     * 
     * @param value Value to set
     */
    public void setModifiedSince(Object value) {
        this.modifiedSince = value;
    }

    /**
     * Sets versionId
     * 
     * @param value Value to set
     */
    public void setVersionId(Object value) {
        this.versionId = value;
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
     * Sets key
     * 
     * @param value Value to set
     */
    public void setKey(Object value) {
        this.key = value;
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
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, GetObjectMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final String _transformedKey = ((String) evaluateAndTransform(getMuleContext(), event, GetObjectMessageProcessor.class.getDeclaredField("_keyType").getGenericType(), null, key));
            final String _transformedVersionId = ((String) evaluateAndTransform(getMuleContext(), event, GetObjectMessageProcessor.class.getDeclaredField("_versionIdType").getGenericType(), null, versionId));
            final Date _transformedModifiedSince = ((Date) evaluateAndTransform(getMuleContext(), event, GetObjectMessageProcessor.class.getDeclaredField("_modifiedSinceType").getGenericType(), null, modifiedSince));
            final Date _transformedUnmodifiedSince = ((Date) evaluateAndTransform(getMuleContext(), event, GetObjectMessageProcessor.class.getDeclaredField("_unmodifiedSinceType").getGenericType(), null, unmodifiedSince));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((S3Connector) object).getObject(_transformedBucketName, _transformedKey, _transformedVersionId, _transformedModifiedSince, _transformedUnmodifiedSince);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

}
