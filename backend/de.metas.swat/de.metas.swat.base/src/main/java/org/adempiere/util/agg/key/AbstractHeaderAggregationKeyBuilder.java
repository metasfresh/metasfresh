package org.adempiere.util.agg.key;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.List;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

/**
 * Base class for header aggregation key builders that uses {@link IAggregationKeyRegistry#getValuesForModel(String, Object)} to for the aggregation keys' components.
 */
public class AbstractHeaderAggregationKeyBuilder<T> extends AbstractAggregationKeyBuilder<T>
{
	public AbstractHeaderAggregationKeyBuilder(final String registrationKey)
	{
		super(registrationKey);
	}

	@Override
	public final String buildKey(final T item)
	{
		final ArrayKey key = buildArrayKey(item);
		return key.toString();
	}

	@Override
	public final boolean isSame(final T item1, final T item2)
	{
		final ArrayKey aggregationKey1 = buildArrayKey(item1);
		final ArrayKey aggregationKey2 = buildArrayKey(item2);

		return aggregationKey1.equals(aggregationKey2);
	}

	private final ArrayKey buildArrayKey(final T item)
	{
		final List<Object> keyValues = aggregationKeyRegistry.getValuesForModel(registrationKey, item);
		return Util.mkKey(keyValues.toArray());
	}
}
