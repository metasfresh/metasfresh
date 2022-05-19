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

package de.metas.externalsystem.rabbitmqhttp;

import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.util.Check;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.bpartner.ExportBPartnerToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service to export BPartners (on future maybe other sorts of data) to external systems via {@link ExternalSystemType#RabbitMQ}.
 */
@Service
public class ExportBPartnerToRabbitMQService extends ExportBPartnerToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER = "exportBPartner";

	private final UserGroupRepository userGroupRepository;

	public ExportBPartnerToRabbitMQService(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService,
			@NonNull final UserGroupRepository userGroupRepository)
	{
		super(externalSystemConfigRepo,
			  externalSystemMessageSender,
			  dataExportAuditLogRepository,
			  dataExportAuditRepository,
			  externalSystemConfigService);
		this.userGroupRepository = userGroupRepository;
	}

	@Override
	@NonNull
	protected Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(final @NonNull BPartnerId bPartnerId)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);

		final Set<UserGroupId> assignedUserGroupIds = userGroupRepository.getAssignedGroupIdsByUserId(UserId.ofRepoId(bPartner.getCreatedBy()));

		if (Check.isEmpty(assignedUserGroupIds))
		{
			return Optional.empty();
		}

		return Optional.of(externalSystemConfigRepo.getActiveByType(getExternalSystemType())
								   .stream()
								   .filter(ExternalSystemParentConfig::isActive)
								   .map(ExternalSystemParentConfig::getChildConfig)
								   .map(ExternalSystemRabbitMQConfig::cast)
								   .filter(ExternalSystemRabbitMQConfig::isSyncBPartnerToRabbitMQ)
								   .filter(config -> config.shouldExportBasedOnUserGroup(assignedUserGroupIds))
								   .map(ExternalSystemRabbitMQConfig::getId)
								   .collect(ImmutableSet.toImmutableSet()));
	}

	@Override
	@NonNull
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, final @NonNull BPartnerId bPartnerId)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL, rabbitMQConfig.getRemoteUrl());
		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY, rabbitMQConfig.getRoutingKey());
		parameters.put(ExternalSystemConstants.PARAM_BPARTNER_ID, String.valueOf(BPartnerId.toRepoId(bPartnerId)));
		parameters.put(ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN, rabbitMQConfig.getAuthToken());

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
