package org.adempiere.ad.migration.xml;

import de.metas.util.ISingletonService;

public interface IXMLHandlerFactory extends ISingletonService
{
	/**
	 * Register a handler
	 * 
	 * @param nodeName
	 * @param beanClass bean class (e.g. I_AD_Migration)
	 * @param converterClass converter class (e.g. MigrationConverter)
	 */
	<T> void registerHandler(String nodeName, Class<T> beanClass, Class<? extends IXMLHandler<T>> converterClass);

	<T> Class<T> getBeanClassByNodeName(String nodeName);

	<T> IXMLHandler<T> getHandlerByNodeName(String nodeName);

	<T> IXMLHandler<T> getHandler(Class<T> clazz);

}
