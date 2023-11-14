package de.metas.calendar.plan_optimizer;

import ai.timefold.solver.core.api.solver.SolverFactory;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.persistance.DatabasePlanLoaderAndSaver;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.organization.IOrgDAO;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.resource.HumanResourceTestGroupService;
import de.metas.resource.ResourceService;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SimulationOptimizerTaskExecutor
{
	private final SolverFactory<Plan> solverFactory;
	private final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher;
	private final DatabasePlanLoaderAndSaver planLoaderAndSaver;
	private final ExecutorService executorService;

	private final ConcurrentHashMap<SimulationPlanId, SimulationOptimizerTask> runningTasks = new ConcurrentHashMap<>();

	public SimulationOptimizerTaskExecutor(
			@NonNull final SolverFactory<Plan> solverFactory,
			@NonNull final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher,
			@NonNull final WOProjectService woProjectService,
			@NonNull final WOProjectSimulationService woProjectSimulationService,
			@NonNull final WOProjectConflictService woProjectConflictService,
			@NonNull final ResourceService resourceService,
			@NonNull final HumanResourceTestGroupService humanResourceTestGroupService)
	{
		this.solverFactory = solverFactory;
		this.simulationOptimizerStatusDispatcher = simulationOptimizerStatusDispatcher;
		this.planLoaderAndSaver = new DatabasePlanLoaderAndSaver(
				Services.get(IOrgDAO.class),
				woProjectService,
				woProjectSimulationService,
				woProjectConflictService,
				resourceService,
				humanResourceTestGroupService);

		executorService = createExecutorService();
	}

	private static ExecutorService createExecutorService()
	{
		final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("simulation-optimizer-");
		threadFactory.setDaemon(true);

		return Executors.newFixedThreadPool(10, threadFactory);
	}

	public void start(@NonNull final SimulationPlanId simulationId)
	{
		runningTasks.compute(
				simulationId,
				(k, existingTask) -> {
					if (existingTask != null && existingTask.isRunning())
					{
						return existingTask;
					}
					else
					{
						final SimulationOptimizerTask task = SimulationOptimizerTask.builder()
								.executorService(executorService)
								.solverFactory(solverFactory)
								.simulationOptimizerStatusDispatcher(simulationOptimizerStatusDispatcher)
								.planLoaderAndSaver(planLoaderAndSaver)
								.simulationId(simulationId)
								.onTaskComplete(() -> runningTasks.remove(simulationId))
								.build();
						task.start();
						return task;
					}
				});
	}

	public void stop(@NonNull final SimulationPlanId simulationId)
	{
		final SimulationOptimizerTask task = runningTasks.remove(simulationId);
		if (task != null)
		{
			task.stop();
		}
	}

	public boolean isRunning(@NonNull final SimulationPlanId simulationId)
	{
		final SimulationOptimizerTask task = runningTasks.get(simulationId);
		return task != null && task.isRunning();
	}
}
