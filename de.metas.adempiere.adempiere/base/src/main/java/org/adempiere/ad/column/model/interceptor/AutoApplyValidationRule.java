package org.adempiere.ad.column.model.interceptor;

import lombok.NonNull;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.IColumnInfo;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
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

public class AutoApplyValidationRule implements IModelInterceptor
{

	private static final Logger logger = LogManager.getLogger(AutoApplyValidationRule.class);

	private int m_AD_Client_ID = -1;
	private IModelValidationEngine engine;

	private final ImmutableMap<String, I_AD_Column> columns;

	private final String tableName;

	AutoApplyValidationRule(
			@NonNull final String tableName,
			@NonNull final ImmutableList<I_AD_Column> columns)
	{
		this.tableName = tableName;
		this.columns = Maps.uniqueIndex(columns, I_AD_Column::getColumnName);
	}

	@Override
	public void initialize(IModelValidationEngine engine, I_AD_Client client)
	{
		if (this.engine != null)
		{
			throw new IllegalStateException("Validator " + this + " was already registered to another validation engine: " + engine);
		}
		this.engine = engine;

		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		// nothing

	}

	@Override
	public void onModelChange(Object recordModel, ModelChangeType changeType)
	{
		if (!ModelChangeType.BEFORE_NEW.equals(changeType))
		{
			return;
		}

		final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		for (final String columnName : columns.keySet())
		{
			if (!InterfaceWrapperHelper.isNullOrEmpty(recordModel, columnName))
			{
				continue;
			}

			final I_AD_Column column = columns.get(columnName);

			final IColumnInfo orderLineColumnInfo = lookupDAO.retrieveColumnInfo(column.getAD_Column_ID());
			final ITableRefInfo tableRefInfo;
			if (column.getAD_Reference_Value_ID() > 0)
			{
				tableRefInfo = lookupDAO.retrieveTableRefInfo(column.getAD_Reference_Value_ID());
			}
			else
			{
				tableRefInfo = lookupDAO.retrieveTableDirectRefInfo(column.getColumnName());
			}

			InterfaceWrapperHelper.setDynAttribute(recordModel, Env.DYNATTR_WindowNo, 0);
			InterfaceWrapperHelper.setDynAttribute(recordModel, Env.DYNATTR_TabNo, 0);

			final ValidationRuleQueryFilter<Object> validationRuleQueryFilter = new ValidationRuleQueryFilter<>(recordModel, column.getAD_Val_Rule_ID());
			final int resultId = Services.get(IQueryBL.class)
					.createQueryBuilder(tableRefInfo.getTableName())
					.filter(validationRuleQueryFilter)
					.orderBy(tableRefInfo.getKeyColumn())
					.create()
					.firstId();
			// todo handle e.g. AD_User_ID with possibility of 0
			if (resultId > 0)
			{
				InterfaceWrapperHelper.setValue(recordModel, orderLineColumnInfo.getColumnName(), resultId);
			}
		}

	}

	@Override
	public void onDocValidate(Object model, DocTimingType timing) throws Exception
	{
		// nothing

	}
}
