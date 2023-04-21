package de.metas.calendar.plan_optimizer;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class ComputePlanService
{
	private final MultiCalendarService calendarService;

	public ComputePlanService(
			@NonNull final MultiCalendarService calendarService)
	{
		this.calendarService = calendarService;
	}

	public void start(SimulationPlanId simulationId)
	{

	}

	public void stop(SimulationPlanId simulationId)
	{

	}

	public void getStatus(SimulationPlanId simulationId)
	{

	}

	private Solver<Plan> createSolver()
	{
		final SolverFactory<Plan> solverFactory = createSolverFactory();
		return solverFactory.buildSolver();
	}

	private SolverFactory<Plan> createSolverFactory()
	{
		return SolverFactory.create(new SolverConfig()
				.withSolutionClass(Plan.class)
				.withEntityClasses(Step.class)
				.withConstraintProviderClass(PlanConstraintProvider.class)
				// The solver runs only for 5 seconds on this small dataset.
				// It's recommended to run for at least 5 minutes ("5m") otherwise.
				.withTerminationSpentLimit(Duration.ofSeconds(60)));
	}

	private Plan getPlan(@NonNull final SimulationPlanId simulationId)
	{
		final Stream<CalendarEntry> a = calendarService.query(CalendarQuery.builder()
				.simulationId(simulationId)
				.build());

		return null; // TODO
	}

}
