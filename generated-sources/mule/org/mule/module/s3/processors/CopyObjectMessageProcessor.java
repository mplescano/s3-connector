
package org.mule.module.s3.processors;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.s3.AccessControlList;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.StorageClass;
import org.mule.module.s3.connectivity.S3ConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * CopyObjectMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#copyObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.mule.module.s3.AccessControlList, org.mule.module.s3.StorageClass, java.util.Map, java.util.Date, java.util.Date, java.lang.String)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T02:18:49-05:00", comments = "Build M4.1875.17b58a3")
public class CopyObjectMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object sourceBucketName;
    protected String _sourceBucketNameType;
    protected Object sourceKey;
    protected String _sourceKeyType;
    protected Object sourceVersionId;
    protected String _sourceVersionIdType;
    protected Object destinationBucketName;
    protected String _destinationBucketNameType;
    protected Object destinationKey;
    protected String _destinationKeyType;
    protected Object destinationAcl;
    protected AccessControlList _destinationAclType;
    protected Object destinationStorageClass;
    protected StorageClass _destinationStorageClassType;
    protected Object destinationUserMetadata;
    protected Map<String, String> _destinationUserMetadataType;
    protected Object modifiedSince;
    protected Date _modifiedSinceType;
    protected Object unmodifiedSince;
    protected Date _unmodifiedSinceType;
    protected Object encryption;
    protected String _encryptionType;

    public CopyObjectMessageProcessor(String operationName) {
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
     * Sets sourceKey
     * 
     * @param value Value to set
     */
    public void setSourceKey(Object value) {
        this.sourceKey = value;
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
     * Sets destinationKey
     * 
     * @param value Value to set
     */
    public void setDestinationKey(Object value) {
        this.destinationKey = value;
    }

    /**
     * Sets encryption
     * 
     * @param value Value to set
     */
    public void setEncryption(Object value) {
        this.encryption = value;
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
     * Sets sourceBucketName
     * 
     * @param value Value to set
     */
    public void setSourceBucketName(Object value) {
        this.sourceBucketName = value;
    }

    /**
     * Sets destinationStorageClass
     * 
     * @param value Value to set
     */
    public void setDestinationStorageClass(Object value) {
        this.destinationStorageClass = value;
    }

    /**
     * Sets destinationBucketName
     * 
     * @param value Value to set
     */
    public void setDestinationBucketName(Object value) {
        this.destinationBucketName = value;
    }

    /**
     * Sets sourceVersionId
     * 
     * @param value Value to set
     */
    public void setSourceVersionId(Object value) {
        this.sourceVersionId = value;
    }

    /**
     * Sets destinationAcl
     * 
     * @param value Value to set
     */
    public void setDestinationAcl(Object value) {
        this.destinationAcl = value;
    }

    /**
     * Sets destinationUserMetadata
     * 
     * @param value Value to set
     */
    public void setDestinationUserMetadata(Object value) {
        this.destinationUserMetadata = value;
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
            final String _transformedSourceBucketName = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_sourceBucketNameType").getGenericType(), null, sourceBucketName));
            final String _transformedSourceKey = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_sourceKeyType").getGenericType(), null, sourceKey));
            final String _transformedSourceVersionId = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_sourceVersionIdType").getGenericType(), null, sourceVersionId));
            final String _transformedDestinationBucketName = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_destinationBucketNameType").getGenericType(), null, destinationBucketName));
            final String _transformedDestinationKey = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_destinationKeyType").getGenericType(), null, destinationKey));
            final AccessControlList _transformedDestinationAcl = ((AccessControlList) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_destinationAclType").getGenericType(), null, destinationAcl));
            final StorageClass _transformedDestinationStorageClass = ((StorageClass) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_destinationStorageClassType").getGenericType(), null, destinationStorageClass));
            final Map<String, String> _transformedDestinationUserMetadata = ((Map<String, String> ) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_destinationUserMetadataType").getGenericType(), null, destinationUserMetadata));
            final Date _transformedModifiedSince = ((Date) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_modifiedSinceType").getGenericType(), null, modifiedSince));
            final Date _transformedUnmodifiedSince = ((Date) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_unmodifiedSinceType").getGenericType(), null, unmodifiedSince));
            final String _transformedEncryption = ((String) evaluateAndTransform(getMuleContext(), event, CopyObjectMessageProcessor.class.getDeclaredField("_encryptionType").getGenericType(), null, encryption));
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
                    return ((S3Connector) object).copyObject(_transformedSourceBucketName, _transformedSourceKey, _transformedSourceVersionId, _transformedDestinationBucketName, _transformedDestinationKey, _transformedDestinationAcl, _transformedDestinationStorageClass, _transformedDestinationUserMetadata, _transformedModifiedSince, _transformedUnmodifiedSince, _transformedEncryption);
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
