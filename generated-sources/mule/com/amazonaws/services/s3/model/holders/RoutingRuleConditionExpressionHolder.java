
package com.amazonaws.services.s3.model.holders;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-18T08:21:51-05:00", comments = "Build M4.1875.17b58a3")
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
