/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.process;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class InvokeOtherAction extends InvokeExternalSystemProcess
{

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		// dev-note: for "Other" external system there is no "real" ChildConfig, so when `childConfigId` process param is set
		// (i.e. only from the AD_Scheduler) it is actually referring to an ExternalSystem_Config_ID, aka ParentConfigID

		final int parentConfigRepoId = childConfigId > 0 ? childConfigId : getRecord_ID();

		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(parentConfigRepoId);
		return ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(parentConfigId);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemOtherConfig otherConfig = ExternalSystemOtherConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		otherConfig.getParameters().forEach(parameter -> parameters.put(parameter.getName(), parameter.getValue()));

		if (getSinceParameterValue() != null)
		{
			parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE, String.valueOf(TimeUtil.asInstant(getSinceParameterValue())));
		}

		getParameters().forEach(param -> parameters.put(param.getParameterName(), param.getParameterAsString()));

		return parameters;
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.Other.getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Other;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		throw new UnsupportedOperationException("getSelectedRecordCount unsupported for InvokeOtherAction!");
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		else if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId());

		if (!ExternalSystemType.Other.getCode().equals(externalSystemConfigDAO.getParentTypeById(parentConfigId)))
		{
			return ProcessPreconditionsResolution.reject();
		}

		final ExternalSystemOtherConfigId externalSystemOtherConfigId = ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(parentConfigId);

		final ExternalSystemParentConfig externalSystemParentConfig = externalSystemConfigDAO.getById(externalSystemOtherConfigId);
		final ExternalSystemOtherConfig otherConfig = ExternalSystemOtherConfig.cast(externalSystemParentConfig.getChildConfig());

		if (otherConfig.getParameters().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
