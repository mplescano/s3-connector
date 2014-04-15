
package org.mule.module.s3.processors;

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
 * CreateObjectMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#createObject(java.lang.String, java.lang.String, java.lang.Object, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, org.mule.module.s3.AccessControlList, org.mule.module.s3.StorageClass, java.util.Map, java.lang.String)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T08:28:25-05:00", comments = "Build master.1915.dd1962d")
public class CreateObjectMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object key;
    protected String _keyType;
    protected Object content;
    protected Object _contentType;
    protected Object contentLength;
    protected Long _contentLengthType;
    protected Object contentMd5;
    protected String _contentMd5Type;
    protected Object contentType;
    protected String _contentTypeType;
    protected Object contentDisposition;
    protected String _contentDispositionType;
    protected Object acl;
    protected AccessControlList _aclType;
    protected Object storageClass;
    protected StorageClass _storageClassType;
    protected Object userMetadata;
    protected Map<String, String> _userMetadataType;
    protected Object encryption;
    protected String _encryptionType;

    public CreateObjectMessageProcessor(String operationName) {
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
     * Sets content
     * 
     * @param value Value to set
     */
    public void setContent(Object value) {
        this.content = value;
    }

    /**
     * Sets storageClass
     * 
     * @param value Value to set
     */
    public void setStorageClass(Object value) {
        this.storageClass = value;
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
     * Sets acl
     * 
     * @param value Value to set
     */
    public void setAcl(Object value) {
        this.acl = value;
    }

    /**
     * Sets userMetadata
     * 
     * @param value Value to set
     */
    public void setUserMetadata(Object value) {
        this.userMetadata = value;
    }

    /**
     * Sets contentType
     * 
     * @param value Value to set
     */
    public void setContentType(Object value) {
        this.contentType = value;
    }

    /**
     * Sets contentMd5
     * 
     * @param value Value to set
     */
    public void setContentMd5(Object value) {
        this.contentMd5 = value;
    }

    /**
     * Sets contentDisposition
     * 
     * @param value Value to set
     */
    public void setContentDisposition(Object value) {
        this.contentDisposition = value;
    }

    /**
     * Sets contentLength
     * 
     * @param value Value to set
     */
    public void setContentLength(Object value) {
        this.contentLength = value;
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
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final String _transformedKey = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_keyType").getGenericType(), null, key));
            final Object _transformedContent = ((Object) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_contentType").getGenericType(), null, content));
            final Long _transformedContentLength = ((Long) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_contentLengthType").getGenericType(), null, contentLength));
            final String _transformedContentMd5 = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_contentMd5Type").getGenericType(), null, contentMd5));
            final String _transformedContentType = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_contentTypeType").getGenericType(), null, contentType));
            final String _transformedContentDisposition = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_contentDispositionType").getGenericType(), null, contentDisposition));
            final AccessControlList _transformedAcl = ((AccessControlList) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_aclType").getGenericType(), null, acl));
            final StorageClass _transformedStorageClass = ((StorageClass) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_storageClassType").getGenericType(), null, storageClass));
            final Map<String, String> _transformedUserMetadata = ((Map<String, String> ) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_userMetadataType").getGenericType(), null, userMetadata));
            final String _transformedEncryption = ((String) evaluateAndTransform(getMuleContext(), event, CreateObjectMessageProcessor.class.getDeclaredField("_encryptionType").getGenericType(), null, encryption));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
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
                    return ((S3Connector) object).createObject(_transformedBucketName, _transformedKey, _transformedContent, _transformedContentLength, _transformedContentMd5, _transformedContentType, _transformedContentDisposition, _transformedAcl, _transformedStorageClass, _transformedUserMetadata, _transformedEncryption);
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
