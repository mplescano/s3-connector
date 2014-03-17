
package com.amazonaws.services.s3.model.transformers;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.RoutingRule;
import com.amazonaws.services.s3.model.holders.BucketWebsiteConfigurationExpressionHolder;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.MessageTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transformer.TransformerMessagingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-17T02:21:48-05:00", comments = "Build M4.1875.17b58a3")
public class BucketWebsiteConfigurationExpressionHolderTransformer
    extends ExpressionEvaluatorSupport
    implements DiscoverableTransformer, MessageTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;
    private ImmutableEndpoint endpoint;
    private MuleContext muleContext;
    private String name;

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

    public boolean isSourceTypeSupported(Class<?> aClass) {
        return (aClass == BucketWebsiteConfigurationExpressionHolder.class);
    }

    public boolean isSourceDataTypeSupported(DataType<?> dataType) {
        return (dataType.getType() == BucketWebsiteConfigurationExpressionHolder.class);
    }

    public List<Class<?>> getSourceTypes() {
        return Arrays.asList(new Class<?> [] {BucketWebsiteConfigurationExpressionHolder.class });
    }

    public List<DataType<?>> getSourceDataTypes() {
        return Arrays.asList(new DataType<?> [] {DataTypeFactory.create(BucketWebsiteConfigurationExpressionHolder.class)});
    }

    public boolean isAcceptNull() {
        return false;
    }

    public boolean isIgnoreBadInput() {
        return false;
    }

    public Object transform(Object src)
        throws TransformerException
    {
        throw new UnsupportedOperationException();
    }

    public Object transform(Object src, String encoding)
        throws TransformerException
    {
        throw new UnsupportedOperationException();
    }

    public void setReturnClass(Class<?> theClass) {
        throw new UnsupportedOperationException();
    }

    public Class<?> getReturnClass() {
        return BucketWebsiteConfiguration.class;
    }

    public void setReturnDataType(DataType<?> type) {
        throw new UnsupportedOperationException();
    }

    public DataType<?> getReturnDataType() {
        return DataTypeFactory.create(BucketWebsiteConfiguration.class);
    }

    public void setEndpoint(ImmutableEndpoint ep) {
        endpoint = ep;
    }

    public ImmutableEndpoint getEndpoint() {
        return endpoint;
    }

    public void dispose() {
    }

    public void initialise()
        throws InitialisationException
    {
    }

    public void setMuleContext(MuleContext context) {
        muleContext = context;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public Object transform(Object src, MuleEvent event)
        throws TransformerMessagingException
    {
        return transform(src, null, event);
    }

    public Object transform(Object src, String encoding, MuleEvent event)
        throws TransformerMessagingException
    {
        if (src == null) {
            return null;
        }
        BucketWebsiteConfigurationExpressionHolder holder = ((BucketWebsiteConfigurationExpressionHolder) src);
        BucketWebsiteConfiguration result = new BucketWebsiteConfiguration();
        try {
            final String _transformedIndexDocumentSuffix = ((String) evaluateAndTransform(this.muleContext, event, BucketWebsiteConfigurationExpressionHolder.class.getDeclaredField("_indexDocumentSuffixType").getGenericType(), null, holder.getIndexDocumentSuffix()));
            result.setIndexDocumentSuffix(_transformedIndexDocumentSuffix);
            final String _transformedErrorDocument = ((String) evaluateAndTransform(this.muleContext, event, BucketWebsiteConfigurationExpressionHolder.class.getDeclaredField("_errorDocumentType").getGenericType(), null, holder.getErrorDocument()));
            result.setErrorDocument(_transformedErrorDocument);
            final RedirectRule _transformedRedirectAllRequestsTo = ((RedirectRule) evaluateAndTransform(this.muleContext, event, BucketWebsiteConfigurationExpressionHolder.class.getDeclaredField("_redirectAllRequestsToType").getGenericType(), null, holder.getRedirectAllRequestsTo()));
            result.setRedirectAllRequestsTo(_transformedRedirectAllRequestsTo);
            final List<RoutingRule> _transformedRoutingRules = ((List<RoutingRule> ) evaluateAndTransform(this.muleContext, event, BucketWebsiteConfigurationExpressionHolder.class.getDeclaredField("_routingRulesType").getGenericType(), null, holder.getRoutingRules()));
            result.setRoutingRules(_transformedRoutingRules);
        } catch (NoSuchFieldException e) {
            throw new TransformerMessagingException(CoreMessages.createStaticMessage("internal error"), event, this, e);
        } catch (TransformerException e) {
            throw new TransformerMessagingException(e.getI18nMessage(), event, this, e);
        }
        return result;
    }

    public MuleEvent process(MuleEvent event) {
        return null;
    }

    public String getMimeType() {
        return null;
    }

    public String getEncoding() {
        return null;
    }

    public MuleContext getMuleContext() {
        return muleContext;
    }

}
