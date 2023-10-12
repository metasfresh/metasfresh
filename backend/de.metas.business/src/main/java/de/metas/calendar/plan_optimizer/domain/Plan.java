package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.bendable.BendableScore;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import de.metas.calendar.plan_optimizer.solver.PlanCloner;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@PlanningSolution(solutionCloner = PlanCloner.class)
@Data
@Builder(toBuilder = true)
public class Plan
{
	public static final ChronoUnit PLANNING_TIME_PRECISION = ChronoUnit.HOURS;

	private SimulationPlanId simulationId;
	private ZoneId timeZone;

	@PlanningEntityCollectionProperty
	private ArrayList<Step> stepsList;

	@PlanningScore(bendableHardLevelsSize = PlanConstraintProvider.HARD_LEVELS_SIZE, bendableSoftLevelsSize = PlanConstraintProvider.SOFT_LEVELS_SIZE)
	private BendableScore score;
	private ScoreExplanation<Plan, BendableScore> scoreExplanation;
	private boolean isFinalSolution;
	private Duration timeSpent = Duration.ZERO;

	public static Plan newInstance() {return builder().build();}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for timefold troubleshooting
		final StringBuilder sb = new StringBuilder();
		sb.append("\nsimulationId: ").append(simulationId);
		sb.append("\nPlan score: ").append(score).append(", Time spent: ").append(timeSpent).append(", IsFinalSolution=").append(isFinalSolution);
		if (stepsList != null && !stepsList.isEmpty())
		{
			final ImmutableListMultimap<ProjectId, Step> stepsByProjectId = Multimaps.index(stepsList, Step::getProjectId);
			final ImmutableSetMultimap<ProjectId, InternalPriority> priorityByProjectId = stepsList.stream().collect(ImmutableSetMultimap.toImmutableSetMultimap(Step::getProjectId, Step::getProjectPriority));
			for (final ProjectId projectId : stepsByProjectId.keySet())
			{
				sb.append("\n").append(projectId).append(" (").append(priorityByProjectId.get(projectId)).append(")");
				stepsByProjectId.get(projectId).forEach(step -> sb.append("\n\t").append(step));

			}
		}
		else
		{
			sb.append("\n\t(No steps)");
		}

		if (scoreExplanation != null)
		{
			sb.append("\n").append(scoreExplanation.getSummary());
		}

		return sb.toString();
	}

	public Plan copy()
	{
		final Plan newPlan = toBuilder().stepsList(null).build();

		if (stepsList != null && !stepsList.isEmpty())
		{
			final LinkedHashMap<StepId, Step> newSteps = new LinkedHashMap<>(stepsList.size());
			final HashMap<StepId, StepId> current2prevId = new HashMap<>();
			final HashMap<StepId, StepId> current2nextId = new HashMap<>();

			for (final Step originalStep : stepsList)
			{
				if (originalStep.getPreviousStep() != null)
				{
					current2prevId.put(originalStep.getId(), originalStep.getPreviousStep().getId());
				}
				if (originalStep.getNextStep() != null)
				{
					current2nextId.put(originalStep.getId(), originalStep.getNextStep().getId());
				}

				final Step newStep = originalStep.toBuilder()
						.previousStep(null)
						.nextStep(null)
						.build();
				newSteps.put(newStep.getId(), newStep);
			}

			for (final Step newStep : newSteps.values())
			{
				final StepId prevId = current2prevId.get(newStep.getId());
				if (prevId != null)
				{
					final Step prev = newSteps.get(prevId);
					newStep.setPreviousStep(prev);
				}

				final StepId nextId = current2nextId.get(newStep.getId());
				if (nextId != null)
				{
					final Step next = newSteps.get(nextId);
					newStep.setNextStep(next);
				}
			}

			newPlan.setStepsList(new ArrayList<>(newSteps.values()));
		}

		return newPlan;
	}
}
