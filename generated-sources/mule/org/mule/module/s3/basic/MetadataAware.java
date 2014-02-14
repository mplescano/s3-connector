
package org.mule.module.s3.basic;

import javax.annotation.Generated;


/**
 *  This interface is implemented for every {@link org.mule.api.annotations.Module} and {@link org.mule.api.annotations.Connector} annotated class and its purpose is to define a contract to query the annotated class about its metadata.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-07-18T05:10:21-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface MetadataAware {

     /**
     * Returns the user-friendly name of this module
     */
    String getModuleName();

    /**
     * Returns the version of this module
     */
    String getModuleVersion();

    /**
     * Returns the version of the DevKit used to create this module
     */
    String getDevkitVersion();

    /**
     * Returns the build of the DevKit used to create this module
     */
    String getDevkitBuild();
}
