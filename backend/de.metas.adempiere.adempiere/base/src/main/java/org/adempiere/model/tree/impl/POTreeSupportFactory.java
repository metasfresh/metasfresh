/**
 *
 */
package org.adempiere.model.tree.impl;

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

import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.adempiere.model.tree.spi.impl.DefaultPOTreeSupport;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tsa
 *
 */
public class POTreeSupportFactory implements IPOTreeSupportFactory
{
	private static final Logger logger = LogManager.getLogger(POTreeSupportFactory.class);

	private final ConcurrentHashMap<TableName, Class<? extends IPOTreeSupport>> map = new ConcurrentHashMap<>();

	@Override
	public IPOTreeSupport get(@NonNull final String tableName)
	{
		return get(TableName.ofString(tableName));
	}

	public IPOTreeSupport get(@NonNull final TableName tableName)
	{
		// NOTE: we need to create a new instance each time because IPOTreeSupport implementations are stateful

		final Class<? extends IPOTreeSupport> cl = map.get(tableName);

		final IPOTreeSupport result;
		if (cl == null)
		{
			result = new DefaultPOTreeSupport();
		}
		else
		{
			try
			{
				result = cl.getConstructor().newInstance();
			}
			catch (final Exception e)
			{
				throw new AdempiereException(e);
			}
		}
		result.setTableName(tableName.getAsString());
		return result;
	}

	@Override
	public void register(@NonNull final String tableName, @NonNull final Class<? extends IPOTreeSupport> clazz)
	{
		// do checks
		try
		{
			clazz.getConstructor();
		}
		catch (NoSuchMethodException e)
		{
			throw new AdempiereException("Class " + clazz + " does not have a public constructor without parameters", e);
		}

		// register
		map.put(TableName.ofString(tableName), clazz);

		logger.info("Registered {} for {}", clazz, tableName);
	}
}
