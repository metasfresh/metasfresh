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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.Set;

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
	public List<WOProjectResource> updateAll(@NonNull final List<WOProjectResource> projectResourceList)
	{
		final Set<Integer> resourceIds = projectResourceList.stream()
				.map(WOProjectResource::getWoProjectResourceId)
				.map(WOProjectResourceId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Project_WO_Resource> resourceRecords = InterfaceWrapperHelper.loadByIds(resourceIds, I_C_Project_WO_Resource.class);

		final Map<Integer, I_C_Project_WO_Resource> projectResourceId2Record = Maps.uniqueIndex(resourceRecords, I_C_Project_WO_Resource::getC_Project_WO_Resource_ID);

		final ImmutableList.Builder<WOProjectResource> savedProjectResource = ImmutableList.builder();

		for (final WOProjectResource projectResource : projectResourceList)
		{
			final I_C_Project_WO_Resource resourceRecord = projectResourceId2Record.get(projectResource.getWoProjectResourceId().getRepoId());

			if (resourceRecord == null)
			{
				throw new AdempiereException("Missing C_Project_WO_Resource for repoId:" + projectResource.getWoProjectResourceId());
			}

			if (projectResource.getExternalId() != null)
			{
				resourceRecord.setExternalId(projectResource.getExternalId().getValue());
			}

			if (projectResource.getIsActive() != null)
			{
				resourceRecord.setIsActive(projectResource.getIsActive());
			}
			if (projectResource.getIsAllDay() != null)
			{
				resourceRecord.setIsAllDay(projectResource.getIsAllDay());
			}

			if (projectResource.getDurationUnit() != null)
			{
				resourceRecord.setDurationUnit(projectResource.getDurationUnit().getCode());
			}

			resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(projectResource.getWoProjectStepId()));
			resourceRecord.setS_Resource_ID(ResourceId.toRepoId(projectResource.getResourceId()));
			resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(projectResource.getAssignDateFrom()));
			resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(projectResource.getAssignDateTo()));
			resourceRecord.setDuration(projectResource.getDuration());
			resourceRecord.setBudget_Project_ID(ProjectId.toRepoId(projectResource.getBudgetProjectId()));
			resourceRecord.setC_Project_Resource_Budget_ID(BudgetProjectResourceId.toRepoId(projectResource.getProjectResourceBudgetId()));
			resourceRecord.setC_Project_ID(ProjectId.toRepoId(projectResource.getWoProjectStepId().getProjectId()));
			resourceRecord.setExternalId(ExternalId.toValue(projectResource.getExternalId()));
			resourceRecord.setWOTestFacilityGroupName(projectResource.getTestFacilityGroupName());

			saveRecord(resourceRecord);

			savedProjectResource.add(ofRecord(resourceRecord));
		}

		return savedProjectResource.build();
	}

	@NonNull
	public List<WOProjectResource> createAll(@NonNull final List<CreateWOProjectResourceRequest> createWOProjectResourceRequests)
	{
		final ImmutableList.Builder<WOProjectResource> savedProjectResource = ImmutableList.builder();

		for (final CreateWOProjectResourceRequest createWOProjectResourceRequest : createWOProjectResourceRequests)
		{
			final I_C_Project_WO_Resource resourceRecord = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);

			if (createWOProjectResourceRequest.getIsActive() != null)
			{
				resourceRecord.setIsActive(createWOProjectResourceRequest.getIsActive());
			}
			if (createWOProjectResourceRequest.getIsAllDay() != null)
			{
				resourceRecord.setIsAllDay(createWOProjectResourceRequest.getIsAllDay());
			}

			if (createWOProjectResourceRequest.getDurationUnit() != null)
			{
				resourceRecord.setDurationUnit(createWOProjectResourceRequest.getDurationUnit().getCode());
			}

			if (createWOProjectResourceRequest.getExternalId() != null)
			{
				resourceRecord.setExternalId(createWOProjectResourceRequest.getExternalId().getValue());
			}

			resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId()));
			resourceRecord.setS_Resource_ID(ResourceId.toRepoId(createWOProjectResourceRequest.getResourceId()));
			resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateFrom()));
			resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateTo()));
			resourceRecord.setDuration(createWOProjectResourceRequest.getDuration());
			resourceRecord.setBudget_Project_ID(ProjectId.toRepoId(createWOProjectResourceRequest.getBudgetProjectId()));
			resourceRecord.setC_Project_Resource_Budget_ID(BudgetProjectResourceId.toRepoId(createWOProjectResourceRequest.getProjectResourceBudgetId()));
			resourceRecord.setC_Project_ID(ProjectId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId().getProjectId()));
			resourceRecord.setExternalId(ExternalId.toValue(createWOProjectResourceRequest.getExternalId()));
			resourceRecord.setWOTestFacilityGroupName(createWOProjectResourceRequest.getTestFacilityGroupName());

			saveRecord(resourceRecord);

			savedProjectResource.add(ofRecord(resourceRecord));
		}

		return savedProjectResource.build();
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
