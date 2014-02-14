
package org.mule.module.s3.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

public class StringToDateTransformer
    extends AbstractTransformer
    implements MuleContextAware, DiscoverableTransformer
{

    /**
     * Mule Context
     * 
     */
    private MuleContext muleContext;
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public StringToDateTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(Date.class);
        setName("StringToDateTransformer");
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        this.muleContext = context;
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        try {
            return SIMPLE_DATE_FORMAT.parse(src.toString());
        } catch (ParseException e) {
            throw new TransformerException(MessageFactory.createStaticMessage(String.format("Could not parse %s using the format %s", src, SIMPLE_DATE_FORMAT.toPattern())), this, e);
        }
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
