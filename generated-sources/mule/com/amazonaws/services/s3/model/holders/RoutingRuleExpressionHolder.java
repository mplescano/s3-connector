
package com.amazonaws.services.s3.model.holders;

import javax.annotation.Generated;
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.RoutingRuleCondition;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T08:28:25-05:00", comments = "Build master.1915.dd1962d")
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
