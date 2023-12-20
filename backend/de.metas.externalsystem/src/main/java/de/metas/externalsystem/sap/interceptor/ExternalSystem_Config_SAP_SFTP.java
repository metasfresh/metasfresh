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

import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_SFTP;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.externalsystem.sap.interceptor.ExternalSystemConfigSAPHelper.sanitizeDirectoryRelativePath;

@Interceptor(I_ExternalSystem_Config_SAP_SFTP.class)
@Component
public class ExternalSystem_Config_SAP_SFTP
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_SFTP_Product_TargetDirectory })
	public void sanitizeProductTargetDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setSFTP_Product_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getSFTP_Product_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_SFTP_BPartner_TargetDirectory })
	public void sanitizeBPartnerTargetDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setSFTP_BPartner_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getSFTP_BPartner_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_SFTP_CreditLimit_TargetDirectory })
	public void sanitizeCreditLimitTargetDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setSFTP_CreditLimit_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getSFTP_CreditLimit_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_SFTP_ConversionRate_TargetDirectory })
	public void sanitizeConversionRateTargetDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setSFTP_ConversionRate_TargetDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getSFTP_ConversionRate_TargetDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_ProcessedDirectory })
	public void sanitizeProcessedDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setProcessedDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getProcessedDirectory()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_ErroredDirectory })
	public void sanitizeErroredDirectory(final I_ExternalSystem_Config_SAP_SFTP sapConfigSFTP)
	{
		sapConfigSFTP.setErroredDirectory(sanitizeDirectoryRelativePath(sapConfigSFTP.getErroredDirectory()));
	}
}
