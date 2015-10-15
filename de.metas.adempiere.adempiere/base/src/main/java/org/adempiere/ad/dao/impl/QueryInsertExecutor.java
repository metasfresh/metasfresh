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


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.dao.IQueryInsertExecutor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

class QueryInsertExecutor<ToModelType, FromModelType> implements IQueryInsertExecutor<ToModelType, FromModelType>
{
	private final Class<ToModelType> toModelClass;
	private final String toTableName;

	private final AbstractTypedQuery<FromModelType> fromQuery;
	private final Class<FromModelType> fromModelClass;

	private final Map<String, IQueryInsertFromColumn> toColumn2fromColumn = new HashMap<>();
	private final Map<String, IQueryInsertFromColumn> toColumn2fromColumnRO = Collections.unmodifiableMap(toColumn2fromColumn);

	QueryInsertExecutor(final Class<ToModelType> toModelClass, final AbstractTypedQuery<FromModelType> fromQuery)
	{
		super();

		Check.assumeNotNull(toModelClass, "toModelClass not null");
		this.toModelClass = toModelClass;
		this.toTableName = InterfaceWrapperHelper.getTableName(toModelClass);

		Check.assumeNotNull(fromQuery, "fromQuery not null");
		this.fromQuery = fromQuery;
		this.fromModelClass = fromQuery.getModelClass();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int execute()
	{
		return fromQuery.executeInsert(this);
	}

	@Override
	public QueryInsertExecutor<ToModelType, FromModelType> mapCommonColumns()
	{
		final Set<String> fromColumnNames = InterfaceWrapperHelper.getModelColumnNames(fromModelClass);
		final Set<String> toColumnNames = new HashSet<>(InterfaceWrapperHelper.getModelColumnNames(toModelClass));
		toColumnNames.retainAll(fromColumnNames);

		for (final String toColumnName : toColumnNames)
		{
			final String fromColumnName = toColumnName;
			final IQueryInsertFromColumn from = new QueryInsertFromColumn(fromColumnName);
			mapColumn(toColumnName, from);
		}

		return this;
	}

	@Override
	public QueryInsertExecutor<ToModelType, FromModelType> mapColumn(final String toColumnName, final String fromColumnName)
	{
		final IQueryInsertFromColumn from = new QueryInsertFromColumn(fromColumnName);
		mapColumn(toColumnName, from);
		return this;
	}

	private final QueryInsertExecutor<ToModelType, FromModelType> mapColumn(final String toColumnName, final IQueryInsertFromColumn from)
	{
		this.toColumn2fromColumn.put(toColumnName, from);
		return this;
	}

	@Override
	public QueryInsertExecutor<ToModelType, FromModelType> mapColumnToConstant(final String toColumnName, final Object constantValue)
	{
		final IQueryInsertFromColumn from = new ConstantQueryInsertFromColumn(constantValue);
		mapColumn(toColumnName, from);
		return this;
	}

	/**
	 *
	 * @return "To ColumnName" to "From Column" map
	 */
	public Map<String, IQueryInsertFromColumn> getColumnMapping()
	{
		return toColumn2fromColumnRO;
	}

	public boolean isEmpty()
	{
		return toColumn2fromColumn.isEmpty();
	}

	public String getToTableName()
	{
		return toTableName;
	}
	
	public Class<ToModelType> getToModelClass()
	{
		return toModelClass;
	}
}
