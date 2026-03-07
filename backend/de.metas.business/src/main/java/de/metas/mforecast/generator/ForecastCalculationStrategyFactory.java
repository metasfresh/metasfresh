package de.metas.mforecast.generator;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ForecastCalculationStrategyFactory
{
	@NonNull private final SalesHistoryRepository salesHistoryRepo;

	public ForecastCalculationStrategyFactory(@NonNull final SalesHistoryRepository salesHistoryRepo)
	{
		this.salesHistoryRepo = salesHistoryRepo;
	}

	public ForecastCalculationStrategy getStrategy(@NonNull final ForecastCalculationMethod period)
	{
		switch (period)
		{
			case AVG_52_WEEKS:
				return new RollingWeeksAvgStrategy(52, salesHistoryRepo);
			case AVG_26_WEEKS:
				return new RollingWeeksAvgStrategy(26, salesHistoryRepo);
			case AVG_12_WEEKS:
				return new RollingWeeksAvgStrategy(12, salesHistoryRepo);
			case AVG_PREV_CALENDAR_YEAR:
				return new PrevCalendarYearAvgStrategy(salesHistoryRepo);
			case AVG_SAME_PERIOD_PREV_YEAR:
				return new SamePeriodPrevYearAvgStrategy(salesHistoryRepo);
			default:
				throw new IllegalArgumentException("Unknown calculation method: " + period);
		}
	}
}
