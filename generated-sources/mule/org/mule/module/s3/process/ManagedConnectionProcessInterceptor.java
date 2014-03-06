
package org.mule.module.s3.process;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessInterceptor;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.module.s3.adapters.S3ConnectorConnectionIdentifierAdapter;
import org.mule.module.s3.connection.ConnectionManager;
import org.mule.module.s3.connection.UnableToAcquireConnectionException;
import org.mule.module.s3.connection.UnableToReleaseConnectionException;
import org.mule.module.s3.connectivity.S3ConnectorConnectionKey;
import org.mule.module.s3.processors.AbstractConnectedProcessor;
import org.mule.security.oauth.callback.ProcessCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T06:25:51-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class ManagedConnectionProcessInterceptor<T >
    extends ExpressionEvaluatorSupport
    implements ProcessInterceptor<T, S3ConnectorConnectionIdentifierAdapter>
{

    private static Logger logger = LoggerFactory.getLogger(ManagedConnectionProcessInterceptor.class);
    private final ConnectionManager<S3ConnectorConnectionKey, S3ConnectorConnectionIdentifierAdapter> connectionManager;
    private final MuleContext muleContext;
    private final ProcessInterceptor<T, S3ConnectorConnectionIdentifierAdapter> next;

    public ManagedConnectionProcessInterceptor(ProcessInterceptor<T, S3ConnectorConnectionIdentifierAdapter> next, ConnectionManager<S3ConnectorConnectionKey, S3ConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        this.next = next;
        this.connectionManager = connectionManager;
        this.muleContext = muleContext;
    }

    @Override
    public T execute(ProcessCallback<T, S3ConnectorConnectionIdentifierAdapter> processCallback, S3ConnectorConnectionIdentifierAdapter object, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        S3ConnectorConnectionIdentifierAdapter connection = null;
        S3ConnectorConnectionKey key = null;
        if (hasConnectionKeysOverride(messageProcessor)) {
            final String _transformedAccessKey = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_accessKeyType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getAccessKey()));
            if (_transformedAccessKey == null) {
                throw new UnableToAcquireConnectionException("Parameter accessKey in method connect can't be null because is not @Optional");
            }
            final String _transformedSecretKey = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_secretKeyType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getSecretKey()));
            if (_transformedSecretKey == null) {
                throw new UnableToAcquireConnectionException("Parameter secretKey in method connect can't be null because is not @Optional");
            }
            key = new S3ConnectorConnectionKey(_transformedAccessKey, _transformedSecretKey);
        } else {
            key = connectionManager.getEvaluatedConnectionKey(event);
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(("Attempting to acquire connection using "+ key.toString()));
            }
            connection = connectionManager.acquireConnection(key);
            if (connection == null) {
                throw new UnableToAcquireConnectionException();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug((("Connection has been acquired with [id="+ connection.getConnectionIdentifier())+"]"));
                }
            }
            return next.execute(processCallback, connection, messageProcessor, event);
        } catch (Exception e) {
            if (processCallback.getManagedExceptions()!= null) {
                for (Class exceptionClass: ((List<Class<? extends Exception>> ) processCallback.getManagedExceptions())) {
                    if (exceptionClass.isInstance(e)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug((((("An exception ( "+ exceptionClass.getName())+") has been thrown. Destroying the connection with [id=")+ connection.getConnectionIdentifier())+"]"));
                        }
                        try {
                            if (connection!= null) {
                                connectionManager.destroyConnection(key, connection);
                                connection = null;
                            }
                        } catch (Exception innerException) {
                            logger.error(innerException.getMessage(), innerException);
                        }
                    }
                }
            }
            throw e;
        } finally {
            try {
                if (connection!= null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug((("Releasing the connection back into the pool [id="+ connection.getConnectionIdentifier())+"]"));
                    }
                    connectionManager.releaseConnection(key, connection);
                }
            } catch (Exception e) {
                throw new UnableToReleaseConnectionException(e);
            }
        }
    }

    /**
     * Validates that the current message processor has changed any of its connection parameters at processor level. If so, a new S3ConnectorConnectionKey must be generated
     * 
     * @param messageProcessor
     *     the message processor to test against the keys
     * @return
     */
    private Boolean hasConnectionKeysOverride(MessageProcessor messageProcessor) {
        if ((messageProcessor == null)||(!(messageProcessor instanceof AbstractConnectedProcessor))) {
            return false;
        }
        AbstractConnectedProcessor abstractConnectedProcessor = ((AbstractConnectedProcessor) messageProcessor);
        if (abstractConnectedProcessor.getAccessKey()!= null) {
            return true;
        }
        return false;
    }

    public T execute(ProcessCallback<T, S3ConnectorConnectionIdentifierAdapter> processCallback, S3ConnectorConnectionIdentifierAdapter object, Filter filter, MuleMessage message)
        throws Exception
    {
        throw new UnsupportedOperationException();
    }

}
