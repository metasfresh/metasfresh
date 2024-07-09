package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.bendable.BendableScore;
import de.metas.calendar.plan_optimizer.solver.PlanCloner;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.Data;
import org.adempiere.exceptions.AdempiereException;

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
	private ArrayList<StepAllocation> stepsList;

	@PlanningScore(bendableHardLevelsSize = PlanConstraintProvider.HARD_LEVELS_SIZE, bendableSoftLevelsSize = PlanConstraintProvider.SOFT_LEVELS_SIZE)
	private BendableScore score;
	private ScoreExplanation<Plan, BendableScore> scoreExplanation;
	private boolean isFinalSolution;
	private Duration timeSpent = Duration.ZERO;

	public static Plan newInstance() {return builder().build();}

	@Override
	public String toString() {return PlanToStringHelper.of(this).toString();}

	public Plan copy()
	{
		final Plan newPlan = toBuilder().stepsList(null).build();

		if (stepsList != null && !stepsList.isEmpty())
		{
			final LinkedHashMap<StepAllocationId, StepAllocation> newSteps = new LinkedHashMap<>(stepsList.size());
			final HashMap<StepAllocationId, StepAllocationId> current2prevId = new HashMap<>();
			final HashMap<StepAllocationId, StepAllocationId> current2nextId = new HashMap<>();

			for (final StepAllocation originalStep : stepsList)
			{
				if (originalStep.getPrevious() != null)
				{
					current2prevId.put(originalStep.getId(), originalStep.getPrevious().getId());
				}
				if (originalStep.getNext() != null)
				{
					current2nextId.put(originalStep.getId(), originalStep.getNext().getId());
				}

				final StepAllocation newStep = originalStep.toBuilder().previous(null).next(null).build();
				newSteps.put(newStep.getId(), newStep);
			}

			for (final StepAllocation newStep : newSteps.values())
			{
				final StepAllocationId prevId = current2prevId.get(newStep.getId());
				if (prevId != null)
				{
					final StepAllocation prev = newSteps.get(prevId);
					newStep.setPrevious(prev);
				}

				final StepAllocationId nextId = current2nextId.get(newStep.getId());
				if (nextId != null)
				{
					final StepAllocation next = newSteps.get(nextId);
					newStep.setNext(next);
				}
			}

			newPlan.setStepsList(new ArrayList<>(newSteps.values()));
		}

		return newPlan;
	}

	public void prepareProblem()
	{
		if (stepsList == null || stepsList.isEmpty())
		{
			throw new AdempiereException("No steps");
		}

		//
		// Check steps are valid
		for (int i = 0, lastIndex = stepsList.size() - 1; i <= lastIndex; i++)
		{
			final StepAllocation step = stepsList.get(i);
			step.checkProblemFactsValid().assertTrue();
		}
	}

}
