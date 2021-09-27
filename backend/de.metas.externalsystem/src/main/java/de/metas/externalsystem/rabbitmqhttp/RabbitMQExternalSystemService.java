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

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service to export BPartners (on future maybe other sorts of data) to external systems via {@link ExternalSystemType#RabbitMQ}.
 */
@Service
public class RabbitMQExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(RabbitMQExternalSystemService.class);

	private final static String EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER = "exportBPartner";

	private final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final ExternalSystemMessageSender externalSystemMessageSender;
	private final DataExportAuditLogRepository dataExportAuditLogRepository;
	private final DataExportAuditRepository dataExportAuditRepository;
	private final Debouncer<BPartnerId> syncBPartnerDebouncer;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public RabbitMQExternalSystemService(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final DataExportAuditRepository dataExportAuditRepository)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalSystemMessageSender = externalSystemMessageSender;
		this.dataExportAuditLogRepository = dataExportAuditLogRepository;
		this.dataExportAuditRepository = dataExportAuditRepository;

		this.syncBPartnerDebouncer = Debouncer.<BPartnerId>builder()
				.name("syncBPartnerDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.externalsystem.rabbitmqhttp.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.externalsystem.rabbitmqhttp.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::syncBPartners)
				.build();
	}

	/**
	 * Sends a {@link JsonExternalSystemRequest} that requests metasfresh-externalsystems to export the given {@code bpartnerId}.
	 */
	public void exportBPartner(
			@NonNull final ExternalSystemRabbitMQConfigId externalSystemConfigRabbitMQId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId)
	{
		toJsonExternalSystemRequest(externalSystemConfigRabbitMQId, bpartnerId, pInstanceId)
				.ifPresent(externalSystemMessageSender::send);
	}

	/**
	 * Similar to {@link #exportBPartner(ExternalSystemRabbitMQConfigId, BPartnerId, PInstanceId)}, but
	 * <li>uses a debouncer, so it might send the same {@code bPartnerId} just once</li>
	 * <li>uses the export-audit-log to check to which external systems the respective bpartner was exported in the past; then re-exports it to those systems</li>
	 */
	public void enqueueBPartnerSync(@NonNull final BPartnerId bPartnerId)
	{
		syncBPartnerDebouncer.add(bPartnerId);
	}

	@VisibleForTesting
	@NonNull
	protected Optional<JsonExternalSystemRequest> toJsonExternalSystemRequest(
			@NonNull final ExternalSystemRabbitMQConfigId externalSystemConfigRabbitMQId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId)
	{
		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemConfigRabbitMQId);

		if (!config.getIsActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(config.getChildConfig());

		if (!rabbitMQConfig.isSyncBPartnerToRabbitMQ())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("RabbitMQConfig: {} isSyncBPartnerToRabbitMQ = false! No action is performed!", externalSystemConfigRabbitMQId);
			return Optional.empty();
		}

		final I_C_BPartner bpartner = bPartnerDAO.getById(bpartnerId);
		final String orgCode = orgDAO.getById(bpartner.getAD_Org_ID()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(ExternalSystemType.RabbitMQ.getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)))
								   .command(EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER)
								   .parameters(buildParameters(rabbitMQConfig, bpartnerId))
								   .build());
	}

	@NonNull
	private ImmutableSet<ExternalSystemRabbitMQConfigId> getRabbitMQConfigsToSyncWith(@NonNull final DataExportAuditId dataExportAuditId)
	{
		final ImmutableSet<Integer> externalSystemConfigIds = dataExportAuditLogRepository.getExternalSystemConfigIds(dataExportAuditId);

		return externalSystemConfigIds.stream()
				.map(id -> externalSystemConfigRepo.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(id), ExternalSystemType.RabbitMQ))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(IExternalSystemChildConfig::getId)
				.map(ExternalSystemRabbitMQConfigId::cast)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Map<String, String> buildParameters(
			@NonNull final ExternalSystemRabbitMQConfig rabbitMQConfig,
			@NonNull final BPartnerId bpartnerId)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL, rabbitMQConfig.getRemoteUrl());
		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY, rabbitMQConfig.getRoutingKey());
		parameters.put(ExternalSystemConstants.PARAM_BPARTNER_ID, String.valueOf(bpartnerId.getRepoId()));
		parameters.put(ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN, rabbitMQConfig.getAuthToken());

		return parameters;
	}

	private void syncBPartners(@NonNull final Collection<BPartnerId> bPartnerIdList)
	{
		if (bPartnerIdList.isEmpty())
		{
			return;
		}
		if (!externalSystemConfigRepo.isAnyConfigActive(ExternalSystemType.RabbitMQ))
		{
			return; // nothing to do
		}

		bPartnerIdList.forEach(this::syncBPartnerIfRequired);
	}

	private void syncBPartnerIfRequired(@NonNull final BPartnerId bPartnerId)
	{
		final TableRecordReference bPartnerRecordReference = TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId);

		final Optional<DataExportAudit> dataExportAudit = dataExportAuditRepository.getByTableRecordReference(bPartnerRecordReference);
		if (!dataExportAudit.isPresent())
		{
			return;
		}

		final ImmutableSet<ExternalSystemRabbitMQConfigId> rabbitMQConfigIds = getRabbitMQConfigsToSyncWith(dataExportAudit.get().getId());

		if (rabbitMQConfigIds.isEmpty())
		{
			return;
		}

		rabbitMQConfigIds.forEach(id -> exportBPartner(id, bPartnerId, null));
	}
}
