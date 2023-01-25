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

package de.metas.externalsystem.export.project;

import ch.qos.logback.classic.Level;
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
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.project.Project;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.util.Loggables;
import de.metas.util.async.Debouncer;
import de.metas.util.async.DebouncerSysConfig;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ExportProjectToExternalSystem extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportProjectToExternalSystem.class);

	@NonNull
	private final ExternalSystemConfigService externalSystemConfigService;
	@NonNull
	private final ProjectRepository projectRepository;
	@NonNull
	private final Debouncer<ProjectId> syncProjectDebouncer;

	protected ExportProjectToExternalSystem(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService,
			@NonNull final ProjectRepository projectRepository)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalSystemConfigService = externalSystemConfigService;
		this.projectRepository = projectRepository;
		this.syncProjectDebouncer = Debouncer.<ProjectId>builder()
				.name("syncProjectDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getSysConfigName(), DebouncerSysConfig.EXPORT_BUFFER_MAX_SIZE.getDefaultValue()))
				.delayInMillis(sysConfigBL.getIntValue(DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getSysConfigName(), DebouncerSysConfig.EXPORT_DELAY_IN_MILLIS.getDefaultValue()))
				.distinct(true)
				.consumer(this::syncCollectedProjectsIfRequired)
				.build();
	}

	/**
	 * Similar to {@link ExportToExternalSystemService#exportToExternalSystem(IExternalSystemChildConfigId, TableRecordReference, PInstanceId)}, but
	 * <li>uses a debouncer, so it might send the same {@code projectId} just once</li>
	 * <li>uses the export-audit-log to check to which external systems the respective project was exported in the past; then re-exports it to those systems</li>
	 */
	public void enqueueProjectSync(@NonNull final ProjectId projectId)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("ProjectId: {} enqueued to be synced.", projectId);

		syncProjectDebouncer.add(projectId);
	}

	@Override
	@NonNull
	protected Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference recordReference,
			@Nullable final PInstanceId pInstanceId)
	{
		final ProjectId projectId = recordReference.getIdAssumingTableName(I_C_Project.Table_Name, ProjectId::ofRepoId);

		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		if (!isSyncEnabled(config.getChildConfig()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {} ExportProject to external system is false! No action is performed!", config.getChildConfig().getId());
			return Optional.empty();
		}

		final Project project = projectRepository.getOptionalById(projectId)
				.orElseThrow(() -> new AdempiereException("C_Project record cannot be missing for C_Project_ID = " + projectId.getRepoId()));

		final String orgCode = orgDAO.getById(project.getProjectData().getOrgId()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.ofOrNull(PInstanceId.toRepoId(pInstanceId)))
								   .command(getExternalCommand())
								   .parameters(buildParameters(config.getChildConfig(), projectId))
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport) {}

	@Override
	public int getCurrentPendingItems()
	{
		return syncProjectDebouncer.getCurrentBufferSize();
	}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final ProjectId projectId);

	protected abstract String getExternalCommand();

	@NonNull
	protected abstract Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final ProjectId projectId);

	protected abstract boolean isSyncEnabled(@NonNull final IExternalSystemChildConfig childConfig);

	private void syncCollectedProjectsIfRequired(@NonNull final Collection<ProjectId> projectIdList)
	{
		if (projectIdList.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Project list to sync empty! No action is performed!");
			return;
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
			return;
		}

		for (final ProjectId projectId : projectIdList)
		{
			final TableRecordReference projectToExportRecordRef = TableRecordReference.of(I_C_Project.Table_Name, projectId);

			exportToExternalSystemIfRequired(projectToExportRecordRef, () -> getAdditionalExternalSystemConfigIds(projectId));
		}
	}
}
