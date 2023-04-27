package de.metas.calendar.plan_optimizer;

import ch.qos.logback.classic.Level;
import com.google.common.base.Stopwatch;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.junit.jupiter.api.Disabled;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Disabled
public class ManualPOCTest
{
	public static final Duration TERMINATION_SPENT_LIMIT = Duration.ofMinutes(5);
	private static final ProjectId PROJECT_ID1 = ProjectId.ofRepoId(1);
	private static final ProjectId PROJECT_ID2 = ProjectId.ofRepoId(2);
	private static final AtomicInteger nextStepRepoId = new AtomicInteger(1);
	private static SolutionManager<Plan, HardSoftScore> scoreManager;

	private static final Debouncer<Plan> printSolutionDebouncer = Debouncer.<Plan>builder()
			.bufferMaxSize(100)
			.delayInMillis(2000)
			.consumer((solutions) -> {
				if (!solutions.isEmpty())
				{
					final Plan lastSolution = solutions.get(solutions.size() - 1);
					printSolution(lastSolution);
				}
			})
			.build();

	public static void main(String[] args)
	{
		LogManager.setLoggerLevel("org.drools", Level.INFO);
		LogManager.setLoggerLevel("org.optaplanner", Level.INFO);

		// The solver runs only for 5 seconds on this small dataset.
		// It's recommended to run for at least 5 minutes ("5m") otherwise.
		SolverFactory<Plan> solverFactory = SolverFactory.create(new SolverConfig()
				.withSolutionClass(Plan.class)
				.withEntityClasses(Step.class)
				.withConstraintProviderClass(PlanConstraintProvider.class)
				// The solver runs only for 5 seconds on this small dataset.
				// It's recommended to run for at least 5 minutes ("5m") otherwise.
				.withTerminationSpentLimit(TERMINATION_SPENT_LIMIT));

		scoreManager = SolutionManager.create(solverFactory);

		final Solver<Plan> solver = solverFactory.buildSolver();
		solver.addEventListener(ManualPOCTest::printBestSolutionChanged);

		// Solve the problem
		Plan problem = generateDemoData();
		printSolution(problem);

		do
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();
			final Plan solution = solver.solve(problem);
			stopwatch.stop();
			solution.setFinalSolution(true);

			printSolutionDebouncer.processAndClearBufferSync();
			printSolution(solution);
			System.out.println("Took " + stopwatch);
			checkStepsInOrder(solution);

			System.out.println("\n\n Press 'c' to continue...");
			String key = new Scanner(System.in).next();
			if (key.length() > 0 && key.charAt(0) == 'c')
			{
				problem = solution;
			}
			else
			{
				break;
			}
		}
		while (true);
	}

	private static Plan generateDemoData()
	{
		final ArrayList<Step> stepsList = new ArrayList<>();

		// Project 1:
		for (int i = 10; i >= 1; i--)
		{
			stepsList.add(Step.builder()
					.id(StepId.builder()
							.woProjectStepId(WOProjectStepId.ofRepoId(PROJECT_ID1, nextStepRepoId.getAndIncrement()))
							.woProjectResourceId(WOProjectResourceId.ofRepoId(PROJECT_ID1, nextStepRepoId.get()))
							.build())
					.projectPriority(InternalPriority.MEDIUM)
					.projectSeqNo(i * 10)
					.resource(resource(i))
					.duration(Duration.ofHours(1))
					.dueDate(LocalDateTime.parse("2023-05-01T15:00"))
					.build());
		}

		// Project 2:
		for (int i = 1; i <= 10; i++)
		{
			stepsList.add(Step.builder()
					.id(StepId.builder()
							.woProjectStepId(WOProjectStepId.ofRepoId(PROJECT_ID2, nextStepRepoId.getAndIncrement()))
							.woProjectResourceId(WOProjectResourceId.ofRepoId(PROJECT_ID2, nextStepRepoId.get()))
							.build())
					.projectPriority(InternalPriority.MEDIUM)
					.projectSeqNo(i * 10)
					.resource(resource(i))
					.duration(Duration.ofHours(1))
					.dueDate(LocalDateTime.parse("2023-05-01T15:00"))
					.build());
		}

		// stepsList.stream()
		// 		.filter(step -> ProjectId.equals(step.getProjectId(), PROJECT_ID1)
		// 				&& ResourceId.equals(step.getResource().getId(), resourceId(1)))
		// 		.findFirst()
		// 		.ifPresent(step -> {
		// 			step.setStartDate(LocalDateTime.parse("2023-04-02T17:00"));
		// 			step.updateEndDate();
		// 			step.setPinned(true);
		// 		});

		final Plan plan = new Plan();
		plan.setSimulationId(SimulationPlanId.ofRepoId(123)); // dummy
		plan.setTimeZone(SystemTime.zoneId());
		plan.setPlanningStartDate(LocalDate.parse("2023-04-01").atStartOfDay());
		plan.setPlanningEndDate(LocalDate.parse("2023-05-02").atStartOfDay());
		plan.setStepsList(stepsList);

		return plan;
	}

	private static Resource resource(int index) {return new Resource(resourceId(index), "R" + index);}

	@NonNull
	private static ResourceId resourceId(final int index) {return ResourceId.ofRepoId(100 + index);}

	private static void printBestSolutionChanged(final BestSolutionChangedEvent<Plan> event)
	{
		final Plan solution = event.getNewBestSolution();
		solution.setTimeSpent(Duration.ofMillis(event.getTimeMillisSpent()));
		solution.setScore(solution.getScore());
		printSolutionDebouncer.add(solution);
	}

	private static void printSolution(final Plan solution)
	{
		System.out.println(toString(solution));

		if (solution.getScore() != null)
		{
			ScoreExplanation<Plan, HardSoftScore> scoreExplanation = scoreManager.explain(solution);
			System.out.println(scoreExplanation.getSummary());
		}
	}

	private static String toString(final Plan solution)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("**************************************************************************************************");
		sb.append("\nsimulationId: ").append(solution.getSimulationId());
		sb.append("\nPlan score: ").append(solution.getScore()).append(", Time spent: ").append(solution.getTimeSpent()).append(", IsFinalSolution=").append(solution.isFinalSolution());
		sb.append("\nPlanning dates: ").append(solution.getPlanningStartDate()).append(" -> ").append(solution.getPlanningEndDate());
		solution.getStepsList()
				.stream()
				// .sorted(Comparator.<Step, ResourceId>comparing(step -> step.getResource().getId())
				// 		.thenComparing(Step::getProjectSeqNo)
				.sorted(Comparator.comparing(Step::getProjectId).thenComparing(Step::getProjectSeqNo))
				.forEach(step -> sb.append("\n").append(toString(step)));
		sb.append("\n**************************************************************************************************");
		return sb.toString();
	}

	private static String toString(final Step step)
	{
		return step.getStartDate() + " -> " + step.getEndDate()
				+ " (" + step.getDuration() + ")"
				+ ": dueDate=" + step.getDueDate() + ", " + step.getResource()
				+ ", P" + step.getProjectId().getRepoId()
				//+ ", ID=" + step.getId().getWoProjectResourceId().getRepoId()
				+ ", seq=" + step.getProjectSeqNo()
				//+", prio="+step.getProjectPriority()
				+ (step.isPinned() ? " [PINNED]" : "")
				+ ", |td-te|=" + step.getDurationFromEndToDueDateInHoursAbs()
				;
	}

	private static void checkStepsInOrder(final Plan plan)
	{
		final HashMap<ProjectId, ArrayList<Step>> map = new HashMap<>();
		for (Step step : plan.getStepsList())
		{
			map.computeIfAbsent(step.getProjectId(), projectId -> new ArrayList<>())
					.add(step);
		}

		System.out.println("Checking steps are in order: ");
		for (final ProjectId projectId : map.keySet())
		{
			final ArrayList<Step> projectSteps = map.get(projectId);
			projectSteps.sort(Comparator.comparing(Step::getProjectSeqNo));

			for (int i = 0; i < projectSteps.size() - 1; i++)
			{
				Step step = projectSteps.get(i);
				Step nextStep = projectSteps.get(i + 1);
				final boolean notInOrder = PlanConstraintProvider.projectStepsNotInOrder(step, nextStep);
				if (notInOrder)
				{
					System.out.println("\t" + toString(step));
					System.out.println("\t" + toString(nextStep));
					//noinspection ConstantValue
					System.out.println("\t=> notInOrder=" + notInOrder);
					System.out.println("\t---");
				}
			}
		}
		System.out.println("--");
	}

}
