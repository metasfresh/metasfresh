/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement;
import de.metas.externalsystem.pcm.ExternalSystemPCMConfig;
import de.metas.externalsystem.pcm.ExternalSystemPCMConfigId;
import de.metas.externalsystem.pcm.InvokePCMService;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.Map;

public class InvokePCMAction extends AlterExternalSystemServiceStatusAction
{
	private final InvokePCMService invokePCMService = SpringContextHolder.instance.getBean(InvokePCMService.class);
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
					.orElseThrow(() -> new AdempiereException("No childConfig found for type Pro Care Management and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId", getRecord_ID()));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemPCMConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemPCMConfig pcmConfig = ExternalSystemPCMConfig.cast(externalSystemParentConfig.getChildConfig());
		final OrgId orgId = pcmConfig.getOrgId();

		final BPartnerLocationId orgBPartnerLocationId = orgDAO.getOrgInfoById(orgId).getOrgBPartnerLocationId();

		Check.assumeNotNull(orgBPartnerLocationId, "AD_Org_ID={} needs to have an Organisation Business Partner Location ID", OrgId.toRepoId(orgId));

		return invokePCMService.getParameters(pcmConfig, externalRequest, orgBPartnerLocationId.getBpartnerId());
	}

	@Override
	protected String getTabName()
	{
		return getExternalSystemType().getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.ProCareManagement;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_ProCareManagement.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@Override
	protected String getOrgCode(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemPCMConfig pcmConfig = ExternalSystemPCMConfig.cast(externalSystemParentConfig.getChildConfig());
		final OrgId orgId = pcmConfig.getOrgId();

		return orgDAO.getById(orgId).getValue();
	}
}
