package de.metas.async.api.impl;

/*
 * #%L
 * de.metas.async
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IReference;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessParams;

public class WorkpackageParamDAO implements IWorkpackageParamDAO
{
	@Override
	public IParams retrieveWorkpackageParams(final I_C_Queue_WorkPackage workpackage)
	{
		// NOTE: the actual parameters are lazy loaded on demand
		return new ProcessParams(new IReference<List<ProcessInfoParameter>>()
		{
			@Override
			public List<ProcessInfoParameter> getValue()
			{
				return retrieveWorkpackageProcessInfoParameters(workpackage);
			}
		});
	}

	/**
	 * Retrieves workpackage parametes as {@link ProcessInfoParameter} list:
	 * <ul>
	 * <li>parameters which were directly set to workpackage (i.e. {@link I_C_Queue_WorkPackage_Param})
	 * <li>fallback: parameters which were set to creator AD_PInstance_ID
	 * </ul>
	 * 
	 * @param workpackage
	 * @return
	 */
	private List<ProcessInfoParameter> retrieveWorkpackageProcessInfoParameters(final I_C_Queue_WorkPackage workpackage)
	{
		Check.assumeNotNull(workpackage, "workpackage not null");

		final Map<String, ProcessInfoParameter> workpackagesParamsMap = new HashMap<>();

		//
		// Load parameters from creator's AD_PInstance_ID
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
			final int adPInstanceId = extractAD_PInstance_ID(workpackage);
			final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
			for (final ProcessInfoParameter param : adPInstanceDAO.retrieveProcessInfoParameters(ctx, adPInstanceId))
			{
				workpackagesParamsMap.put(param.getParameterName(), param);
			}
		}

		//
		// Load parameters which are directly set to this workpackage
		// NOTE: this will OVERRIDE AD_PInstance level parameters
		for (final I_C_Queue_WorkPackage_Param workpackageParam : retrieveWorkpackageParametersList(workpackage))
		{
			final ProcessInfoParameter param = createProcessInfoParameter(workpackageParam);
			workpackagesParamsMap.put(param.getParameterName(), param);
		}

		return new ArrayList<>(workpackagesParamsMap.values());
	}

	private List<I_C_Queue_WorkPackage_Param> retrieveWorkpackageParametersList(final I_C_Queue_WorkPackage workpackage)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Queue_WorkPackage_Param.class, workpackage)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_WorkPackage_Param.COLUMN_C_Queue_WorkPackage_ID, workpackage.getC_Queue_WorkPackage_ID())
				.create()
				.list();
	}

	private ProcessInfoParameter createProcessInfoParameter(final I_C_Queue_WorkPackage_Param workpackageParam)
	{
		// NOTE to developer: when changing this, make sure you are also changing the counterpart method setParameterValue()
		
		Check.assumeNotNull(workpackageParam, "workpackageParam not null");

		final String parameterName = workpackageParam.getParameterName();
		final int displayType = workpackageParam.getAD_Reference_ID();
		final Object parameter;
		if (DisplayType.isText(displayType))
		{
			parameter = workpackageParam.getP_String();
		}
		else if (DisplayType.YesNo == displayType)
		{
			parameter = workpackageParam.getP_String();
		}
		else if (DisplayType.isNumeric(displayType))
		{
			parameter = workpackageParam.getP_Number();
		}
		else if (DisplayType.isDate(displayType))
		{
			parameter = workpackageParam.getP_Date();
		}
		else
		{
			throw new AdempiereException("Unsupported display type: " + displayType
					+ "\n @C_Queue_WorkPackage_Param_ID@: " + workpackageParam);
		}

		final Object parameter_To = null; // N/A
		final String info = parameter == null ? "" : String.valueOf(parameter);
		final String info_To = null; // N/A
		return new ProcessInfoParameter(parameterName, parameter, parameter_To, info, info_To);
	}
	
	@Override
	public void setParameterValue(final I_C_Queue_WorkPackage_Param workpackageParam, final Object parameterValue)
	{
		// NOTE to developer: when changing this, make sure you are also changing the counterpart method createProcessInfoParameter()
		
		if (parameterValue == null)
		{
			workpackageParam.setAD_Reference_ID(DisplayType.String);
			resetParameterValue(workpackageParam);
		}
		else if (parameterValue instanceof String)
		{
			workpackageParam.setAD_Reference_ID(DisplayType.String);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_String(parameterValue.toString());
		}
		else if (parameterValue instanceof Date)
		{
			final Date date = (Date)parameterValue;
			workpackageParam.setAD_Reference_ID(DisplayType.DateTime);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_Date(TimeUtil.asTimestamp(date));
		}
		else if (parameterValue instanceof BigDecimal)
		{
			final BigDecimal valueBD = (BigDecimal)parameterValue;
			workpackageParam.setAD_Reference_ID(DisplayType.Number);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_Number(valueBD);
		}
		else if (parameterValue instanceof Number)
		{
			final int valueInt = ((Number)parameterValue).intValue();
			workpackageParam.setAD_Reference_ID(DisplayType.Integer);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_Number(BigDecimal.valueOf(valueInt));
		}
		else if (parameterValue instanceof Boolean)
		{
			final boolean valueBoolean = (boolean)parameterValue;
			workpackageParam.setAD_Reference_ID(DisplayType.YesNo);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_String(DisplayType.toBooleanString(valueBoolean));
		}
		else
		{
			throw new IllegalArgumentException("Unsupported parameter value: " + parameterValue + " (" + parameterValue.getClass() + ")");
		}
	}

	private final void resetParameterValue(final I_C_Queue_WorkPackage_Param workpackageParam)
	{
		workpackageParam.setP_String(null);
		workpackageParam.setP_Number(null);
		workpackageParam.setP_Date(null);
	}


	private static final int extractAD_PInstance_ID(final I_C_Queue_WorkPackage workpackage)
	{
		//
		// Get the AD_PInstance_ID from Workpackage
		final int workpackageADPInstanceId = workpackage.getAD_PInstance_ID();
		if (workpackageADPInstanceId > 0)
		{
			return workpackageADPInstanceId;
		}

		//
		// Get the AD_PInstance_ID from Workpackage Block (if any)
		// NOTE: even if now, the C_Queue_Block_ID is mandatory, in future it could be made optional.
		// Also, JUnit tests are not setting this all the time, so, for now we can tollerate it.
		final I_C_Queue_Block queueBlock = workpackage.getC_Queue_Block();
		final int blockAD_PInstance_ID = queueBlock == null ? -1 : queueBlock.getAD_PInstance_Creator_ID();
		return blockAD_PInstance_ID > 0 ? blockAD_PInstance_ID : -1;
	}
}
