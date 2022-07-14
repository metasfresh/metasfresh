/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.data;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceRequest;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class WOProjectResource
{
	@Nullable
	@Getter(AccessLevel.NONE)
	WOProjectResourceId woProjectResourceId;

	@NonNull
	ProjectId projectId;

	@NonNull
	OrgId orgId;

	@NonNull
	WOProjectStepId woProjectStepId;

	@NonNull
	LocalDate assignDateFrom;

	@NonNull
	LocalDate assignDateTo;

	@Nullable
	Boolean isActive;

	@Nullable
	ResourceId resourceId;

	@Nullable
	Boolean isAllDay;

	@Nullable
	BigDecimal duration;

	@Nullable
	String durationUnit;

	@Nullable
	ProjectId budgetProjectId;

	@Nullable
	BudgetProjectResourceId projectResourceBudgetId;

	@NonNull
	public WOProjectResourceId getWOProjectResourceIdNotNull()
	{
		if (woProjectResourceId == null)
		{
			throw new AdempiereException("WOProjectResourceId cannot be null at this stage!");
		}
		return woProjectResourceId;
	}

	@NonNull
	public static WOProjectResource ofRecord(@NonNull final I_C_Project_WO_Resource resourceRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(resourceRecord.getAD_Org_ID());
		final LocalDate assignDateFrom = TimeUtil.asLocalDate(resourceRecord.getAssignDateFrom(), orgId);
		final LocalDate assignDateTo = TimeUtil.asLocalDate(resourceRecord.getAssignDateTo(), orgId);
		if (assignDateTo == null || assignDateFrom == null)
		{
			throw new AdempiereException("I_C_Project_WO_Resource.assignDateFrom and I_C_Project_WO_Resource.assignDateTo should be set on the record at this point!");
		}

		return WOProjectResource.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(resourceRecord.getC_Project_WO_Step_ID()))
				.woProjectResourceId(WOProjectResourceId.ofRepoId(resourceRecord.getC_Project_WO_Resource_ID()))
				.orgId(orgId)
				.projectId(ProjectId.ofRepoId(resourceRecord.getC_Project_ID()))
				.isAllDay(resourceRecord.isAllDay())
				.isActive(resourceRecord.isActive())
				.resourceId(ResourceId.ofRepoIdOrNull(resourceRecord.getS_Resource_ID()))
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(resourceRecord.getDuration())
				.durationUnit(resourceRecord.getDurationUnit())
				.budgetProjectId(ProjectId.ofRepoIdOrNull(resourceRecord.getBudget_Project_ID()))
				.projectResourceBudgetId(BudgetProjectResourceId.ofRepoIdOrNull(resourceRecord.getC_Project_Resource_Budget_ID()))
				.build();

	}

	@NonNull
	public static WOProjectResource fromJson(
			@NonNull final JsonWorkOrderResourceRequest jsonWorkOrderResourceRequest,
			@NonNull final OrgId orgId,
			@NonNull final ProjectId projectId,
			@NonNull final BigDecimal duration,
			@NonNull final String durationUnit,
			@Nullable final ProjectId budgetProjectId,
			@Nullable final BudgetProjectResourceId budgetProjectResourceId)
	{
		return WOProjectResource.builder()
				.woProjectResourceId(WOProjectResourceId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(jsonWorkOrderResourceRequest.getWoResourceId())))
				.projectId(projectId)
				.resourceId(ResourceId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(jsonWorkOrderResourceRequest.getResourceId())))
				.woProjectStepId(WOProjectStepId.ofRepoId(jsonWorkOrderResourceRequest.getStepId().getValue()))
				.isActive(jsonWorkOrderResourceRequest.getIsActive())
				.isAllDay(jsonWorkOrderResourceRequest.getIsAllDay())
				.assignDateFrom(jsonWorkOrderResourceRequest.getAssignDateFrom())
				.assignDateTo(jsonWorkOrderResourceRequest.getAssignDateTo())
				.orgId(orgId)
				.duration(duration)
				.durationUnit(durationUnit)
				.budgetProjectId(budgetProjectId)
				.projectResourceBudgetId(budgetProjectResourceId)
				.build();
	}
}