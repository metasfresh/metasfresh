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
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class WorkOrderProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public List<WOProjectResource> getByStepId(@NonNull final WOProjectStepId woProjectStepId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepId)
				.create()
				.stream()
				.map(WorkOrderProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectResource> listByStepIds(@NonNull final Set<WOProjectStepId> woProjectStepIds)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepIds)
				.create()
				.stream()
				.map(WorkOrderProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectResource> updateAll(@NonNull final Collection<WOProjectResource> projectResourceList)
	{
		final Set<Integer> woResourceIds = projectResourceList.stream()
				.map(WOProjectResource::getWoProjectResourceId)
				.map(WOProjectResourceId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Project_WO_Resource> resourceRecords = InterfaceWrapperHelper.loadByIds(woResourceIds, I_C_Project_WO_Resource.class);

		final Map<Integer, I_C_Project_WO_Resource> projectResourceId2Record = Maps.uniqueIndex(resourceRecords, I_C_Project_WO_Resource::getC_Project_WO_Resource_ID);

		final ImmutableList.Builder<WOProjectResource> savedProjectResource = ImmutableList.builder();

		for (final WOProjectResource projectResource : projectResourceList)
		{
			final I_C_Project_WO_Resource resourceRecord = projectResourceId2Record.get(projectResource.getWoProjectResourceId().getRepoId());

			if (resourceRecord == null)
			{
				throw new AdempiereException("Missing C_Project_WO_Resource for repoId:" + projectResource.getWoProjectResourceId());
			}

			resourceRecord.setExternalId(ExternalId.toValue(projectResource.getExternalId()));
			resourceRecord.setIsActive(Boolean.TRUE.equals(projectResource.getIsActive()));
			resourceRecord.setIsAllDay(Boolean.TRUE.equals(projectResource.getIsAllDay()));

			resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(projectResource.getAssignDateFrom()));
			resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(projectResource.getAssignDateTo()));

			resourceRecord.setS_Resource_ID(ResourceId.toRepoId(projectResource.getResourceId()));
			resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(projectResource.getWoProjectStepId()));
			resourceRecord.setC_Project_ID(ProjectId.toRepoId(projectResource.getWoProjectStepId().getProjectId()));

			resourceRecord.setWOTestFacilityGroupName(projectResource.getTestFacilityGroupName());

			if (projectResource.getDuration() == null || projectResource.getDurationUnit() == null)
			{
				resourceRecord.setDuration(BigDecimal.ZERO);
				resourceRecord.setDurationUnit(WFDurationUnit.Hour.getCode());
			}
			else
			{
				resourceRecord.setDuration(projectResource.getDuration());
				resourceRecord.setDurationUnit(projectResource.getDurationUnit().getCode());
			}

			saveRecord(resourceRecord);

			savedProjectResource.add(ofRecord(resourceRecord));
		}

		return savedProjectResource.build();
	}

	@NonNull
	public WOProjectResource create(@NonNull final CreateWOProjectResourceRequest createWOProjectResourceRequest)
	{
		final I_C_Project_WO_Resource resourceRecord = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);
		resourceRecord.setAD_Org_ID(createWOProjectResourceRequest.getOrgId().getRepoId());
		resourceRecord.setExternalId(ExternalId.toValue(createWOProjectResourceRequest.getExternalId()));

		resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateFrom()));
		resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateTo()));

		resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId()));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(createWOProjectResourceRequest.getResourceId()));
		resourceRecord.setC_Project_ID(ProjectId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId().getProjectId()));

		resourceRecord.setWOTestFacilityGroupName(createWOProjectResourceRequest.getTestFacilityGroupName());

		if (createWOProjectResourceRequest.getIsActive() != null)
		{
			resourceRecord.setIsActive(createWOProjectResourceRequest.getIsActive());
		}
		if (createWOProjectResourceRequest.getIsAllDay() != null)
		{
			resourceRecord.setIsAllDay(createWOProjectResourceRequest.getIsAllDay());
		}

		if (createWOProjectResourceRequest.getDuration() == null || createWOProjectResourceRequest.getDurationUnit() == null)
		{
			resourceRecord.setDuration(BigDecimal.ZERO);
			resourceRecord.setDurationUnit(WFDurationUnit.Hour.getCode());
		}
		else
		{
			resourceRecord.setDuration(createWOProjectResourceRequest.getDuration());
			resourceRecord.setDurationUnit(createWOProjectResourceRequest.getDurationUnit().getCode());
		}

		saveRecord(resourceRecord);

		return ofRecord(resourceRecord);
	}

	@NonNull
	private static WOProjectResource ofRecord(@NonNull final I_C_Project_WO_Resource resourceRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(resourceRecord.getAD_Org_ID());

		final Instant assignDateFrom = TimeUtil.asInstant(resourceRecord.getAssignDateFrom());
		final Instant assignDateTo = TimeUtil.asInstant(resourceRecord.getAssignDateTo());

		if (assignDateTo == null || assignDateFrom == null)
		{
			throw new AdempiereException("I_C_Project_WO_Resource.assignDateFrom and I_C_Project_WO_Resource.assignDateTo should be set on the record at this point!");
		}

		final ProjectId projectId = ProjectId.ofRepoId(resourceRecord.getC_Project_ID());

		return WOProjectResource.builder()
				.orgId(orgId)
				.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Resource_ID()))
				.externalId(ExternalId.ofOrNull(resourceRecord.getExternalId()))

				.resourceId(ResourceId.ofRepoId(resourceRecord.getS_Resource_ID()))
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Step_ID()))

				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(resourceRecord.getDuration())
				.durationUnit(WFDurationUnit.ofNullableCode(resourceRecord.getDurationUnit()))

				.isAllDay(resourceRecord.isAllDay())
				.isActive(resourceRecord.isActive())

				.testFacilityGroupName(resourceRecord.getWOTestFacilityGroupName())
				.build();
	}
}
