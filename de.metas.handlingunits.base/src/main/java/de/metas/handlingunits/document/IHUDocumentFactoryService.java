package de.metas.handlingunits.document;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;

import de.metas.process.ProcessInfo;

public interface IHUDocumentFactoryService extends ISingletonService, IHUDocumentFactory
{
	@Override
	List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId);

	@Override
	List<IHUDocument> createHUDocuments(final ProcessInfo pi);

	@Override
	List<IHUDocument> createHUDocumentsFromModel(final Object model);

	void registerHUDocumentFactory(String tableName, IHUDocumentFactory factory);

	/**
	 * Gets {@link IHUDocumentFactory} for given table.
	 *
	 * @param tableName
	 * @return factory
	 * @throws AdempiereException if factory was not found
	 */
	IHUDocumentFactory getHUDocumentFactory(String tableName);

	/**
	 * Check if given table name is supported by this factory service
	 *
	 * @param tableName
	 * @return true if is supported
	 */
	boolean isSupported(final String tableName);
}
