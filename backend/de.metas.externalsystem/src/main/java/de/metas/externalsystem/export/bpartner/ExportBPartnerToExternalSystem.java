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

package de.metas.externalsystem.export.bpartner;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
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
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import de.metas.util.async.DebouncerSysConfig;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ExportBPartnerToExternalSystem extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportBPartnerToExternalSystem.class);

	protected final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final ExternalSystemConfigService externalSystemConfigService;
	private final Debouncer<BPartnerId> syncBPartnerDebouncer;

	protected ExportBPartnerToExternalSystem(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalSystemConfigService = externalSystemConfigService;

		this.syncBPartnerDebouncer = Debouncer.<BPartnerId>builder()
				.name("syncBPartnerDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getSysConfigName(), DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getDefaultValue()))
				.delayInMillis(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getSysConfigName(), DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getDefaultValue()))
				.distinct(true)
				.consumer(this::syncCollectedBPartnersIfRequired)
				.build();
	}

	/**
	 * Similar to {@link ExportToExternalSystemService#exportToExternalSystem(IExternalSystemChildConfigId, TableRecordReference, PInstanceId)}, but
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
	public Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference bPartnerRecordReference,
			@Nullable final PInstanceId pInstanceId)
	{
		final BPartnerId bpartnerId = bPartnerRecordReference.getIdAssumingTableName(I_C_BPartner.Table_Name, BPartnerId::ofRepoId);

		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		if (!isSyncBPartnerEnabled(config.getChildConfig()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {} isSendBPartnerAllowed to external system is false! No action is performed!", config.getChildConfig().getId());
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

	private void syncCollectedBPartnersIfRequired(@NonNull final Collection<BPartnerId> bPartnerIdList)
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

		for (final BPartnerId bPartnerId : bPartnerIdList)
		{
			final TableRecordReference bPartnerToExportRecordRef = TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId);

			exportToExternalSystemIfRequired(bPartnerToExportRecordRef, () -> getAdditionalExternalSystemConfigIds(bPartnerId));
		}
	}

	@Override
	public int getCurrentPendingItems()
	{
		return syncBPartnerDebouncer.getCurrentBufferSize();
	}

	@NonNull
	protected Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final BPartnerId bpartnerId)
	{
		return Optional.empty();
	}

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport) {}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final BPartnerId bPartnerId);

	protected abstract boolean isSyncBPartnerEnabled(@NonNull final IExternalSystemChildConfig childConfig);

	protected abstract String getExternalCommand();
}
