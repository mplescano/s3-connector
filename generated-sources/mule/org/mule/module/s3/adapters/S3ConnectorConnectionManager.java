
package org.mule.module.s3.adapters;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.mule.api.Capabilities;
import org.mule.api.Capability;
import org.mule.api.ConnectionManager;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.config.PoolingProfile;
import org.mule.module.s3.S3Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code S3ConnectorConnectionManager} is a wrapper around {@link S3Connector } that adds connection management capabilities to the pojo.
 * 
 */
public class S3ConnectorConnectionManager
    implements Capabilities, ConnectionManager<S3ConnectorConnectionManager.ConnectionKey, S3ConnectorLifecycleAdapter> , MuleContextAware, Initialisable
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
    private static Logger logger = LoggerFactory.getLogger(S3ConnectorConnectionManager.class);
    /**
     * Mule Context
     * 
     */
    private MuleContext muleContext;
    /**
     * Flow construct
     * 
     */
    private FlowConstruct flowConstruct;
    /**
     * Connector Pool
     * 
     */
    private GenericKeyedObjectPool connectionPool;
    protected PoolingProfile connectionPoolingProfile;

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

    /**
     * Sets flow construct
     * 
     * @param flowConstruct Flow construct to set
     */
    public void setFlowConstruct(FlowConstruct flowConstruct) {
        this.flowConstruct = flowConstruct;
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        this.muleContext = context;
    }

    public void initialise() {
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        if (connectionPoolingProfile!= null) {
            config.maxIdle = connectionPoolingProfile.getMaxIdle();
            config.maxActive = connectionPoolingProfile.getMaxActive();
            config.maxWait = connectionPoolingProfile.getMaxWait();
            config.whenExhaustedAction = ((byte) connectionPoolingProfile.getExhaustedAction());
        }
        connectionPool = new GenericKeyedObjectPool(new S3ConnectorConnectionManager.ConnectionFactory(this), config);
    }

    public S3ConnectorLifecycleAdapter acquireConnection(S3ConnectorConnectionManager.ConnectionKey key)
        throws Exception
    {
        return ((S3ConnectorLifecycleAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(S3ConnectorConnectionManager.ConnectionKey key, S3ConnectorLifecycleAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(S3ConnectorConnectionManager.ConnectionKey key, S3ConnectorLifecycleAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

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

    private static class ConnectionFactory
        implements KeyedPoolableObjectFactory
    {

        private S3ConnectorConnectionManager connectionManager;

        public ConnectionFactory(S3ConnectorConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        public Object makeObject(Object key)
            throws Exception
        {
            if (!(key instanceof S3ConnectorConnectionManager.ConnectionKey)) {
                throw new RuntimeException("Invalid key type");
            }
            S3ConnectorLifecycleAdapter connector = new S3ConnectorLifecycleAdapter();
            connector.setProxyUsername(connectionManager.getProxyUsername());
            connector.setProxyPort(connectionManager.getProxyPort());
            connector.setProxyPassword(connectionManager.getProxyPassword());
            connector.setProxyHost(connectionManager.getProxyHost());
            if (connector instanceof Initialisable) {
                connector.initialise();
            }
            if (connector instanceof Startable) {
                connector.start();
            }
            return connector;
        }

        public void destroyObject(Object key, Object obj)
            throws Exception
        {
            if (!(key instanceof S3ConnectorConnectionManager.ConnectionKey)) {
                throw new RuntimeException("Invalid key type");
            }
            if (!(obj instanceof S3ConnectorLifecycleAdapter)) {
                throw new RuntimeException("Invalid connector type");
            }
            try {
                ((S3ConnectorLifecycleAdapter) obj).disconnect();
            } catch (Exception e) {
                throw e;
            } finally {
                if (((S3ConnectorLifecycleAdapter) obj) instanceof Stoppable) {
                    ((S3ConnectorLifecycleAdapter) obj).stop();
                }
                if (((S3ConnectorLifecycleAdapter) obj) instanceof Disposable) {
                    ((S3ConnectorLifecycleAdapter) obj).dispose();
                }
            }
        }

        public boolean validateObject(Object key, Object obj) {
            if (!(obj instanceof S3ConnectorLifecycleAdapter)) {
                throw new RuntimeException("Invalid connector type");
            }
            try {
                return ((S3ConnectorLifecycleAdapter) obj).isConnected();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }

        public void activateObject(Object key, Object obj)
            throws Exception
        {
            if (!(key instanceof S3ConnectorConnectionManager.ConnectionKey)) {
                throw new RuntimeException("Invalid key type");
            }
            if (!(obj instanceof S3ConnectorLifecycleAdapter)) {
                throw new RuntimeException("Invalid connector type");
            }
            try {
                if (!((S3ConnectorLifecycleAdapter) obj).isConnected()) {
                    ((S3ConnectorLifecycleAdapter) obj).connect(((S3ConnectorConnectionManager.ConnectionKey) key).getAccessKey(), ((S3ConnectorConnectionManager.ConnectionKey) key).getSecretKey());
                }
            } catch (Exception e) {
                throw e;
            }
        }

        public void passivateObject(Object key, Object obj)
            throws Exception
        {
        }

    }


    /**
     * A tuple of connection parameters
     * 
     */
    public static class ConnectionKey {

        /**
         * 
         */
        private String accessKey;
        /**
         * 
         */
        private String secretKey;

        public ConnectionKey(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
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

        public int hashCode() {
            int hash = 1;
            hash = ((hash* 31)+ this.accessKey.hashCode());
            return hash;
        }

        public boolean equals(Object obj) {
            return ((obj instanceof S3ConnectorConnectionManager.ConnectionKey)&&(this.accessKey == ((S3ConnectorConnectionManager.ConnectionKey) obj).accessKey));
        }

    }

}
