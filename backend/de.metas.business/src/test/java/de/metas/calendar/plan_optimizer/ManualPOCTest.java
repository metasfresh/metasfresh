package de.metas.calendar.plan_optimizer;

import ai.timefold.solver.core.api.solver.SolverFactory;
import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.plan_optimizer.domain.HumanResource;
import de.metas.calendar.plan_optimizer.domain.HumanResourceId;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Project;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.calendar.plan_optimizer.domain.StepDef;
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
import de.metas.resource.ResourceWeeklyAvailability;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.junit.jupiter.api.Disabled;

import javax.annotation.Nullable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private final AtomicInteger nextStepRepoId = new AtomicInteger(1);
	private final AtomicInteger nextHumanResourceId = new AtomicInteger(1);

	ManualPOCTest()
	{
		LogManager.setLoggerLevel(SimulationOptimizerTask.class, Level.DEBUG);
		Adempiere.enableUnitTestMode();
	}

	void start()
	{
		final SimulationPlanId simulationId = SimulationPlanId.ofRepoId(123);

		final InMemoryPlanLoaderAndSaver planLoaderAndSaver = new InMemoryPlanLoaderAndSaver();
		//planLoaderAndSaver.saveSolution(generateProblem_X_Projects_with_Y_Steps(simulationId));
		planLoaderAndSaver.saveSolution(generateProblem_StepHRRequirementExceedingWeeklyCapacity(simulationId));
		// planLoaderAndSaver.saveSolution(generateProblem_oneStep_ToTestThatMachineAndHRShallStartAtTheSameTime(simulationId));

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
	private static Plan createPlan(final @NonNull SimulationPlanId simulationId, final List<Project> projects)
	{
		final ArrayList<StepAllocation> allocations = new ArrayList<>(Project.createAllocations(projects));

		final Plan plan = Plan.newInstance();
		plan.setSimulationId(simulationId);
		plan.setTimeZone(SystemTime.zoneId());
		plan.setStepsList(allocations);

		return plan;
	}

	@SuppressWarnings("unused")
	private Plan generateProblem_X_Projects_with_Y_Steps(@NonNull final SimulationPlanId simulationId)
	{
		final HumanResource humanResourceCapacity = humanResource(ResourceWeeklyAvailability.MONDAY_TO_FRIDAY_09_TO_17);

		final StepDef.StepDefBuilder stepTemplate = StepDef.builder()
				//.id(nextStepId(projectId))
				.projectPriority(InternalPriority.MEDIUM)
				//.resource(resource(resourceIdx))
				.requiredResourceCapacity(Duration.ofHours(1))
				.requiredHumanCapacity(Duration.ofHours(1))
				.dueDate(LocalDateTime.parse("2023-05-01T15:00"))
				.startDateMin(LocalDate.parse("2023-04-01").atStartOfDay())
				//.delay(0)
				;

		final ArrayList<Project> projects = new ArrayList<>();
		for (int projectIdx = 1; projectIdx <= 3; projectIdx++)
		{
			final ProjectId projectId = projectId(projectIdx);

			final ArrayList<StepDef> stepsList = new ArrayList<>();
			for (int resourceIdx = 1; resourceIdx <= 10; resourceIdx++)
			{
				final Resource resource = resource(resourceIdx, ResourceWeeklyAvailability.ALWAYS_AVAILABLE, humanResourceCapacity);
				final StepDef step = stepTemplate
						.id(nextStepId(projectId))
						.resource(resource)
						.projectPriority(projectIdx == 1 ? InternalPriority.HIGH : InternalPriority.MEDIUM)
						.pinnedStartDate(projectIdx == 1 && resourceIdx == 5 ? LocalDateTime.parse("2023-04-02T00:00") : null)
						.build();

				stepsList.add(step);
			}

			projects.add(Project.builder().steps(stepsList).build());
		}

		return createPlan(simulationId, projects);
	}

	private Plan generateProblem_StepHRRequirementExceedingWeeklyCapacity(@NonNull final SimulationPlanId simulationId)
	{
		final ProjectId P1 = projectId(1); // A
		final ProjectId P2 = projectId(2); // B
		final ProjectId P3 = projectId(3); // C
		final ProjectId P4 = projectId(4); // D

		final HumanResource humanResourceCapacity = humanResource(ResourceWeeklyAvailability.MONDAY_TO_FRIDAY_09_TO_17);
		final ResourceWeeklyAvailability availability_8x5 = ResourceWeeklyAvailability.builder()
				.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
				.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("17:00"))
				.build()
				.timeSlotTruncatedTo(Plan.PLANNING_TIME_PRECISION);
		final Resource R1 = resource(1, availability_8x5, humanResourceCapacity); // EMV Burst  8x5
		final Resource R2 = resource(2, availability_8x5, humanResourceCapacity); // Nicht-EMV 8x5

		final StepDef.StepDefBuilder stepTemplate = StepDef.builder()
				//.id(nextStepId(projectId))
				//.resource(resource(resourceIdx))
				// .duration(Duration.ofHours(1))
				// .humanResourceTestGroupDuration(Duration.ofHours(1))
				.startDateMin(LocalDateTime.parse("2024-01-25T00:00"))
				.dueDate(LocalDateTime.parse("2024-12-31T23:59")) // far in the future
				.projectPriority(InternalPriority.MEDIUM)
				//.delay(0)
				;

		final ImmutableList<Project> projects = ImmutableList.of(
				Project.builder()
						.step(stepTemplate.id(nextStepId(P1)).resource(R1).requiredResourceCapacity(Duration.ofHours(5)).requiredHumanCapacity(Duration.ofHours(41)).build())
						.step(stepTemplate.id(nextStepId(P1)).resource(R1).requiredResourceCapacity(Duration.ofHours(32)).requiredHumanCapacity(Duration.ofHours(5)).build())
						.build(),
				Project.builder()
						.step(stepTemplate.id(nextStepId(P2)).resource(R1).requiredResourceCapacity(Duration.ofHours(5)).requiredHumanCapacity(Duration.ofHours(5)).build())
						.build(),
				Project.builder()
						.step(stepTemplate.id(nextStepId(P3)).resource(R2).requiredResourceCapacity(Duration.ofHours(1)).requiredHumanCapacity(Duration.ofHours(1)).build())
						.build(),
				Project.builder()
						.step(stepTemplate.id(nextStepId(P4)).resource(R2).requiredResourceCapacity(Duration.ofHours(41)).requiredHumanCapacity(Duration.ofHours(16)).build())
						.build()
		);

		return createPlan(simulationId, projects);
	}

	private Plan generateProblem_oneStep_ToTestThatMachineAndHRShallStartAtTheSameTime(@NonNull final SimulationPlanId simulationId)
	{
		final ProjectId P1 = projectId(1); // A

		final HumanResource humanResourceCapacity = humanResource(ResourceWeeklyAvailability.MONDAY_TO_FRIDAY_09_TO_17);
		final Resource R1 = resource(1, ResourceWeeklyAvailability.ALWAYS_AVAILABLE, humanResourceCapacity);

		final StepDef.StepDefBuilder stepTemplate = StepDef.builder()
				//.id(nextStepId(projectId))
				//.resource(resource(resourceIdx))
				// .duration(Duration.ofHours(1))
				// .humanResourceTestGroupDuration(Duration.ofHours(1))
				.startDateMin(LocalDateTime.parse("2024-01-25T00:00"))
				.dueDate(LocalDateTime.parse("2024-12-31T23:59")) // far in the future
				.projectPriority(InternalPriority.MEDIUM)
				//.delay(0)
				;

		final ImmutableList<Project> projects = ImmutableList.of(
				Project.builder()
						.step(stepTemplate.id(nextStepId(P1)).resource(R1).requiredResourceCapacity(Duration.ofHours(10)).requiredHumanCapacity(Duration.ofHours(5)).build())
						.build()
		);

		return createPlan(simulationId, projects);
	}

	@NonNull
	private static ProjectId projectId(final int index) {return ProjectId.ofRepoId(index);}

	private HumanResource humanResource(@NonNull ResourceWeeklyAvailability availability)
	{
		return HumanResource.builder()
				.id(HumanResourceId.of(HumanResourceTestGroupId.ofRepoId(nextHumanResourceId.getAndIncrement())))
				.availability(availability.timeSlotTruncatedTo(Plan.PLANNING_TIME_PRECISION))
				.build();
	}

	private Resource resource(
			int index,
			@NonNull ResourceWeeklyAvailability availability,
			@Nullable HumanResource humanResourceCapacity)
	{
		return Resource.builder()
				.id(resourceId(index))
				.name("R" + index)
				.availability(availability.timeSlotTruncatedTo(Plan.PLANNING_TIME_PRECISION))
				.humanResource(humanResourceCapacity)
				.build();
	}

	@NonNull
	private static ResourceId resourceId(final int index) {return ResourceId.ofRepoId(100 + index);}

	private StepId nextStepId(final ProjectId projectId)
	{
		final int stepRepoId = nextStepRepoId.getAndIncrement();
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, stepRepoId))
				.machineWOProjectResourceId(WOProjectResourceId.ofRepoId(projectId, stepRepoId)) // we use stepRepoId for project resource id because does not matter, and we just want something unique
				.build();
	}
}
