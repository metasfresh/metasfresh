package de.metas.calendar.simulation.process;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class C_SimulationPlan_Process extends JavaProcess
{
	private final SimulationPlanService simulationPlanService = SpringContextHolder.instance.getBean(SimulationPlanService.class);

	@Override
	protected String doIt()
	{
		final SimulationPlanId simulationPlanId = SimulationPlanId.ofRepoId(getRecord_ID());
		simulationPlanService.complete(simulationPlanId);
		return MSG_OK;
	}
}
