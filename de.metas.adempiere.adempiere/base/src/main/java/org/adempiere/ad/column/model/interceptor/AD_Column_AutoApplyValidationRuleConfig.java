package org.adempiere.ad.column.model.interceptor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.util.Services;
import lombok.NonNull;

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
@Component("org.adempiere.ad.column.model.interceptor.AD_Column_AutoApplyValidationRuleConfig")
public class AD_Column_AutoApplyValidationRuleConfig
{
	private IModelValidationEngine engine;

	private Map<String, AD_Column_AutoApplyValidationRule> tableName2validator;

	@Init
	public void initialize(@NonNull final IModelValidationEngine engine)
	{
		this.engine = engine;

		// TODO move into a DAO; let the DAO retrieve pojo(s) with tableNAme
		final IQueryBuilder<I_AD_Column> queryBuilder = createQueryBuilder();

		createAndRegisterForQuery(engine, queryBuilder.create());
	}

	private IQueryBuilder<I_AD_Column> createQueryBuilder()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_IsAutoApplyValidationRule, true)
				.addNotEqualsFilter(I_AD_Column.COLUMN_AD_Val_Rule_ID, null)
				.orderBy(I_AD_Column.COLUMN_AD_Table_ID)
				.orderBy(I_AD_Column.COLUMN_AD_Column_ID);
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_AD_Val_Rule_ID, I_AD_Column.COLUMNNAME_IsAutoApplyValidationRule })
	public void resetModelInterceptor(@NonNull final I_AD_Column column)
	{
		final String tableName = column.getAD_Table().getTableName();

		if (tableName2validator != null)
		{
			final AD_Column_AutoApplyValidationRule interceptorToRemove = tableName2validator.remove(tableName);
			if (interceptorToRemove != null)
			{
				engine.removeModelChange(tableName, interceptorToRemove);
			}
		}

		final IQueryBuilder<I_AD_Column> queryBuilder = createQueryBuilder().addEqualsFilter(I_AD_Column.COLUMN_AD_Table_ID, column.getAD_Table_ID());

		createAndRegisterForQuery(engine, queryBuilder.create());
	}

	private void createAndRegisterForQuery(
			@NonNull final IModelValidationEngine engine,
			@NonNull final IQuery<I_AD_Column> query)
	{
		final List<I_AD_Column> columnsToHandle = query.list();

		final ImmutableListMultimap<Integer, I_AD_Column> tableId2columns = Multimaps.index(columnsToHandle, I_AD_Column::getAD_Table_ID);

		tableName2validator = new ConcurrentHashMap<>();

		for (final int adTableId : tableId2columns.keySet())
		{
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
			final AD_Column_AutoApplyValidationRule autoApplyValidationRule = new AD_Column_AutoApplyValidationRule(tableName, tableId2columns.get(adTableId));

			engine.addModelChange(tableName, autoApplyValidationRule);
			tableName2validator.put(tableName, autoApplyValidationRule);
		}
	}
}
