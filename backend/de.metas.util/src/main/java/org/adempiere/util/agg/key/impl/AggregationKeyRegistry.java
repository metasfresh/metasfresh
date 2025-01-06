package org.adempiere.util.agg.key.impl;

/*
 * #%L
 * de.metas.util
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

import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.adempiere.util.lang.ObjectUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link IAggregationKeyRegistry} default implementation
 *
 * @author al
 */
public class AggregationKeyRegistry implements IAggregationKeyRegistry
{
	private static final Map<String, List<String>> dependsOnColumnNames = new HashMap<>();

	private static final Map<String, CompositeAggregationKeyValueHandler> _valueHandlers = new HashMap<>();

	@Override
	public void registerDependsOnColumnnames(final String registrationKey, final String... columnNames)
	{
		dependsOnColumnNames.put(registrationKey, Arrays.asList(columnNames));
	}

	@Override
	public List<String> getDependsOnColumnNames(final String registrationKey)
	{
		return dependsOnColumnNames.get(registrationKey);
	}

	private CompositeAggregationKeyValueHandler getCompositeKeyValueHandler(@NonNull final String registrationKey)
	{
		CompositeAggregationKeyValueHandler compositeKeyValueHandler = _valueHandlers.get(registrationKey);
		if (compositeKeyValueHandler == null)
		{
			compositeKeyValueHandler = new CompositeAggregationKeyValueHandler();
			_valueHandlers.put(registrationKey, compositeKeyValueHandler);
		}
		return compositeKeyValueHandler;
	}

	@Override
	public void registerAggregationKeyValueHandler(final String registrationKey, final IAggregationKeyValueHandler<?> aggregationKeyValueHandler)
	{
		final CompositeAggregationKeyValueHandler compositeKeyValueHandler = getCompositeKeyValueHandler(registrationKey);

		@SuppressWarnings("unchecked")
		final IAggregationKeyValueHandler<Object> aggregationKeyValueHandlerCast = (IAggregationKeyValueHandler<Object>)aggregationKeyValueHandler;
		compositeKeyValueHandler.registerAggregationKeyValueHandler(aggregationKeyValueHandlerCast);
	}

	@Override
	public <T> List<Object> getValuesForModel(final String registrationKey, final T model)
	{
		final CompositeAggregationKeyValueHandler compositeKeyValueHandler = getCompositeKeyValueHandler(registrationKey);
		return compositeKeyValueHandler.getValues(model);
	}

	@Override
	public void clearHandlers(final String registrationKey)
	{
		_valueHandlers.remove(registrationKey);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
