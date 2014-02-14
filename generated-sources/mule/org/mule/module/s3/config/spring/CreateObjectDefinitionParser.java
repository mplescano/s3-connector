
package org.mule.module.s3.config.spring;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.util.SpringXMLUtils;
import org.mule.module.s3.config.CreateObjectMessageProcessor;
import org.mule.util.TemplateParser;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class CreateObjectDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public CreateObjectDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CreateObjectMessageProcessor.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef!= null)&&(!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        if ((element.getAttribute("bucketName")!= null)&&(!StringUtils.isBlank(element.getAttribute("bucketName")))) {
            builder.addPropertyValue("bucketName", element.getAttribute("bucketName"));
        }
        if ((element.getAttribute("key")!= null)&&(!StringUtils.isBlank(element.getAttribute("key")))) {
            builder.addPropertyValue("key", element.getAttribute("key"));
        }
        if ((element.getAttribute("content-ref")!= null)&&(!StringUtils.isBlank(element.getAttribute("content-ref")))) {
            if (element.getAttribute("content-ref").startsWith("#")) {
                builder.addPropertyValue("content", element.getAttribute("content-ref"));
            } else {
                builder.addPropertyValue("content", (("#[registry:"+ element.getAttribute("content-ref"))+"]"));
            }
        }
        if ((element.getAttribute("contentLength")!= null)&&(!StringUtils.isBlank(element.getAttribute("contentLength")))) {
            builder.addPropertyValue("contentLength", element.getAttribute("contentLength"));
        }
        if ((element.getAttribute("contentMd5")!= null)&&(!StringUtils.isBlank(element.getAttribute("contentMd5")))) {
            builder.addPropertyValue("contentMd5", element.getAttribute("contentMd5"));
        }
        if ((element.getAttribute("contentType")!= null)&&(!StringUtils.isBlank(element.getAttribute("contentType")))) {
            builder.addPropertyValue("contentType", element.getAttribute("contentType"));
        }
        if (element.hasAttribute("acl")) {
            builder.addPropertyValue("acl", element.getAttribute("acl"));
        }
        if (element.hasAttribute("storageClass")) {
            builder.addPropertyValue("storageClass", element.getAttribute("storageClass"));
        }
        Element userMetadataListElement = null;
        userMetadataListElement = DomUtils.getChildElementByTagName(element, "user-metadata");
        List<Element> userMetadataListChilds = null;
        if (userMetadataListElement!= null) {
            String userMetadataRef = userMetadataListElement.getAttribute("ref");
            if ((userMetadataRef!= null)&&(!StringUtils.isBlank(userMetadataRef))) {
                if ((!userMetadataRef.startsWith(patternInfo.getPrefix()))&&(!userMetadataRef.endsWith(patternInfo.getSuffix()))) {
                    builder.addPropertyValue("userMetadata", new RuntimeBeanReference(userMetadataRef));
                } else {
                    builder.addPropertyValue("userMetadata", userMetadataRef);
                }
            } else {
                ManagedMap userMetadata = new ManagedMap();
                userMetadataListChilds = DomUtils.getChildElementsByTagName(userMetadataListElement, "user-metadatum");
                if (userMetadataListChilds!= null) {
                    if (userMetadataListChilds.size() == 0) {
                        userMetadataListChilds = DomUtils.getChildElements(userMetadataListElement);
                    }
                    for (Element userMetadataChild: userMetadataListChilds) {
                        String userMetadataValueRef = userMetadataChild.getAttribute("value-ref");
                        String userMetadataKeyRef = userMetadataChild.getAttribute("key-ref");
                        Object valueObject = null;
                        Object keyObject = null;
                        if ((userMetadataValueRef!= null)&&(!StringUtils.isBlank(userMetadataValueRef))) {
                            valueObject = new RuntimeBeanReference(userMetadataValueRef);
                        } else {
                            valueObject = userMetadataChild.getTextContent();
                        }
                        if ((userMetadataKeyRef!= null)&&(!StringUtils.isBlank(userMetadataKeyRef))) {
                            keyObject = new RuntimeBeanReference(userMetadataKeyRef);
                        } else {
                            keyObject = userMetadataChild.getAttribute("key");
                        }
                        if ((keyObject == null)||((keyObject instanceof String)&&StringUtils.isBlank(((String) keyObject)))) {
                            keyObject = userMetadataChild.getTagName();
                        }
                        userMetadata.put(keyObject, valueObject);
                    }
                }
                builder.addPropertyValue("userMetadata", userMetadata);
            }
        }
        if ((element.getAttribute("retryMax")!= null)&&(!StringUtils.isBlank(element.getAttribute("retryMax")))) {
            builder.addPropertyValue("retryMax", element.getAttribute("retryMax"));
        }
        if ((element.getAttribute("accessKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("accessKey")))) {
            builder.addPropertyValue("accessKey", element.getAttribute("accessKey"));
        }
        if ((element.getAttribute("secretKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("secretKey")))) {
            builder.addPropertyValue("secretKey", element.getAttribute("secretKey"));
        }
        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        MutablePropertyValues propertyValues = parserContent.getContainingBeanDefinition().getPropertyValues();
        if (parserContent.getContainingBeanDefinition().getBeanClassName().equals("org.mule.config.spring.factories.PollingMessageSourceFactoryBean")) {
            propertyValues.addPropertyValue("messageProcessor", definition);
        } else {
            if (parserContent.getContainingBeanDefinition().getBeanClassName().equals("org.mule.enricher.MessageEnricher")) {
                propertyValues.addPropertyValue("enrichmentMessageProcessor", definition);
            } else {
                PropertyValue messageProcessors = propertyValues.getPropertyValue("messageProcessors");
                if ((messageProcessors == null)||(messageProcessors.getValue() == null)) {
                    propertyValues.addPropertyValue("messageProcessors", new ManagedList());
                }
                List listMessageProcessors = ((List) propertyValues.getPropertyValue("messageProcessors").getValue());
                listMessageProcessors.add(definition);
            }
        }
        return definition;
    }

    protected String getAttributeValue(Element element, String attributeName) {
        if (!StringUtils.isEmpty(element.getAttribute(attributeName))) {
            return element.getAttribute(attributeName);
        }
        return null;
    }

    private String generateChildBeanName(Element element) {
        String id = SpringXMLUtils.getNameOrId(element);
        if (StringUtils.isBlank(id)) {
            String parentId = SpringXMLUtils.getNameOrId(((Element) element.getParentNode()));
            return ((("."+ parentId)+":")+ element.getLocalName());
        } else {
            return id;
        }
    }

}
