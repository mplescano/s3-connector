
package org.mule.module.s3.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultResult;
import org.mule.common.DefaultTestResult;
import org.mule.common.FailureType;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.config.PoolingProfile;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.module.s3.S3Connector;
import org.mule.module.s3.adapters.S3ConnectorConnectionIdentifierAdapter;
import org.mule.module.s3.connection.ConnectionManager;
import org.mule.module.s3.connection.UnableToAcquireConnectionException;
import org.mule.module.s3.processors.AbstractConnectedProcessor;


/**
 * A {@code S3ConnectorConnectionManager} is a wrapper around {@link S3Connector } that adds connection management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-18T08:21:51-05:00", comments = "Build M4.1875.17b58a3")
public class S3ConnectorConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<S3ConnectorConnectionIdentifierAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectionManager<S3ConnectorConnectionKey, S3ConnectorConnectionIdentifierAdapter>
{

    /**
     * 
     */
    private String accessKey;
    /**
     * 
     */
    private String secretKey;
    private String proxyUsername;
    private Integer proxyPort;
    private String proxyPassword;
    private String proxyHost;
    private Integer socketTimeout;
    private Integer connectionTimeout;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Flow Construct
     * 
     */
    protected FlowConstruct flowConstruct;
    /**
     * Connector Pool
     * 
     */
    private GenericKeyedObjectPool connectionPool;
    protected PoolingProfile connectionPoolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "Amazon S3";
    private final static String MODULE_VERSION = "2.6.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-M4";
    private final static String DEVKIT_BUILD = "M4.1875.17b58a3";
    private final static String MIN_MULE_VERSION = "3.5";

    /**
     * Sets proxyUsername
     * 
     * @param value Value to set
     */
    public void setProxyUsername(String value) {
        this.proxyUsername = value;
    }

    /**
     * Retrieves proxyUsername
     * 
     */
    public String getProxyUsername() {
        return this.proxyUsername;
    }

    /**
     * Sets proxyPort
     * 
     * @param value Value to set
     */
    public void setProxyPort(Integer value) {
        this.proxyPort = value;
    }

    /**
     * Retrieves proxyPort
     * 
     */
    public Integer getProxyPort() {
        return this.proxyPort;
    }

    /**
     * Sets proxyPassword
     * 
     * @param value Value to set
     */
    public void setProxyPassword(String value) {
        this.proxyPassword = value;
    }

    /**
     * Retrieves proxyPassword
     * 
     */
    public String getProxyPassword() {
        return this.proxyPassword;
    }

    /**
     * Sets proxyHost
     * 
     * @param value Value to set
     */
    public void setProxyHost(String value) {
        this.proxyHost = value;
    }

    /**
     * Retrieves proxyHost
     * 
     */
    public String getProxyHost() {
        return this.proxyHost;
    }

    /**
     * Sets socketTimeout
     * 
     * @param value Value to set
     */
    public void setSocketTimeout(Integer value) {
        this.socketTimeout = value;
    }

    /**
     * Retrieves socketTimeout
     * 
     */
    public Integer getSocketTimeout() {
        return this.socketTimeout;
    }

    /**
     * Sets connectionTimeout
     * 
     * @param value Value to set
     */
    public void setConnectionTimeout(Integer value) {
        this.connectionTimeout = value;
    }

    /**
     * Retrieves connectionTimeout
     * 
     */
    public Integer getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets flowConstruct
     * 
     * @param value Value to set
     */
    public void setFlowConstruct(FlowConstruct value) {
        this.flowConstruct = value;
    }

    /**
     * Retrieves flowConstruct
     * 
     */
    public FlowConstruct getFlowConstruct() {
        return this.flowConstruct;
    }

    /**
     * Sets connectionPoolingProfile
     * 
     * @param value Value to set
     */
    public void setConnectionPoolingProfile(PoolingProfile value) {
        this.connectionPoolingProfile = value;
    }

    /**
     * Retrieves connectionPoolingProfile
     * 
     */
    public PoolingProfile getConnectionPoolingProfile() {
        return this.connectionPoolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public String getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets secretKey
     * 
     * @param value Value to set
     */
    public void setSecretKey(String value) {
        this.secretKey = value;
    }

    /**
     * Retrieves secretKey
     * 
     */
    public String getSecretKey() {
        return this.secretKey;
    }

    public void initialise() {
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        if (connectionPoolingProfile!= null) {
            config.maxIdle = connectionPoolingProfile.getMaxIdle();
            config.maxActive = connectionPoolingProfile.getMaxActive();
            config.maxWait = connectionPoolingProfile.getMaxWait();
            config.whenExhaustedAction = ((byte) connectionPoolingProfile.getExhaustedAction());
            config.timeBetweenEvictionRunsMillis = connectionPoolingProfile.getEvictionCheckIntervalMillis();
            config.minEvictableIdleTimeMillis = connectionPoolingProfile.getMinEvictionMillis();
        }
        connectionPool = new GenericKeyedObjectPool(new S3ConnectorConnectionFactory(this), config);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public S3ConnectorConnectionIdentifierAdapter acquireConnection(S3ConnectorConnectionKey key)
        throws Exception
    {
        return ((S3ConnectorConnectionIdentifierAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(S3ConnectorConnectionKey key, S3ConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(S3ConnectorConnectionKey key, S3ConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

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

    @Override
    public<P >ProcessTemplate<P, S3ConnectorConnectionIdentifierAdapter> getProcessTemplate() {
        return new ManagedConnectionProcessTemplate(this, muleContext);
    }

    @Override
    public S3ConnectorConnectionKey getDefaultConnectionKey() {
        return new S3ConnectorConnectionKey(getAccessKey(), getSecretKey());
    }

    @Override
    public S3ConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedAccessKey = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_accessKeyType").getGenericType(), null, getAccessKey()));
            if (_transformedAccessKey == null) {
                throw new UnableToAcquireConnectionException("Parameter accessKey in method connect can't be null because is not @Optional");
            }
            final String _transformedSecretKey = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_secretKeyType").getGenericType(), null, getSecretKey()));
            if (_transformedSecretKey == null) {
                throw new UnableToAcquireConnectionException("Parameter secretKey in method connect can't be null because is not @Optional");
            }
            return new S3ConnectorConnectionKey(_transformedAccessKey, _transformedSecretKey);
        }
        return getDefaultConnectionKey();
    }

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

    public TestResult test() {
        S3ConnectorConnectionIdentifierAdapter connection = null;
        DefaultTestResult result;
        S3ConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            result = new DefaultTestResult(org.mule.common.Result.Status.SUCCESS);
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            result = ((DefaultTestResult) buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
        return result;
    }

    public DefaultResult buildFailureTestResult(Exception exception) {
        DefaultTestResult result;
        if (exception instanceof ConnectionException) {
            ConnectionExceptionCode code = ((ConnectionException) exception).getCode();
            if (code == ConnectionExceptionCode.UNKNOWN_HOST) {
                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNKNOWN_HOST, exception);
            } else {
                if (code == ConnectionExceptionCode.CANNOT_REACH) {
                    result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.RESOURCE_UNAVAILABLE, exception);
                } else {
                    if (code == ConnectionExceptionCode.INCORRECT_CREDENTIALS) {
                        result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                    } else {
                        if (code == ConnectionExceptionCode.CREDENTIALS_EXPIRED) {
                            result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                        } else {
                            if (code == ConnectionExceptionCode.UNKNOWN) {
                                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            } else {
                                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            }
                        }
                    }
                }
            }
        } else {
            result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
        }
        return result;
    }

}
