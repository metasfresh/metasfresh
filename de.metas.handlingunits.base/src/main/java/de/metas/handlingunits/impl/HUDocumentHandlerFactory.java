package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.exceptions.AdempiereException;

import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.util.Check;

public class HUDocumentHandlerFactory implements IHUDocumentHandlerFactory
{

	private final Map<String, Class<? extends IHUDocumentHandler>> producerClasses = new HashMap<String, Class<? extends IHUDocumentHandler>>();

	@Override
	public IHUDocumentHandler createHandler(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "Table name not empty");
		final Class<? extends IHUDocumentHandler> producerClass = producerClasses.get(tableName);
		if (null != producerClass)
		{
			try
			{
				return producerClass.newInstance();
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Cannot instantiate producer class " + producerClass, e);
			}

		}
		return null;
	}

	@Override
	public void registerHandler(final String tableName, final Class<? extends IHUDocumentHandler> producerClass)
	{
		Check.assumeNotEmpty(tableName, "Table name not empty");
		Check.assumeNotNull(producerClass, "Class not null");

		if (producerClasses.containsKey(tableName))
		{
			// No composite implemented. Assume only one handler per table.
			throw new AdempiereException("HU document handler already implemented for " + tableName);
		}

		producerClasses.put(tableName, producerClass);

	}

}
