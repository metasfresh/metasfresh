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

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

public class AttributeSetInstanceAwareFactoryService implements IAttributeSetInstanceAwareFactoryService
{
	private final Map<String, IAttributeSetInstanceAwareFactory> factories = new HashMap<String, IAttributeSetInstanceAwareFactory>();

	/**
	 * Factory to be used when no other factory was found
	 */
	private IAttributeSetInstanceAwareFactory defaultFactory;

	public AttributeSetInstanceAwareFactoryService()
	{
		super();

		defaultFactory = new GenericAttributeSetInstanceAwareFactory();
	}

	@Override
	public void registerFactory(String tableName, IAttributeSetInstanceAwareFactory factory)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(factory, "factory not null");

		factories.put(tableName, factory);
	}

	@Override
	public IAttributeSetInstanceAware createOrNull(final Object referencedObj)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableNameOrNull(referencedObj);
		if (tableName == null)
		{
			// NOTE: we are supporting only database models
			// if this is not a database model, sooner or later an exception will be thrown anyway
			return null;
		}

		final IAttributeSetInstanceAwareFactory factory = getFactory(tableName);
		return factory.createOrNull(referencedObj);
	}

	private IAttributeSetInstanceAwareFactory getFactory(final String tableName)
	{
		final IAttributeSetInstanceAwareFactory factory = factories.get(tableName);
		if (factory != null)
		{
			return factory;
		}

		return defaultFactory;
	}

}
