package de.metas.ui.web.document.filter.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.util.DB;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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

/**
 * Helper class used to collect SQL parameters between method calls.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@EqualsAndHashCode
@ToString
public final class SqlParamsCollector
{
	public static SqlParamsCollector newInstance()
	{
		return new SqlParamsCollector(new ArrayList<>());
	}

	/**
	 * Wraps the given list. All changes will be directed to that list.
	 */
	public static SqlParamsCollector wrapNullable(@Nullable final List<Object> list)
	{
		if (list == null)
		{
			return NOT_COLLECTING;
		}
		return new SqlParamsCollector(list);
	}

	/** An {@link SqlParamsCollector} which is actually not collecting the parameters but it's automatically translating it to SQL code. */
	public static SqlParamsCollector notCollecting()
	{
		return NOT_COLLECTING;
	}

	private static final SqlParamsCollector NOT_COLLECTING = new SqlParamsCollector(null);

	private final List<Object> params;
	private final List<Object> paramsRO;

	private SqlParamsCollector(final List<Object> params)
	{
		this.params = params;
		paramsRO = params != null ? Collections.unmodifiableList(params) : null;
	}

	public boolean isCollecting()
	{
		return params != null;
	}

	/**
	 * Directly append all sqlParams.
	 * 
	 * @deprecated Please avoid using it. It's used mainly to adapt with old code
	 * 
	 * @param sqlParams
	 */
	@Deprecated
	public void collectAll(final Collection<? extends Object> sqlParams)
	{
		if (sqlParams == null || sqlParams.isEmpty())
		{
			return;
		}

		if (params == null)
		{
			throw new IllegalStateException("Cannot append " + sqlParams + " to not collecting params");
		}

		params.addAll(sqlParams);
	}

	/**
	 * Collects given SQL value and returns an SQL placeholder, i.e. "?"
	 * 
	 * In case this is in non-collecting mode, the given SQL value will be converted to SQL code and it will be returned.
	 * The internal list won't be affected, because it does not exist.
	 */
	public String placeholder(final Object sqlValue)
	{
		if (params == null)
		{
			return DB.TO_SQL(sqlValue);
		}
		else
		{
			params.add(sqlValue);
			return "?";
		}
	}

	/** @return readonly live list */
	public List<Object> toList()
	{
		return paramsRO;
	}
}
