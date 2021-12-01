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

package de.metas.externalsystem.export.bpartner;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
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
import java.util.Map;
import java.util.Optional;

@Service
public abstract class ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportToExternalSystemService.class);

	protected final ExternalSystemConfigRepo externalSystemConfigRepo;
	protected final DataExportAuditRepository dataExportAuditRepository;
	protected final DataExportAuditLogRepository dataExportAuditLogRepository;
	protected final ExternalSystemMessageSender externalSystemMessageSender;
	protected final Debouncer<BPartnerId> syncBPartnerDebouncer;
	protected final ExternalSystemConfigService externalSystemConfigService;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	protected ExportToExternalSystemService(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalSystemMessageSender = externalSystemMessageSender;
		this.dataExportAuditLogRepository = dataExportAuditLogRepository;
		this.dataExportAuditRepository = dataExportAuditRepository;
		this.externalSystemConfigService = externalSystemConfigService;
		this.syncBPartnerDebouncer = Debouncer.<BPartnerId>builder()
				.name("syncBPartnerDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.externalsystem.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.externalsystem.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::syncBPartners)
				.build();
	}

	/**
	 * Sends a {@link JsonExternalSystemRequest} that requests metasfresh-externalsystems to export the given {@code bpartnerId}.
	 */
	public void exportBPartner(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId)
	{
		toJsonExternalSystemRequest(externalSystemChildConfigId, bpartnerId, pInstanceId)
				.ifPresent(externalSystemMessageSender::send);
	}

	/**
	 * Similar to {@link #exportBPartner(IExternalSystemChildConfigId, BPartnerId, PInstanceId)}, but
	 * <li>uses a debouncer, so it might send the same {@code bPartnerId} just once</li>
	 * <li>uses the export-audit-log to check to which external systems the respective bpartner was exported in the past; then re-exports it to those systems</li>
	 */
	public void enqueueBPartnerSync(@NonNull final BPartnerId bPartnerId)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("BPartnerId: {} enqueued to be synced.", bPartnerId);

		syncBPartnerDebouncer.add(bPartnerId);
	}

	@VisibleForTesting
	@NonNull
	public Optional<JsonExternalSystemRequest> toJsonExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final PInstanceId pInstanceId)
	{
		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.getIsActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		if (!isSyncBPartnerEnabled(config.getChildConfig()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {} isSyncBPartners to external system is false! No action is performed!", config.getChildConfig().getId());
			return Optional.empty();
		}

		final I_C_BPartner bpartner = bPartnerDAO.getById(bpartnerId);

		final String orgCode = orgDAO.getById(bpartner.getAD_Org_ID()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)))
								   .command(getExternalCommand())
								   .parameters(buildParameters(config.getChildConfig(), bpartnerId))
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	private void syncBPartners(@NonNull final Collection<BPartnerId> bPartnerIdList)
	{
		if (bPartnerIdList.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("BPartnerId list to sync empty! No action is performed!");
			return;
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
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
			Loggables.withLogger(logger, Level.DEBUG).addLog("No dataExportAudit found for bPartnerRecordReference: {}! No action is performed!", bPartnerRecordReference);
			return;
		}

		final ImmutableSet<IExternalSystemChildConfigId> externalSystemConfigIds = getExternalSystemConfigsToSyncWith(dataExportAudit.get().getId());

		if (externalSystemConfigIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No externalSystemConfigIds found for DataExportAuditId: {}! No action is performed!", dataExportAudit.get().getId());
			return;
		}

		externalSystemConfigIds.forEach(id -> exportBPartner(id, bPartnerId, null));
	}

	@NonNull
	private ImmutableSet<IExternalSystemChildConfigId> getExternalSystemConfigsToSyncWith(@NonNull final DataExportAuditId dataExportAuditId)
	{
		return dataExportAuditLogRepository.getExternalSystemConfigIds(dataExportAuditId)
				.stream()
				.map(id -> externalSystemConfigRepo.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(id), getExternalSystemType()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(IExternalSystemChildConfig::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final BPartnerId bPartnerId);

	protected abstract boolean isSyncBPartnerEnabled(@NonNull final IExternalSystemChildConfig childConfig);

	protected abstract ExternalSystemType getExternalSystemType();

	protected abstract String getExternalCommand();
}
