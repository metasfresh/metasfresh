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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class WorkOrderProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	public List<WOProjectResource> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectResource> getByStepId(@NonNull final WOProjectStepId woProjectStepId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepId)
				.create()
				.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public WOProjectResource save(@NonNull final WOProjectResource resourceData)
	{
		final WOProjectResourceId existingWoProjectResourceId;
		if (resourceData.getWoProjectResourceId() != null)
		{
			existingWoProjectResourceId = resourceData.getWOProjectResourceIdNotNull();

		}
		else if (resourceData.getExternalId() != null)
		{
			existingWoProjectResourceId = getByProjectId(resourceData.getWoProjectStepId().getProjectId())
					.stream()
					.filter(s -> Objects.equals(s.getExternalId(), resourceData.getExternalId()))
					.findAny()
					.map(WOProjectResource::getWoProjectResourceId)
					.orElse(null);
		}
		else
		{
			existingWoProjectResourceId = null;
		}
		final I_C_Project_WO_Resource resourceRecord = InterfaceWrapperHelper.loadOrNew(existingWoProjectResourceId, I_C_Project_WO_Resource.class);

		if (resourceData.getIsActive() != null)
		{
			resourceRecord.setIsActive(resourceData.getIsActive());
		}
		if (resourceData.getIsAllDay() != null)
		{
			resourceRecord.setIsAllDay(resourceData.getIsAllDay());
		}

		resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId()));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(resourceData.getResourceId()));
		resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(resourceData.getAssignDateFrom()));
		resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(resourceData.getAssignDateTo()));
		resourceRecord.setDuration(resourceData.getDuration());
		resourceRecord.setBudget_Project_ID(ProjectId.toRepoId(resourceData.getBudgetProjectId()));
		resourceRecord.setC_Project_Resource_Budget_ID(BudgetProjectResourceId.toRepoId(resourceData.getProjectResourceBudgetId()));
		resourceRecord.setC_Project_ID(ProjectId.toRepoId(resourceData.getWoProjectStepId().getProjectId()));
		resourceRecord.setExternalId(ExternalId.toValue(resourceData.getExternalId()));
		resourceRecord.setWOTestFacilityGroupName(resourceData.getTestFacilityGroupName());

		if (resourceData.getDurationUnit() != null)
		{
			resourceRecord.setDurationUnit(resourceData.getDurationUnit().getCode());
		}

		saveRecord(resourceRecord);

		return ofRecord(resourceRecord);
	}

	@NonNull
	private WOProjectResource ofRecord(@NonNull final I_C_Project_WO_Resource resourceRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(resourceRecord.getAD_Org_ID());

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(resourceRecord.getAssignDateFrom(), timeZone);
		final Instant assignDateTo = TimeUtil.asInstant(resourceRecord.getAssignDateTo(), timeZone);
		if (assignDateTo == null || assignDateFrom == null)
		{
			throw new AdempiereException("I_C_Project_WO_Resource.assignDateFrom and I_C_Project_WO_Resource.assignDateTo should be set on the record at this point!");
		}

		final ProjectId projectId = ProjectId.ofRepoId(resourceRecord.getC_Project_ID());

		return WOProjectResource.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Step_ID()))
				.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Resource_ID()))
				.isAllDay(resourceRecord.isAllDay())
				.isActive(resourceRecord.isActive())
				.resourceId(ResourceId.ofRepoIdOrNull(resourceRecord.getS_Resource_ID()))
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(resourceRecord.getDuration())
				.durationUnit(DurationUnit.ofNullableCode(resourceRecord.getDurationUnit()))
				.budgetProjectId(ProjectId.ofRepoIdOrNull(resourceRecord.getBudget_Project_ID()))
				.projectResourceBudgetId(BudgetProjectResourceId.ofRepoIdOrNull(resourceRecord.getBudget_Project_ID(), resourceRecord.getC_Project_Resource_Budget_ID()))
				.externalId(ExternalId.ofOrNull(resourceRecord.getExternalId()))
				.testFacilityGroupName(resourceRecord.getWOTestFacilityGroupName())
				.build();
	}
}
