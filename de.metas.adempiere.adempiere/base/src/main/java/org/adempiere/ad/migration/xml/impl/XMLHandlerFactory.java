package org.adempiere.ad.migration.xml.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.xml.IXMLHandler;
import org.adempiere.ad.migration.xml.IXMLHandlerFactory;
import org.adempiere.exceptions.AdempiereException;

public class XMLHandlerFactory implements IXMLHandlerFactory
{
	/**
	 * Map: Bean Class -> Converter Class
	 * 
	 * e.g. I_AD_Migration -> MigrationHandler
	 */
	private final Map<Class<?>, Class<?>> handlerByBeanClass = new HashMap<Class<?>, Class<?>>();
	/**
	 * Map: NodeName -> Bean Class
	 * 
	 * e.g. "Migration" -> I_AD_Migration
	 */
	private final Map<String, Class<?>> beanClassesByNodeName = new HashMap<String, Class<?>>();

	public XMLHandlerFactory()
	{
		// Register defaults
		registerHandler(MigrationHandler.NODENAME, I_AD_Migration.class, MigrationHandler.class);
		registerHandler(MigrationStepHandler.NODENAME, I_AD_MigrationStep.class, MigrationStepHandler.class);
		registerHandler(MigrationDataHandler.NODENAME, I_AD_MigrationData.class, MigrationDataHandler.class);
	}

	@Override
	public <T> void registerHandler(String nodeName, Class<T> beanClass, Class<? extends IXMLHandler<T>> converterClass)
	{
		beanClassesByNodeName.put(nodeName, beanClass);
		handlerByBeanClass.put(beanClass, converterClass);

	}

	@Override
	public <T> Class<T> getBeanClassByNodeName(String nodeName)
	{
		@SuppressWarnings("unchecked")
		final Class<T> beanClass = (Class<T>)beanClassesByNodeName.get(nodeName);
		return beanClass;
	}

	@Override
	public <T> IXMLHandler<T> getHandlerByNodeName(String nodeName)
	{
		final Class<T> beanClass = getBeanClassByNodeName(nodeName);
		return getHandler(beanClass);
	}

	@Override
	public <T> IXMLHandler<T> getHandler(Class<T> clazz)
	{
		final Class<?> converterClass = handlerByBeanClass.get(clazz);
		try
		{
			@SuppressWarnings("unchecked")
			IXMLHandler<T> converter = (IXMLHandler<T>)converterClass.newInstance();
			return converter;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
}
