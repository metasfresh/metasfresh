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
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class InvokeAlbertaAction extends InvokeExternalSystemProcess
{
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Alberta.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			id = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.get().getId().getRepoId();
		}

		return ExternalSystemAlbertaConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemAlbertaConfig albertaConfig = ExternalSystemAlbertaConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_API_KEY, albertaConfig.getApiKey());
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, albertaConfig.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_TENANT, albertaConfig.getTenant());

		if (getSinceParameterValue() != null)
		{
			parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE, String.valueOf(TimeUtil.asInstant(getSinceParameterValue())));
		}

		parameters.put(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE, albertaConfig.getValue());

		if (albertaConfig.getRootBPartnerIdForUsers() != null)
		{
			parameters.put(ExternalSystemConstants.PARAM_ROOT_BPARTNER_ID_FOR_USERS, String.valueOf(albertaConfig.getRootBPartnerIdForUsers().getRepoId()));
		}

		return parameters;
	}

	@NonNull
	protected String getTabName()
	{
		return ExternalSystemType.Alberta.getName();
	}

	@NonNull
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Alberta;
	}
}
