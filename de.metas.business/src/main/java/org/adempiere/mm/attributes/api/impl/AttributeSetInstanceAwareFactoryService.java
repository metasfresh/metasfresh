package org.adempiere.mm.attributes.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;

import lombok.NonNull;

public class AttributeSetInstanceAwareFactoryService implements IAttributeSetInstanceAwareFactoryService
{
	private final Map<String, IAttributeSetInstanceAwareFactory> tableName2Factory = new HashMap<>();

	private final Map<Class<?>, IAttributeSetInstanceAwareFactory> class2Factory = new HashMap<>();

	/**
	 * Factory to be used when no other factory was found
	 */
	private IAttributeSetInstanceAwareFactory defaultFactory;

	public AttributeSetInstanceAwareFactoryService()
	{
		defaultFactory = new GenericAttributeSetInstanceAwareFactory();
	}

	@Override
	public void registerFactoryForTableName(
			@NonNull final String tableName,
			@NonNull final IAttributeSetInstanceAwareFactory factory)
	{
		tableName2Factory.put(tableName, factory);
	}

	@Override
	public void registerFactoryForOtherClass(
			@NonNull final Class<?> classOfInput,
			@NonNull final IAttributeSetInstanceAwareFactory factory)
	{
		class2Factory.put(classOfInput, factory);
	}

	@Override
	public IAttributeSetInstanceAware createOrNull(final Object referencedObj)
	{
		if (referencedObj == null)
		{
			return null;
		}
		// If already an ASI aware, return it
		if (referencedObj instanceof IAttributeSetInstanceAware)
		{
			return (IAttributeSetInstanceAware)referencedObj;
		}

		final IAttributeSetInstanceAwareFactory factory;

		final String tableName = InterfaceWrapperHelper.getModelTableNameOrNull(referencedObj);
		if (tableName != null)
		{
			factory = getFactoryForTableNameOrDefault(tableName);
		}
		else if (class2Factory.containsKey(referencedObj.getClass()))
		{
			factory = class2Factory.get(referencedObj.getClass());
		}
		else
		{
			return null;
		}
		return factory.createOrNull(referencedObj);
	}

	private IAttributeSetInstanceAwareFactory getFactoryForTableNameOrDefault(@NonNull final String tableName)
	{
		final IAttributeSetInstanceAwareFactory factory = tableName2Factory.get(tableName);
		if (factory != null)
		{
			return factory;
		}
		return defaultFactory;
	}
}
