package de.metas.calendar.plan_optimizer;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.resource.HumanResourceTestGroupService;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.Disabled;
import org.optaplanner.core.api.solver.SolverFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Disabled
public class ManualPOCTest
{
	public static void main(String[] args) {new ManualPOCTest().start();}

	public static final Duration TERMINATION_SPENT_LIMIT = Duration.ofMinutes(5);
	private final InMemoryHumanResourceTestGroupRepository humanResourceTestGroupRepository;
	private final AtomicInteger nextStepRepoId = new AtomicInteger(1);
	private HumanResourceTestGroupId humanResourceTestGroupId;

	ManualPOCTest()
	{
		LogManager.setLoggerLevel(SimulationOptimizerTask.class, Level.DEBUG);
		Adempiere.enableUnitTestMode();

		this.humanResourceTestGroupRepository = new InMemoryHumanResourceTestGroupRepository();
		SpringContextHolder.registerJUnitBean(new HumanResourceTestGroupService(humanResourceTestGroupRepository));
	}

	void start()
	{
		final SimulationPlanId simulationId = SimulationPlanId.ofRepoId(123);

		final InMemoryPlanLoaderAndSaver planLoaderAndSaver = new InMemoryPlanLoaderAndSaver();
		planLoaderAndSaver.saveSolution(generateProblem(simulationId));

		do
		{
			SimulationOptimizerTask.builder()
					.executorService(Executors.newSingleThreadExecutor())
					.solverFactory(createSolverFactory())
					.simulationOptimizerStatusDispatcher(new SimulationOptimizerStatusDispatcher())
					.planLoaderAndSaver(planLoaderAndSaver)
					.simulationId(simulationId)
					.onTaskComplete(() -> {}) // do nothing
					.build()
					.run();

			System.out.println("\n\n Press 'q' to quit, any key to run again...");
			final String key = new Scanner(System.in).next();
			if (!key.isEmpty() && key.charAt(0) == 'q')
			{
				break;
			}
		}
		while (true);
	}

	private static SolverFactory<Plan> createSolverFactory()
	{
		return SolverFactory.create(SimulationOptimizerConfiguration.solverConfig(null, TERMINATION_SPENT_LIMIT));
	}

	private Plan generateProblem(SimulationPlanId simulationId)
	{
		this.humanResourceTestGroupId = humanResourceTestGroupRepository.createHumanResourceTestGroup(2);

		final ArrayList<Step> stepsList = new ArrayList<>();

		for (int projectIdx = 1; projectIdx <= 2; projectIdx++)
		{
			final ProjectId projectId = projectId(projectIdx);

			for (int resourceIdx = 1; resourceIdx <= 2; resourceIdx++)
			{
				stepsList.add(Step.builder()
						.id(StepId.builder()
								.woProjectStepId(WOProjectStepId.ofRepoId(projectId, nextStepRepoId.getAndIncrement()))
								.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, nextStepRepoId.get()))
								.build())
						.projectPriority(InternalPriority.MEDIUM)
						.resource(resource(resourceIdx))
						.duration(Duration.ofHours(1))
						.dueDate(LocalDateTime.parse("2023-05-01T15:00"))
						.startDateMin(LocalDate.parse("2023-04-01").atStartOfDay())
						.humanResourceTestGroupDuration(Duration.ofHours(1))
						.build());
			}
		}

		updatePreviousAndNextStep(stepsList);

		final Plan plan = new Plan();
		plan.setSimulationId(simulationId);
		plan.setTimeZone(SystemTime.zoneId());
		plan.setStepsList(stepsList);

		return plan;
	}

	@NonNull
	private static ProjectId projectId(final int index) {return ProjectId.ofRepoId(index);}

	private Resource resource(int index) {return new Resource(resourceId(index), "R" + index, humanResourceTestGroupId);}

	@NonNull
	private static ResourceId resourceId(final int index) {return ResourceId.ofRepoId(100 + index);}

	private static void updatePreviousAndNextStep(final List<Step> steps)
	{
		ImmutableListMultimap<ProjectId, Step> stepsByProjectId = Multimaps.index(steps, Step::getProjectId);

		for (final ProjectId projectId : stepsByProjectId.keySet())
		{
			final ImmutableList<Step> projectSteps = stepsByProjectId.get(projectId);

			for (int i = 0, lastIndex = projectSteps.size() - 1; i <= lastIndex; i++)
			{
				final Step step = projectSteps.get(i);
				step.setPreviousStep(i == 0 ? null : projectSteps.get(i - 1));
				step.setNextStep(i == lastIndex ? null : projectSteps.get(i + 1));
			}
		}
	}
}
