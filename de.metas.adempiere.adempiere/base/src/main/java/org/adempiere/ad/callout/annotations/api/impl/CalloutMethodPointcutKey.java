package org.adempiere.ad.callout.annotations.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

/* package */final class CalloutMethodPointcutKey
{
	private final String columnName;
	private final ArrayKey key;

	CalloutMethodPointcutKey(String columnName)
	{
		super();

		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		key = Util.mkKey(columnName);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + key + "]";
	}

	@Override
	public int hashCode()
	{
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj)
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

		final CalloutMethodPointcutKey other = (CalloutMethodPointcutKey)obj;
		return key.equals(other.key);
	}

	public String getColumnName()
	{
		return columnName;
	}
}
