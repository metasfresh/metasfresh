package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;

import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@PlanningSolution
@Data
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

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for optaplanner troubleshooting
		final StringBuilder sb = new StringBuilder();
		sb.append("\nsimulationId: ").append(simulationId);
		sb.append("\nPlan score: ").append(score).append(", Time spent: ").append(timeSpent).append(", IsFinalSolution=").append(isFinalSolution);
		if (stepsList != null && !stepsList.isEmpty())
		{
			stepsList.forEach(step -> sb.append("\n").append(step));
		}

		if (scoreExplanation != null)
		{
			sb.append("\n").append(scoreExplanation.getSummary());
		}

		return sb.toString();
	}
}
