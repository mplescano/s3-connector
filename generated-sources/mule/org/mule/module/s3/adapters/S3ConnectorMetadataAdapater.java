
package org.mule.module.s3.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.module.s3.S3Connector;


/**
 * A <code>S3ConnectorMetadataAdapater</code> is a wrapper around {@link S3Connector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T02:00:49-05:00", comments = "Build M4.1875.17b58a3")
public class S3ConnectorMetadataAdapater
    extends S3ConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Amazon S3";
    private final static String MODULE_VERSION = "2.6.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-M4";
    private final static String DEVKIT_BUILD = "M4.1875.17b58a3";
    private final static String MIN_MULE_VERSION = "3.5";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

}
