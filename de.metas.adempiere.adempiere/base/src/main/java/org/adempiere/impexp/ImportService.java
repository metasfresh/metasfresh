/**
 *
 */
package org.adempiere.impexp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.impl.CompositeImportListener;
import org.adempiere.impexp.impl.NullImportListener;
import org.springframework.stereotype.Service;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class ImportService
{
	private final Map<String, IImportListener> tableName2handlers = new ConcurrentHashMap<>();

	public synchronized void registerListenerForTableName(@NonNull final IImportListener handlerNew, @NonNull final String tableName)
	{
		if (handlerNew instanceof CompositeImportListener)
		{
			throw new AdempiereException("Composite shall not be used: " + handlerNew);
		}

		//
		final IImportListener handlerCurrent = tableName2handlers.get(tableName);
		final IImportListener handlerComposed = CompositeImportListener.compose(handlerCurrent, handlerNew);
		tableName2handlers.put(tableName, handlerComposed);

	}

	public IImportListener getHandler(final String tableName)
	{
		final IImportListener handlers = tableName2handlers.get(tableName);
		if (handlers == null)
		{
			return NullImportListener.instance;
		}

		return handlers;
	}
}
