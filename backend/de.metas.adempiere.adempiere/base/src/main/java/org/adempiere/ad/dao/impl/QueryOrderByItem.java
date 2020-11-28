package org.adempiere.ad.dao.impl;

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


import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.util.Check;
import lombok.NonNull;

@Immutable
final class QueryOrderByItem
{
	private final String columnName;
	private final Direction direction;
	private final Nulls nulls;

	public QueryOrderByItem(
			@NonNull final String columnName,
			@NonNull final Direction direction,
			@NonNull final Nulls nulls)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		this.direction = direction;

		this.nulls = nulls;
	}

	@Override
	public String toString()
	{
		return "OrderByItem ["
				+ "columnName=" + columnName
				+ ", direction=" + direction
				+ ", nulls=" + nulls
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(columnName)
				.append(direction)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final QueryOrderByItem other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.columnName, other.columnName)
				.append(this.direction, other.direction)
				.isEqual();
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName()
	{
		return columnName;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public Nulls getNulls()
	{
		return nulls;
	}
}
