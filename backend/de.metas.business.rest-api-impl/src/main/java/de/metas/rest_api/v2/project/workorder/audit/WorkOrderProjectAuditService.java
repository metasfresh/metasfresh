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

package de.metas.rest_api.v2.project.workorder.audit;

import de.metas.JsonMapperUtil;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import de.metas.rest_api.v2.project.audit.ProjectAuditBase;
import de.metas.rest_api.v2.project.workorder.WorkOrderProjectRestController;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

@Service
public class WorkOrderProjectAuditService extends ProjectAuditBase
{
	//dev-note: meant to capture any rest calls made against `WORKORDER_PROJECT_RESOURCE`
	private final static String WORKORDER_PROJECT_RESOURCE = WorkOrderProjectRestController.WORKORDER_PROJECT_REST_CONTROLLER_PATH_V2 + "/**";

	public WorkOrderProjectAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		super(dataExportAuditService);
	}

	@Override
	protected void performAudit(
			final @NonNull GenericDataExportAuditRequest auditRequest,
			final @NonNull Function<ProjectId, DataExportAuditId> auditProject)
	{
		final JsonWorkOrderProjectResponse jsonWorkOrderProject = JsonMapperUtil
				.tryDeserializeToType(auditRequest.getExportedObject(), JsonWorkOrderProjectResponse.class)
				.orElse(null);

		if (jsonWorkOrderProject == null)
		{
			return;
		}

		final DataExportAuditId woProjectExportAuditId = auditProject.apply(jsonWorkOrderProject.getProjectId().mapValue(ProjectId::ofRepoId));

		Optional.ofNullable(jsonWorkOrderProject.getSteps())
				.ifPresent(stepList -> stepList.forEach(step -> auditWOStep(step,
																			woProjectExportAuditId,
																			auditRequest.getExternalSystemParentConfigId(),
																			auditRequest.getPInstanceId())));

		Optional.ofNullable(jsonWorkOrderProject.getObjectsUnderTest())
				.ifPresent(objectUnderTestList -> objectUnderTestList.forEach(objectUnderTest -> auditObjectUnderTest(objectUnderTest,
																													  woProjectExportAuditId,
																													  auditRequest.getExternalSystemParentConfigId(),
																													  auditRequest.getPInstanceId())));
	}

	@Override
	@NonNull
	protected String getResourcePath()
	{
		return WORKORDER_PROJECT_RESOURCE;
	}

	private void auditWOStep(
			@NonNull final JsonWorkOrderStepResponse jsonStep,
			@NonNull final DataExportAuditId woProjectExportAuditId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest woStepRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_Step.Table_Name, jsonStep.getStepId().getValue()))
				.action(Action.AlongWithParent)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(woProjectExportAuditId)
				.build();

		final DataExportAuditId woStepAuditId = dataExportAuditService.createExportAudit(woStepRequest);

		Optional.ofNullable(jsonStep.getResources())
				.ifPresent(resourcesList -> resourcesList.forEach(resource -> auditResource(resource,
																							woStepAuditId,
																							externalSystemParentConfigId,
																							pInstanceId)));
	}

	private void auditObjectUnderTest(
			@NonNull final JsonWorkOrderObjectsUnderTestResponse jsonObjectUnderTest,
			@NonNull final DataExportAuditId woProjectExportAuditId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest woObjectUnderTestRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_ObjectUnderTest.Table_Name, jsonObjectUnderTest.getObjectUnderTestId().getValue()))
				.action(Action.AlongWithParent)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(woProjectExportAuditId)
				.build();

		dataExportAuditService.createExportAudit(woObjectUnderTestRequest);
	}

	private void auditResource(
			@NonNull final JsonWorkOrderResourceResponse jsonWorkOrderResourceResponse,
			@NonNull final DataExportAuditId stepExportAuditId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest woResourceRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_Resource.Table_Name, jsonWorkOrderResourceResponse.getWoResourceId().getValue()))
				.action(Action.AlongWithParent)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(stepExportAuditId)
				.build();

		dataExportAuditService.createExportAudit(woResourceRequest);
	}
}
