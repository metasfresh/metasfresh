package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SamePeriodPrevYearAvgStrategyTest
{
	private SalesHistoryRepository salesHistoryRepo;

	@BeforeEach
	void beforeEach()
	{
		salesHistoryRepo = Mockito.mock(SalesHistoryRepository.class);
	}

	@Test
	void testPhaseAligned()
	{
		// 8 week horizon, same 8 week period last year had 163 sales -> 163 (no rescaling)
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("163"));

		final SamePeriodPrevYearAvgStrategy strategy = new SamePeriodPrevYearAvgStrategy(salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(8));

		assertThat(result).isEqualByComparingTo("163");

		// Verify: dateFrom = 2026-03-07 - 1 year = 2025-03-07, dateTo = 2025-03-07 + 8 weeks = 2025-05-02
		final ArgumentCaptor<SalesHistoryQuery> queryCaptor = ArgumentCaptor.forClass(SalesHistoryQuery.class);
		verify(salesHistoryRepo).retrieveTotalSalesQty(queryCaptor.capture());
		assertThat(queryCaptor.getValue().getDateFrom()).isEqualTo(LocalDate.of(2025, 3, 7));
		assertThat(queryCaptor.getValue().getDateTo()).isEqualTo(LocalDate.of(2025, 5, 2));
	}

	@Test
	void testExcelBeispiel2()
	{
		// From customer Excel: phase-aligned, 8 weeks, 163 sales in same period last year
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("163"));

		final SamePeriodPrevYearAvgStrategy strategy = new SamePeriodPrevYearAvgStrategy(salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(8));

		assertThat(result).isEqualByComparingTo("163");
	}

	@Test
	void testNoSalesInSamePeriod()
	{
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(BigDecimal.ZERO);

		final SamePeriodPrevYearAvgStrategy strategy = new SamePeriodPrevYearAvgStrategy(salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		assertThat(result).isEqualByComparingTo("0");
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
