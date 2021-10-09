package org.adempiere.ad.column.autoapplyvalrule;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.IColumnInfo;
import org.adempiere.ad.service.TableRefInfo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public class ValRuleAutoApplier
{
	@Getter
	private final String tableName;

	private final ImmutableMap<String, I_AD_Column> columns;

	public ValRuleAutoApplier(
			@NonNull final String tableName,
			@NonNull final Collection<I_AD_Column> columns)
	{
		this.tableName = tableName;
		this.columns = Maps.uniqueIndex(columns, I_AD_Column::getColumnName);
	}

	/**
	 *
	 * @param recordModel the record in question; generally not yet be persisted in DB.
	 */
	public void handleRecord(@NonNull final Object recordModel)
	{
		for (final String columnName : columns.keySet())
		{
			handleColumn(recordModel, columnName);
		}
	}

	private void handleColumn(
			@NonNull final Object recordModel,
			@NonNull final String columnName)
	{
		if (!InterfaceWrapperHelper.isNullOrEmpty(recordModel, columnName))
		{
			return;
		}

		final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		final I_AD_Column column = columns.get(columnName);
		final IColumnInfo columnInfo = lookupDAO.retrieveColumnInfo(column.getAD_Column_ID());

		final int resultId = retrieveFirstValRuleResultId(recordModel, column);

		final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(columnInfo.getColumnName());
		if (resultId >= firstValidId)
		{
			InterfaceWrapperHelper.setValue(recordModel, columnInfo.getColumnName(), resultId);
		}
	}

	private int retrieveFirstValRuleResultId(
			@NonNull final Object recordModel,
			@NonNull final I_AD_Column column)
	{
		final TableRefInfo tableRefInfo = extractTableRefInfo(column);

		final IQueryBuilder<Object> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(tableRefInfo.getTableName());

		if (column.getAD_Val_Rule_ID() > 0)
		{
			final ValidationRuleQueryFilter<Object> validationRuleQueryFilter = new ValidationRuleQueryFilter<>(recordModel, column.getAD_Val_Rule_ID());
			queryBuilder.filter(validationRuleQueryFilter);
		}
		final IQuery<Object> query = queryBuilder
				.create()
				.setRequiredAccess(Access.READ);

		final String orderByClause = tableRefInfo.getOrderByClause();
		if (query instanceof TypedSqlQuery && !Check.isEmpty(orderByClause, true))
		{
			@SuppressWarnings("rawtypes")
			final TypedSqlQuery sqlQuery = (TypedSqlQuery)query;
			sqlQuery.setOrderBy(orderByClause);
		}

		final int resultId = query.firstId();
		return resultId;
	}

	private TableRefInfo extractTableRefInfo(@NonNull final I_AD_Column column)
	{
		final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		final TableRefInfo tableRefInfo;
		if (column.getAD_Reference_Value_ID() > 0)
		{
			tableRefInfo = lookupDAO.retrieveTableRefInfo(column.getAD_Reference_Value_ID());
		}
		else
		{
			tableRefInfo = lookupDAO.retrieveTableDirectRefInfo(column.getColumnName());
		}
		return tableRefInfo;
	}
}
