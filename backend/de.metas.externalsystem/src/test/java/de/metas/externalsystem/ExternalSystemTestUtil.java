/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem;

import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Metasfresh;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_Acct_Export;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_LocalFile;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_SFTP;
import de.metas.externalsystem.model.I_SAP_BPartnerImportSettings;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@UtilityClass
public class ExternalSystemTestUtil
{
	@NonNull
	@Builder(builderMethodName = "createI_ExternalSystem_ConfigBuilder", builderClassName = "I_ExternalSystem_ConfigBuilder")
	public I_ExternalSystem_Config createI_ExternalSystem_Config(
			@NonNull final String type,
			@Nullable final Boolean active,
			final int customParentConfigId)
	{
		final Boolean isActive = CoalesceUtil.coalesceNotNull(active, Boolean.TRUE);

		final I_ExternalSystem_Config record = newInstance(I_ExternalSystem_Config.class);
		record.setName("name");
		record.setType(type);
		record.setIsActive(isActive);
		record.setWriteAudit(true);
		record.setAuditFileFolder("auditFileFolder");

		if (customParentConfigId > 0)
		{
			record.setExternalSystem_Config_ID(customParentConfigId);
		}

		saveRecord(record);

		return record;
	}

	@NonNull
	@Builder(builderMethodName = "createGrsConfigBuilder", builderClassName = "grsConfigBuilder")
	private I_ExternalSystem_Config_GRSSignum createGrsConfig(
			final int externalSystemConfigId,
			@NonNull final String value,
			final boolean syncBPartnersToRestEndpoint,
			final boolean syncHUsOnMaterialReceipt,
			final boolean syncHUsOnProductionReceipt)
	{
		final Boolean isSyncBPartnersToRestEndpoint = CoalesceUtil.coalesceNotNull(syncBPartnersToRestEndpoint, Boolean.FALSE);
		final Boolean isSyncHUsOnMaterialReceipt = CoalesceUtil.coalesceNotNull(syncHUsOnMaterialReceipt, Boolean.FALSE);
		final Boolean isSyncHUsOnProductionReceipt = CoalesceUtil.coalesceNotNull(syncHUsOnProductionReceipt, Boolean.FALSE);

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(externalSystemConfigId);
		childRecord.setExternalSystemValue(value);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setTenantId("tenantId");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRestEndpoint(isSyncBPartnersToRestEndpoint);
		childRecord.setIsSyncHUsOnMaterialReceipt(isSyncHUsOnMaterialReceipt);
		childRecord.setIsSyncHUsOnProductionReceipt(isSyncHUsOnProductionReceipt);
		saveRecord(childRecord);

		return childRecord;
	}

