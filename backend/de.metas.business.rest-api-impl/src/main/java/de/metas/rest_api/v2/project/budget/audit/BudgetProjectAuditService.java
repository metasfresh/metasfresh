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
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.process.PInstanceId;
import de.metas.project.ProjectId;
import de.metas.rest_api.v2.project.audit.ProjectAuditService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class BudgetProjectAuditService extends ProjectAuditService
{
	//dev-note: meant to capture any rest calls made against `BUDGET_PROJECT_RESOURCE`
	private final static String BUDGET_PROJECT_RESOURCE = PROJECT_RESOURCE + "/budget/**";

	protected BudgetProjectAuditService(final @NonNull DataExportAuditService dataExportAuditService)
	{
		super(dataExportAuditService);
	}

	@Override
	public boolean isHandled(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return antPathMatcher.match(BUDGET_PROJECT_RESOURCE, genericDataExportAuditRequest.getRequestURI());
	}

	public void processExportedObject(
			@NonNull final Object exportedObject,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Optional<JsonBudgetProjectResponse> jsonBudgetProject = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonBudgetProjectResponse.class);

		jsonBudgetProject.ifPresent(jsonBudgetProjectResponse ->
									{
										final ProjectId projectId = ProjectId.ofRepoId(jsonBudgetProjectResponse.getProjectId().getValue());
										auditProject(projectId, externalSystemParentConfigId, pInstanceId);
									});
	}
}
