/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.project.audit;

import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.IMasterDataExportAuditService;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class ProjectAuditBase implements IMasterDataExportAuditService
{
	@NonNull
	protected final DataExportAuditService dataExportAuditService;

	protected ProjectAuditBase(@NonNull final DataExportAuditService dataExportAuditService)
	{
		this.dataExportAuditService = dataExportAuditService;
	}

	@Override
	public boolean isHandled(@NonNull final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return antPathMatcher.match(getResourcePath(), genericDataExportAuditRequest.getRequestURI());
	}

	@Override
	public void performDataAuditForRequest(@NonNull final GenericDataExportAuditRequest request)
	{
		if (!isHandled(request))
		{
			return;
		}

		performAudit(request, projectId -> auditProject(projectId, request.getExternalSystemParentConfigId(), request.getPInstanceId()));
	}

	@NonNull
	private DataExportAuditId auditProject(
			@NonNull final ProjectId projectId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest projectDataExportAuditRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project.Table_Name, projectId))
				.action(Action.Standalone)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.build();

		return dataExportAuditService.createExportAudit(projectDataExportAuditRequest);
	}

	protected abstract void performAudit(
			@NonNull final GenericDataExportAuditRequest genericDataExportAuditRequest,
			@NonNull final Function<ProjectId, DataExportAuditId> auditProject);

	protected abstract String getResourcePath();
}
