package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RollingWeeksAvgStrategyTest
{
	private SalesHistoryRepository salesHistoryRepo;

	@BeforeEach
	void beforeEach()
	{
		salesHistoryRepo = Mockito.mock(SalesHistoryRepository.class);
	}

	@Test
	void test52WeeksAvg()
	{
		// 4360 sales over 52 weeks, forecast for 12 weeks
		// Expected: 4360 / 52 * 12 = 1006.15
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("4360"));

		final RollingWeeksAvgStrategy strategy = new RollingWeeksAvgStrategy(52, salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		assertThat(result).isEqualByComparingTo("1006.15");
	}

	@Test
	void test26WeeksAvg()
	{
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("2600"));

		final RollingWeeksAvgStrategy strategy = new RollingWeeksAvgStrategy(26, salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		// 2600 / 26 * 12 = 1200
		assertThat(result).isEqualByComparingTo("1200.00");
	}

	@Test
	void test12WeeksAvg()
	{
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("600"));

		final RollingWeeksAvgStrategy strategy = new RollingWeeksAvgStrategy(12, salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(8));

		// 600 / 12 * 8 = 400
		assertThat(result).isEqualByComparingTo("400.00");
	}

	@Test
	void testNoSalesData()
	{
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(BigDecimal.ZERO);

		final RollingWeeksAvgStrategy strategy = new RollingWeeksAvgStrategy(52, salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		assertThat(result).isEqualByComparingTo("0");
	}

	@Test
	void testExcelBeispiel1()
	{
		// From the customer Excel: 4360 total sales over 52 weeks, 12 week horizon
		// Expected: 4360 / 52 * 12 = 1006.15 (rounded to 1006.15)
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("4360"));

		final RollingWeeksAvgStrategy strategy = new RollingWeeksAvgStrategy(52, salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		assertThat(result).isEqualByComparingTo("1006.15");
	}

	private ForecastCalculationContext createContext(final int horizonWeeks)
	{
		return ForecastCalculationContext.builder()
				.productId(ProductId.ofRepoId(1000))
				.referenceDate(LocalDate.of(2026, 3, 7))
				.precisionUnit(ForecastPrecisionUnit.WEEK)
				.forecastHorizonInPrecisionUnits(horizonWeeks)
				.orgId(OrgId.ofRepoId(1000000))
				.build();
	}
}
