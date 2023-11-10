package de.metas.calendar.plan_optimizer;

import ai.timefold.solver.core.api.solver.SolverFactory;
import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.plan_optimizer.domain.StepPreviousEndDateUpdater;
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
		//planLoaderAndSaver.saveSolution(generateProblem_X_Projects_with_Y_Steps(simulationId));
		planLoaderAndSaver.saveSolution(generateProblem_StepHRRequirementExceedingWeeklyCapacity(simulationId));

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

	@NonNull
	private static Plan createPlan(final @NonNull SimulationPlanId simulationId, final ArrayList<Step> stepsList)
	{
		updatePreviousAndNextStep(stepsList);

		final Plan plan = Plan.newInstance();
		plan.setSimulationId(simulationId);
		plan.setTimeZone(SystemTime.zoneId());
		plan.setStepsList(stepsList);

		return plan;
	}

	private Plan generateProblem_X_Projects_with_Y_Steps(@NonNull final SimulationPlanId simulationId)
	{
		this.humanResourceTestGroupId = humanResourceTestGroupRepository.createHumanResourceTestGroup(15);

		final ArrayList<Step> stepsList = new ArrayList<>();

		final Step.StepBuilder stepTemplate = Step.builder()
				//.id(nextStepId(projectId))
				.projectPriority(InternalPriority.MEDIUM)
				//.resource(resource(resourceIdx))
				.requiredResourceCapacity(Duration.ofHours(1))
				.requiredHumanCapacity(Duration.ofHours(1))
				.dueDate(LocalDateTime.parse("2023-05-01T15:00"))
				.startDateMin(LocalDate.parse("2023-04-01").atStartOfDay())
				.delay(0);

		for (int projectIdx = 1; projectIdx <= 3; projectIdx++)
		{
			final ProjectId projectId = projectId(projectIdx);

			for (int resourceIdx = 1; resourceIdx <= 10; resourceIdx++)
			{
				final Step step = stepTemplate.id(nextStepId(projectId)).resource(resource(resourceIdx)).build();

				if (projectIdx == 1)
				{
					step.setProjectPriority(InternalPriority.HIGH);
				}
				if (projectIdx == 1 && resourceIdx == 5)
				{
					step.setPinnedStartDate(LocalDateTime.parse("2023-04-02T00:00"));
				}

				stepsList.add(step);
			}
		}

		return createPlan(simulationId, stepsList);
	}

	private Plan generateProblem_StepHRRequirementExceedingWeeklyCapacity(@NonNull final SimulationPlanId simulationId)
	{
		this.humanResourceTestGroupId = humanResourceTestGroupRepository.createHumanResourceTestGroup(40);

		ProjectId P1 = projectId(1); // A
		ProjectId P2 = projectId(2); // B
		ProjectId P3 = projectId(3); // C
		ProjectId P4 = projectId(4); // D

		Resource R1 = resource(1); // EMV Burst  8x5
		Resource R2 = resource(2); // Nicht-EMV 8x5

		final Step.StepBuilder stepTemplate = Step.builder()
				//.id(nextStepId(projectId))
				//.resource(resource(resourceIdx))
				// .duration(Duration.ofHours(1))
				// .humanResourceTestGroupDuration(Duration.ofHours(1))
				.startDateMin(LocalDateTime.parse("2023-11-20T00:00"))
				.dueDate(LocalDateTime.parse("2025-12-31T23:59")) // far in the future
				.projectPriority(InternalPriority.MEDIUM)
				.delay(0);

		final ArrayList<Step> stepsList = new ArrayList<>();
		stepsList.add(stepTemplate.id(nextStepId(P1)).resource(R1).requiredResourceCapacity(Duration.ofHours(5)).requiredHumanCapacity(Duration.ofHours(41)).build());
		stepsList.add(stepTemplate.id(nextStepId(P1)).resource(R1).requiredResourceCapacity(Duration.ofHours(32)).requiredHumanCapacity(Duration.ofHours(5)).build());
		stepsList.add(stepTemplate.id(nextStepId(P2)).resource(R1).requiredResourceCapacity(Duration.ofHours(5)).requiredHumanCapacity(Duration.ofHours(5)).build());
		stepsList.add(stepTemplate.id(nextStepId(P3)).resource(R2).requiredResourceCapacity(Duration.ofHours(1)).requiredHumanCapacity(Duration.ofHours(1)).build());
		stepsList.add(stepTemplate.id(nextStepId(P4)).resource(R2).requiredResourceCapacity(Duration.ofHours(41)).requiredHumanCapacity(Duration.ofHours(16)).build());
		return createPlan(simulationId, stepsList);
	}

	@NonNull
	private static ProjectId projectId(final int index) {return ProjectId.ofRepoId(index);}

	private Resource resource(int index) {return new Resource(resourceId(index), "R" + index, humanResourceTestGroupId);}

	@NonNull
	private static ResourceId resourceId(final int index) {return ResourceId.ofRepoId(100 + index);}

	private StepId nextStepId(final ProjectId projectId)
	{
		final int stepRepoId = nextStepRepoId.getAndIncrement();
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, stepRepoId))
				.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, stepRepoId)) // we use stepRepoId for project respource id because does not matter and we just want something unique
				.build();
	}

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

			StepPreviousEndDateUpdater.updateStep(projectSteps.get(0));
		}
	}
}
