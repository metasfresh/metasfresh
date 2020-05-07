package org.adempiere.ad.dao.impl;

import java.util.List;

import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

class SqlQueryInsertFromColumn implements IQueryInsertFromColumn
{
	private final String sql;

	SqlQueryInsertFromColumn(final String sql)
	{
		Check.assumeNotEmpty(sql, "sql is not empty");
		this.sql = sql;
	}

	@Override
	public String getSql(final List<Object> sqlParams)
	{
		return sql;
	}

	@Override
	public boolean update(final Object toModel, final String toColumnName, final Object fromModel)
	{
		throw new UnsupportedOperationException();
	}

}
