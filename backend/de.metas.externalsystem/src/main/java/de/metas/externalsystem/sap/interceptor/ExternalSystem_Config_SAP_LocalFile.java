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

import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_LocalFile;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.externalsystem.sap.interceptor.ExternalSystemConfigSAPHelper.sanitizeDirectoryRelativePath;

@Interceptor(I_ExternalSystem_Config_SAP_LocalFile.class)
@Component
public class ExternalSystem_Config_SAP_LocalFile
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_LocalFile_Product_TargetDirectory })
	public void sanitizeProductTargetDirectory(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		sapConfigLocalFile.setLocalFile_Product_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigLocalFile.getLocalFile_Product_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_LocalFile_BPartner_TargetDirectory })
	public void sanitizeBPartnerTargetDirectory(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		sapConfigLocalFile.setLocalFile_BPartner_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigLocalFile.getLocalFile_BPartner_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_LocalFile_CreditLimit_TargetDirectory })
	public void sanitizeCreditLimitTargetDirectory(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		sapConfigLocalFile.setLocalFile_CreditLimit_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigLocalFile.getLocalFile_CreditLimit_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_ProcessedDirectory })
	public void sanitizeProcessedDirectory(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		sapConfigLocalFile.setProcessedDirectory(sanitizeDirectoryRelativePath(sapConfigLocalFile.getProcessedDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_ErroredDirectory })
	public void sanitizeErroredDirectory(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		sapConfigLocalFile.setErroredDirectory(sanitizeDirectoryRelativePath(sapConfigLocalFile.getErroredDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_Local_Root_Location })
	public void sanitizeRootLocation(final I_ExternalSystem_Config_SAP_LocalFile sapConfigLocalFile)
	{
		final String rootLocation = sapConfigLocalFile.getLocal_Root_Location();
		if (rootLocation.endsWith("/") || rootLocation.endsWith("\\"))
		{
			sapConfigLocalFile.setLocal_Root_Location(rootLocation.substring(0, rootLocation.length() - 1));
		}
	}

}
