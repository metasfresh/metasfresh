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
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import de.metas.rest_api.v2.project.ProjectRestController;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public abstract class ProjectAuditService implements IMasterDataExportAuditService
{
	//dev-note: meant to capture any rest calls made against `PROJECT_RESOURCE`
	public final static String PROJECT_RESOURCE = ProjectRestController.PROJECT_REST_CONTROLLER_PATH_V2 + "/**";

	private final DataExportAuditService dataExportAuditService;

	protected ProjectAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		this.dataExportAuditService = dataExportAuditService;
	}

	@Override
	public void performDataAuditForRequest(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		if (!isHandled(genericDataExportAuditRequest))
		{
			return;
		}

		final Object exportedObject = genericDataExportAuditRequest.getExportedObject();

		final ExternalSystemParentConfigId externalSystemParentConfigId = genericDataExportAuditRequest.getExternalSystemParentConfigId();
		final PInstanceId pInstanceId = genericDataExportAuditRequest.getPInstanceId();

		processExportedObject(exportedObject, externalSystemParentConfigId, pInstanceId);
	}

	@Override
	public abstract boolean isHandled(final GenericDataExportAuditRequest genericDataExportAuditRequest);

	public abstract void processExportedObject(Object exportedObject, final ExternalSystemParentConfigId externalSystemParentConfigId, final PInstanceId pInstanceId);

	public void auditProject(
			@NonNull final ProjectId projectId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest budgetProjectDataExportAuditRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project.Table_Name, projectId))
				.action(Action.Standalone)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.build();

		dataExportAuditService.createExportAudit(budgetProjectDataExportAuditRequest);
	}
}
