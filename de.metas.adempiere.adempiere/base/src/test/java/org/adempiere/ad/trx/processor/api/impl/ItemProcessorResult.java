package org.adempiere.ad.trx.processor.api.impl;

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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

@Ignore
public class ItemProcessorResult
{
	private final List<AggregatedItem> aggregatedItems = new ArrayList<AggregatedItem>();

	public ItemProcessorResult(final AggregatedItem... aggregatedItems)
	{
		super();

		if (aggregatedItems != null && aggregatedItems.length > 0)
		{
			this.aggregatedItems.addAll(Arrays.asList(aggregatedItems));
		}
	}

	public void addAggregatedItem(final AggregatedItem aggregatedItem)
	{
		aggregatedItems.add(aggregatedItem);
	}

	public List<AggregatedItem> getAggregatedItem()
	{
		return new ArrayList<AggregatedItem>(aggregatedItems);
	}

	@Override
	public String toString()
	{
		return "ItemProcessorResult[" + aggregatedItems + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggregatedItems == null) ? 0 : aggregatedItems.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemProcessorResult other = (ItemProcessorResult)obj;
		if (aggregatedItems == null)
		{
			if (other.aggregatedItems != null)
				return false;
		}
		else if (!aggregatedItems.equals(other.aggregatedItems))
			return false;
		return true;
	}
}
