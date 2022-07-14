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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectResourceRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<WOProjectResource> getOptionalById(@NonNull final WOProjectResourceId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(WOProjectResource::ofRecord);
	}

	@Nullable
	public I_C_Project_WO_Resource getRecordById(@NonNull final WOProjectResourceId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project_WO_Resource.class);
	}

	@NonNull
	public List<WOProjectResource> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(WOProjectResource::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public WOProjectResource update(@NonNull final WOProjectResource resource)
	{
		final I_C_Project_WO_Resource resourceRecord = getRecordById(resource.getWOProjectResourceIdNotNull());

		if (resourceRecord == null)
		{
			throw new AdempiereException("No C_Project_WO_Resource record found for id.")
						.appendParametersToMessage()
						.setParameter("WOProjectResourceId", resource);
		}

		return save(resourceRecord, resource);
	}

	@NonNull
	public WOProjectResource create(@NonNull final WOProjectResource resourceData)
	{
		final I_C_Project_WO_Resource record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);

		return save(record, resourceData);
	}

	@NonNull
	private WOProjectResource save(
			@NonNull final I_C_Project_WO_Resource resourceRecord,
			@NonNull final WOProjectResource resourceData)
	{
		if (resourceData.getIsActive() != null)
		{
			resourceRecord.setIsActive(resourceData.getIsActive());
		}
		else
		{
			resourceRecord.setIsActive(true);
		}

		if (resourceData.getIsAllDay() != null)
		{
			resourceRecord.setIsAllDay(resourceData.getIsAllDay());
		}
		resourceRecord.setAD_Org_ID(OrgId.toRepoId(resourceData.getOrgId()));
		resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId()));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(resourceData.getResourceId()));
		resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(resourceData.getAssignDateFrom()));
		resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(resourceData.getAssignDateTo()));
		resourceRecord.setDuration(resourceData.getDuration());
		resourceRecord.setDurationUnit(resourceData.getDurationUnit());
		resourceRecord.setBudget_Project_ID(ProjectId.toRepoId(resourceData.getProjectId()));
		resourceRecord.setC_Project_Resource_Budget_ID(BudgetProjectResourceId.toRepoId(resourceData.getProjectResourceBudgetId()));
		resourceRecord.setC_Project_ID(resourceData.getProjectId().getRepoId());

		saveRecord(resourceRecord);

		return WOProjectResource.ofRecord(resourceRecord);
	}
}
