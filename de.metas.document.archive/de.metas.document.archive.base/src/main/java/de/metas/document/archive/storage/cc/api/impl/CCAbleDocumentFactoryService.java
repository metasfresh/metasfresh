package de.metas.document.archive.storage.cc.api.impl;

import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
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
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactory;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.util.Check;

public class CCAbleDocumentFactoryService implements ICCAbleDocumentFactoryService
{
	private final Map<String, ICCAbleDocumentFactory> factories = new HashMap<>();

	public CCAbleDocumentFactoryService()
	{
		registerCCAbleDocumentFactory(org.compiere.model.I_C_Order.Table_Name, new OrderCCAbleDocumentFactory());
	}

	@Override
	public void registerCCAbleDocumentFactory(
			@NonNull final String tableName,
			@NonNull final ICCAbleDocumentFactory factory)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		factories.put(tableName, factory);
	}

	@Override
	public ICCAbleDocument createCCAbleDocument(@NonNull final Object model)
	{
		final ICCAbleDocumentFactory factory = getCCAbleDocumentFactoryOrNull(model);
		if (factory == null)
		{
			throw new AdempiereException("No factory found for " + model);
		}
		return factory.createCCAbleDocumentAdapter(model);
	}

	private ICCAbleDocumentFactory getCCAbleDocumentFactoryOrNull(@NonNull final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final ICCAbleDocumentFactory factory = factories.get(tableName);
		return factory;
	}

	@Override
	public boolean isSupported(@NonNull final Object model)
	{
		final ICCAbleDocumentFactory factory = getCCAbleDocumentFactoryOrNull(model);
		return factory != null;
	}
}
