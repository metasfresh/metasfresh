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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import org.adempiere.ad.dao.IQueryOrderBy;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Comparator;

@Immutable
@EqualsAndHashCode
public final class SqlQueryOrderBy implements IQueryOrderBy
{
	private static final SqlQueryOrderBy NONE = new SqlQueryOrderBy(null);

	private final String orderBy;

	private static final String KEYWORD_ORDER_BY = "ORDER BY";

	private SqlQueryOrderBy(@Nullable final String orderBy)
	{
		this.orderBy = orderBy;
	}

	public static SqlQueryOrderBy of(@Nullable final String orderBy)
	{
		final String orderByNorm = normalize(orderBy);
		return orderByNorm != null
				? new SqlQueryOrderBy(orderByNorm)
				: NONE;
	}

	private static String normalize(@Nullable final String orderBy)
	{
		String orderByFinal = StringUtils.trimBlankToNull(orderBy);
		if (orderByFinal == null)
		{
			return null;
		}

		if (orderByFinal.toUpperCase().startsWith(KEYWORD_ORDER_BY))
		{
			orderByFinal = orderByFinal.substring(KEYWORD_ORDER_BY.length()).trim();
		}

		return orderByFinal;
	}

	@Override
	@Deprecated
	public String toString() {return getSql();}

	@Override
	public String getSql() {return orderBy;}

	@Override
	public Comparator<Object> getComparator() {throw new UnsupportedOperationException("SqlQueryOrderBy does not support Comparator");}
}
