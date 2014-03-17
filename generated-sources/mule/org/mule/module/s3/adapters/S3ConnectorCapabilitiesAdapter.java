
package org.mule.module.s3.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.module.s3.S3Connector;


/**
 * A <code>S3ConnectorCapabilitiesAdapter</code> is a wrapper around {@link S3Connector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T07:15:49-05:00", comments = "Build M4.1875.17b58a3")
public class S3ConnectorCapabilitiesAdapter
    extends S3Connector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

}
