
package org.mule.module.s3.adapter;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T11:28:06-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface PoolManager {

    /**
     * Retrieves the pool of objects
     */
    public org.mule.util.pool.LifecyleEnabledObjectPool getLifecyleEnabledObjectPool();
}
