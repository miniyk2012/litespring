package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

public class XmlBeanDefinitionReader {
	
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";
    
    public static final String VALUE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String CONSTRUCTOR_ATTRIBUTE = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    private BeanDefinitionRegistry registry;

    protected final Log logger = LogFactory.getLog(getClass());

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
    	this.registry = registry;
    }
	public void loadBeanDefinitions(Resource resource){
		InputStream is = null;
		try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            //<beans>
            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {
                	bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                parsePropertyElement(ele, bd);
                parseConstructorElement(ele, bd);
                this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
		}finally{
			if(is != null){
                try {
                	is.close();
                } catch (IOException e) {
                	e.printStackTrace();
                }
			}
		}
		
	}

    private void parseConstructorElement(Element beanElem, BeanDefinition bd) {
        Iterator iter= beanElem.elementIterator(CONSTRUCTOR_ATTRIBUTE);
        ConstructorArgument constructorArgument = new ConstructorArgument();
        while(iter.hasNext()){
            Element consElem = (Element)iter.next();
            Object val = parsePropertyValue(consElem,null);
            ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder();
            valueHolder.setValue(val);
            boolean hasTypeAttribute = (consElem.attribute(TYPE_ATTRIBUTE)!=null);
            boolean hasNameAttribute = (consElem.attribute(NAME_ATTRIBUTE) !=null);
            if (hasTypeAttribute) {
                String type = consElem.attributeValue(TYPE_ATTRIBUTE);
                if (!StringUtils.hasText(type)) {
                    logger.error("constructor-arg contains empty 'type' attribute");
                }
                valueHolder.setType(type);
            }
            if (hasNameAttribute) {
                String name = consElem.attributeValue(NAME_ATTRIBUTE);
                if (!StringUtils.hasText(name)) {
                    logger.error("constructor-arg contains empty 'name' attribute");
                }
                valueHolder.setName(name);
            }
            constructorArgument.addArgumentValue(valueHolder);
        }
        bd.setConstructorArgument(constructorArgument);
    }

    public void parsePropertyElement(Element beanElem, BeanDefinition bd) {
		Iterator iter= beanElem.elementIterator(PROPERTY_ELEMENT);
		while(iter.hasNext()){
            Element propElem = (Element)iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }


            Object val = parsePropertyValue(propElem, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);

            bd.getPropertyValues().add(pv);
		}
		
	}
	
	public Object parsePropertyValue(Element ele, String propertyName) {
		String elementName = (propertyName != null) ?
						"<property> element for property '" + propertyName + "'" :
						"<constructor-arg> element";

		
		boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!=null);
		boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) !=null);
		
		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		}else if (hasValueAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
			
			return valueHolder;
		}		
		else {
			
			throw new RuntimeException(elementName + " must specify a ref or value");
		}
	}
}

