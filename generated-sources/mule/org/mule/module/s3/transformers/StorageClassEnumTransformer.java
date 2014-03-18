
package org.mule.module.s3.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.s3.StorageClass;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-18T08:21:51-05:00", comments = "Build M4.1875.17b58a3")
public class StorageClassEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public StorageClassEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(StorageClass.class);
        setName("StorageClassEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        StorageClass result = null;
        result = Enum.valueOf(StorageClass.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
