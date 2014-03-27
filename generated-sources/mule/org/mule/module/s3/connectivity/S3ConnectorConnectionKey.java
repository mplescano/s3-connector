
package org.mule.module.s3.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-27T12:22:35-05:00", comments = "Build M4.1875.17b58a3")
public class S3ConnectorConnectionKey {

    /**
     * 
     */
    private String accessKey;
    /**
     * 
     */
    private String secretKey;

    public S3ConnectorConnectionKey(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public String getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets secretKey
     * 
     * @param value Value to set
     */
    public void setSecretKey(String value) {
        this.secretKey = value;
    }

    /**
     * Retrieves secretKey
     * 
     */
    public String getSecretKey() {
        return this.secretKey;
    }

    @Override
    public int hashCode() {
        int result = ((this.accessKey!= null)?this.accessKey.hashCode(): 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof S3ConnectorConnectionKey)) {
            return false;
        }
        S3ConnectorConnectionKey that = ((S3ConnectorConnectionKey) o);
        if (((this.accessKey!= null)?(!this.accessKey.equals(that.accessKey)):(that.accessKey!= null))) {
            return false;
        }
        return true;
    }

}
