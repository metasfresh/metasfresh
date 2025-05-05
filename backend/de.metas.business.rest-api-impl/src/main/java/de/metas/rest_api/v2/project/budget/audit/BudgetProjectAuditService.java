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

package de.metas.rest_api.v2.project.budget.audit;

import de.metas.JsonMapperUtil;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResourceResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.rest_api.v2.project.audit.ProjectAuditBase;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.function.Function;

import static de.metas.rest_api.v2.project.budget.BudgetProjectRestController.BUDGET_PROJECT_REST_CONTROLLER_PATH_V2;

@Service
public class BudgetProjectAuditService extends ProjectAuditBase
{
	//dev-note: meant to capture any rest calls made against `BUDGET_PROJECT_RESOURCE`
	private final static String BUDGET_PROJECT_RESOURCE = BUDGET_PROJECT_REST_CONTROLLER_PATH_V2 + "/**";

	public BudgetProjectAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		super(dataExportAuditService);
	}

	@Override
	protected void performAudit(
			final @NonNull GenericDataExportAuditRequest auditRequest,
			final @NonNull Function<ProjectId, DataExportAuditId> auditProject)
	{
		final JsonBudgetProjectResponse jsonBudgetProject = JsonMapperUtil
				.tryDeserializeToType(auditRequest.getExportedObject(), JsonBudgetProjectResponse.class)
				.orElse(null);

		if (jsonBudgetProject == null)
		{
			return;
		}

		final DataExportAuditId dataExportAuditId = auditProject.apply(jsonBudgetProject.getProjectId().mapValue(ProjectId::ofRepoId));

		jsonBudgetProject.getProjectResources()
				.forEach(jsonProjectResource -> auditBudgetProjectResource(jsonProjectResource,
																		   dataExportAuditId,
																		   auditRequest.getExternalSystemParentConfigId(),
																		   auditRequest.getPInstanceId()));
	}

	@Override
	protected String getResourcePath()
	{
		return BUDGET_PROJECT_RESOURCE;
	}

	private void auditBudgetProjectResource(
			@NonNull final JsonBudgetProjectResourceResponse jsonBudgetResource,
			@NonNull final DataExportAuditId budgetProjectExportAuditId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final BudgetProjectResourceId budgetProjectResourceId = BudgetProjectResourceId.ofRepoId(jsonBudgetResource.getProjectId().getValue(),
																								 jsonBudgetResource.getBudgetProjectResourceId().getValue());

		final DataExportAuditRequest budgetResourceRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Project_Resource_Budget.Table_Name, budgetProjectResourceId))
				.action(Action.AlongWithParent)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(budgetProjectExportAuditId)
				.build();

		dataExportAuditService.createExportAudit(budgetResourceRequest);
	}
}
