
package com.amazonaws.services.s3.model.holders;

import java.util.List;
import javax.annotation.Generated;
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.RoutingRule;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T11:28:06-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class BucketWebsiteConfigurationExpressionHolder {

    protected Object indexDocumentSuffix;
    protected String _indexDocumentSuffixType;
    protected Object errorDocument;
    protected String _errorDocumentType;
    protected Object redirectAllRequestsTo;
    protected RedirectRule _redirectAllRequestsToType;
    protected Object routingRules;
    protected List<RoutingRule> _routingRulesType;

    /**
     * Sets indexDocumentSuffix
     * 
     * @param value Value to set
     */
    public void setIndexDocumentSuffix(Object value) {
        this.indexDocumentSuffix = value;
    }

    /**
     * Retrieves indexDocumentSuffix
     * 
     */
    public Object getIndexDocumentSuffix() {
        return this.indexDocumentSuffix;
    }

    /**
     * Sets errorDocument
     * 
     * @param value Value to set
     */
    public void setErrorDocument(Object value) {
        this.errorDocument = value;
    }

    /**
     * Retrieves errorDocument
     * 
     */
    public Object getErrorDocument() {
        return this.errorDocument;
    }

    /**
     * Sets redirectAllRequestsTo
     * 
     * @param value Value to set
     */
    public void setRedirectAllRequestsTo(Object value) {
        this.redirectAllRequestsTo = value;
    }

    /**
     * Retrieves redirectAllRequestsTo
     * 
     */
    public Object getRedirectAllRequestsTo() {
        return this.redirectAllRequestsTo;
    }

    /**
     * Sets routingRules
     * 
     * @param value Value to set
     */
    public void setRoutingRules(Object value) {
        this.routingRules = value;
    }

    /**
     * Retrieves routingRules
     * 
     */
    public Object getRoutingRules() {
        return this.routingRules;
    }

}
