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
import de.metas.process.IProcessPreconditionsContext;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_HOST_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PORT;
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

		parameters.put(PARAM_SFTP_HOST_NAME, sapConfig.getSftpHostName());
		parameters.put(PARAM_SFTP_PORT, sapConfig.getSftpPort());
		parameters.put(PARAM_SFTP_USERNAME, sapConfig.getSftpUsername());
		parameters.put(PARAM_SFTP_PASSWORD, sapConfig.getSftpPassword());
		parameters.put(PARAM_CHILD_CONFIG_VALUE, sapConfig.getValue());
		parameters.put(PARAM_SFTP_PRODUCT_TARGET_DIRECTORY, sapConfig.getSftpProductTargetDirectory());
		parameters.put(PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY, sapConfig.getSftpCreditLimitTargetDirectory());
		parameters.put(PARAM_PROCESSED_DIRECTORY, sapConfig.getProcessedDirectory());
		parameters.put(PARAM_ERRORED_DIRECTORY, sapConfig.getErroredDirectory());
		parameters.put(PARAM_POLLING_FREQUENCY_MS, String.valueOf(sapConfig.getPollingFrequency().toMillis()));

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
}
