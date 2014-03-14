
package com.amazonaws.services.s3.model.holders;

import javax.annotation.Generated;
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.RoutingRuleCondition;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-14T01:28:01-05:00", comments = "Build M4.1875.17b58a3")
public class RoutingRuleExpressionHolder {

    protected Object condition;
    protected RoutingRuleCondition _conditionType;
    protected Object redirect;
    protected RedirectRule _redirectType;

    /**
     * Sets condition
     * 
     * @param value Value to set
     */
    public void setCondition(Object value) {
        this.condition = value;
    }

    /**
     * Retrieves condition
     * 
     */
    public Object getCondition() {
        return this.condition;
    }

    /**
     * Sets redirect
     * 
     * @param value Value to set
     */
    public void setRedirect(Object value) {
        this.redirect = value;
    }

    /**
     * Retrieves redirect
     * 
     */
    public Object getRedirect() {
        return this.redirect;
    }

}
