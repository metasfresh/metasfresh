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
public class AggregatedItem
{
	private final String groupKey;
	private final List<Item> items = new ArrayList<Item>();

	private final String trxName;

	public AggregatedItem(final String groupKey, final String trxName, final Item... items)
	{
		super();
		this.groupKey = groupKey;
		this.trxName = trxName;

		if (items != null && items.length > 0)
		{
			this.items.addAll(Arrays.asList(items));
		}
	}

	public void addItem(Item item)
	{
		items.add(item);
	}

	public String getGroupKey()
	{
		return groupKey;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public List<Item> getItems()
	{
		return new ArrayList<Item>(items);
	}

	public String toString()
	{
		return "AggregatedItem[" + groupKey
				+ ", trxName=" + trxName
				+ ", items: " + items
				+ "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupKey == null) ? 0 : groupKey.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((trxName == null) ? 0 : trxName.hashCode());
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
		AggregatedItem other = (AggregatedItem)obj;
		if (groupKey == null)
		{
			if (other.groupKey != null)
				return false;
		}
		else if (!groupKey.equals(other.groupKey))
			return false;
		if (items == null)
		{
			if (other.items != null)
				return false;
		}
		else if (!items.equals(other.items))
			return false;
		if (trxName == null)
		{
			if (other.trxName != null)
				return false;
		}
		else if (!trxName.equals(other.trxName))
			return false;
		return true;
	}
}
