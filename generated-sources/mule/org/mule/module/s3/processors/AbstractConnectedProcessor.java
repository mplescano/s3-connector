
package org.mule.module.s3.processors;

import javax.annotation.Generated;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T08:16:30-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public abstract class AbstractConnectedProcessor
    extends DevkitBasedMessageProcessor
{

    protected Object accessKey;
    protected String _accessKeyType;
    protected Object secretKey;
    protected String _secretKeyType;

    public AbstractConnectedProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(Object value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public Object getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets secretKey
     * 
     * @param value Value to set
     */
    public void setSecretKey(Object value) {
        this.secretKey = value;
    }

    /**
     * Retrieves secretKey
     * 
     */
    public Object getSecretKey() {
        return this.secretKey;
    }

}
