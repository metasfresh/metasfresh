package org.adempiere.ad.column.autoapplyvalrule.interceptor;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.ad_reference.ADReferenceService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.column.autoapplyvalrule.ValRuleAutoApplier;
import org.adempiere.ad.column.autoapplyvalrule.ValRuleAutoApplierService;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_AD_Column.class)
@Component
public class AD_Column_AutoApplyValRuleConfig
{
	private IModelValidationEngine engine;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final ITabCalloutFactory tabCalloutFactory = Services.get(ITabCalloutFactory.class);
	private final ADReferenceService adReferenceService;
	private final ValRuleAutoApplierService valRuleAutoApplierService;

	private AD_Column_AutoApplyValRuleInterceptor autoApplyValRuleInterceptor;

	public AD_Column_AutoApplyValRuleConfig(
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final ValRuleAutoApplierService valRuleAutoApplierService)
	{
		this.adReferenceService = adReferenceService;
		this.valRuleAutoApplierService = valRuleAutoApplierService;
	}

	@Init
	public void initialize(@NonNull final IModelValidationEngine engine)
	{
		this.engine = engine;

		this.autoApplyValRuleInterceptor = new AD_Column_AutoApplyValRuleInterceptor(valRuleAutoApplierService);

		// TODO move into a DAO; let the DAO retrieve pojo(s) with tableNAme
		final IQueryBuilder<I_AD_Column> queryBuilder = createQueryBuilder();

		createAndRegisterForQuery(engine, queryBuilder.create());
	}

	private IQueryBuilder<I_AD_Column> createQueryBuilder()
	{
		return queryBL
				.createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_IsAutoApplyValidationRule, true)
				.orderBy(I_AD_Column.COLUMNNAME_AD_Table_ID)
				.orderBy(I_AD_Column.COLUMN_AD_Column_ID);
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_AD_Val_Rule_ID, I_AD_Column.COLUMNNAME_IsAutoApplyValidationRule })
	public void resetModelInterceptor(@NonNull final I_AD_Column column)
	{
		final String tableName = adTableDAO.retrieveTableName(column.getAD_Table_ID());
		valRuleAutoApplierService.unregisterForTableName(tableName);

		// createAndRegisterForQuery might add it again
		engine.removeModelChange(tableName, autoApplyValRuleInterceptor);
		tabCalloutFactory.unregisterTabCalloutForTable(tableName, AD_Column_AutoApplyValRuleTabCallout.class);

		final IQueryBuilder<I_AD_Column> queryBuilder = createQueryBuilder()
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, column.getAD_Table_ID());

		createAndRegisterForQuery(engine, queryBuilder.create());
	}

	private void createAndRegisterForQuery(
			@NonNull final IModelValidationEngine engine,
			@NonNull final IQuery<I_AD_Column> query)
	{
		final HashSet<String> tableNamesWithRegisteredColumn = new HashSet<>();

		final ImmutableSet<AdColumnId> allColumnIds = query.listIds(AdColumnId::ofRepoId);
		final Collection<MinimalColumnInfo> allColumns = adTableDAO.getMinimalColumnInfosByIds(allColumnIds);

		final ImmutableListMultimap<AdTableId, MinimalColumnInfo> tableId2columns = Multimaps.index(allColumns, MinimalColumnInfo::getAdTableId);

		for (final AdTableId adTableId : tableId2columns.keySet())
		{
			final String tableName = adTableDAO.retrieveTableName(adTableId);
			final Collection<MinimalColumnInfo> columns = tableId2columns.get(adTableId);

			final ValRuleAutoApplier valRuleAutoApplier = ValRuleAutoApplier.builder()
					.adTableDAO(adTableDAO)
					.adReferenceService(adReferenceService)
					.tableName(tableName)
					.columns(columns)
					.build();
			valRuleAutoApplierService.registerApplier(valRuleAutoApplier);

			tableNamesWithRegisteredColumn.add(tableName);
		}

		tableNamesWithRegisteredColumn
				.forEach(tableNameWithRegisteredColum -> {

					engine.addModelChange(tableNameWithRegisteredColum, autoApplyValRuleInterceptor);
					tabCalloutFactory.registerTabCalloutForTable(tableNameWithRegisteredColum, AD_Column_AutoApplyValRuleTabCallout.class);
				});
	}
}
