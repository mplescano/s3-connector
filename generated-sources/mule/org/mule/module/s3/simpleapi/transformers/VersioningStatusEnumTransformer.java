
package org.mule.module.s3.simpleapi.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.s3.simpleapi.VersioningStatus;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-06T08:16:30-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class VersioningStatusEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public VersioningStatusEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(VersioningStatus.class);
        setName("VersioningStatusEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        VersioningStatus result = null;
        result = Enum.valueOf(VersioningStatus.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
