package de.metas.calendar.plan_optimizer;

import ch.qos.logback.classic.Level;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.logging.LogManager;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.project.workorder.step.WOProjectStepId;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class App
{
	private static final ProjectId PROJECT_ID1 = ProjectId.ofRepoId(1);
	private static final ProjectId PROJECT_ID2 = ProjectId.ofRepoId(2);
	private static final AtomicInteger nextStepRepoId = new AtomicInteger(1);

	public static void main(String[] args)
	{
		LogManager.setLoggerLevel("org.drools", Level.INFO);
		LogManager.setLoggerLevel("org.optaplanner", Level.INFO);

		final SolverFactory<Plan> solverFactory = SolverFactory.create(new SolverConfig()
				.withSolutionClass(Plan.class)
				.withEntityClasses(Step.class)
				.withConstraintProviderClass(PlanConstraintProvider.class)
				// The solver runs only for 5 seconds on this small dataset.
				// It's recommended to run for at least 5 minutes ("5m") otherwise.
				.withTerminationSpentLimit(Duration.ofSeconds(60)));

		final Solver<Plan> solver = solverFactory.buildSolver();
		solver.addEventListener(App::printBestSolutionChanged);

		// Solve the problem
		final Plan problem = generateDemoData();
		final Plan solution = solver.solve(problem);

		System.out.println("\n\n Last solution:");
		printSolution(solution);
	}

	private static Plan generateDemoData()
	{
		final ArrayList<Step> stepsList = new ArrayList<>();

		// Project 1:
		for (int i = 10; i >= 1; i--)
		{
			stepsList.add(Step.builder()
					.id(WOProjectStepId.ofRepoId(PROJECT_ID1, nextStepRepoId.getAndIncrement()))
					.projectSeqNo(i * 10)
					.resource(resource(i))
					.duration(Duration.ofHours(1))
					.dueDate(LocalDate.parse("2023-05-01").atTime(LocalTime.parse("15:00")))
					.build());
		}

		// Project 2:
		for (int i = 1; i <= 10; i++)
		{
			stepsList.add(Step.builder()
					.id(WOProjectStepId.ofRepoId(PROJECT_ID2, nextStepRepoId.getAndIncrement()))
					.projectSeqNo(i * 10)
					.resource(resource(i))
					.duration(Duration.ofHours(1))
					.dueDate(LocalDate.parse("2023-05-01").atTime(LocalTime.parse("15:00")))
					.build());
		}

		final Plan plan = new Plan();
		plan.setStepsList(stepsList);

		return plan;
	}

	private static Resource resource(int index)
	{
		return new Resource(ResourceId.ofRepoId(100 + index), "R" + index);
	}

	private static void printBestSolutionChanged(final BestSolutionChangedEvent<Plan> event)
	{
		final Plan solution = event.getNewBestSolution();
		System.out.println("**************************************************************************************************");
		System.out.println("Time spent: " + Duration.ofMillis(event.getTimeMillisSpent()));
		System.out.println("Plan score: " + solution.getScore());
		solution.getStepsList().forEach(System.out::println);
		System.out.println("**************************************************************************************************");
	}

	private static void printSolution(final Plan solution)
	{
		System.out.println("**************************************************************************************************");
		System.out.println("Plan score: " + solution.getScore());
		solution.getStepsList().forEach(System.out::println);
		System.out.println("**************************************************************************************************");
	}
}
