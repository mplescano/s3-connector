
package com.amazonaws.services.s3.model.holders;

import java.util.Date;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.5.0-bighorn", date = "2013-11-29T11:41:19-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class ObjectMetadataExpressionHolder {

    protected Object userMetadata;
    protected Map<String, String> _userMetadataType;
    protected Object httpExpiresDate;
    protected Date _httpExpiresDateType;
    protected Object expirationTime;
    protected Date _expirationTimeType;
    protected Object expirationTimeRuleId;
    protected String _expirationTimeRuleIdType;
    protected Object ongoingRestore;
    protected Boolean _ongoingRestoreType;
    protected Object restoreExpirationTime;
    protected Date _restoreExpirationTimeType;

    /**
     * Sets userMetadata
     * 
     * @param value Value to set
     */
    public void setUserMetadata(Object value) {
        this.userMetadata = value;
    }

    /**
     * Retrieves userMetadata
     * 
     */
    public Object getUserMetadata() {
        return this.userMetadata;
    }

    /**
     * Sets httpExpiresDate
     * 
     * @param value Value to set
     */
    public void setHttpExpiresDate(Object value) {
        this.httpExpiresDate = value;
    }

    /**
     * Retrieves httpExpiresDate
     * 
     */
    public Object getHttpExpiresDate() {
        return this.httpExpiresDate;
    }

    /**
     * Sets expirationTime
     * 
     * @param value Value to set
     */
    public void setExpirationTime(Object value) {
        this.expirationTime = value;
    }

    /**
     * Retrieves expirationTime
     * 
     */
    public Object getExpirationTime() {
        return this.expirationTime;
    }

    /**
     * Sets expirationTimeRuleId
     * 
     * @param value Value to set
     */
    public void setExpirationTimeRuleId(Object value) {
        this.expirationTimeRuleId = value;
    }

    /**
     * Retrieves expirationTimeRuleId
     * 
     */
    public Object getExpirationTimeRuleId() {
        return this.expirationTimeRuleId;
    }

    /**
     * Sets ongoingRestore
     * 
     * @param value Value to set
     */
    public void setOngoingRestore(Object value) {
        this.ongoingRestore = value;
    }

    /**
     * Retrieves ongoingRestore
     * 
     */
    public Object getOngoingRestore() {
        return this.ongoingRestore;
    }

    /**
     * Sets restoreExpirationTime
     * 
     * @param value Value to set
     */
    public void setRestoreExpirationTime(Object value) {
        this.restoreExpirationTime = value;
    }

    /**
     * Retrieves restoreExpirationTime
     * 
     */
    public Object getRestoreExpirationTime() {
        return this.restoreExpirationTime;
    }

}
