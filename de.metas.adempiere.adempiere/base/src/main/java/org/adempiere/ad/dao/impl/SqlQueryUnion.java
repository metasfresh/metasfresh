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


import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.IQuery;

public class SqlQueryUnion<T>
{
	private final IQuery<T> query;
	private final boolean distinct;

	protected SqlQueryUnion(final IQuery<T> query, final boolean distinct)
	{
		super();
		Check.assumeNotNull(query, "query not null");
		this.query = query;
		this.distinct = distinct;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public IQuery<T> getQuery()
	{
		return query;
	}

	public boolean isDistinct()
	{
		return distinct;
	}
}
