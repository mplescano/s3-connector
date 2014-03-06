
package com.amazonaws.services.s3.model.holders;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T08:16:30-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class RoutingRuleConditionExpressionHolder {

    protected Object keyPrefixEquals;
    protected String _keyPrefixEqualsType;
    protected Object httpErrorCodeReturnedEquals;
    protected String _httpErrorCodeReturnedEqualsType;

    /**
     * Sets keyPrefixEquals
     * 
     * @param value Value to set
     */
    public void setKeyPrefixEquals(Object value) {
        this.keyPrefixEquals = value;
    }

    /**
     * Retrieves keyPrefixEquals
     * 
     */
    public Object getKeyPrefixEquals() {
        return this.keyPrefixEquals;
    }

    /**
     * Sets httpErrorCodeReturnedEquals
     * 
     * @param value Value to set
     */
    public void setHttpErrorCodeReturnedEquals(Object value) {
        this.httpErrorCodeReturnedEquals = value;
    }

    /**
     * Retrieves httpErrorCodeReturnedEquals
     * 
     */
    public Object getHttpErrorCodeReturnedEquals() {
        return this.httpErrorCodeReturnedEquals;
    }

}
