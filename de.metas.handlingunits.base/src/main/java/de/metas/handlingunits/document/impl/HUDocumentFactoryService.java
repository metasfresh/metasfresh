package de.metas.handlingunits.document.impl;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.eevolution.model.I_DD_Order;

import de.metas.handlingunits.ddorder.api.impl.DDOrderHUDocumentFactory;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactory;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.ProcessInfo;

public class HUDocumentFactoryService implements IHUDocumentFactoryService
{
	private final Map<String, IHUDocumentFactory> factories = new HashMap<String, IHUDocumentFactory>();

	public HUDocumentFactoryService()
	{
		super();

		//
		// Register standard factories
		registerHUDocumentFactory(I_M_HU.Table_Name, new HandlingUnitHUDocumentFactory());
		registerHUDocumentFactory(I_DD_Order.Table_Name, new DDOrderHUDocumentFactory());
	}

	@Override
	public List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId)
	{
		final IHUDocumentFactory factory = getHUDocumentFactory(tableName);
		return factory.createHUDocuments(ctx, tableName, recordId);
	}

	@Override
	public IHUDocumentFactory getHUDocumentFactory(final String tableName)
	{
		final IHUDocumentFactory factory = getHUDocumentFactoryOrNull(tableName);
		if (factory == null)
		{
			throw new AdempiereException("No factory found for " + tableName);
		}
		return factory;

	}

	private IHUDocumentFactory getHUDocumentFactoryOrNull(final String tableName)
	{
		final IHUDocumentFactory factory = factories.get(tableName);
		return factory;
	}

	@Override
	public void registerHUDocumentFactory(final String tableName, final IHUDocumentFactory factory)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(factory, "factory not null");

		factories.put(tableName, factory);
	}

	@Override
	public List<IHUDocument> createHUDocuments(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "process info not null");

		final String tableName = pi.getTableNameOrNull();
		Check.assumeNotNull(tableName, "tableName not null ({})", pi);

		final IHUDocumentFactory factory = getHUDocumentFactory(tableName);
		return factory.createHUDocuments(pi);
	}

	@Override
	public <T> List<IHUDocument> createHUDocuments(final Properties ctx, final Class<T> modelClass, final Iterator<T> records)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final IHUDocumentFactory factory = getHUDocumentFactory(tableName);
		return factory.createHUDocuments(ctx, modelClass, records);
	}

	@Override
	public List<IHUDocument> createHUDocumentsFromModel(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final IHUDocumentFactory factory = getHUDocumentFactory(tableName);
		return factory.createHUDocumentsFromModel(model);
	}

	@Override
	public boolean isSupported(final String tableName)
	{
		final IHUDocumentFactory factory = getHUDocumentFactoryOrNull(tableName);
		return factory != null;
	}
}
