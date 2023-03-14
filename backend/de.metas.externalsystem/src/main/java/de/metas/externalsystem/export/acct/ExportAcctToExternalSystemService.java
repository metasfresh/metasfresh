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

package de.metas.externalsystem.export.acct;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ExportAcctToExternalSystemService extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportAcctToExternalSystemService.class);

	@NonNull
	private final ExternalSystemConfigService externalSystemConfigService;

	@NonNull
	private final Debouncer<TableRecordReference> syncFactAcctDebouncer;

	public ExportAcctToExternalSystemService(
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalSystemConfigService = externalSystemConfigService;

		this.syncFactAcctDebouncer = Debouncer.<TableRecordReference>builder()
				.name("syncFactAcctDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.externalsystem.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.externalsystem.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::syncCollectedDocumentsIfRequired)
				.build();
	}

	public void enqueueDocument(@NonNull final TableRecordReference recordReference)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("TableRecordReference: {} enqueued to be synced.",
														 recordReference.getTableName() + "_" + recordReference.getRecord_ID());

		syncFactAcctDebouncer.add(recordReference);
	}

	@VisibleForTesting
	@Override
	public int getCurrentPendingItems()
	{
		return this.syncFactAcctDebouncer.getCurrentBufferSize();
	}

	@Override
	protected Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference recordReference,
			@Nullable final PInstanceId pInstanceId)
	{

		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		if (!isSyncAcctRecordsEnabled(recordReference, config))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {}; isSyncAcctRecordsEnabled = false! No action is performed!", config.getChildConfig().getId());
			return Optional.empty();
		}

		final JsonMetasfreshId pinstanceId = CoalesceUtil.coalesceSuppliers(() -> JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)),
																			() -> createPInstanceId(recordReference, config));

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(getOrgCode(recordReference))
								   .adPInstanceId(pinstanceId)
								   .command(getExternalCommand())
								   .parameters(buildParameters(recordReference, config))
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	private void syncCollectedDocumentsIfRequired(@NonNull final Collection<TableRecordReference> tableRecordReferenceList)
	{
		if (tableRecordReferenceList.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("tableRecordReferenceList to sync is empty! No further action!");
			return;
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
			return; // nothing to do
		}

		for (final TableRecordReference recordReference : tableRecordReferenceList)
		{
			exportToExternalSystemIfRequired(recordReference, () -> getAdditionalExternalSystemConfigIds(recordReference));
		}
	}

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport)
	{
	}

	@NonNull
	protected abstract Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull TableRecordReference recordReference);

	@NonNull
	protected abstract Map<String, String> buildParameters(@NonNull TableRecordReference recordReference, @NonNull ExternalSystemParentConfig config);

	@NonNull
	protected abstract String getExternalCommand();

	protected abstract boolean isSyncAcctRecordsEnabled(@NonNull TableRecordReference recordReference, @NonNull ExternalSystemParentConfig config);

	@NonNull
	protected abstract String getOrgCode(@NonNull TableRecordReference recordReference);

	@Nullable
	protected abstract JsonMetasfreshId createPInstanceId(@NonNull TableRecordReference recordReference, @NonNull ExternalSystemParentConfig config);
}