	@NonNull
	@Builder(builderMethodName = "createRabbitMQConfigBuilder", builderClassName = "rabbitMQConfigBuilder")
	private I_ExternalSystem_Config_RabbitMQ_HTTP createRabbitMQConfig(
			final int externalSystemConfigId,
			@Nullable final String value,
			final boolean isSyncBPartnerToRabbitMQ,
			final boolean isAutoSendWhenCreatedByUserGroup,
			final int subjectCreatedByUserGroupId,
			final boolean isSyncExternalReferencesToRabbitMQ,
			final int customChildConfigId)
	{
		final int subjectCreatedByUserGroup_ID = CoalesceUtil.coalesceNotNull(subjectCreatedByUserGroupId, 1);
		final String configValue = CoalesceUtil.coalesceNotNull(value, "notImportant");

		final I_ExternalSystem_Config_RabbitMQ_HTTP childRecord = newInstance(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
		childRecord.setExternalSystemValue(configValue);
		childRecord.setRemoteURL("remoteURL");
		childRecord.setRouting_Key("routingKey");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRabbitMQ(isSyncBPartnerToRabbitMQ);
		childRecord.setExternalSystem_Config_ID(externalSystemConfigId);
		childRecord.setIsAutoSendWhenCreatedByUserGroup(isAutoSendWhenCreatedByUserGroup);
		childRecord.setSubjectCreatedByUserGroup_ID(subjectCreatedByUserGroup_ID);
		childRecord.setIsSyncExternalReferencesToRabbitMQ(isSyncExternalReferencesToRabbitMQ);

		if (customChildConfigId > 0)
		{
			childRecord.setExternalSystem_Config_RabbitMQ_HTTP_ID(customChildConfigId);
		}

		saveRecord(childRecord);

		return childRecord;
	}

	@NonNull
	@Builder(builderMethodName = "createMetasfreshConfigBuilder", builderClassName = "metasfreshConfigBuilder")
	private I_ExternalSystem_Config_Metasfresh createMetasfreshConfig(
			final int externalSystemConfigId,
			@NonNull final String value)
	{
		final I_ExternalSystem_Config_Metasfresh childRecord = newInstance(I_ExternalSystem_Config_Metasfresh.class);
		childRecord.setExternalSystem_Config_ID(externalSystemConfigId);
		childRecord.setExternalSystemValue(value);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setFeedbackResourceURL("feedbackResourceURL");
		childRecord.setFeedbackResourceAuthToken("feedbackResourceAuthToken");
		saveRecord(childRecord);

		return childRecord;
	}

	@NonNull
	@Builder(builderMethodName = "createSAPConfigBuilder", builderClassName = "sapConfigBuilder")
	private I_ExternalSystem_Config_SAP createSAPConfig(
			final int externalSystemConfigId,
			@Nullable final String value)
	{
		final String configValue = CoalesceUtil.coalesceNotNull(value, "notImportant");

		final I_ExternalSystem_Config_SAP childRecord = newInstance(I_ExternalSystem_Config_SAP.class);
		childRecord.setExternalSystemValue(configValue);
		childRecord.setExternalSystem_Config_ID(externalSystemConfigId);
		childRecord.setBaseURL("baseURL");
		childRecord.setApiVersion("ApiVersion");
		childRecord.setSignatureSAS("Signature");
		childRecord.setSignedPermissions("SignedPermissions");
		childRecord.setSignedVersion("signedVersion");
		childRecord.setPost_Acct_Documents_Path("Post_Acct_Documents_Path");

		saveRecord(childRecord);

		final I_ExternalSystem_Config_SAP_Acct_Export exportConfig = newInstance(I_ExternalSystem_Config_SAP_Acct_Export.class);
		exportConfig.setExternalSystem_Config_SAP_ID(childRecord.getExternalSystem_Config_SAP_ID());
		exportConfig.setC_DocType_ID(1);
		exportConfig.setAD_Process_ID(2);

		saveRecord(exportConfig);

		createSAPBPartnerImportSettings(childRecord.getExternalSystem_Config_SAP_ID());

		return childRecord;
	}

	@NonNull
	@Builder(builderMethodName = "createSAPContentSourceSFTPBuilder", builderClassName = "sapContentSourceSFTPBuilder")
	private I_ExternalSystem_Config_SAP_SFTP createSAPContentSourceSFTP(
			final int externalSystemConfigSAPId)
	{
		final I_ExternalSystem_Config_SAP_SFTP contentSourceSAPSFTP = newInstance(I_ExternalSystem_Config_SAP_SFTP.class);

		contentSourceSAPSFTP.setSFTP_HostName("testSAPSFTPHostName");
		contentSourceSAPSFTP.setSFTP_Port("testSAPSFTPPort");
		contentSourceSAPSFTP.setSFTP_Username("testSAPSFTPUsername");
		contentSourceSAPSFTP.setSFTP_Password("testSAPSFTPPassword");
		contentSourceSAPSFTP.setSFTP_Product_TargetDirectory("testSAPSFTPProductDirectory");
		contentSourceSAPSFTP.setSFTP_BPartner_TargetDirectory("testSAPSFTPBPartnerDirectory");
		contentSourceSAPSFTP.setSFTP_CreditLimit_TargetDirectory("testSAPSFTPCreditLimitDirectory");
		contentSourceSAPSFTP.setSFTP_ConversionRate_TargetDirectory("testSAPSFTPConversionRateDirectory");
		contentSourceSAPSFTP.setProcessedDirectory("testSAPSFTPProcessedDirectory");
		contentSourceSAPSFTP.setErroredDirectory("testSAPSFTPErroredDirectory");
		contentSourceSAPSFTP.setPollingFrequencyInMs(1000);
		contentSourceSAPSFTP.setSFTP_Product_FileName_Pattern("ProductSFTP*");
		contentSourceSAPSFTP.setSFTP_BPartner_FileName_Pattern("BPartnerSFTP*");
		contentSourceSAPSFTP.setSFTP_CreditLimit_FileName_Pattern("CreditLimitSFTP*");
		contentSourceSAPSFTP.setSFTP_ConversionRate_FileName_Pattern("ConversionRateSFTP*");
		contentSourceSAPSFTP.setExternalSystem_Config_SAP_ID(externalSystemConfigSAPId);

		saveRecord(contentSourceSAPSFTP);

		return contentSourceSAPSFTP;
	}

	@NonNull
	@Builder(builderMethodName = "createSAPContentSourceLocalFileBuilder", builderClassName = "sapContentSourceLocalFileBuilder")
	private I_ExternalSystem_Config_SAP_LocalFile createSAPContentSourceLocalFile(
			final int externalSystemConfigSAPId)
	{
		final I_ExternalSystem_Config_SAP_LocalFile contentSourceSAPLocalFile = newInstance(I_ExternalSystem_Config_SAP_LocalFile.class);

		contentSourceSAPLocalFile.setLocal_Root_Location("testLocalFileRootLocation");
		contentSourceSAPLocalFile.setLocalFile_Product_TargetDirectory("testSAPLocalFileProductDirectory");
		contentSourceSAPLocalFile.setLocalFile_BPartner_TargetDirectory("testSAPLocalFileBPartnerDirectory");
		contentSourceSAPLocalFile.setLocalFile_CreditLimit_TargetDirectory("testSAPLocalFileCreditLimitDirectory");
		contentSourceSAPLocalFile.setLocalFile_ConversionRate_TargetDirectory("testSAPLocalFileConversionRateDirectory");
		contentSourceSAPLocalFile.setProcessedDirectory("testSAPLocalFileProcessedDirectory");
		contentSourceSAPLocalFile.setErroredDirectory("testSAPLocalFileErroredDirectory");
		contentSourceSAPLocalFile.setPollingFrequencyInMs(1000);
		contentSourceSAPLocalFile.setLocalFile_Product_FileName_Pattern("ProductLocalFile*");
		contentSourceSAPLocalFile.setLocalFile_BPartner_FileName_Pattern("BPartnerLocalFile*");
		contentSourceSAPLocalFile.setLocalFile_CreditLimit_FileName_Pattern("CreditLimitLocalFile*");
		contentSourceSAPLocalFile.setLocalFile_ConversionRate_FileName_Pattern("ConversionRateLocalFile*");
		contentSourceSAPLocalFile.setExternalSystem_Config_SAP_ID(externalSystemConfigSAPId);

		saveRecord(contentSourceSAPLocalFile);

		return contentSourceSAPLocalFile;
	}

	@NonNull
	public I_SAP_BPartnerImportSettings createSAPBPartnerImportSettings(final int externalSystemConfigSAPId)
	{
		final I_SAP_BPartnerImportSettings importSettings = newInstance(I_SAP_BPartnerImportSettings.class);

		importSettings.setExternalSystem_Config_SAP_ID(externalSystemConfigSAPId);
		importSettings.setIsSingleBPartner(true);
		importSettings.setPartnerCodePattern("PartnerCode-Pattern");
		importSettings.setSeqNo(10);

		saveRecord(importSettings);

		return importSettings;
	}
}
