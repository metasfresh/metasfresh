package de.metas.calendar.plan_optimizer;

import ch.qos.logback.classic.Level;
import com.google.common.base.Stopwatch;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

class SimulationOptimizerTask implements Runnable
{
	private static final Logger logger = LogManager.getLogger(SimulationOptimizerTask.class);

	//
	// Params
	private final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher;
	private final PlanLoaderAndSaver planLoaderAndSaver;
	private final SimulationPlanId simulationId;
	private final Duration terminationSpentLimit;
	private final Runnable onTaskComplete;

	//
	// State
	private Solver<Plan> solver;
	private CompletableFuture<?> future;

	@Builder
	private SimulationOptimizerTask(
			@NonNull final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher,
			@NonNull final PlanLoaderAndSaver planLoaderAndSaver,
			@NonNull final SimulationPlanId simulationId,
			@NonNull final Duration terminationSpentLimit,
			@NonNull final Runnable onTaskComplete)
	{
		this.simulationOptimizerStatusDispatcher = simulationOptimizerStatusDispatcher;
		this.planLoaderAndSaver = planLoaderAndSaver;
		this.simulationId = simulationId;
		this.terminationSpentLimit = terminationSpentLimit;
		this.onTaskComplete = onTaskComplete;
	}

	public synchronized boolean isRunning()
	{
		final CompletableFuture<?> future = this.future;
		return future != null && !future.isDone();
	}

	synchronized void start()
	{
		if (isRunning())
		{
			return;
		}

		solver = null; // just to make sure
		future = CompletableFuture.runAsync(this)
				.whenComplete((v, ex) -> {
					if (ex != null)
					{
						logger.warn("Got error", ex);
					}

					clearFutureComplete();
					onTaskComplete.run();
					simulationOptimizerStatusDispatcher.notifyStopped(simulationId);
				});
	}

	synchronized void stop()
	{
		final Solver<Plan> solver = this.solver;
		if (solver != null)
		{
			solver.terminateEarly();
			this.solver = null;
		}

		if (future != null)
		{
			future.cancel(true);
			future = null;
		}
	}

	private synchronized void clearFutureComplete()
	{
		this.future = null;
		this.solver = null;
	}

	private synchronized Solver<Plan> createSolver()
	{
		if (this.solver != null)
		{
			this.solver.terminateEarly();
			this.solver = null;
		}

		final SolverFactory<Plan> solverFactory = createSolverFactory();
		final Solver<Plan> solver = this.solver = solverFactory.buildSolver();
		solver.addEventListener(event -> {
			final Plan solution = event.getNewBestSolution();
			solution.setFinalSolution(false);
			solution.setTimeSpent(Duration.ofMillis(event.getTimeMillisSpent()));
			onSolutionFound(solution);
		});
		return solver;
	}

	private SolverFactory<Plan> createSolverFactory()
	{
		return SolverFactory.create(new SolverConfig()
				.withSolutionClass(Plan.class)
				.withEntityClasses(Step.class)
				.withConstraintProviderClass(PlanConstraintProvider.class)
				.withTerminationSpentLimit(terminationSpentLimit));
	}

	@Override
	public void run()
	{
		// FIXME debugging
		LogManager.setLoggerLevel("org.drools", Level.INFO);
		LogManager.setLoggerLevel("org.optaplanner", Level.INFO);

		simulationOptimizerStatusDispatcher.notifyStarted(simulationId);

		final Plan problem = planLoaderAndSaver.getPlan(simulationId);
		printSolution(problem);

		final Solver<Plan> solver = createSolver();

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Plan solution = solver.solve(problem);
		stopwatch.stop();
		solution.setFinalSolution(true);
		solution.setTimeSpent(stopwatch.elapsed());

		onSolutionFound(solution);
	}

	private void onSolutionFound(final Plan solution)
	{
		printSolution(solution);
		planLoaderAndSaver.saveSolution(solution);
		simulationOptimizerStatusDispatcher.notifyProgress(solution);
	}

	private static void printSolution(final Plan solution)
	{
		System.out.println(toString(solution));
	}

	private static String toString(final Plan solution)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("**************************************************************************************************");
		sb.append("\nsimulationId: ").append(solution.getSimulationId());
		sb.append("\nPlan score: ").append(solution.getScore()).append(", Time spent: ").append(solution.getTimeSpent()).append(", IsFinalSolution=").append(solution.isFinalSolution());
		sb.append("\nPlanning dates: ").append(solution.getPlanningStartDate()).append(" -> ").append(solution.getPlanningEndDate());
		solution.getStepsList().forEach(step -> sb.append("\n").append(toString(step)));
		sb.append("\n**************************************************************************************************");
		return sb.toString();
	}

	private static String toString(final Step step)
	{
		return step.getStartDate() + " -> " + step.getEndDate()
				+ " (" + step.getDuration() + ")"
				+ ": dueDate=" + step.getDueDate() + ", " + step.getResource() + ", ID=" + step.getId().getWoProjectResourceId().getRepoId();
	}

}
