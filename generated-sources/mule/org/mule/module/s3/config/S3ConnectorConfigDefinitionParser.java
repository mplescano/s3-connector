
package org.mule.module.s3.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.config.PoolingProfile;
import org.mule.module.s3.connectivity.S3ConnectorConnectionManager;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T11:28:06-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class S3ConnectorConfigDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(S3ConnectorConfigDefinitionParser.class);

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parseConfigName(element);
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        setInitMethodIfNeeded(builder, S3ConnectorConnectionManager.class);
        setDestroyMethodIfNeeded(builder, S3ConnectorConnectionManager.class);
        parseProperty(builder, element, "proxyUsername", "proxyUsername");
        parseProperty(builder, element, "proxyPort", "proxyPort");
        parseProperty(builder, element, "proxyPassword", "proxyPassword");
        parseProperty(builder, element, "proxyHost", "proxyHost");
        parseProperty(builder, element, "socketTimeout", "socketTimeout");
        parseProperty(builder, element, "connectionTimeout", "connectionTimeout");
        parseProperty(builder, element, "accessKey", "accessKey");
        parseProperty(builder, element, "secretKey", "secretKey");
        BeanDefinitionBuilder connectionPoolingProfileBuilder = BeanDefinitionBuilder.rootBeanDefinition(PoolingProfile.class.getName());
        Element connectionPoolingProfileElement = DomUtils.getChildElementByTagName(element, "connection-pooling-profile");
        if (connectionPoolingProfileElement!= null) {
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxActive");
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxIdle");
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxWait");
            if (hasAttribute(connectionPoolingProfileElement, "exhaustedAction")) {
                connectionPoolingProfileBuilder.addPropertyValue("exhaustedAction", PoolingProfile.POOL_EXHAUSTED_ACTIONS.get(connectionPoolingProfileElement.getAttribute("exhaustedAction")));
            }
            if (hasAttribute(connectionPoolingProfileElement, "initialisationPolicy")) {
                connectionPoolingProfileBuilder.addPropertyValue("initialisationPolicy", PoolingProfile.POOL_INITIALISATION_POLICIES.get(connectionPoolingProfileElement.getAttribute("initialisationPolicy")));
            }
            if (hasAttribute(connectionPoolingProfileElement, "evictionCheckIntervalMillis")) {
                parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "evictionCheckIntervalMillis");
            }
            if (hasAttribute(connectionPoolingProfileElement, "minEvictionMillis")) {
                parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "minEvictionMillis");
            }
            builder.addPropertyValue("connectionPoolingProfile", connectionPoolingProfileBuilder.getBeanDefinition());
        }
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        parseRetryPolicyTemplate("reconnect", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-forever", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-custom-strategy", element, parserContext, builder, definition);
        return definition;
    }

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(S3ConnectorConnectionManager.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the configuration [config] within the connector [s3] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the configuration [config] within the connector [s3] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

}
