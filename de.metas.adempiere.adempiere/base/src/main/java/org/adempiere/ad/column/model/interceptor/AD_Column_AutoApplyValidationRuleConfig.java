package org.adempiere.ad.column.model.interceptor;

import lombok.NonNull;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Column;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.util.Services;

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

	@Init
	public void initialize(@NonNull final IModelValidationEngine engine)
	{
		if (this.engine != null)
		{
			throw new IllegalStateException("Validator " + this + " was already registered to another validation engine: " + engine);
		}
		this.engine = engine;

		register();
	}

	private void register()
	{
		final List<I_AD_Column> columnsToHandle = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_IsAutoApplyValidationRule, true)
				.addNotEqualsFilter(I_AD_Column.COLUMN_AD_Val_Rule_ID, null)
				.orderBy().addColumn(I_AD_Column.COLUMN_AD_Table_ID).addColumn(I_AD_Column.COLUMN_AD_Column_ID).endOrderBy()
				.create()
				.list();

		final ImmutableListMultimap<Integer, I_AD_Column> tableId2columns = Multimaps.index(columnsToHandle, I_AD_Column::getAD_Table_ID);

		for (final int adTableId : tableId2columns.keySet())
		{
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
			final AutoApplyValidationRule autoApplyValidationRule = new AutoApplyValidationRule(tableName, tableId2columns.get(adTableId));

			engine.addModelChange(tableName, autoApplyValidationRule);
		}
	}

}
