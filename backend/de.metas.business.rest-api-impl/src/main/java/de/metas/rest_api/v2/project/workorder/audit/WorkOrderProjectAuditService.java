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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTestId;
import de.metas.rest_api.v2.project.audit.ProjectAuditService;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class WorkOrderProjectAuditService extends ProjectAuditService
{
	//dev-note: meant to capture any rest calls made against `WORKORDER_PROJECT_RESOURCE`
	private final static String WORKORDER_PROJECT_RESOURCE = PROJECT_RESOURCE + "/workorder/**";

	private final DataExportAuditService dataExportAuditService;

	protected WorkOrderProjectAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		super(dataExportAuditService);
		this.dataExportAuditService = dataExportAuditService;
	}

	@Override
	public boolean isHandled(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return antPathMatcher.match(WORKORDER_PROJECT_RESOURCE, genericDataExportAuditRequest.getRequestURI());
	}

	public void processExportedObject(
			@NonNull final Object exportedObject,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Optional<JsonWorkOrderProjectResponse> jsonWorkOrderProject = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonWorkOrderProjectResponse.class);

		if (jsonWorkOrderProject.isPresent())
		{
			final ProjectId projectId = ProjectId.ofRepoId(jsonWorkOrderProject.get().getProjectId().getValue());
			final DataExportAuditId woProjectDataExportAuditId = auditProject(projectId, externalSystemParentConfigId, pInstanceId);

			Optional.ofNullable(jsonWorkOrderProject.get().getSteps())
					.ifPresent(stepList ->
									   stepList.forEach(step -> auditWOStep(step, woProjectDataExportAuditId, externalSystemParentConfigId, pInstanceId)));

			Optional.ofNullable(jsonWorkOrderProject.get().getObjectsUnderTest())
					.ifPresent(objectUnderTestList ->
									   objectUnderTestList.forEach(objectUnderTest -> auditObjectUnderTest(objectUnderTest, projectId, woProjectDataExportAuditId, externalSystemParentConfigId, pInstanceId)));
		}
	}

	public void auditWOStep(
			@NonNull final JsonWorkOrderStepResponse jsonWorkOrderStepResponse,
			@Nullable final DataExportAuditId dataExportParentId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Action exportAction = dataExportParentId != null ? Action.AlongWithParent : Action.Standalone;

		final WOProjectStepId woStepId = WOProjectStepId.ofRepoId(jsonWorkOrderStepResponse.getProjectId().getValue(),
																  jsonWorkOrderStepResponse.getStepId().getValue());

		final DataExportAuditRequest woStepRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_Step.Table_Name, woStepId.getRepoId()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(dataExportParentId)
				.build();

		final DataExportAuditId woStepAuditId = dataExportAuditService.createExportAudit(woStepRequest);

		Optional.ofNullable(jsonWorkOrderStepResponse.getResources())
				.ifPresent(resourcesList ->
								   resourcesList.forEach(resource -> auditResource(resource, jsonWorkOrderStepResponse.getProjectId(), woStepAuditId, externalSystemParentConfigId, pInstanceId)));
	}

	public void auditObjectUnderTest(
			@NonNull final JsonWorkOrderObjectsUnderTestResponse jsonWorkOrderObjectsUnderTestResponse,
			@NonNull final ProjectId projectId,
			@Nullable final DataExportAuditId dataExportParentId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId
	)
	{
		final Action exportAction = dataExportParentId != null ? Action.AlongWithParent : Action.Standalone;

		final WOProjectObjectUnderTestId woProjectObjectUnderTestId = WOProjectObjectUnderTestId.ofRepoId(projectId.getRepoId(),
																										  jsonWorkOrderObjectsUnderTestResponse.getObjectUnderTestId().getValue());

		final DataExportAuditRequest woObjectUnderTestRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_ObjectUnderTest.Table_Name, woProjectObjectUnderTestId.getRepoId()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(dataExportParentId)
				.build();

		dataExportAuditService.createExportAudit(woObjectUnderTestRequest);
	}

	public void auditResource(
			@NonNull final JsonWorkOrderResourceResponse jsonWorkOrderResourceResponse,
			@NonNull final JsonMetasfreshId projectId,
			@Nullable final DataExportAuditId dataExportParentId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Action exportAction = dataExportParentId != null ? Action.AlongWithParent : Action.Standalone;

		final WOProjectResourceId woResourceId = WOProjectResourceId.ofRepoId(projectId.getValue(), jsonWorkOrderResourceResponse.getWoResourceId().getValue());

		final DataExportAuditRequest woResourceRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_WO_Resource.Table_Name, woResourceId.getRepoId()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(dataExportParentId)
				.build();

		dataExportAuditService.createExportAudit(woResourceRequest);
	}
}
