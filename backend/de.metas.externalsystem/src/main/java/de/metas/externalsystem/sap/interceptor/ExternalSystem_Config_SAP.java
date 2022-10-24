/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.sap.interceptor;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import javax.annotation.Nullable;

public class ExternalSystem_Config_SAP
{
	public final ExternalSystemConfigRepo externalSystemConfigRepo;

	public ExternalSystem_Config_SAP(final ExternalSystemConfigRepo externalSystemConfigRepo)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP.COLUMNNAME_ExternalSystem_Config_ID })
	public void checkType(final I_ExternalSystem_Config_SAP sapConfig)
	{
		final String parentType =
				externalSystemConfigRepo.getParentTypeById(ExternalSystemParentConfigId.ofRepoId(sapConfig.getExternalSystem_Config_ID()));

		if (!ExternalSystemType.SAP.getCode().equals(parentType))
		{
			throw new AdempiereException("Invalid external system type!");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP.COLUMNNAME_SFTP_TargetDirectory })
	public void sanitizeTargetDirectory(final I_ExternalSystem_Config_SAP sapConfig)
	{
		sapConfig.setSFTP_TargetDirectory(sanitizeDirectoryRelativePath(sapConfig.getSFTP_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP.COLUMNNAME_ProcessedDirectory })
	public void sanitizeProcessedDirectory(final I_ExternalSystem_Config_SAP sapConfig)
	{
		sapConfig.setSFTP_TargetDirectory(sanitizeDirectoryRelativePath(sapConfig.getProcessedDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP.COLUMNNAME_ErroredDirectory })
	public void sanitizeErroredDirectory(final I_ExternalSystem_Config_SAP sapConfig)
	{
		sapConfig.setSFTP_TargetDirectory(sanitizeDirectoryRelativePath(sapConfig.getErroredDirectory()));
	}

	@Nullable
	private static String sanitizeDirectoryRelativePath(@Nullable final String sftpTargetDirectory)
	{
		if (sftpTargetDirectory == null)
		{
			return null;
		}

		if (sftpTargetDirectory.startsWith("/"))
		{
			return sftpTargetDirectory.replaceFirst("/", "");
		}
		else if (sftpTargetDirectory.startsWith("./"))
		{
			return sftpTargetDirectory.replaceFirst("\\./", "");
		}

		return sftpTargetDirectory;
	}
}
