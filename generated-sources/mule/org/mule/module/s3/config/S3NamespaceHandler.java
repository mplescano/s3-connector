
package org.mule.module.s3.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/s3</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T07:15:49-05:00", comments = "Build M4.1875.17b58a3")
public class S3NamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(S3NamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [s3] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [s3] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new S3ConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-bucket", new CreateBucketDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-bucket", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-bucket", new DeleteBucketDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-bucket", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-bucket-website-configuration", new DeleteBucketWebsiteConfigurationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-bucket-website-configuration", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-bucket-policy", new GetBucketPolicyDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-bucket-policy", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-bucket-policy", new SetBucketPolicyDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-bucket-policy", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-bucket-policy", new DeleteBucketPolicyDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-bucket-policy", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-bucket-website-configuration", new SetBucketWebsiteConfigurationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-bucket-website-configuration", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-bucket-website-configuration", new GetBucketWebsiteConfigurationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-bucket-website-configuration", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-buckets", new ListBucketsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-buckets", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-objects", new ListObjectsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-objects", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-object-versions", new ListObjectVersionsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-object-versions", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-object", new CreateObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-object", new DeleteObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-objects", new DeleteObjectsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-objects", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-object-storage-class", new SetObjectStorageClassDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-object-storage-class", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("copy-object", new CopyObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("copy-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-object-presigned-uri", new CreateObjectPresignedUriDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-object-presigned-uri", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object-content", new GetObjectContentDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object-content", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object", new GetObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object-metadata", new GetObjectMetadataDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object-metadata", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-bucket-versioning-status", new SetBucketVersioningStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-bucket-versioning-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-bucket-versioning-configuration", new GetBucketVersioningConfigurationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-bucket-versioning-configuration", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-object-uri", new CreateObjectUriDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-object-uri", "@Processor", ex);
        }
    }

}
