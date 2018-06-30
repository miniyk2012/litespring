package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.RuntimeBeanReferencePropertyValue;
import org.litespring.beans.factory.config.TypedStringPropertyValue;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlBeanDefinitionReader {
	
	public static final String ID_ATTRIBUTE = "id";

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    protected final Log logger = LogFactory.getLog(getClass());

    BeanDefinitionRegistry registry;
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	public void loadBeanDefinitions(Resource resource){
		InputStream is = null;
		try{			
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement(); //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
				parseProperties(bd, ele);
				if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {					
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));					
				}
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

	private void parseProperties(BeanDefinition bd, Element ele) {
		Iterator<Element> iter = ele.elementIterator();
        List<PropertyValue> propertyValues = new LinkedList<PropertyValue>();
		while(iter.hasNext()){
            Element property = iter.next();
            String name = property.attributeValue(NAME_ATTRIBUTE);
            String ref = property.attributeValue(REF_ATTRIBUTE);
            String value = property.attributeValue(VALUE_ATTRIBUTE);
            PropertyValue propertyValue;
            if (!StringUtils.hasLength(name)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }
            if (ref != null && !"".equals(ref)) {
                propertyValue = new RuntimeBeanReferencePropertyValue(name, ref);
            } else if (value != null && !"".equals(value)) {
                propertyValue = new TypedStringPropertyValue(name, value);
            } else {
                throw new RuntimeException(name + " must specify a ref or value");
            }
            propertyValues.add(propertyValue);
		}
        bd.setPropertyValues(propertyValues);
	}
}

