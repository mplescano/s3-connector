
package org.mule.module.s3.config.spring;

import org.apache.commons.lang.StringUtils;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.config.PoolingProfile;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.parsers.generic.AutoIdUtils;
import org.mule.module.s3.config.S3ConnectorConnectionManager;
import org.mule.util.TemplateParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class S3ConnectorConfigDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public S3ConnectorConfigDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        String name = element.getAttribute("name");
        if ((name == null)||StringUtils.isBlank(name)) {
            element.setAttribute("name", AutoIdUtils.getUniqueName(element, "mule-bean"));
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(S3ConnectorConnectionManager.class.getName());
        if (Initialisable.class.isAssignableFrom(S3ConnectorConnectionManager.class)) {
            builder.setInitMethodName(Initialisable.PHASE_NAME);
        }
        if (Disposable.class.isAssignableFrom(S3ConnectorConnectionManager.class)) {
            builder.setDestroyMethodName(Disposable.PHASE_NAME);
        }
        if ((element.getAttribute("proxyUsername")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyUsername")))) {
            builder.addPropertyValue("proxyUsername", element.getAttribute("proxyUsername"));
        }
        if ((element.getAttribute("proxyPort")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyPort")))) {
            builder.addPropertyValue("proxyPort", element.getAttribute("proxyPort"));
        }
        if ((element.getAttribute("proxyPassword")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyPassword")))) {
            builder.addPropertyValue("proxyPassword", element.getAttribute("proxyPassword"));
        }
        if ((element.getAttribute("proxyHost")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyHost")))) {
            builder.addPropertyValue("proxyHost", element.getAttribute("proxyHost"));
        }
        if ((element.getAttribute("accessKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("accessKey")))) {
            builder.addPropertyValue("accessKey", element.getAttribute("accessKey"));
        }
        if ((element.getAttribute("secretKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("secretKey")))) {
            builder.addPropertyValue("secretKey", element.getAttribute("secretKey"));
        }
        BeanDefinitionBuilder connectionPoolingProfileBuilder = BeanDefinitionBuilder.rootBeanDefinition(PoolingProfile.class.getName());
        Element connectionPoolingProfileElement = DomUtils.getChildElementByTagName(element, "connection-pooling-profile");
        if (connectionPoolingProfileElement!= null) {
            if ((connectionPoolingProfileElement.getAttribute("maxActive")!= null)&&(!StringUtils.isBlank(connectionPoolingProfileElement.getAttribute("maxActive")))) {
                connectionPoolingProfileBuilder.addPropertyValue("maxActive", connectionPoolingProfileElement.getAttribute("maxActive"));
            }
            if ((connectionPoolingProfileElement.getAttribute("maxIdle")!= null)&&(!StringUtils.isBlank(connectionPoolingProfileElement.getAttribute("maxIdle")))) {
                connectionPoolingProfileBuilder.addPropertyValue("maxIdle", connectionPoolingProfileElement.getAttribute("maxIdle"));
            }
            if ((connectionPoolingProfileElement.getAttribute("maxWait")!= null)&&(!StringUtils.isBlank(connectionPoolingProfileElement.getAttribute("maxWait")))) {
                connectionPoolingProfileBuilder.addPropertyValue("maxWait", connectionPoolingProfileElement.getAttribute("maxWait"));
            }
            if ((connectionPoolingProfileElement.getAttribute("exhaustedAction")!= null)&&(!StringUtils.isBlank(connectionPoolingProfileElement.getAttribute("exhaustedAction")))) {
                connectionPoolingProfileBuilder.addPropertyValue("exhaustedAction", PoolingProfile.POOL_EXHAUSTED_ACTIONS.get(connectionPoolingProfileElement.getAttribute("exhaustedAction")));
            }
            if ((connectionPoolingProfileElement.getAttribute("exhaustedAction")!= null)&&(!StringUtils.isBlank(connectionPoolingProfileElement.getAttribute("exhaustedAction")))) {
                connectionPoolingProfileBuilder.addPropertyValue("initialisationPolicy", PoolingProfile.POOL_INITIALISATION_POLICIES.get(connectionPoolingProfileElement.getAttribute("initialisationPolicy")));
            }
            builder.addPropertyValue("connectionPoolingProfile", connectionPoolingProfileBuilder.getBeanDefinition());
        }
        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        return definition;
    }

}
