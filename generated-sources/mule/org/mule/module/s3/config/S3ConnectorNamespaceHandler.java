
package org.mule.module.s3.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/s3</code>.
 * 
 */
public class S3ConnectorNamespaceHandler
    extends NamespaceHandlerSupport
{


    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        registerBeanDefinitionParser("config", new S3ConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("create-bucket", new CreateBucketDefinitionParser());
        registerBeanDefinitionParser("delete-bucket", new DeleteBucketDefinitionParser());
        registerBeanDefinitionParser("delete-bucket-website-configuration", new DeleteBucketWebsiteConfigurationDefinitionParser());
        registerBeanDefinitionParser("get-bucket-policy", new GetBucketPolicyDefinitionParser());
        registerBeanDefinitionParser("set-bucket-policy", new SetBucketPolicyDefinitionParser());
        registerBeanDefinitionParser("delete-bucket-policy", new DeleteBucketPolicyDefinitionParser());
        registerBeanDefinitionParser("set-bucket-website-configuration", new SetBucketWebsiteConfigurationDefinitionParser());
        registerBeanDefinitionParser("get-bucket-website-configuration", new GetBucketWebsiteConfigurationDefinitionParser());
        registerBeanDefinitionParser("list-buckets", new ListBucketsDefinitionParser());
        registerBeanDefinitionParser("list-objects", new ListObjectsDefinitionParser());
        registerBeanDefinitionParser("list-object-versions", new ListObjectVersionsDefinitionParser());
        registerBeanDefinitionParser("create-object", new CreateObjectDefinitionParser());
        registerBeanDefinitionParser("delete-object", new DeleteObjectDefinitionParser());
        registerBeanDefinitionParser("set-object-storage-class", new SetObjectStorageClassDefinitionParser());
        registerBeanDefinitionParser("copy-object", new CopyObjectDefinitionParser());
        registerBeanDefinitionParser("create-object-presigned-uri", new CreateObjectPresignedUriDefinitionParser());
        registerBeanDefinitionParser("get-object-content", new GetObjectContentDefinitionParser());
        registerBeanDefinitionParser("get-object", new GetObjectDefinitionParser());
        registerBeanDefinitionParser("get-object-metadata", new GetObjectMetadataDefinitionParser());
        registerBeanDefinitionParser("set-bucket-versioning-status", new SetBucketVersioningStatusDefinitionParser());
        registerBeanDefinitionParser("create-object-uri", new CreateObjectUriDefinitionParser());
    }

}
