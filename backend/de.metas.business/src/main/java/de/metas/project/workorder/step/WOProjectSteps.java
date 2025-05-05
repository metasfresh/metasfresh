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

package de.metas.project.workorder.step;

import com.google.common.collect.ImmutableList;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class WOProjectSteps
{
	@Getter ProjectId projectId;
	ImmutableList<WOProjectStep> stepsInOrder;

	@Builder
	private WOProjectSteps(
			@NonNull final ProjectId projectId,
			@NonNull final List<WOProjectStep> steps)
	{
		assertStepsAreMatchingProject(steps, projectId);

		this.projectId = projectId;
		this.stepsInOrder = steps.stream()
				.sorted(Comparator.comparing(WOProjectStep::getSeqNo)
						.thenComparing(WOProjectStep::getWoProjectStepId))
				.collect(ImmutableList.toImmutableList());
	}

	private static void assertStepsAreMatchingProject(final @NonNull List<WOProjectStep> steps, final @NonNull ProjectId expectedProjectId)
	{
		if (steps.isEmpty())
		{
			return;
		}

		final ImmutableList<WOProjectStep> stepsFromOtherProjects = steps.stream()
				.filter(step -> !ProjectId.equals(step.getProjectId(), expectedProjectId))
				.collect(ImmutableList.toImmutableList());
		if (!stepsFromOtherProjects.isEmpty())
		{
			throw new AdempiereException("Expected all steps to be from project " + expectedProjectId + " but followings are not: " + stepsFromOtherProjects);
		}
	}

	public ImmutableList<WOProjectStep> toOrderedList() {return stepsInOrder;}

	public Stream<WOProjectStep> stream() {return stepsInOrder.stream();}

	public WOProjectStep getById(@NonNull WOProjectStepId stepId)
	{
		return stepsInOrder.stream()
				.filter(step -> WOProjectStepId.equals(step.getWoProjectStepId(), stepId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No step found for " + stepId + " in " + this));
	}

	public List<WOProjectStep> getStepsBeforeFromLastToFirst(@NonNull final WOProjectStepId stepId)
	{
		final ArrayList<WOProjectStep> result = new ArrayList<>();
		for (final WOProjectStep step : stepsInOrder)
		{
			if (WOProjectStepId.equals(step.getWoProjectStepId(), stepId))
			{
				break;
			}

			result.add(0, step);
		}

		return ImmutableList.copyOf(result);
	}

	public List<WOProjectStep> getStepsAfterInOrder(@NonNull final WOProjectStepId stepId)
	{
		final ImmutableList.Builder<WOProjectStep> result = ImmutableList.builder();
		boolean addStepsToResult = false;
		for (final WOProjectStep step : stepsInOrder)
		{
			if (addStepsToResult)
			{
				result.add(step);
			}
			else if (WOProjectStepId.equals(step.getWoProjectStepId(), stepId))
			{
				addStepsToResult = true;
			}
		}

		return result.build();
	}

}
