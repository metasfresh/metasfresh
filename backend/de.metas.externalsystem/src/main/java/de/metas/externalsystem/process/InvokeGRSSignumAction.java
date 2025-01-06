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
import de.metas.common.util.EmptyUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfig;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

public class InvokeGRSSignumAction extends AlterExternalSystemServiceStatusAction
{
	public final ExternalSystemConfigRepo externalSystemConfigDAO = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);

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
			final IExternalSystemChildConfig childConfig = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.orElseThrow(() -> new AdempiereException("No childConfig found for type GRSSignum and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId:", ExternalSystemParentConfigId.ofRepoId(getRecord_ID())));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemGRSSignumConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemGRSSignumConfig grsConfig = ExternalSystemGRSSignumConfig.cast(externalSystemParentConfig.getChildConfig());

		if (EmptyUtil.isEmpty(grsConfig.getCamelHttpResourceAuthKey()))
		{
			throw new AdempiereException("camelHttpResourceAuthKey for childConfig should not be empty at this point")
					.appendParametersToMessage()
					.setParameter("childConfigId", grsConfig.getId());
		}

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY, grsConfig.getCamelHttpResourceAuthKey());
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, grsConfig.getBaseUrl());

		return parameters;
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.GRSSignum.getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.GRSSignum;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_GRSSignum.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@Override
	protected String getOrgCode(@NonNull final ExternalSystemParentConfig config)
	{
		return orgDAO.getById(config.getOrgId()).getValue();
	}

}
