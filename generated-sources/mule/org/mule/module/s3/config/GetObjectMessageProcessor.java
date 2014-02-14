
package org.mule.module.s3.config;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.module.s3.S3Connector;
import org.mule.transformer.TransformerTemplate;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;
import org.mule.util.TemplateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GetObjectMessageProcessor invokes the {@link org.mule.module.s3.S3Connector#getObject(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date)} method in {@link S3Connector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
public class GetObjectMessageProcessor
    implements FlowConstructAware, MuleContextAware, Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    private Object bucketName;
    private String _bucketNameType;
    private Object key;
    private String _keyType;
    private Object versionId;
    private String _versionIdType;
    private Object modifiedSince;
    private Date _modifiedSinceType;
    private Object unmodifiedSince;
    private Date _unmodifiedSinceType;
    private Object accessKey;
    private String _accessKeyType;
    private Object secretKey;
    private String _secretKeyType;
    private static Logger logger = LoggerFactory.getLogger(GetObjectMessageProcessor.class);
    /**
     * Module object
     * 
     */
    private Object moduleObject;
    /**
     * Mule Context
     * 
     */
    private MuleContext muleContext;
    /**
     * Mule Expression Manager
     * 
     */
    private ExpressionManager expressionManager;
    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;
    /**
     * Flow construct
     * 
     */
    private FlowConstruct flowConstruct;
    /**
     * Variable used to track how many retries we have attempted on this message processor
     * 
     */
    private AtomicInteger retryCount;
    /**
     * Maximum number of retries that can be attempted.
     * 
     */
    private int retryMax;

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
        retryCount = new AtomicInteger();
        expressionManager = muleContext.getExpressionManager();
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
        if (moduleObject == null) {
            try {
                moduleObject = muleContext.getRegistry().lookupObject(S3ConnectorConnectionManager.class);
                if (moduleObject == null) {
                    throw new InitialisationException(MessageFactory.createStaticMessage("Cannot find object"), this);
                }
            } catch (RegistrationException e) {
                throw new InitialisationException(CoreMessages.initialisationFailure("org.mule.module.s3.config.S3ConnectorConnectionManager"), e, this);
            }
        }
        if (moduleObject instanceof String) {
            moduleObject = muleContext.getRegistry().lookupObject(((String) moduleObject));
            if (moduleObject == null) {
                throw new InitialisationException(MessageFactory.createStaticMessage("Cannot find object by config name"), this);
            }
        }
    }

    public void start()
        throws MuleException
    {
    }

    public void stop()
        throws MuleException
    {
    }

    public void dispose() {
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        this.muleContext = context;
    }

    /**
     * Sets flow construct
     * 
     * @param flowConstruct Flow construct to set
     */
    public void setFlowConstruct(FlowConstruct flowConstruct) {
        this.flowConstruct = flowConstruct;
    }

    /**
     * Sets the instance of the object under which the processor will execute
     * 
     * @param moduleObject Instace of the module
     */
    public void setModuleObject(Object moduleObject) {
        this.moduleObject = moduleObject;
    }

    /**
     * Sets retryMax
     * 
     * @param value Value to set
     */
    public void setRetryMax(int value) {
        this.retryMax = value;
    }

    /**
     * Sets unmodifiedSince
     * 
     * @param value Value to set
     */
    public void setUnmodifiedSince(Object value) {
        this.unmodifiedSince = value;
    }

    /**
     * Sets modifiedSince
     * 
     * @param value Value to set
     */
    public void setModifiedSince(Object value) {
        this.modifiedSince = value;
    }

    /**
     * Sets versionId
     * 
     * @param value Value to set
     */
    public void setVersionId(Object value) {
        this.versionId = value;
    }

    /**
     * Sets bucketName
     * 
     * @param value Value to set
     */
    public void setBucketName(Object value) {
        this.bucketName = value;
    }

    /**
     * Sets key
     * 
     * @param value Value to set
     */
    public void setKey(Object value) {
        this.key = value;
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(Object value) {
        this.accessKey = value;
    }

    /**
     * Sets secretKey
     * 
     * @param value Value to set
     */
    public void setSecretKey(Object value) {
        this.secretKey = value;
    }

    /**
     * Get all superclasses and interfaces recursively.
     * 
     * @param classes List of classes to which to add all found super classes and interfaces.
     * @param clazz   The class to start the search with.
     */
    private void computeClassHierarchy(Class clazz, List classes) {
        for (Class current = clazz; (current!= null); current = current.getSuperclass()) {
            if (classes.contains(current)) {
                return ;
            }
            classes.add(current);
            for (Class currentInterface: current.getInterfaces()) {
                computeClassHierarchy(currentInterface, classes);
            }
        }
    }

    /**
     * Checks whether the specified class parameter is an instance of {@link List }
     * 
     * @param clazz <code>Class</code> to check.
     * @return
     */
    private boolean isListClass(Class clazz) {
        List<Class> classes = new ArrayList<Class>();
        computeClassHierarchy(clazz, classes);
        return classes.contains(List.class);
    }

    /**
     * Checks whether the specified class parameter is an instance of {@link Map }
     * 
     * @param clazz <code>Class</code> to check.
     * @return
     */
    private boolean isMapClass(Class clazz) {
        List<Class> classes = new ArrayList<Class>();
        computeClassHierarchy(clazz, classes);
        return classes.contains(Map.class);
    }

    private boolean isList(Type type) {
        if ((type instanceof Class)&&isListClass(((Class) type))) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return isList(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return ((upperBounds.length!= 0)&&isList(upperBounds[ 0 ]));
        }
        return false;
    }

    private boolean isMap(Type type) {
        if ((type instanceof Class)&&isMapClass(((Class) type))) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return isMap(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return ((upperBounds.length!= 0)&&isMap(upperBounds[ 0 ]));
        }
        return false;
    }

    private boolean isAssignableFrom(Type expectedType, Class clazz) {
        if (expectedType instanceof Class) {
            return ((Class) expectedType).isAssignableFrom(clazz);
        }
        if (expectedType instanceof ParameterizedType) {
            return isAssignableFrom(((ParameterizedType) expectedType).getRawType(), clazz);
        }
        if (expectedType instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) expectedType).getUpperBounds();
            if (upperBounds.length!= 0) {
                return isAssignableFrom(upperBounds[ 0 ], clazz);
            }
        }
        return false;
    }

    private Object evaluate(MuleMessage muleMessage, Object source) {
        if (source instanceof String) {
            String stringSource = ((String) source);
            if (stringSource.startsWith(patternInfo.getPrefix())&&stringSource.endsWith(patternInfo.getSuffix())) {
                return expressionManager.evaluate(stringSource, muleMessage);
            } else {
                return expressionManager.parse(stringSource, muleMessage);
            }
        }
        return source;
    }

    private Object evaluateAndTransform(MuleMessage muleMessage, Type expectedType, String expectedMimeType, Object source)
        throws TransformerException
    {
        if (source == null) {
            return source;
        }
        Object target = null;
        if (isList(source.getClass())) {
            if (isList(expectedType)) {
                List newList = new ArrayList();
                Type valueType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 0 ];
                ListIterator iterator = ((List) source).listIterator();
                while (iterator.hasNext()) {
                    Object subTarget = iterator.next();
                    newList.add(evaluateAndTransform(muleMessage, valueType, expectedMimeType, subTarget));
                }
                target = newList;
            } else {
                target = source;
            }
        } else {
            if (isMap(source.getClass())) {
                if (isMap(expectedType)) {
                    Type keyType = Object.class;
                    Type valueType = Object.class;
                    if (expectedType instanceof ParameterizedType) {
                        keyType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 0 ];
                        valueType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 1 ];
                    }
                    Map map = ((Map) source);
                    Map newMap = new HashMap();
                    for (Object entryObj: map.entrySet()) {
                        {
                            Map.Entry entry = ((Map.Entry) entryObj);
                            Object newKey = evaluateAndTransform(muleMessage, keyType, expectedMimeType, entry.getKey());
                            Object newValue = evaluateAndTransform(muleMessage, valueType, expectedMimeType, entry.getValue());
                            newMap.put(newKey, newValue);
                        }
                    }
                    target = newMap;
                } else {
                    target = source;
                }
            } else {
                target = evaluate(muleMessage, source);
            }
        }
        if ((target!= null)&&(!isAssignableFrom(expectedType, target.getClass()))) {
            DataType sourceDataType = DataTypeFactory.create(target.getClass());
            DataType targetDataType = null;
            if (expectedMimeType!= null) {
                targetDataType = DataTypeFactory.create(((Class) expectedType), expectedMimeType);
            } else {
                targetDataType = DataTypeFactory.create(((Class) expectedType));
            }
            Transformer t = muleContext.getRegistry().lookupTransformer(sourceDataType, targetDataType);
            return t.transform(target);
        } else {
            return target;
        }
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws MuleException
     */
    public MuleEvent process(MuleEvent event)
        throws MuleException
    {
        MuleMessage _muleMessage = event.getMessage();
        S3ConnectorConnectionManager castedModuleObject = null;
        if (moduleObject instanceof String) {
            castedModuleObject = ((S3ConnectorConnectionManager) muleContext.getRegistry().lookupObject(((String) moduleObject)));
            if (castedModuleObject == null) {
                throw new MessagingException(CoreMessages.failedToCreate("getObject"), event, new RuntimeException("Cannot find the configuration specified by the config-ref attribute."));
            }
        } else {
            castedModuleObject = ((S3ConnectorConnectionManager) moduleObject);
        }
        String transformedAccessKey = null;
        String transformedSecretKey = null;
        S3ConnectorLifecycleAdapter connection = null;
        try {
            if (accessKey!= null) {
                transformedAccessKey = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_accessKeyType").getGenericType(), null, accessKey));
            } else {
                if (castedModuleObject.getAccessKey() == null) {
                    throw new MessagingException(CoreMessages.failedToCreate("getObject"), event, new RuntimeException("You must provide a accessKey at the config or the message processor level."));
                }
                transformedAccessKey = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_accessKeyType").getGenericType(), null, castedModuleObject.getAccessKey()));
            }
            if (secretKey!= null) {
                transformedSecretKey = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_secretKeyType").getGenericType(), null, secretKey));
            } else {
                if (castedModuleObject.getSecretKey() == null) {
                    throw new MessagingException(CoreMessages.failedToCreate("getObject"), event, new RuntimeException("You must provide a secretKey at the config or the message processor level."));
                }
                transformedSecretKey = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_secretKeyType").getGenericType(), null, castedModuleObject.getSecretKey()));
            }
            String transformedBucketName = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_bucketNameType").getGenericType(), null, bucketName));
            String transformedKey = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_keyType").getGenericType(), null, key));
            String transformedVersionId = ((String) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_versionIdType").getGenericType(), null, versionId));
            Date transformedModifiedSince = ((Date) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_modifiedSinceType").getGenericType(), null, modifiedSince));
            Date transformedUnmodifiedSince = ((Date) evaluateAndTransform(_muleMessage, GetObjectMessageProcessor.class.getDeclaredField("_unmodifiedSinceType").getGenericType(), null, unmodifiedSince));
            if (logger.isDebugEnabled()) {
                StringBuilder messageStringBuilder = new StringBuilder();
                messageStringBuilder.append("Attempting to acquire a connection using ");
                messageStringBuilder.append("[accessKey = ");
                messageStringBuilder.append(transformedAccessKey);
                messageStringBuilder.append("] ");
                messageStringBuilder.append("[secretKey = ");
                messageStringBuilder.append(transformedSecretKey);
                messageStringBuilder.append("] ");
                logger.debug(messageStringBuilder.toString());
            }
            connection = castedModuleObject.acquireConnection(new S3ConnectorConnectionManager.ConnectionParameters(transformedAccessKey, transformedSecretKey));
            if (connection == null) {
                throw new MessagingException(CoreMessages.failedToCreate("getObject"), event, new RuntimeException("Cannot create connection"));
            } else {
                if (logger.isDebugEnabled()) {
                    StringBuilder messageStringBuilder = new StringBuilder();
                    messageStringBuilder.append("Connection has been acquired with ");
                    messageStringBuilder.append("[id = ");
                    messageStringBuilder.append(connection.connectionId());
                    messageStringBuilder.append("] ");
                    logger.debug(messageStringBuilder.toString());
                }
            }
            retryCount.getAndIncrement();
            Object resultPayload;
            resultPayload = connection.getObject(transformedBucketName, transformedKey, transformedVersionId, transformedModifiedSince, transformedUnmodifiedSince);
            TransformerTemplate.OverwitePayloadCallback overwritePayloadCallback = null;
            if (resultPayload == null) {
                overwritePayloadCallback = new TransformerTemplate.OverwitePayloadCallback(NullPayload.getInstance());
            } else {
                overwritePayloadCallback = new TransformerTemplate.OverwitePayloadCallback(resultPayload);
            }
            List<Transformer> transformerList;
            transformerList = new ArrayList<Transformer>();
            transformerList.add(new TransformerTemplate(overwritePayloadCallback));
            event.getMessage().applyTransformers(event, transformerList);
            retryCount.set(0);
            return event;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("getObject"), event, e);
        } finally {
            try {
                if (connection!= null) {
                    if (logger.isDebugEnabled()) {
                        StringBuilder messageStringBuilder = new StringBuilder();
                        messageStringBuilder.append("Releasing the connection back into the pool [id=");
                        messageStringBuilder.append(connection.connectionId());
                        messageStringBuilder.append("].");
                        logger.debug(messageStringBuilder.toString());
                    }
                    castedModuleObject.releaseConnection(new S3ConnectorConnectionManager.ConnectionParameters(transformedAccessKey, transformedSecretKey), connection);
                }
            } catch (Exception e) {
                throw new MessagingException(CoreMessages.failedToInvoke("getObject"), event, e);
            }
        }
    }

}
