package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Plan;
import ai.timefold.solver.core.api.domain.solution.cloner.SolutionCloner;

public class PlanCloner implements SolutionCloner<Plan>
{
	@Override
	public Plan cloneSolution(final Plan original) {return original.copy();}
}
