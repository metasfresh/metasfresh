package org.adempiere.ad.callout.annotations.api.impl;

import java.util.Objects;

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

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/* package */final class CalloutMethodPointcutKey
{
	public static final CalloutMethodPointcutKey of(final String columnName)
	{
		return new CalloutMethodPointcutKey(columnName);
	}

	private final String columnName;

	private CalloutMethodPointcutKey(final String columnName)
	{
		super();

		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("columnName", columnName)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(columnName);
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

		final CalloutMethodPointcutKey other = (CalloutMethodPointcutKey)obj;
		return columnName.equals(other.columnName);
	}

	public String getColumnName()
	{
		return columnName;
	}
}
