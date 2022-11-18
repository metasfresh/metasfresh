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

package de.metas.externalsystem.process;

import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP;
import de.metas.externalsystem.sap.ExternalSystemSAPConfig;
import de.metas.externalsystem.sap.ExternalSystemSAPConfigId;
import de.metas.externalsystem.sap.SAPExternalRequest;
import de.metas.externalsystem.sap.source.ISAPContentSource;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CREDIT_LIMIT_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CREDIT_LIMIT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PRODUCT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ROOT_LOCATION;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_BPARTNER_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_HOST_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PRODUCT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_USERNAME;

public class InvokeSAPAction extends AlterExternalSystemServiceStatusAction
{
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
					.orElseThrow(() -> new AdempiereException("No childConfig found for type SAP and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId", getRecord_ID()));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemSAPConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemSAPConfig sapConfig = ExternalSystemSAPConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_CHILD_CONFIG_VALUE, sapConfig.getValue());
		parameters.putAll(extractContentSourceParameters(sapConfig));

		return parameters;
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.SAP.getName();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.SAP;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_SAP.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	private Map<String, String> extractContentSourceParameters(@NonNull final ExternalSystemSAPConfig sapConfig)
	{
		final SAPExternalRequest sapExternalRequest = SAPExternalRequest.ofCode(externalRequest);

		switch (sapExternalRequest)
		{
			case START_PRODUCT_SYNC_SFTP:
			case START_BPARTNER_SYNC_SFTP:
			case STOP_PRODUCT_SYNC_SFTP:
			case STOP_BPARTNER_SYNC_SFTP:
				return Optional.ofNullable(sapConfig.getContentSourceSFTP())
						.map(sapContentSourceSFTP -> validateExternalSystemConfig(sapContentSourceSFTP,
																				  sapExternalRequest,
																				  sapConfig.getParentId(),
																				  msgBL))
						.map(InvokeSAPAction::extractSFTPSourceParameters)
						.orElseThrow(() -> new AdempiereException("No SFTP contentSource found for SAP config")
								.appendParametersToMessage()
								.setParameter("configId", sapConfig.getId()));
			case START_PRODUCT_SYNC_LOCAL_FILE:
			case START_BPARTNER_SYNC_LOCAL_FILE:
			case STOP_PRODUCT_SYNC_LOCAL_FILE:
			case STOP_BPARTNER_SYNC_LOCAL_FILE:
				return Optional.ofNullable(sapConfig.getContentSourceLocalFile())
						.map(sapContentSourceLocalFile -> validateExternalSystemConfig(sapContentSourceLocalFile,
																				  sapExternalRequest,
																				  sapConfig.getParentId(),
																				  msgBL))
						.map(InvokeSAPAction::extractLocalFileSourceParameters)
						.orElseThrow(() -> new AdempiereException("No LocalFile contentSource found for SAP config")
								.appendParametersToMessage()
								.setParameter("configId", sapConfig.getId()));
			default:
				throw new AdempiereException("SAP External Request not supported: " + sapExternalRequest);
		}
	}

	@NonNull
	private static Map<String, String> extractSFTPSourceParameters(final @NonNull ISAPContentSource sapContentSource)
	{
		final SAPContentSourceSFTP contentSourceSFTP = SAPContentSourceSFTP.cast(sapContentSource);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_SFTP_HOST_NAME, contentSourceSFTP.getHostName());
		parameters.put(PARAM_SFTP_PORT, contentSourceSFTP.getPort());
		parameters.put(PARAM_SFTP_USERNAME, contentSourceSFTP.getUsername());
		parameters.put(PARAM_SFTP_PASSWORD, contentSourceSFTP.getPassword());
		parameters.put(PARAM_SFTP_PRODUCT_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryProduct());
		parameters.put(PARAM_SFTP_BPARTNER_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryBPartner());
		parameters.put(PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryCreditLimit());
		parameters.put(PARAM_SFTP_PROCESSED_DIRECTORY, contentSourceSFTP.getProcessedDirectory());
		parameters.put(PARAM_SFTP_ERRORED_DIRECTORY, contentSourceSFTP.getErroredDirectory());
		parameters.put(PARAM_SFTP_POLLING_FREQUENCY_MS, String.valueOf(contentSourceSFTP.getPollingFrequency().toMillis()));
		parameters.put(PARAM_SFTP_PRODUCT_FILE_NAME_PATTERN, contentSourceSFTP.getFileNamePatternProduct());
		parameters.put(PARAM_SFTP_BPARTNER_FILE_NAME_PATTERN, contentSourceSFTP.getFileNamePatternBPartner());
		parameters.put(PARAM_SFTP_CREDIT_LIMIT_FILENAME_PATTERN, contentSourceSFTP.getFileNamePatternCreditLimit());

		return parameters;
	}

	@NonNull
	private static Map<String, String> extractLocalFileSourceParameters(final @NonNull ISAPContentSource sapContentSource)
	{
		final SAPContentSourceLocalFile contentSourceLocalFile = SAPContentSourceLocalFile.cast(sapContentSource);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_LOCAL_FILE_PRODUCT_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryProduct());
		parameters.put(PARAM_LOCAL_FILE_BPARTNER_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryBPartner());
		parameters.put(PARAM_LOCAL_FILE_CREDIT_LIMIT_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryCreditLimit());
		parameters.put(PARAM_LOCAL_FILE_PROCESSED_DIRECTORY, contentSourceLocalFile.getProcessedDirectory());
		parameters.put(PARAM_LOCAL_FILE_ERRORED_DIRECTORY, contentSourceLocalFile.getErroredDirectory());
		parameters.put(PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS, String.valueOf(contentSourceLocalFile.getPollingFrequency().toMillis()));
		parameters.put(PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternProduct());
		parameters.put(PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternBPartner());
		parameters.put(PARAM_LOCAL_FILE_CREDIT_LIMIT_FILENAME_PATTERN, contentSourceLocalFile.getFileNamePatternCreditLimit());
		parameters.put(PARAM_LOCAL_FILE_ROOT_LOCATION, contentSourceLocalFile.getRootLocation());

		return parameters;
	}

	public ISAPContentSource validateExternalSystemConfig(
			@NonNull final ISAPContentSource contentSource,
			@NonNull final SAPExternalRequest externalRequest,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final IMsgBL msgBL)
	{
		final BooleanWithReason isStartServicePossible = contentSource.validateTargetDirectoriesAndFileNamePatterns(externalRequest, parentId, msgBL);

		if (isStartServicePossible.isFalse())
		{
			throw new AdempiereException(isStartServicePossible.getReason()).markAsUserValidationError();
		}
		return contentSource;
	}
}
