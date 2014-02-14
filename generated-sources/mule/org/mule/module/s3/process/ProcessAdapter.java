
package org.mule.module.s3.process;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-07-18T05:10:21-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface ProcessAdapter<O >{

    <T> ProcessTemplate<T, O> getProcessTemplate();
}
