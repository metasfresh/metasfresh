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
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfig;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_API_MODE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_API_USER_REFRESH_TOKEN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_APP_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CERT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_DEV_ID;

public class InvokeEbayAction extends InvokeExternalSystemProcess
{
	@Override
	@NonNull
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			final IExternalSystemChildConfig childConfig = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.orElseThrow(() -> new AdempiereException("No childConfig found for type EBAY and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId:", ExternalSystemParentConfigId.ofRepoId(getRecord_ID())));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemEbayConfigId.ofRepoId(id);
	}

	@Override
	@NonNull
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemEbayConfig ebayConfig = ExternalSystemEbayConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_APP_ID, ebayConfig.getAppId());
		parameters.put(PARAM_CERT_ID, ebayConfig.getCertId());
		parameters.put(PARAM_DEV_ID, ebayConfig.getDevId());
		parameters.put(PARAM_API_USER_REFRESH_TOKEN, ebayConfig.getRefreshToken());
		parameters.put(PARAM_API_MODE, ebayConfig.getApiMode().getCode());

		return parameters;
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.Ebay.getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Ebay;
	}

	@Override
	protected long getSelectedRecordCount(@NonNull final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Ebay.Table_Name.equals(recordRef.getTableName()))
				.count();
	}
}
