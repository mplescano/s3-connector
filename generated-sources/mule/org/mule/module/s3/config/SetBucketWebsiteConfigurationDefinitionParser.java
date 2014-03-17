
package org.mule.module.s3.config;

import javax.annotation.Generated;
import com.amazonaws.services.s3.model.holders.BucketWebsiteConfigurationExpressionHolder;
import com.amazonaws.services.s3.model.holders.RedirectRuleExpressionHolder;
import com.amazonaws.services.s3.model.holders.RoutingRuleConditionExpressionHolder;
import com.amazonaws.services.s3.model.holders.RoutingRuleExpressionHolder;
import org.mule.config.MuleManifest;
import org.mule.module.s3.processors.SetBucketWebsiteConfigurationMessageProcessor;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser.ParseDelegate;
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

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T02:21:48-05:00", comments = "Build M4.1875.17b58a3")
public class SetBucketWebsiteConfigurationDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(SetBucketWebsiteConfigurationDefinitionParser.class);

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(SetBucketWebsiteConfigurationMessageProcessor.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the @Processor [set-bucket-website-configuration] within the connector [s3] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the @Processor [set-bucket-website-configuration] within the connector [s3] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.addConstructorArgValue("setBucketWebsiteConfiguration");
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        parseProperty(builder, element, "bucketName", "bucketName");
        if (!parseObjectRef(element, builder, "bucket-website-configuration", "bucketWebsiteConfiguration")) {
            BeanDefinitionBuilder bucketWebsiteConfigurationBuilder = BeanDefinitionBuilder.rootBeanDefinition(BucketWebsiteConfigurationExpressionHolder.class.getName());
            Element bucketWebsiteConfigurationChildElement = DomUtils.getChildElementByTagName(element, "bucket-website-configuration");
            if (bucketWebsiteConfigurationChildElement!= null) {
                parseProperty(bucketWebsiteConfigurationBuilder, bucketWebsiteConfigurationChildElement, "indexDocumentSuffix", "indexDocumentSuffix");
                parseProperty(bucketWebsiteConfigurationBuilder, bucketWebsiteConfigurationChildElement, "errorDocument", "errorDocument");
                if (!parseObjectRef(bucketWebsiteConfigurationChildElement, bucketWebsiteConfigurationBuilder, "redirect-all-requests-to", "redirectAllRequestsTo")) {
                    BeanDefinitionBuilder _redirectAllRequestsToBuilder = BeanDefinitionBuilder.rootBeanDefinition(RedirectRuleExpressionHolder.class.getName());
                    Element _redirectAllRequestsToChildElement = DomUtils.getChildElementByTagName(bucketWebsiteConfigurationChildElement, "redirect-all-requests-to");
                    if (_redirectAllRequestsToChildElement!= null) {
                        parseProperty(_redirectAllRequestsToBuilder, _redirectAllRequestsToChildElement, "hostName", "hostName");
                        parseProperty(_redirectAllRequestsToBuilder, _redirectAllRequestsToChildElement, "replaceKeyPrefixWith", "replaceKeyPrefixWith");
                        parseProperty(_redirectAllRequestsToBuilder, _redirectAllRequestsToChildElement, "replaceKeyWith", "replaceKeyWith");
                        parseProperty(_redirectAllRequestsToBuilder, _redirectAllRequestsToChildElement, "httpRedirectCode", "httpRedirectCode");
                        bucketWebsiteConfigurationBuilder.addPropertyValue("redirectAllRequestsTo", _redirectAllRequestsToBuilder.getBeanDefinition());
                    }
                }
                parseListAndSetProperty(bucketWebsiteConfigurationChildElement, bucketWebsiteConfigurationBuilder, "routingRules", "routing-rules", "routing-rule", new ParseDelegate<BeanDefinition>() {


                    public BeanDefinition parse(Element element) {
                        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RoutingRuleExpressionHolder.class);
                        if (!parseObjectRef(element, builder, "condition", "condition")) {
                            BeanDefinitionBuilder __conditionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RoutingRuleConditionExpressionHolder.class.getName());
                            Element __conditionChildElement = DomUtils.getChildElementByTagName(element, "condition");
                            if (__conditionChildElement!= null) {
                                parseProperty(__conditionBuilder, __conditionChildElement, "keyPrefixEquals", "keyPrefixEquals");
                                parseProperty(__conditionBuilder, __conditionChildElement, "httpErrorCodeReturnedEquals", "httpErrorCodeReturnedEquals");
                                builder.addPropertyValue("condition", __conditionBuilder.getBeanDefinition());
                            }
                        }
                        if (!parseObjectRef(element, builder, "redirect", "redirect")) {
                            BeanDefinitionBuilder __redirectBuilder = BeanDefinitionBuilder.rootBeanDefinition(RedirectRuleExpressionHolder.class.getName());
                            Element __redirectChildElement = DomUtils.getChildElementByTagName(element, "redirect");
                            if (__redirectChildElement!= null) {
                                parseProperty(__redirectBuilder, __redirectChildElement, "hostName", "hostName");
                                parseProperty(__redirectBuilder, __redirectChildElement, "replaceKeyPrefixWith", "replaceKeyPrefixWith");
                                parseProperty(__redirectBuilder, __redirectChildElement, "replaceKeyWith", "replaceKeyWith");
                                parseProperty(__redirectBuilder, __redirectChildElement, "httpRedirectCode", "httpRedirectCode");
                                builder.addPropertyValue("redirect", __redirectBuilder.getBeanDefinition());
                            }
                        }
                        return builder.getBeanDefinition();
                    }

                }
                );
                builder.addPropertyValue("bucketWebsiteConfiguration", bucketWebsiteConfigurationBuilder.getBeanDefinition());
            }
        }
        parseProperty(builder, element, "accessKey", "accessKey");
        parseProperty(builder, element, "secretKey", "secretKey");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
