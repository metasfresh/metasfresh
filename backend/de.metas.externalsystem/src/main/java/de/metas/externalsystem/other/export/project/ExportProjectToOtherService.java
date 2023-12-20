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

package de.metas.externalsystem.other.export.project;

import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.export.project.ExportProjectToExternalSystem;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ExportProjectToOtherService extends ExportProjectToExternalSystem
{
	protected ExportProjectToOtherService(
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull ExternalSystemConfigService externalSystemConfigService,
			final @NonNull ProjectRepository projectRepository)
	{
		super(externalSystemConfigRepo, externalSystemMessageSender, dataExportAuditLogRepository, dataExportAuditRepository, externalSystemConfigService, projectRepository);
	}

	@Override
	protected Map<String, String> buildParameters(
			final @NonNull IExternalSystemChildConfig childConfig,
			final @NonNull ProjectId projectId)
	{
		final ExternalSystemOtherConfig otherConfig = ExternalSystemOtherConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();
		otherConfig.getParameters().forEach(parameter -> parameters.put(parameter.getName(), parameter.getValue()));

		parameters.put(ExternalSystemConstants.PARAM_PROJECT_ID, String.valueOf(projectId.getRepoId()));

		return parameters;
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Other;
	}
}
