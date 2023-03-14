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

package de.metas.externalsystem.sap;

import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_LocalFile;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_SFTP;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class SAPConfigMapper
{
	@NonNull
	public static SAPContentSourceSFTP buildContentSourceSFTP(@NonNull final I_ExternalSystem_Config_SAP_SFTP externalSystemConfigSapSftp)
	{
		return SAPContentSourceSFTP.builder()
				.hostName(externalSystemConfigSapSftp.getSFTP_HostName())
				.port(externalSystemConfigSapSftp.getSFTP_Port())
				.username(externalSystemConfigSapSftp.getSFTP_Username())
				.password(externalSystemConfigSapSftp.getSFTP_Password())

				.targetDirectoryProduct(externalSystemConfigSapSftp.getSFTP_Product_TargetDirectory())
				.fileNamePatternProduct(externalSystemConfigSapSftp.getSFTP_Product_FileName_Pattern())

				.targetDirectoryBPartner(externalSystemConfigSapSftp.getSFTP_BPartner_TargetDirectory())
				.fileNamePatternBPartner(externalSystemConfigSapSftp.getSFTP_BPartner_FileName_Pattern())

				.targetDirectoryCreditLimit(externalSystemConfigSapSftp.getSFTP_CreditLimit_TargetDirectory())
				.fileNamePatternCreditLimit(externalSystemConfigSapSftp.getSFTP_CreditLimit_FileName_Pattern())

				.targetDirectoryConversionRate(externalSystemConfigSapSftp.getSFTP_ConversionRate_TargetDirectory())
				.fileNamePatternConversionRate(externalSystemConfigSapSftp.getSFTP_ConversionRate_FileName_Pattern())

				.processedDirectory(externalSystemConfigSapSftp.getProcessedDirectory())
				.erroredDirectory(externalSystemConfigSapSftp.getErroredDirectory())
				.pollingFrequency(Duration.ofMillis(externalSystemConfigSapSftp.getPollingFrequencyInMs()))

				.approvedBy(UserId.ofRepoIdOrNullIfSystem(externalSystemConfigSapSftp.getApprovedBy_ID()))

				.build();
	}

	@NonNull
	public static SAPContentSourceLocalFile buildContentSourceLocalFile(@NonNull final I_ExternalSystem_Config_SAP_LocalFile externalSystemConfigSapLocalFile)
	{
		return SAPContentSourceLocalFile.builder()
				.rootLocation(externalSystemConfigSapLocalFile.getLocal_Root_Location())

				.targetDirectoryProduct(externalSystemConfigSapLocalFile.getLocalFile_Product_TargetDirectory())
				.fileNamePatternProduct(externalSystemConfigSapLocalFile.getLocalFile_Product_FileName_Pattern())

				.targetDirectoryBPartner(externalSystemConfigSapLocalFile.getLocalFile_BPartner_TargetDirectory())
				.fileNamePatternBPartner(externalSystemConfigSapLocalFile.getLocalFile_BPartner_FileName_Pattern())

				.targetDirectoryCreditLimit(externalSystemConfigSapLocalFile.getLocalFile_CreditLimit_TargetDirectory())
				.fileNamePatternCreditLimit(externalSystemConfigSapLocalFile.getLocalFile_CreditLimit_FileName_Pattern())

				.targetDirectoryConversionRate(externalSystemConfigSapLocalFile.getLocalFile_ConversionRate_TargetDirectory())
				.fileNamePatternConversionRate(externalSystemConfigSapLocalFile.getLocalFile_ConversionRate_FileName_Pattern())

				.processedDirectory(externalSystemConfigSapLocalFile.getProcessedDirectory())
				.erroredDirectory(externalSystemConfigSapLocalFile.getErroredDirectory())
				.pollingFrequency(Duration.ofMillis(externalSystemConfigSapLocalFile.getPollingFrequencyInMs()))

				.approvedBy(UserId.ofRepoIdOrNullIfSystem(externalSystemConfigSapLocalFile.getApprovedBy_ID()))

				.build();
	}
}
