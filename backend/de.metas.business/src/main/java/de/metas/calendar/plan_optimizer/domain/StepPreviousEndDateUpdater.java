package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.variable.VariableListener;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class StepPreviousEndDateUpdater implements VariableListener<Plan, StepAllocation>
{
	@Override
	public void beforeEntityAdded(ScoreDirector<Plan> scoreDirector, StepAllocation step) {}

	@Override
	public void afterEntityAdded(ScoreDirector<Plan> scoreDirector, StepAllocation step)
	{
		//System.out.println("afterEntityAdded: " + step + " --------------------------");
		updateStep(scoreDirector, step);
		//System.out.println("---------------------------------------------------------");
	}

	@Override
	public void beforeVariableChanged(ScoreDirector<Plan> scoreDirector, StepAllocation step) {}

	@Override
	public void afterVariableChanged(ScoreDirector<Plan> scoreDirector, StepAllocation step)
	{
		//System.out.println("afterVariableChanged: " + step + " ----------------------");
		updateStep(scoreDirector, step);
		//System.out.println("---------------------------------------------------------");
	}

	@Override
	public void beforeEntityRemoved(ScoreDirector<Plan> scoreDirector, StepAllocation step) {}

	@Override
	public void afterEntityRemoved(ScoreDirector<Plan> scoreDirector, StepAllocation step) {}

	public static void updateStep(@NonNull StepAllocation originalStep) {updateStep(null, originalStep);}

	private static void updateStep(@Nullable final ScoreDirector<Plan> scoreDirector, @NonNull StepAllocation originalStep)
	{
		final ArrayDeque<StepAllocation> uncheckedSuccessorQueue = new ArrayDeque<>();

		if (originalStep.getNext() != null)
		{
			uncheckedSuccessorQueue.add(originalStep.getNext());
		}

		while (!uncheckedSuccessorQueue.isEmpty())
		{
			StepAllocation allocation = uncheckedSuccessorQueue.remove();
			boolean updated = updatePreviousEndDate(scoreDirector, allocation);
			if (updated && allocation.getNext() != null)
			{
				uncheckedSuccessorQueue.add(allocation.getNext());
			}
		}
	}

	private static boolean updatePreviousEndDate(final @Nullable ScoreDirector<Plan> scoreDirector, final StepAllocation step)
	{
		final LocalDateTime previousEndDate = step.getPrevious() == null ? step.getStartDateMin() : step.getPrevious().getEndDate();

		// if (Objects.equals(step.getPreviousStepEndDate(), previousEndDate))
		// {
		// 	return false; // no change
		// }

		if (scoreDirector != null)
		{
			scoreDirector.beforeVariableChanged(step, StepAllocation.FIELD_PreviousStepEndDate);
			//System.out.println("beforeVariableChanged: " + StepAllocation.FIELD_PreviousStepEndDate + "=" + step.getPreviousStepEndDate() + " -- " + step.getId());
		}

		step.setPreviousStepEndDateAndUpdate(previousEndDate);

		if (scoreDirector != null)
		{
			scoreDirector.afterVariableChanged(step, StepAllocation.FIELD_PreviousStepEndDate);
			//System.out.println("afterVariableChanged: " + StepAllocation.FIELD_PreviousStepEndDate + "=" + step.getPreviousStepEndDate() + " -- " + step.getId());
		}

		return true;
	}

	private static StepAllocation getFirstStep(@NonNull final StepAllocation step)
	{
		StepAllocation firstStep = step;
		while (firstStep.getPrevious() != null)
		{
			firstStep = firstStep.getPrevious();
		}

		return firstStep;
	}
}
