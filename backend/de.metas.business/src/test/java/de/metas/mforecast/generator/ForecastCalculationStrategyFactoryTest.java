package de.metas.mforecast.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastCalculationStrategyFactoryTest
{
	private ForecastCalculationStrategyFactory factory;

	@BeforeEach
	void beforeEach()
	{
		final SalesHistoryRepository salesHistoryRepo = Mockito.mock(SalesHistoryRepository.class);
		factory = new ForecastCalculationStrategyFactory(salesHistoryRepo);
	}

	@Test
	void test52Weeks()
	{
		assertThat(factory.getStrategy(ForecastCalculationMethod.AVG_52_WEEKS)).isInstanceOf(RollingWeeksAvgStrategy.class);
	}

	@Test
	void test26Weeks()
	{
		assertThat(factory.getStrategy(ForecastCalculationMethod.AVG_26_WEEKS)).isInstanceOf(RollingWeeksAvgStrategy.class);
	}

	@Test
	void test12Weeks()
	{
		assertThat(factory.getStrategy(ForecastCalculationMethod.AVG_12_WEEKS)).isInstanceOf(RollingWeeksAvgStrategy.class);
	}

	@Test
	void testPrevCalendarYear()
	{
		assertThat(factory.getStrategy(ForecastCalculationMethod.AVG_PREV_CALENDAR_YEAR)).isInstanceOf(PrevCalendarYearAvgStrategy.class);
	}

	@Test
	void testSamePeriodPrevYear()
	{
		assertThat(factory.getStrategy(ForecastCalculationMethod.AVG_SAME_PERIOD_PREV_YEAR)).isInstanceOf(SamePeriodPrevYearAvgStrategy.class);
	}
}
