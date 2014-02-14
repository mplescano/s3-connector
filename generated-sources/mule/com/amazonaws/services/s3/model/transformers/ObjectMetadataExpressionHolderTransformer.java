
package com.amazonaws.services.s3.model.transformers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.holders.ObjectMetadataExpressionHolder;
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
import org.mule.security.oauth.processor.AbstractExpressionEvaluator;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-bighorn", date = "2013-11-29T11:41:19-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class ObjectMetadataExpressionHolderTransformer
    extends AbstractExpressionEvaluator
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
        return (aClass == ObjectMetadataExpressionHolder.class);
    }

    public boolean isSourceDataTypeSupported(DataType<?> dataType) {
        return (dataType.getType() == ObjectMetadataExpressionHolder.class);
    }

    public List<Class<?>> getSourceTypes() {
        return Arrays.asList(new Class<?> [] {ObjectMetadataExpressionHolder.class });
    }

    public List<DataType<?>> getSourceDataTypes() {
        return Arrays.asList(new DataType<?> [] {DataTypeFactory.create(ObjectMetadataExpressionHolder.class)});
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
        return ObjectMetadata.class;
    }

    public void setReturnDataType(DataType<?> type) {
        throw new UnsupportedOperationException();
    }

    public DataType<?> getReturnDataType() {
        return DataTypeFactory.create(ObjectMetadata.class);
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
        ObjectMetadataExpressionHolder holder = ((ObjectMetadataExpressionHolder) src);
        ObjectMetadata result = new ObjectMetadata();
        try {
            final Map<String, String> _transformedUserMetadata = ((Map<String, String> ) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_userMetadataType").getGenericType(), null, holder.getUserMetadata()));
            result.setUserMetadata(_transformedUserMetadata);
            final Date _transformedHttpExpiresDate = ((Date) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_httpExpiresDateType").getGenericType(), null, holder.getHttpExpiresDate()));
            result.setHttpExpiresDate(_transformedHttpExpiresDate);
            final Date _transformedExpirationTime = ((Date) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_expirationTimeType").getGenericType(), null, holder.getExpirationTime()));
            result.setExpirationTime(_transformedExpirationTime);
            final String _transformedExpirationTimeRuleId = ((String) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_expirationTimeRuleIdType").getGenericType(), null, holder.getExpirationTimeRuleId()));
            result.setExpirationTimeRuleId(_transformedExpirationTimeRuleId);
            final Boolean _transformedOngoingRestore = ((Boolean) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_ongoingRestoreType").getGenericType(), null, holder.getOngoingRestore()));
            result.setOngoingRestore(_transformedOngoingRestore);
            final Date _transformedRestoreExpirationTime = ((Date) evaluateAndTransform(this.muleContext, event, ObjectMetadataExpressionHolder.class.getDeclaredField("_restoreExpirationTimeType").getGenericType(), null, holder.getRestoreExpirationTime()));
            result.setRestoreExpirationTime(_transformedRestoreExpirationTime);
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
