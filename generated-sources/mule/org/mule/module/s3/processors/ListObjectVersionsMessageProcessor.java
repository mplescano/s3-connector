
package org.mule.module.s3.processors;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.s3.EncodingType;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.connectivity.S3ConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * ListObjectVersionsMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#listObjectVersions(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, org.mule.module.s3.EncodingType)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-14T01:28:01-05:00", comments = "Build M4.1875.17b58a3")
public class ListObjectVersionsMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object bucketName;
    protected String _bucketNameType;
    protected Object prefix;
    protected String _prefixType;
    protected Object keyMarker;
    protected String _keyMarkerType;
    protected Object versionIdMarker;
    protected String _versionIdMarkerType;
    protected Object delimiter;
    protected String _delimiterType;
    protected Object maxResults;
    protected Integer _maxResultsType;
    protected Object encodingType;
    protected EncodingType _encodingTypeType;

    public ListObjectVersionsMessageProcessor(String operationName) {
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
     * Sets delimiter
     * 
     * @param value Value to set
     */
    public void setDelimiter(Object value) {
        this.delimiter = value;
    }

    /**
     * Sets keyMarker
     * 
     * @param value Value to set
     */
    public void setKeyMarker(Object value) {
        this.keyMarker = value;
    }

    /**
     * Sets encodingType
     * 
     * @param value Value to set
     */
    public void setEncodingType(Object value) {
        this.encodingType = value;
    }

    /**
     * Sets prefix
     * 
     * @param value Value to set
     */
    public void setPrefix(Object value) {
        this.prefix = value;
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
     * Sets maxResults
     * 
     * @param value Value to set
     */
    public void setMaxResults(Object value) {
        this.maxResults = value;
    }

    /**
     * Sets versionIdMarker
     * 
     * @param value Value to set
     */
    public void setVersionIdMarker(Object value) {
        this.versionIdMarker = value;
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
            final String _transformedBucketName = ((String) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            final String _transformedPrefix = ((String) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_prefixType").getGenericType(), null, prefix));
            final String _transformedKeyMarker = ((String) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_keyMarkerType").getGenericType(), null, keyMarker));
            final String _transformedVersionIdMarker = ((String) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_versionIdMarkerType").getGenericType(), null, versionIdMarker));
            final String _transformedDelimiter = ((String) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_delimiterType").getGenericType(), null, delimiter));
            final Integer _transformedMaxResults = ((Integer) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_maxResultsType").getGenericType(), null, maxResults));
            final EncodingType _transformedEncodingType = ((EncodingType) evaluateAndTransform(getMuleContext(), event, ListObjectVersionsMessageProcessor.class.getDeclaredField("_encodingTypeType").getGenericType(), null, encodingType));
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
                    return ((S3Connector) object).listObjectVersions(_transformedBucketName, _transformedPrefix, _transformedKeyMarker, _transformedVersionIdMarker, _transformedDelimiter, _transformedMaxResults, _transformedEncodingType);
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
