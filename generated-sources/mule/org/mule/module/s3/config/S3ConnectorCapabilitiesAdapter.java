
package org.mule.module.s3.config;

import org.mule.api.Capabilities;
import org.mule.api.Capability;


/**
 * A <code>S3ConnectorCapabilitiesAdapter</code> is a wrapper around {@link org.mule.module.s3.S3Connector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
public class S3ConnectorCapabilitiesAdapter
    extends org.mule.module.s3.S3Connector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == Capability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

}
