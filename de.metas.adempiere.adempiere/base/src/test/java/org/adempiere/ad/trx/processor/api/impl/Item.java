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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.junit.Ignore;

/**
 * Input item used in tests
 *
 * @author tsa
 *
 */
@Ignore
public class Item
{
	private final String groupKey;
	private final String value;

	private Exception onItemErrorException;
	private boolean processed;

	public Item(final String groupKey, final String value)
	{
		super();
		this.groupKey = groupKey;
		this.value = value;
	}

	public String getGroupKey()
	{
		return groupKey;
	}

	public String getValue()
	{
		return value;
	}

	public boolean isOnItemError()
	{
		return onItemErrorException != null;
	}

	public void setOnItemErrorException(final Exception onItemErrorExectption)
	{
		this.onItemErrorException = onItemErrorExectption;
	}

	@Override
	public String toString()
	{
		return groupKey + "-" + value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (groupKey == null ? 0 : groupKey.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Item other = (Item)obj;
		if (groupKey == null)
		{
			if (other.groupKey != null)
			{
				return false;
			}
		}
		else if (!groupKey.equals(other.groupKey))
		{
			return false;
		}
		if (value == null)
		{
			if (other.value != null)
			{
				return false;
			}
		}
		else if (!value.equals(other.value))
		{
			return false;
		}
		return true;
	}

	/**
	 * Method is called by {@link MockedItemProcessor#process(Item)} so that we can later check which items were processed.
	 * Note that this method is called, even if the item was configured to throw an exception via {@link MockedItemProcessor#setThrowExceptionIfItem(Item)}.
	 */
	public void setProcessed()
	{
		this.processed = true;
	}

	/**
	 *
	 * @return <code>true</code> if {@link MockedItemProcessor#process(Item)} was called for this item. Also see {@link #setProcessed()} for background info.
	 */
	public boolean isProcessed()
	{
		return processed;
	}
}
