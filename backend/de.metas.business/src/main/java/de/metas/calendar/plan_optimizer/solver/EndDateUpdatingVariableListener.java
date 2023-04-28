package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Step;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

public class EndDateUpdatingVariableListener implements VariableListener<Plan, Step>
{
	@Override
	public void beforeEntityAdded(final ScoreDirector<Plan> scoreDirector, final Step step) {}

	@Override
	public void afterEntityAdded(final ScoreDirector<Plan> scoreDirector, final Step step) {updateEndDate(scoreDirector, step);}

	@Override
	public void beforeVariableChanged(final ScoreDirector<Plan> scoreDirector, final Step step) {}

	@Override
	public void afterVariableChanged(final ScoreDirector<Plan> scoreDirector, final Step step) {updateEndDate(scoreDirector, step);}

	@Override
	public void beforeEntityRemoved(final ScoreDirector<Plan> scoreDirector, final Step step) {}

	@Override
	public void afterEntityRemoved(final ScoreDirector<Plan> scoreDirector, final Step step) {}

	private void updateEndDate(final ScoreDirector<Plan> scoreDirector, final Step step)
	{
		scoreDirector.beforeVariableChanged(step, Step.FIELD_endDate);
		step.updateEndDate();
		scoreDirector.afterVariableChanged(step, Step.FIELD_endDate);
	}
}
