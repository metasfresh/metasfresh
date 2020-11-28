package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;

import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.service.IParameterizable;
import de.metas.adempiere.service.IParametersDAO;
import de.metas.adempiere.service.IParametersDAO.IParameterPO;
import de.metas.adempiere.util.Parameter;
import de.metas.util.Services;

public class ParameterBL implements IParameterBL
{
	@Override
	public void createParameters(final Object parent, final IParameterizable parameterizable, final String parameterTable)
	{
		for (final Parameter param : parameterizable.getParameters())
		{
			final IParameterPO paramPO = Services.get(IParametersDAO.class).newParameterPO(parent, parameterTable);

			paramPO.setDisplayName(param.displayName);
			paramPO.setDescription(param.description);
			paramPO.setAD_Reference_ID(param.displayType);
			paramPO.setName(param.name);
			paramPO.setSeqNo(param.seqNo);

			final Object valueObj = param.getValue();

			if (valueObj != null)
			{
				// setting default value
				final String valueString = mkParamValueStr(valueObj, param.displayType);

				paramPO.setParamValue(valueString);
			}

			InterfaceWrapperHelper.save(paramPO);
		}
	}

	@Override
	public List<Parameter> retrieveParams(final Object parent, final String parameterTable)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(parent);
		final String trxName = InterfaceWrapperHelper.getTrxName(parent);
		final int recordId = InterfaceWrapperHelper.getId(parent);
		final String tableName = InterfaceWrapperHelper.getModelTableName(parent);

		return retrieveParams(ctx, tableName, recordId, parameterTable, trxName);
	}

	@Override
	public List<Parameter> retrieveParams(Properties ctx, String parentTable, int parentId, String parameterTable, String trxName)
	{
		final List<Parameter> result = new ArrayList<Parameter>();

		final List<IParameterPO> paramPOs = Services.get(IParametersDAO.class).retrieveParamPOs(ctx, parentTable, parentId, parameterTable, trxName);

		for (final IParameterPO paramPO : paramPOs)
		{
			final String name = paramPO.getName();

			final String displayName = paramPO.getDisplayName();
			final String description = paramPO.getDescription();

			final int displayType = paramPO.getAD_Reference_ID();

			final int seqNo = paramPO.getSeqNo();

			final Parameter param = new Parameter(name, displayName, description, displayType, seqNo);

			final String paramValue = paramPO.getParamValue();

			param.setValue(mkParamValueObj(paramValue, displayType));

			result.add(param);
		}

		return result;
	}

	private Object mkParamValueObj(final String valueStr, final int displayType)
	{
		final Object result;

		if (displayType == DisplayType.String)
		{
			result = valueStr;
		}
		else if (displayType == DisplayType.Integer)
		{
			result = Integer.parseInt(valueStr);
		}
		else if (displayType == DisplayType.Number)
		{
			result = new BigDecimal(valueStr);
		}
		else if (displayType == DisplayType.YesNo)
		{
			result = "Y".equals(valueStr);
		}
		else
		{
			throw new IllegalArgumentException("Illegal displayType " + displayType);
		}

		return result;
	}

	private String mkParamValueStr(final Object valueObj, final int displayType)
	{
		final String result;

		if (displayType == DisplayType.String)
		{
			result = (String)valueObj;
		}
		else if (displayType == DisplayType.Integer)
		{
			result = Integer.toString((Integer)valueObj);
		}
		else if (displayType == DisplayType.Number)
		{
			result = ((BigDecimal)valueObj).toString();
		}
		else if (displayType == DisplayType.YesNo)
		{
			result = ((Boolean)valueObj) ? "Y" : "N";
		}
		else
		{
			throw new IllegalArgumentException("Illegal displayType " + displayType);
		}

		return result;
	}

	@Override
	public void deleteParameters(final Object parent, final String parameterTable)
	{
		for (final IParameterPO paramPO : Services.get(IParametersDAO.class).retrieveParamPOs(parent, parameterTable))
		{
			InterfaceWrapperHelper.delete(paramPO);
		}
	}
}
