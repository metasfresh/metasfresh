package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.variable.VariableListener;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class StepPreviousEndDateUpdater implements VariableListener<Plan, Step>
{
	@Override
	public void beforeEntityAdded(ScoreDirector<Plan> scoreDirector, Step step) {}

	@Override
	public void afterEntityAdded(ScoreDirector<Plan> scoreDirector, Step step)
	{
		System.out.println("afterEntityAdded: " + step + " --------------------------");
		updateStep(scoreDirector, step);
		System.out.println("---------------------------------------------------------");
	}

	@Override
	public void beforeVariableChanged(ScoreDirector<Plan> scoreDirector, Step step) {}

	@Override
	public void afterVariableChanged(ScoreDirector<Plan> scoreDirector, Step step)
	{
		//System.out.println("afterVariableChanged: " + step + " ----------------------");
		updateStep(scoreDirector, step);
		//System.out.println("---------------------------------------------------------");
	}

	@Override
	public void beforeEntityRemoved(ScoreDirector<Plan> scoreDirector, Step step) {}

	@Override
	public void afterEntityRemoved(ScoreDirector<Plan> scoreDirector, Step step) {}

	public static void updateStep(@NonNull Step originalStep) {updateStep(null, originalStep);}

	private static void updateStep(@Nullable final ScoreDirector<Plan> scoreDirector, @NonNull Step originalStep)
	{
		//System.out.println("Updating step: " + originalStep);
		final Step firstStep = getFirstStep(originalStep);
		firstStep.setPreviousStepEndDateAndUpdate(firstStep.getStartDateMin(), scoreDirector);

		LocalDateTime previousEndDate = firstStep.getEndDate();
		for (Step currentStep = firstStep.getNextStep(); currentStep != null; currentStep = currentStep.getNextStep())
		{
			currentStep.setPreviousStepEndDateAndUpdate(previousEndDate, scoreDirector);
			previousEndDate = currentStep.getEndDate();
		}

		// for (Step s = firstStep; s != null; s = s.getNextStep())
		// {
		// 	System.out.println("\t" + s);
		// }
	}

	private static Step getFirstStep(@NonNull final Step step)
	{
		Step firstStep = step;
		while (firstStep.getPreviousStep() != null)
		{
			firstStep = firstStep.getPreviousStep();
		}

		return firstStep;
	}
}
