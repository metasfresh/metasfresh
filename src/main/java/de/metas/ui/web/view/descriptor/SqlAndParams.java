package de.metas.ui.web.view.descriptor;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public final class SqlAndParams
{
	public static final SqlAndParams of(final String sql, final List<Object> sqlParams)
	{
		return new SqlAndParams(sql, sqlParams);
	}

	public static final SqlAndParams of(final String sql, final Object... sqlParamsArray)
	{
		final List<Object> sqlParams = sqlParamsArray != null && sqlParamsArray.length > 0 ? Arrays.asList(sqlParamsArray) : ImmutableList.of();
		return new SqlAndParams(sql, sqlParams);
	}

	@NonNull
	private final String sql;
	private final List<Object> sqlParams;

	public Object[] getSqlParamsArray()
	{
		return sqlParams == null ? null : sqlParams.toArray();
	}
}
