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


import java.util.Comparator;

import javax.annotation.concurrent.Immutable;

@Immutable
/* package */ class SqlQueryOrderBy extends AbstractQueryOrderBy
{
	private final String orderBy;

	public SqlQueryOrderBy(final String orderBy)
	{
		super();
		this.orderBy = normalize(orderBy);
	}

	private static final String normalize(final String orderBy)
	{
		String orderByFinal = orderBy != null ? orderBy.trim() : null;
		if (orderByFinal != null && orderByFinal.toUpperCase().startsWith("ORDER BY"))
		{
			orderByFinal = orderBy.substring(8);
		}
		return orderByFinal;
	}

	@Override
	public String getSql()
	{
		return orderBy;
	}

	@Override
	public Comparator<Object> getComparator()
	{
		throw new UnsupportedOperationException("SqlQueryOrderBy does not support Comparator");
	}

	@Override
	public String toString()
	{
		return "SqlQueryOrderBy[" + orderBy + "]";
	}
}
