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

package de.metas.externalsystem.other.export.project.workorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.other.export.project.ExportProjectToOtherService;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ExportWorkOrderProjectToOtherService extends ExportProjectToOtherService
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_WORK_ORDER = "exportWorkOrderProject";

	protected ExportWorkOrderProjectToOtherService(
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull ExternalSystemConfigService externalSystemConfigService,
			final @NonNull ProjectRepository projectRepository)
	{
		super(externalSystemConfigRepo,
			  externalSystemMessageSender,
			  dataExportAuditLogRepository,
			  dataExportAuditRepository,
			  externalSystemConfigService,
			  projectRepository);
	}

	@Override
	public boolean isSyncEnabled(@NonNull final IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemOtherConfig externalSystemOtherConfig = ExternalSystemOtherConfig.cast(childConfig);

		return externalSystemOtherConfig.isSyncWOProjectsEnabled();
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_WORK_ORDER;
	}

	@NonNull
	@Override
	protected String getExportADProcessClassname()
	{
		return C_WorkOrderProject_SyncTo_Other.class.getName();
	}

	@Override
	protected @NonNull Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(final @NonNull ProjectId projectId)
	{
		final ImmutableList<ExternalSystemParentConfig> otherExternalSysConfigs = externalSystemConfigRepo.getActiveByType(getExternalSystemType());

		return Optional.of(otherExternalSysConfigs.stream()
								   .filter(ExternalSystemParentConfig::isActive)
								   .map(ExternalSystemParentConfig::getChildConfig)
								   .filter(this::isSyncEnabled)
								   .map(IExternalSystemChildConfig::getId)
								   .collect(ImmutableSet.toImmutableSet()));
	}

}
