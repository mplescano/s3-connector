
package org.mule.module.s3.config.spring;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.util.SpringXMLUtils;
import org.mule.module.s3.config.CopyObjectMessageProcessor;
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

public class CopyObjectDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public CopyObjectDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CopyObjectMessageProcessor.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef!= null)&&(!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        if ((element.getAttribute("sourceBucketName")!= null)&&(!StringUtils.isBlank(element.getAttribute("sourceBucketName")))) {
            builder.addPropertyValue("sourceBucketName", element.getAttribute("sourceBucketName"));
        }
        if ((element.getAttribute("sourceKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("sourceKey")))) {
            builder.addPropertyValue("sourceKey", element.getAttribute("sourceKey"));
        }
        if ((element.getAttribute("sourceVersionId")!= null)&&(!StringUtils.isBlank(element.getAttribute("sourceVersionId")))) {
            builder.addPropertyValue("sourceVersionId", element.getAttribute("sourceVersionId"));
        }
        if ((element.getAttribute("destinationBucketName")!= null)&&(!StringUtils.isBlank(element.getAttribute("destinationBucketName")))) {
            builder.addPropertyValue("destinationBucketName", element.getAttribute("destinationBucketName"));
        }
        if ((element.getAttribute("destinationKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("destinationKey")))) {
            builder.addPropertyValue("destinationKey", element.getAttribute("destinationKey"));
        }
        if (element.hasAttribute("destinationAcl")) {
            builder.addPropertyValue("destinationAcl", element.getAttribute("destinationAcl"));
        }
        if (element.hasAttribute("destinationStorageClass")) {
            builder.addPropertyValue("destinationStorageClass", element.getAttribute("destinationStorageClass"));
        }
        Element destinationUserMetadataListElement = null;
        destinationUserMetadataListElement = DomUtils.getChildElementByTagName(element, "destination-user-metadata");
        List<Element> destinationUserMetadataListChilds = null;
        if (destinationUserMetadataListElement!= null) {
            String destinationUserMetadataRef = destinationUserMetadataListElement.getAttribute("ref");
            if ((destinationUserMetadataRef!= null)&&(!StringUtils.isBlank(destinationUserMetadataRef))) {
                if ((!destinationUserMetadataRef.startsWith(patternInfo.getPrefix()))&&(!destinationUserMetadataRef.endsWith(patternInfo.getSuffix()))) {
                    builder.addPropertyValue("destinationUserMetadata", new RuntimeBeanReference(destinationUserMetadataRef));
                } else {
                    builder.addPropertyValue("destinationUserMetadata", destinationUserMetadataRef);
                }
            } else {
                ManagedMap destinationUserMetadata = new ManagedMap();
                destinationUserMetadataListChilds = DomUtils.getChildElementsByTagName(destinationUserMetadataListElement, "destination-user-metadatum");
                if (destinationUserMetadataListChilds!= null) {
                    if (destinationUserMetadataListChilds.size() == 0) {
                        destinationUserMetadataListChilds = DomUtils.getChildElements(destinationUserMetadataListElement);
                    }
                    for (Element destinationUserMetadataChild: destinationUserMetadataListChilds) {
                        String destinationUserMetadataValueRef = destinationUserMetadataChild.getAttribute("value-ref");
                        String destinationUserMetadataKeyRef = destinationUserMetadataChild.getAttribute("key-ref");
                        Object valueObject = null;
                        Object keyObject = null;
                        if ((destinationUserMetadataValueRef!= null)&&(!StringUtils.isBlank(destinationUserMetadataValueRef))) {
                            valueObject = new RuntimeBeanReference(destinationUserMetadataValueRef);
                        } else {
                            valueObject = destinationUserMetadataChild.getTextContent();
                        }
                        if ((destinationUserMetadataKeyRef!= null)&&(!StringUtils.isBlank(destinationUserMetadataKeyRef))) {
                            keyObject = new RuntimeBeanReference(destinationUserMetadataKeyRef);
                        } else {
                            keyObject = destinationUserMetadataChild.getAttribute("key");
                        }
                        if ((keyObject == null)||((keyObject instanceof String)&&StringUtils.isBlank(((String) keyObject)))) {
                            keyObject = destinationUserMetadataChild.getTagName();
                        }
                        destinationUserMetadata.put(keyObject, valueObject);
                    }
                }
                builder.addPropertyValue("destinationUserMetadata", destinationUserMetadata);
            }
        }
        if ((element.getAttribute("modifiedSince")!= null)&&(!StringUtils.isBlank(element.getAttribute("modifiedSince")))) {
            builder.addPropertyValue("modifiedSince", element.getAttribute("modifiedSince"));
        }
        if ((element.getAttribute("unmodifiedSince")!= null)&&(!StringUtils.isBlank(element.getAttribute("unmodifiedSince")))) {
            builder.addPropertyValue("unmodifiedSince", element.getAttribute("unmodifiedSince"));
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
