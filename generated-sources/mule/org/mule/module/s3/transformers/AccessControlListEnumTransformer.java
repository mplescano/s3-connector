
package org.mule.module.s3.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.s3.AccessControlList;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-03-14T03:33:46-05:00", comments = "Build M4.1875.17b58a3")
public class AccessControlListEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public AccessControlListEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(AccessControlList.class);
        setName("AccessControlListEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        AccessControlList result = null;
        result = Enum.valueOf(AccessControlList.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
