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

package de.metas.project.workorder;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Set;
import java.util.function.Function;

@Repository
public class WOProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableMap<ProjectId, WOProjectResources> getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableListMultimap<ProjectId, WOProjectResource> byProjectId = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(WOProjectResourceRepository::fromRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(WOProjectResource::getProjectId, Function.identity()));

		return projectIds.stream()
				.map(projectId -> WOProjectResources.builder()
						.projectId(projectId)
						.resources(byProjectId.get(projectId))
						.build())
				.collect(ImmutableMap.toImmutableMap(WOProjectResources::getProjectId, Function.identity()));
	}

	public WOProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		return getByProjectIds(ImmutableSet.of(projectId)).get(projectId);
	}

	public WOProjectResource getById(@NonNull final ProjectId projectId, @NonNull final WOProjectResourceId projectResourceId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceId)
				.create()
				.firstOnlyOptional(I_C_Project_WO_Resource.class)
				.map(WOProjectResourceRepository::fromRecord)
				.orElseThrow(() -> new AdempiereException("No project resource found for " + projectId + ", " + projectResourceId));
	}

	public static WOProjectResource fromRecord(@NonNull final I_C_Project_WO_Resource record)
	{
		final TemporalUnit durationUnit = WFDurationUnit.ofCode(record.getDurationUnit()).getTemporalUnit();

		return WOProjectResource.builder()
				.id(WOProjectResourceId.ofRepoId(record.getC_Project_WO_Resource_ID()))
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.stepId(WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()))
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.dateRange(CalendarDateRange.builder()
						.startDate(TimeUtil.asZonedDateTime(record.getAssignDateFrom()))
						.endDate(TimeUtil.asZonedDateTime(record.getAssignDateTo()))
						.allDay(record.isAllDay())
						.build())
				.durationUnit(durationUnit)
				.duration(Duration.of(record.getDuration().longValue(), durationUnit))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.build();
	}
}
