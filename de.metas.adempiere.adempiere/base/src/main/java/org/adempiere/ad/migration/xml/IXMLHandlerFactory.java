package org.adempiere.ad.migration.xml;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

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
