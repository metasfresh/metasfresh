package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@PlanningSolution
@Data
public class Plan
{
	private SimulationPlanId simulationId;
	private ZoneId timeZone;
	private LocalDateTime planningStartDate;
	private LocalDateTime planningEndDate;

	@PlanningEntityCollectionProperty
	private ArrayList<Step> stepsList;

	@PlanningScore(bendableHardLevelsSize = PlanConstraintProvider.HARD_LEVELS_SIZE, bendableSoftLevelsSize = PlanConstraintProvider.SOFT_LEVELS_SIZE)
	private BendableScore score;
	private boolean isFinalSolution;
	private Duration timeSpent = Duration.ZERO;

	@ValueRangeProvider
	public CountableValueRange<LocalDateTime> createStartDateList()
	{
		return ValueRangeFactory.createLocalDateTimeValueRange(planningStartDate, planningEndDate, 1, ChronoUnit.HOURS);
	}
}
