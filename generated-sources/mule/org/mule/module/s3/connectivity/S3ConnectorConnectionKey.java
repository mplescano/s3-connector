
package org.mule.module.s3.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:51:32-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
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

    public int hashCode() {
        int hash = 1;
        hash = (hash* 31);
        if (this.accessKey!= null) {
            hash += this.accessKey.hashCode();
        }
        return hash;
    }

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