
package com.amazonaws.services.s3.model.holders;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T11:25:08-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class RedirectRuleExpressionHolder {

    protected Object hostName;
    protected String _hostNameType;
    protected Object replaceKeyPrefixWith;
    protected String _replaceKeyPrefixWithType;
    protected Object replaceKeyWith;
    protected String _replaceKeyWithType;
    protected Object httpRedirectCode;
    protected String _httpRedirectCodeType;

    /**
     * Sets hostName
     * 
     * @param value Value to set
     */
    public void setHostName(Object value) {
        this.hostName = value;
    }

    /**
     * Retrieves hostName
     * 
     */
    public Object getHostName() {
        return this.hostName;
    }

    /**
     * Sets replaceKeyPrefixWith
     * 
     * @param value Value to set
     */
    public void setReplaceKeyPrefixWith(Object value) {
        this.replaceKeyPrefixWith = value;
    }

    /**
     * Retrieves replaceKeyPrefixWith
     * 
     */
    public Object getReplaceKeyPrefixWith() {
        return this.replaceKeyPrefixWith;
    }

    /**
     * Sets replaceKeyWith
     * 
     * @param value Value to set
     */
    public void setReplaceKeyWith(Object value) {
        this.replaceKeyWith = value;
    }

    /**
     * Retrieves replaceKeyWith
     * 
     */
    public Object getReplaceKeyWith() {
        return this.replaceKeyWith;
    }

    /**
     * Sets httpRedirectCode
     * 
     * @param value Value to set
     */
    public void setHttpRedirectCode(Object value) {
        this.httpRedirectCode = value;
    }

    /**
     * Retrieves httpRedirectCode
     * 
     */
    public Object getHttpRedirectCode() {
        return this.httpRedirectCode;
    }

}
