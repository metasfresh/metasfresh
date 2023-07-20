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

package de.metas.externalsystem.export.externalreference;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.model.I_S_ExternalReference;
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
import de.metas.util.async.Debouncer;
import de.metas.util.async.DebouncerSysConfig;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public abstract class ExportExternalReferenceToExternalSystem extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportExternalReferenceToExternalSystem.class);

	protected final ExternalReferenceRepository externalReferenceRepository;
	private final Debouncer<ExternalReferenceId> syncExternalReferenceDebouncer;
	private final ExternalSystemConfigService externalSystemConfigService;

	protected ExportExternalReferenceToExternalSystem(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalReferenceRepository externalReferenceRepository,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)

	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalReferenceRepository = externalReferenceRepository;
		this.externalSystemConfigService = externalSystemConfigService;
		this.syncExternalReferenceDebouncer = Debouncer.<ExternalReferenceId>builder()
				.name("syncExternalReferenceDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getSysConfigName(), DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getDefaultValue()))
				.delayInMillis(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getSysConfigName(), DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getDefaultValue()))
				.distinct(true)
				.consumer(this::syncCollectedExternalReferencesIfRequired)
				.build();
	}

	/**
	 * Similar to {@link ExportToExternalSystemService#exportToExternalSystem(IExternalSystemChildConfigId, TableRecordReference, PInstanceId)}, but
	 * <li>uses a debouncer, so it might send the same {@code externalReferenceId} just once</li>
	 * <li>uses the export-audit-log to check to which external systems the respective externalReference record was exported in the past; then re-exports it to those systems</li>
	 */
	public void enqueueExternalReferenceSync(@NonNull final ExternalReferenceId externalReferenceId)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalReferenceId: {} enqueued to be synced.", externalReferenceId);

		syncExternalReferenceDebouncer.add(externalReferenceId);
	}

	@VisibleForTesting
	@NonNull
	public Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference externalReferenceRecordRef,
			@Nullable final PInstanceId pInstanceId)
	{

		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		if (!isSyncExternalReferenceEnabled(config.getChildConfig()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {}; isSyncExternalReferenceEnabled = false! No action is performed!", config.getChildConfig().getId());
			return Optional.empty();
		}

		final ExternalReferenceId externalReferenceId = externalReferenceRecordRef.getIdAssumingTableName(I_S_ExternalReference.Table_Name, ExternalReferenceId::ofRepoId);

		final ExternalReference externalReference = externalReferenceRepository.getById(externalReferenceId);

		final String orgCode = orgDAO.getById(externalReference.getOrgId()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)))
								   .command(getExternalCommand())
								   .parameters(buildParameters(config.getChildConfig(), externalReferenceId))
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	private void syncCollectedExternalReferencesIfRequired(@NonNull final Collection<ExternalReferenceId> externalReferenceIds)
	{
		if (externalReferenceIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalReferenceId list to sync is empty! No action is performed!");
			return;
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
			return; // nothing to do
		}

		for (final ExternalReferenceId externalReferenceId : externalReferenceIds)
		{
			final TableRecordReference externalReferenceToExportRecordRef = TableRecordReference.of(I_S_ExternalReference.Table_Name, externalReferenceId);

			exportToExternalSystemIfRequired(externalReferenceToExportRecordRef, () -> getAdditionalExternalSystemConfigIds(externalReferenceId));
		}
	}

	@VisibleForTesting
	public int getCurrentPendingItems()
	{
		return this.syncExternalReferenceDebouncer.getCurrentBufferSize();
	}

	@NonNull
	protected abstract Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final ExternalReferenceId externalReferenceId);

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport)
	{
	}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final ExternalReferenceId externalReferenceId);

	protected abstract boolean isSyncExternalReferenceEnabled(@NonNull final IExternalSystemChildConfig childConfig);

	protected abstract String getExternalCommand();
}
