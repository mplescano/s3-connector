
package org.mule.module.s3.adapters;

import javax.annotation.Generated;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.connection.Connection;


/**
 * A <code>S3ConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link S3Connector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-19T08:20:24-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class S3ConnectorConnectionIdentifierAdapter
    extends S3ConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.connectionId();
    }

}
