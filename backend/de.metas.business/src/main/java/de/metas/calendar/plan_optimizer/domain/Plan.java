package de.metas.calendar.plan_optimizer.domain;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@PlanningSolution
@Data
public class Plan
{
	@PlanningEntityCollectionProperty
	private List<Step> stepsList;

	@PlanningScore
	private HardSoftScore score;

	@ValueRangeProvider
	public CountableValueRange<LocalDateTime> createStartDateList()
	{
		final LocalDateTime lastDueDate = stepsList.stream()
				.map(Step::getDueDate)
				.max(LocalDateTime::compareTo)
				.orElseThrow(() -> new IllegalStateException("No due date found"));

		return ValueRangeFactory.createLocalDateTimeValueRange(
				LocalDate.now().atStartOfDay(),
				lastDueDate,
				1,
				ChronoUnit.HOURS);
	}

}
