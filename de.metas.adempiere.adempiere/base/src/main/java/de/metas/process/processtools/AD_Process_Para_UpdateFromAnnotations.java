package de.metas.process.processtools;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.M_Element;
import org.compiere.util.DisplayType;

import com.google.common.base.Function;

import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessClassParamInfo;
import de.metas.process.SvrProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Creates/Updates {@link I_AD_Process_Para} from Classname's annotations (i.e. {@link ProcessClassInfo}).
 * <p>
 * <b>IMPORTANT:</b> this process is an early prototype! Expect the generated <code>AD_Process_Para</code> to be wrong (e.g. wrong <code>AD_Reference_ID</code>).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_Process_Para_UpdateFromAnnotations extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Process process = getRecord(I_AD_Process.class);
		final Map<String, I_AD_Process_Para> processParams = retriveProcessParams(process);

		final ProcessClassInfo processClassInfo = ProcessClassInfo.ofClassname(process.getClassname());
		for (final ProcessClassParamInfo paramInfo : processClassInfo.getParameterInfos())
		{
			final String parameterName = paramInfo.getParameterName();
			final I_AD_Process_Para processParamModel = processParams.get(parameterName);
			createUpdateProcessParam(process, processParamModel, paramInfo);
		}
		return MSG_OK;
	}

	private Map<String, I_AD_Process_Para> retriveProcessParams(final I_AD_Process process)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Process_Para.class, process)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, process.getAD_Process_ID())
				.create()
				.map(I_AD_Process_Para.class, new Function<I_AD_Process_Para, String>()
				{
					@Override
					public String apply(final I_AD_Process_Para param)
					{
						return param.getColumnName();
					}
				});
	}

	private void createUpdateProcessParam(final I_AD_Process process, I_AD_Process_Para processParamModel, final ProcessClassParamInfo paramInfo)
	{
		final boolean isNew;
		if (processParamModel == null)
		{
			isNew = true;

			processParamModel = InterfaceWrapperHelper.newInstance(I_AD_Process_Para.class, process);
			processParamModel.setAD_Process(process);

			final int lastSeqNo = Services.get(IADProcessDAO.class).retrieveProcessParaLastSeqNo(process);
			processParamModel.setSeqNo(lastSeqNo + 10);

			final M_Element adElement = M_Element.get(getCtx(), paramInfo.getParameterName());
			Check.assumeNotNull(adElement, "adElement not null for ColumnName={}", paramInfo.getParameterName());
			processParamModel.setAD_Element(adElement);
			processParamModel.setColumnName(paramInfo.getParameterName());
			processParamModel.setName(adElement.getName());
			processParamModel.setDescription(adElement.getDescription());
			processParamModel.setHelp(adElement.getHelp());

			processParamModel.setAD_Reference_ID(getDisplayType(paramInfo));
		}
		else
		{
			isNew = false;
		}

		processParamModel.setIsMandatory(paramInfo.isMandatory());

		InterfaceWrapperHelper.save(processParamModel);
		addLog((isNew ? "Created" : "Updated") + ": " + processParamModel.getColumnName());
	}

	private int getDisplayType(final ProcessClassParamInfo paramInfo)
	{
		final String parameterName = paramInfo.getParameterName();
		final Class<?> type = paramInfo.getFieldType();
		if (parameterName.endsWith("_ID"))
		{
			return DisplayType.TableDir;
		}
		else if (BigDecimal.class.equals(type))
		{
			if (parameterName.indexOf("Qty") >= 0
					|| parameterName.indexOf("Quantity") >= 0)
			{
				return DisplayType.Quantity;
			}
			else if (parameterName.indexOf("Amt") >= 0
					|| parameterName.indexOf("Amount") >= 0)
			{
				return DisplayType.Amount;
			}
			else
			{
				return DisplayType.Number;
			}
		}
		else if (int.class == type || Integer.class == type)
		{
			return DisplayType.Integer;
		}
		else if (Date.class.isAssignableFrom(type))
		{
			return DisplayType.Date;
		}
		else if (String.class.equals(type))
		{
			return DisplayType.String;
		}
		else if (boolean.class.equals(type) || Boolean.class.equals(type))
		{
			return DisplayType.String;
		}
		else
		{
			throw new AdempiereException("Cannot determine DisplayType for " + paramInfo);
		}
	}

}
