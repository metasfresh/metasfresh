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


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.adempiere.util.lang.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Used in {@link AggregationKeyRegistry} as it's value handler.
 *
 * @author al
 */
/* package */class CompositeAggregationKeyValueHandler implements IAggregationKeyValueHandler<Object>
{

	/**
	 * IMPORTANT:</b> we need the handlers to have a fixed ordering. We use their class names to achieve this.
	 */
	public final Set<IAggregationKeyValueHandler<Object>> aggregationKeyValueHandlers = new TreeSet<IAggregationKeyValueHandler<Object>>(new Comparator<IAggregationKeyValueHandler<Object>>()
	{
		@Override
		public int compare(final IAggregationKeyValueHandler<Object> o1, final IAggregationKeyValueHandler<Object> o2)
		{
			// note: we won't add null handlers to the set, but just to be sure we explicitly avoid an NPE
			final String o1ClassName = o1 == null ? "<null>" : o1.getClass().getName();
			final String o2ClassName = o2 == null ? "<null>" : o2.getClass().getName();

			return new CompareToBuilder().append(o1ClassName, o2ClassName).build();
		}
	});

	public void registerAggregationKeyValueHandler(final IAggregationKeyValueHandler<Object> aggregationKeyValueHandler)
	{
		if (aggregationKeyValueHandler == null)
		{
			return; // nothing to do (should not happen)
		}
		aggregationKeyValueHandlers.add(aggregationKeyValueHandler);
	}

	@Override
	public List<Object> getValues(@NonNull final Object model)
	{
		final List<Object> values = new ArrayList<Object>();
		for (final IAggregationKeyValueHandler<Object> aggregationKeyValueHandler : aggregationKeyValueHandlers)
		{
			final List<Object> aggregationKeyValues = aggregationKeyValueHandler.getValues(model);
			values.addAll(aggregationKeyValues);
		}
		return values;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
