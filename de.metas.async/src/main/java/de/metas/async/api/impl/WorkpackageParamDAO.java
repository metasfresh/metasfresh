package de.metas.async.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.cache.CCache;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessParams;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

public class WorkpackageParamDAO implements IWorkpackageParamDAO
{
	private final CCache<QueueWorkPackageId, List<I_C_Queue_WorkPackage_Param>> cache = CCache
			.<QueueWorkPackageId, List<I_C_Queue_WorkPackage_Param>> builder()
			.tableName(I_C_Queue_WorkPackage.Table_Name)
			.build();

	@Override
	public IParams retrieveWorkpackageParams(final I_C_Queue_WorkPackage workpackage)
	{
		// NOTE: the actual parameters are lazy loaded on demand
		return new ProcessParams(() -> retrieveWorkpackageProcessInfoParameters(workpackage));
	}

	/**
	 * Retrieves workpackage parametes as {@link ProcessInfoParameter} list:
	 * <ul>
	 * <li>parameters which were directly set to workpackage (i.e. {@link I_C_Queue_WorkPackage_Param})
	 * <li>fallback: parameters which were set to creator AD_PInstance_ID
	 * </ul>
	 */
	private List<ProcessInfoParameter> retrieveWorkpackageProcessInfoParameters(@NonNull final I_C_Queue_WorkPackage workpackage)
	{
		final Map<String, ProcessInfoParameter> workpackagesParamsMap = new HashMap<>();

		//
		// Load parameters from creator's AD_PInstance_ID
		{
			final PInstanceId pinstanceId = extractAD_PInstance_ID(workpackage);
			final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
			for (final ProcessInfoParameter param : adPInstanceDAO.retrieveProcessInfoParameters(pinstanceId))
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

	private List<I_C_Queue_WorkPackage_Param> retrieveWorkpackageParametersList(@NonNull final I_C_Queue_WorkPackage workpackage)
	{
		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(workpackage.getC_Queue_WorkPackage_ID());
		return cache.getOrLoad(workPackageId, () -> retrieveWorkpackageParametersList0(workPackageId));
	}

	private List<I_C_Queue_WorkPackage_Param> retrieveWorkpackageParametersList0(@NonNull final QueueWorkPackageId workpackageId)
	{
		final List<I_C_Queue_WorkPackage_Param> result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Queue_WorkPackage_Param.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_WorkPackage_Param.COLUMN_C_Queue_WorkPackage_ID, workpackageId)
				.create()
				.list();
		return result;
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
	public void setParameterValue(
			@NonNull final I_C_Queue_WorkPackage workpackageRecord,
			@NonNull final String parameterName,
			@NonNull final Object parameterValue)
	{
		final I_C_Queue_WorkPackage_Param paramRecord = getOrCreateParamRecord(workpackageRecord, parameterName);

		setParameterValue(paramRecord, parameterValue);

		saveRecord(paramRecord);
	}

	private I_C_Queue_WorkPackage_Param getOrCreateParamRecord(
			@NonNull final I_C_Queue_WorkPackage workpackageRecord,
			@NonNull final String parameterName)
	{
		final List<I_C_Queue_WorkPackage_Param> workpackageParameterRecords = retrieveWorkpackageParametersList(workpackageRecord);
		final ImmutableMap<String, I_C_Queue_WorkPackage_Param> name2ParameterRecord = Maps.uniqueIndex(workpackageParameterRecords, I_C_Queue_WorkPackage_Param::getParameterName);

		final I_C_Queue_WorkPackage_Param workPackageParamRecord = name2ParameterRecord.get(parameterName);
		if (workPackageParamRecord != null)
		{
			return workPackageParamRecord;
		}

		return WorkPackageParamsUtil.createWorkPackageParamRecord(workpackageRecord, parameterName);
	}

	@Override
	public void setParameterValue(
			@NonNull final I_C_Queue_WorkPackage_Param workpackageParam,
			@Nullable final Object parameterValue)
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
		else if (TimeUtil.isDateOrTimeObject(parameterValue))
		{
			final Timestamp date = TimeUtil.asTimestamp(parameterValue);
			workpackageParam.setAD_Reference_ID(DisplayType.DateTime);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_Date(date);
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
		else if (parameterValue instanceof RepoIdAware)
		{
			final int valueInt = ((RepoIdAware)parameterValue).getRepoId();
			workpackageParam.setAD_Reference_ID(DisplayType.Integer);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_Number(BigDecimal.valueOf(valueInt));
		}
		else if (parameterValue instanceof Boolean)
		{
			final boolean valueBoolean = (boolean)parameterValue;
			workpackageParam.setAD_Reference_ID(DisplayType.YesNo);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_String(StringUtils.ofBoolean(valueBoolean));
		}
		else if (parameterValue instanceof ReferenceListAwareEnum)
		{
			final ReferenceListAwareEnum valueEnum = (ReferenceListAwareEnum)parameterValue;
			workpackageParam.setAD_Reference_ID(DisplayType.String);
			resetParameterValue(workpackageParam);
			workpackageParam.setP_String(valueEnum.getCode());
		}
		else
		{
			throw new AdempiereException("Parameter 'parameterValue' has an unsupported class=" + parameterValue.getClass())
					.appendParametersToMessage()
					.setParameter("parameterValue", parameterValue);
		}
	}

	private final void resetParameterValue(final I_C_Queue_WorkPackage_Param workpackageParam)
	{
		workpackageParam.setP_String(null);
		workpackageParam.setP_Number(null);
		workpackageParam.setP_Date(null);
	}

	private static final PInstanceId extractAD_PInstance_ID(final I_C_Queue_WorkPackage workpackage)
	{
		//
		// Get the AD_PInstance_ID from Workpackage
		final int workpackageADPInstanceId = workpackage.getAD_PInstance_ID();
		if (workpackageADPInstanceId > 0)
		{
			return PInstanceId.ofRepoId(workpackageADPInstanceId);
		}

		//
		// Get the AD_PInstance_ID from Workpackage Block (if any)
		// NOTE: even if now, the C_Queue_Block_ID is mandatory, in future it could be made optional.
		// Also, JUnit tests are not setting this all the time, so, for now we can tollerate it.
		final I_C_Queue_Block queueBlock = workpackage.getC_Queue_Block();
		final int blockAD_PInstance_ID = queueBlock == null ? -1 : queueBlock.getAD_PInstance_Creator_ID();
		return blockAD_PInstance_ID > 0 ? PInstanceId.ofRepoId(blockAD_PInstance_ID) : null;
	}

	@Override
	public void deleteWorkpackageParams(@NonNull final QueueWorkPackageId workpackageId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Queue_WorkPackage_Param.class)
				.addEqualsFilter(I_C_Queue_WorkPackage_Param.COLUMN_C_Queue_WorkPackage_ID, workpackageId)
				.create()
				.delete();
	}

}
