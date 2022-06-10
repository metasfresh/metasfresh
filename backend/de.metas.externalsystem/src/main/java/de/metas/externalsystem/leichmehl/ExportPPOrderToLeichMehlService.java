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

package de.metas.externalsystem.leichmehl;

import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExportPPOrderToLeichMehlService extends ExportPPOrderToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_PP_ORDER = "exportPPOrder";

	protected ExportPPOrderToLeichMehlService(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender, externalSystemConfigService);
	}

	@Override
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, final @NonNull PPOrderId ppOrderId)
	{
		final ExternalSystemLeichMehlConfig leichMehlConfig = ExternalSystemLeichMehlConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_FTP_HOST, leichMehlConfig.getFtpHost());
		parameters.put(ExternalSystemConstants.PARAM_FTP_PORT, String.valueOf(leichMehlConfig.getFtpPort()));
		parameters.put(ExternalSystemConstants.PARAM_FTP_USERNAME, leichMehlConfig.getFtpUsername());
		parameters.put(ExternalSystemConstants.PARAM_FTP_PASSWORD, leichMehlConfig.getFtpPassword());
		parameters.put(ExternalSystemConstants.PARAM_PP_ORDER_ID, String.valueOf(ppOrderId.getRepoId()));
		parameters.put(ExternalSystemConstants.PARAM_FTP_DIRECTORY, leichMehlConfig.getFtpDirectory());

		return parameters;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_PP_ORDER;
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.LeichUndMehl;
	}
}
