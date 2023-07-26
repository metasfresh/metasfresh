/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.dimension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DimensionService
{
	private final ImmutableMap<String, DimensionFactory<?>> factoriesByTableName;

	public DimensionService(@NonNull final List<DimensionFactory<?>> factories)
	{
		factoriesByTableName = Maps.uniqueIndex(factories, DimensionFactory::getHandledTableName);
	}

	public Dimension getFromRecord(@NonNull final Object record)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(record);
		return getFactory(tableName).getFromRecord(record);
	}

	public void updateRecord(@NonNull final Object record, @NonNull final Dimension from)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(record);
		getFactory(tableName).updateRecord(record, from);
	}

	private DimensionFactory<Object> getFactory(final String tableName)
	{
		@SuppressWarnings("unchecked")
		final DimensionFactory<Object> factory = (DimensionFactory<Object>)factoriesByTableName.get(tableName);

		if(factory == null)
		{
			throw new AdempiereException("No "+DimensionFactory.class+" found for "+ tableName);
		}
		return factory;
	}
}
