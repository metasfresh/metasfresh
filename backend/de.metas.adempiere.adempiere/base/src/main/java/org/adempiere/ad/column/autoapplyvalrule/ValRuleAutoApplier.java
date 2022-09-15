package org.adempiere.ad.column.autoapplyvalrule;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADRefTable;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import java.util.Collection;

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
	// services
	private final IADTableDAO adTableDAO;
	private final ADReferenceService adReferenceService;

	@Getter private final String tableName;
	private final ImmutableList<MinimalColumnInfo> columns;

	@Builder
	private ValRuleAutoApplier(
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final IADTableDAO adTableDAO,
			@NonNull final String tableName,
			@NonNull final Collection<MinimalColumnInfo> columns)
	{
		this.adReferenceService = adReferenceService;
		this.adTableDAO = adTableDAO;

		this.tableName = tableName;
		this.columns = ImmutableList.copyOf(columns);
	}

	/**
	 * @param recordModel the record in question; generally not yet be persisted in DB.
	 */
	public void handleRecord(@NonNull final Object recordModel)
	{
		columns.forEach((column) -> handleColumn(recordModel, column));
	}

	private void handleColumn(
			@NonNull final Object recordModel,
			@NonNull final MinimalColumnInfo column)
	{
		if (!InterfaceWrapperHelper.isNullOrEmpty(recordModel, column.getColumnName()))
		{
			return;
		}

		final int resultId = retrieveFirstValRuleResultId(recordModel, column, adReferenceService);

		final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(column.getColumnName());
		if (resultId >= firstValidId)
		{
			InterfaceWrapperHelper.setValue(recordModel, column.getColumnName(), resultId);
		}
	}

	private static int retrieveFirstValRuleResultId(
			@NonNull final Object recordModel,
			@NonNull final MinimalColumnInfo column,
			@NonNull final ADReferenceService adReferenceService)
	{
		final ADRefTable tableRefInfo = extractTableRefInfo(column, adReferenceService);

		final IQueryBuilder<Object> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(tableRefInfo.getTableName());

		final AdValRuleId adValRuleId = column.getAdValRuleId();
		if (adValRuleId != null)
		{
			final ValidationRuleQueryFilter<Object> validationRuleQueryFilter = new ValidationRuleQueryFilter<>(recordModel, adValRuleId);
			queryBuilder.filter(validationRuleQueryFilter);
		}
		final IQuery<Object> query = queryBuilder
				.create()
				.setRequiredAccess(Access.READ);

		final String orderByClause = tableRefInfo.getOrderByClause();
		if (query instanceof TypedSqlQuery && !Check.isEmpty(orderByClause, true))
		{
			@SuppressWarnings("rawtypes") final TypedSqlQuery sqlQuery = (TypedSqlQuery)query;
			sqlQuery.setOrderBy(orderByClause);
		}

		return query.firstId();
	}

	private static ADRefTable extractTableRefInfo(@NonNull final MinimalColumnInfo column, @NonNull final ADReferenceService adReferenceService)
	{
		final ReferenceId adReferenceValueId = column.getAdReferenceValueId();
		return adReferenceValueId != null
				? adReferenceService.retrieveTableRefInfo(adReferenceValueId)
				: adReferenceService.getTableDirectRefInfo(column.getColumnName());
	}
}
