
package org.mule.module.s3.adapters;

import javax.annotation.Generated;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.connection.Connection;


/**
 * A <code>S3ConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link S3Connector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T02:00:49-05:00", comments = "Build M4.1875.17b58a3")
public class S3ConnectorConnectionIdentifierAdapter
    extends S3ConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.connectionId();
    }

}
