package de.metas.calendar.plan_optimizer;

import de.metas.calendar.plan_optimizer.domain.Plan;

interface SimulationOptimizerTaskListener
{
	void onSolutionFound(Plan solution);
}
