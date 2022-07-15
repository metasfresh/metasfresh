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
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public class WOProjectStepRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public int getNextSeqNo(@NonNull final ProjectId projectId)
	{
		final int lastSeqNo = queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.maxInt(I_C_Project_WO_Step.COLUMNNAME_SeqNo);

		return lastSeqNo > 0
				? lastSeqNo / 10 * 10 + 10
				: 10;
	}

	public Map<ProjectId, WOProjectSteps> getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableListMultimap<ProjectId, WOProjectStep> stepsByProjectId = queryBL
				.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(WOProjectStepRepository::fromRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(WOProjectStep::getProjectId, step -> step));

		return projectIds.stream()
				.map(projectId -> WOProjectSteps.builder()
						.projectId(projectId)
						.steps(stepsByProjectId.get(projectId))
						.build())
				.collect(ImmutableMap.toImmutableMap(WOProjectSteps::getProjectId, steps -> steps));
	}

	public WOProjectSteps getByProjectId(@NonNull final ProjectId projectId)
	{
		return getByProjectIds(ImmutableSet.of(projectId)).get(projectId);
	}

	public static WOProjectStep fromRecord(@NonNull final I_C_Project_WO_Step record)
	{
		try
		{
			return WOProjectStep.builder()
					.id(WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()))
					.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
					.seqNo(record.getSeqNo())
					.name(record.getName())
					.dateRange(CalendarDateRange.builder()
							.startDate(record.getDateStart().toInstant())
							.endDate(record.getDateEnd().toInstant())
							.build())
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed loading WOProjectStep from " + record, ex);
		}
	}

	public void updateStepDateRanges(@NonNull final Map<WOProjectStepId, CalendarDateRange> stepDateRanges)
	{
		final Set<WOProjectStepId> stepIds = stepDateRanges.keySet();
		if (stepIds.isEmpty())
		{
			return;
		}

		final ImmutableMap<WOProjectStepId, I_C_Project_WO_Step> recordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Step.class)
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, stepIds)
				.create()
				.map(record -> WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()));

		for (final WOProjectStepId stepId : stepIds)
		{
			final I_C_Project_WO_Step record = recordsById.get(stepId);
			if (record == null)
			{
				throw new AdempiereException("No project resource not found for " + stepId);
			}

			final CalendarDateRange dateRange = stepDateRanges.get(stepId);

			updateRecordFromDateRange(record, dateRange);
			InterfaceWrapperHelper.save(record);
		}
	}

	private static void updateRecordFromDateRange(@NonNull final I_C_Project_WO_Step record, @NonNull final CalendarDateRange from)
	{
		record.setDateStart(TimeUtil.asTimestamp(from.getStartDate()));
		record.setDateEnd(TimeUtil.asTimestamp(from.getEndDate()));
	}
}
