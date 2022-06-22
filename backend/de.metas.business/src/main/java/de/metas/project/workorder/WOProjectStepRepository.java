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
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_WO_Step;
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

	public WOProjectStep getById(@NonNull final ProjectId projectId, @NonNull final WOProjectStepId stepId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, stepId)
				.create()
				.firstOnlyOptional(I_C_Project_WO_Step.class)
				.map(WOProjectStepRepository::fromRecord)
				.orElseThrow(() -> new AdempiereException("No Step found for " + projectId + ", " + stepId));
	}

	private static WOProjectStep fromRecord(@NonNull final I_C_Project_WO_Step record)
	{
		return WOProjectStep.builder()
				.id(WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()))
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.seqNo(record.getSeqNo())
				.name(record.getName())
				.build();
	}
}
