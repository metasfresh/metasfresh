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

package de.metas.externalsystem.rabbitmqhttp;

import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.export.bpartner.ExportBPartnerToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to export BPartners (on future maybe other sorts of data) to external systems via {@link ExternalSystemType#RabbitMQ}.
 */
@Service
public class ExportBPartnerToRabbitMQService extends ExportBPartnerToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER = "exportBPartner";

	public ExportBPartnerToRabbitMQService(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(externalSystemConfigRepo, externalSystemMessageSender, dataExportAuditLogRepository, dataExportAuditRepository, externalSystemConfigService);
	}

	@Override
	@NonNull
	protected Map<String, String> buildParameters(
			@NonNull final IExternalSystemChildConfig childConfig,
			@NonNull final BPartnerId bPartnerId)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL, rabbitMQConfig.getRemoteUrl());
		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY, rabbitMQConfig.getRoutingKey());
		parameters.put(ExternalSystemConstants.PARAM_BPARTNER_ID, String.valueOf(BPartnerId.toRepoId(bPartnerId)));
		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN, rabbitMQConfig.getAuthToken());
		parameters.put(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE, rabbitMQConfig.getValue());

		return parameters;
	}

	@Override
	protected boolean isSyncBPartnerEnabled(final @NonNull IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		return rabbitMQConfig.isSyncBPartnerToRabbitMQ();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.RabbitMQ;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER;
	}
}
