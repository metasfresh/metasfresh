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

package de.metas.externalsystem.export.hu;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfig;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import de.metas.util.async.DebouncerSysConfig;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ExportHUToExternalSystemService extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportHUToExternalSystemService.class);

	private final ExternalSystemConfigService externalSystemConfigService;
	private final Debouncer<ExportHUCandidate> syncHuDebouncer;

	protected final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	protected ExportHUToExternalSystemService(
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalSystemConfigService = externalSystemConfigService;

		this.syncHuDebouncer = Debouncer.<ExportHUCandidate>builder()
				.name("syncHuDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getSysConfigName(), DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getDefaultValue()))
				.delayInMillis(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getSysConfigName(), DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getDefaultValue()))
				.distinct(true)
				.consumer(this::exportCollectedHUsIfRequired)
				.build();
	}

	public void enqueueHUExport(@NonNull final ExportHUCandidate exportHUCandidate)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("exportHUCandidate: {} enqueued to be synced.", exportHUCandidate);

		syncHuDebouncer.add(exportHUCandidate);
	}

	@Override
	@VisibleForTesting
	public Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference huRecordReference,
			@Nullable final PInstanceId pInstanceId)
	{
		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		final ExternalSystemGRSSignumConfig grsSignumConfig = ExternalSystemGRSSignumConfig.cast(config.getChildConfig());

		if (!grsSignumConfig.isHUsSyncEnabled())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {} HU export is disabled! No action is performed!", config.getChildConfig().getId());
			return Optional.empty();
		}

		final HuId huId = huRecordReference.getIdAssumingTableName(I_M_HU.Table_Name, HuId::ofRepoId);

		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final String orgCode = orgDAO.getById(hu.getAD_Org_ID()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)))
								   .command(getExternalCommand())
								   .parameters(buildParameters(config.getChildConfig(), huId))
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	@VisibleForTesting
	@Override
	public int getCurrentPendingItems()
	{
		return this.syncHuDebouncer.getCurrentBufferSize();
	}

	private void exportCollectedHUsIfRequired(@NonNull final Collection<ExportHUCandidate> huCandidates)
	{
		if (huCandidates.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("HuId list to sync empty! No action is performed!");
			return;
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
			return; // nothing to do
		}

		for (final ExportHUCandidate exportHUCandidate : huCandidates)
		{
			final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(exportHUCandidate.getHuId());

			final TableRecordReference topLevelRef = TableRecordReference.of(I_M_HU.Table_Name, topLevelParent.getM_HU_ID());

			exportToExternalSystemIfRequired(topLevelRef, () -> getAdditionalExternalSystemConfigIds(exportHUCandidate));
		}
	}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final HuId huId);

	protected abstract String getExternalCommand();

	protected abstract Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final ExportHUCandidate exportHUCandidate);
}
