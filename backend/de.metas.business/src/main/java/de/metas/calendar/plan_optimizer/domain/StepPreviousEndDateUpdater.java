package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.variable.VariableListener;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class StepPreviousEndDateUpdater implements VariableListener<Plan, StepAllocation>
{
	@Override
	public void beforeEntityAdded(ScoreDirector<Plan> scoreDirector, StepAllocation step) {}

	@Override
	public void afterEntityAdded(ScoreDirector<Plan> scoreDirector, StepAllocation step)
	{
		System.out.println("afterEntityAdded: " + step + " --------------------------");
		updateStep(scoreDirector, step);
		System.out.println("---------------------------------------------------------");
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
		//System.out.println("Updating step: " + originalStep);
		final StepAllocation firstStep = getFirstStep(originalStep);
		firstStep.setPreviousStepEndDateAndUpdate(firstStep.getStartDateMin(), scoreDirector);

		LocalDateTime previousEndDate = firstStep.getEndDate();
		for (StepAllocation currentStep = firstStep.getNext(); currentStep != null; currentStep = currentStep.getNext())
		{
			currentStep.setPreviousStepEndDateAndUpdate(previousEndDate, scoreDirector);
			previousEndDate = currentStep.getEndDate();
		}

		// for (Step s = firstStep; s != null; s = s.getNextStep())
		// {
		// 	System.out.println("\t" + s);
		// }
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
