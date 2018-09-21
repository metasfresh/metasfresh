package de.metas.aggregation.api.impl;

/*
 * #%L
 * de.metas.aggregation
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


import java.util.Collection;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.aggregation.api.IAggregation;
import de.metas.aggregation.api.IAggregationItem;
import de.metas.aggregation.api.IAggregationItem.Type;
import de.metas.util.Check;

/**
 * Immutable {@link IAggregation} implementation
 * 
 * @author tsa
 *
 */
/* package */class Aggregation implements IAggregation
{
	private final String tableName;
	private final List<IAggregationItem> items;
	private final int aggregationId;

	/* package */Aggregation(final String tableName, final Collection<IAggregationItem> items, final int aggregationId)
	{
		super();

		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;

		Check.assumeNotEmpty(items, "items not empty");
		this.items = ImmutableList.copyOf(items);
		
		this.aggregationId = aggregationId <= 0 ? -1 : aggregationId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public List<IAggregationItem> getItems()
	{
		return items;
	}

	@Override
	public int getC_Aggregation_ID()
	{
		return aggregationId;
	}

	@Override
	public boolean hasColumnName(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		
		for (final IAggregationItem item : items)
		{
			if (item.getType() != Type.ModelColumn)
			{
				continue;
			}
			
			if (!columnName.equals(item.getColumnName()))
			{
				continue;
			}
			
			return true;
		}
		
		return false;
	}
}
