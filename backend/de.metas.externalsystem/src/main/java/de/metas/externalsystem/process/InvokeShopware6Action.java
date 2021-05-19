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
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class InvokeShopware6Action extends InvokeExternalSystemProcess
{
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

		return ExternalSystemShopware6ConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, shopware6Config.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_CLIENT_SECRET, shopware6Config.getClientSecret());
		parameters.put(ExternalSystemConstants.PARAM_CLIENT_ID, shopware6Config.getClientId());
		parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, extractEffectiveSinceTimestamp().toInstant().toString());
		parameters.put(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_ID, shopware6Config.getBPartnerIdJSONPath());
		parameters.put(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID, shopware6Config.getBPartnerLocationIdJSONPath());

		return parameters;
	}

	@NonNull
	protected String getTabName()
	{
		return ExternalSystemType.Shopware6.getName();
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Shopware6.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@NonNull
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Shopware6;
	}
}
