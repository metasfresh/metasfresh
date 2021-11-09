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

import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import org.adempiere.exceptions.AdempiereException;

public class InvokeActivateShopware6ExternalConfig extends InvokeActivateExternalConfig
{
	public static final String PARAM_BASE_URL = "BaseURL";
	@Param(parameterName = PARAM_BASE_URL)
	protected String baseURl;

	public static final String PARAM_CLIENT_ID = "Client_Id";
	@Param(parameterName = PARAM_CLIENT_ID)
	protected String clientId;

	public static final String PARAM_CLIENT_SECRET = "Client_Secret";
	@Param(parameterName = PARAM_CLIENT_SECRET)
	protected String clientSecret;

	@Override
	protected void activateRecord()
	{
		final ExternalSystemParentConfig parentConfig = getSelectedExternalSystemConfig(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()))
				.orElseThrow(() -> new AdempiereException("No inactive externalSystemConfig found for parentConfig")
						.appendParametersToMessage()
						.setParameter("parentConfigId", ExternalSystemParentConfigId.ofRepoId(getRecord_ID())));

		final ExternalSystemShopware6Config childConfig = ExternalSystemShopware6Config.cast(parentConfig.getChildConfig())
				.toBuilder()
				.baseUrl(baseURl)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.isActive(true)
				.build();

		final ExternalSystemParentConfig recordUpdated = parentConfig.toBuilder()
				.isActive(true)
				.childConfig(childConfig)
				.build();

		externalSystemConfigRepo.saveConfig(recordUpdated);
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Shopware6;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Shopware6.Table_Name.equals(recordRef.getTableName()))
				.count();
	}
}
