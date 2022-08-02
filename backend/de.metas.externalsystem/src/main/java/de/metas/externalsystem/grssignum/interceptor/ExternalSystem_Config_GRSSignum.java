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

package de.metas.externalsystem.grssignum.interceptor;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.ExternalServices;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Interceptor(I_ExternalSystem_Config_GRSSignum.class)
@Component
public class ExternalSystem_Config_GRSSignum
{
	public final ExternalSystemConfigRepo externalSystemConfigRepo;
	public final ExternalServices externalServices;

	public ExternalSystem_Config_GRSSignum(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalServices externalServices)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalServices = externalServices;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystem_Config_ID })
	public void checkType(final I_ExternalSystem_Config_GRSSignum grsConfig)
	{
		final String parentType =
				externalSystemConfigRepo.getParentTypeById(ExternalSystemParentConfigId.ofRepoId(grsConfig.getExternalSystem_Config_ID()));

		if (!ExternalSystemType.GRSSignum.getCode().equals(parentType))
		{
			throw new AdempiereException("Invalid external system type!");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void generateAndSetUUID(final I_ExternalSystem_Config_GRSSignum grsConfig)
	{
		if (grsConfig.getCamelHttpResourceAuthKey() == null)
		{
			grsConfig.setCamelHttpResourceAuthKey(UUID.randomUUID().toString());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createExternalSystemInstance(final I_ExternalSystem_Config_GRSSignum grsConfig)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(grsConfig.getExternalSystem_Config_ID());

		externalServices.initializeServiceInstancesIfRequired(parentConfigId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_ExternalSystem_Config_GRSSignum.COLUMNNAME_IsSyncBPartnersToRestEndpoint)
	public void updateIsAutoFlag(final I_ExternalSystem_Config_GRSSignum grsConfig)
	{
		if (!grsConfig.isSyncBPartnersToRestEndpoint())
		{
			grsConfig.setIsAutoSendVendors(false);
			grsConfig.setIsAutoSendCustomers(false);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_ExternalSystem_Config_GRSSignum.COLUMNNAME_IsCreateBPartnerFolders,
					I_ExternalSystem_Config_GRSSignum.COLUMNNAME_BasePathForExportDirectories,
					I_ExternalSystem_Config_GRSSignum.COLUMNNAME_BPartnerExportDirectories })
	public void checkIsCreateBPartnerFoldersFlag(final I_ExternalSystem_Config_GRSSignum grsConfig)
	{
		if (!grsConfig.isCreateBPartnerFolders())
		{
			return;
		}

		if (Check.isBlank(grsConfig.getBPartnerExportDirectories())
				|| Check.isBlank(grsConfig.getBasePathForExportDirectories()))
		{
			throw new AdempiereException("BPartnerExportDirectories and BasePathForExportDirectories must be set!")
					.appendParametersToMessage()
					.setParameter("ExternalSystem_Config_GRSSignum_ID", grsConfig.getExternalSystem_Config_GRSSignum_ID());
		}
	}
}
