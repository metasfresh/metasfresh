package de.metas.calendar.plan_optimizer.domain;

import com.google.common.collect.ImmutableList;
import de.metas.resource.ResourceAvailabilityRanges;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class Project
{
	@NonNull ImmutableList<StepDef> steps;

	@Builder
	private Project(@NonNull @Singular final ImmutableList<StepDef> steps)
	{
		this.steps = steps;

		for (int i = 0, lastIndex = steps.size() - 1; i <= lastIndex; i++)
		{
			final StepDef step = steps.get(i);
			final StepDef previous = i == 0 ? null : steps.get(i - 1);
			final StepDef next = i < lastIndex ? steps.get(i + 1) : null;

			step.setPrevious(previous);
			step.setNext(next);

			//
			// Fix step's DueDate in case next step is pinned
			if (next != null
					&& next.getPinnedStartDate() != null
					&& next.getPinnedStartDate().isBefore(step.getDueDate()))
			{
				step.setDueDate(next.getPinnedStartDate());
			}
		}
	}

	public static List<StepAllocation> createAllocations(final List<Project> projects)
	{
		final ArrayList<StepAllocation> allocations = new ArrayList<>();
		projects.forEach(project -> allocations.addAll(project.createAllocations()));
		return allocations;
	}

	public List<StepAllocation> createAllocations()
	{
		ArrayList<StepAllocation> list = new ArrayList<>();
		StepAllocation previous = null;
		for (StepDef step : steps)
		{
			final StepAllocation allocation = createAllocation(step, previous);

			if (previous != null)
			{
				previous.setNext(allocation);
			}
			allocation.setPrevious(previous);

			list.add(allocation);
			previous = allocation;
		}
		return list;
	}

	private static StepAllocation createAllocation(@NonNull final StepDef stepDef, @Nullable final StepAllocation previous)
	{
		final LocalDateTime previousEndDate = previous == null ? stepDef.getStartDateMin() : previous.getEndDate();

		final int delay;
		final LocalDateTime startDate;
		if (stepDef.getInitialStartDate() != null)
		{
			startDate = stepDef.getInitialStartDate();
			delay = Math.max(computeStepDelay(previousEndDate, startDate), 0);
		}
		else
		{
			startDate = previousEndDate;
			delay = 0;
		}

		final LocalDateTime endDate;
		if (stepDef.getInitialEndDate() != null)
		{
			endDate = stepDef.getInitialEndDate();
		}
		else if (startDate != null)
		{
			endDate = startDate.plus(stepDef.getRequiredResourceCapacity());
		}
		else
		{
			endDate = null;
		}

		final ResourceAvailabilityRanges scheduledRange = startDate != null
				? ResourceAvailabilityRanges.ofStartAndEndDate(startDate, endDate)
				: null;

		return StepAllocation.builder()
				.id(StepAllocationId.of(stepDef.getId()))
				.stepDef(stepDef)
				.delay(delay)
				.previousStepEndDate(previousEndDate)
				.resourceScheduledRange(scheduledRange)
				.humanResourceScheduledRange(scheduledRange)
				.build();
	}

	public static int computeStepDelay(@Nullable final LocalDateTime lastStepEndDate, @Nullable final LocalDateTime thisStepStartDate)
	{
		return lastStepEndDate != null && thisStepStartDate != null && thisStepStartDate.isAfter(lastStepEndDate)
				? (int)Plan.PLANNING_TIME_PRECISION.between(lastStepEndDate, thisStepStartDate)
				: 0;
	}

}
