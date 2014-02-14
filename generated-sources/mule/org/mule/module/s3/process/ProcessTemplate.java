
package org.mule.module.s3.process;

import javax.annotation.Generated;


/**
 * ProcessTemplate provides a processing context for message processing.
 * <p/>
 * Examples of processing context can be to provide error handling, transaction state verification,
 * transactional demarcation, connection management, security, etc.
 * @param <T> type of the return value of the processing execution
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-07-18T05:10:21-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface ProcessTemplate<T,O >{

       public T execute(ProcessCallback<T, O> callback, org.mule.api.processor.MessageProcessor messageProcessor, org.mule.api.MuleEvent event) throws Exception;
    public T execute(ProcessCallback<T, O> callback, org.mule.api.routing.filter.Filter filter, org.mule.api.MuleMessage message) throws Exception;
}
