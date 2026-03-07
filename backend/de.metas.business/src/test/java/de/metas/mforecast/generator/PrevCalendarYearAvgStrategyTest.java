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

class PrevCalendarYearAvgStrategyTest
{
	private SalesHistoryRepository salesHistoryRepo;

	@BeforeEach
	void beforeEach()
	{
		salesHistoryRepo = Mockito.mock(SalesHistoryRepository.class);
	}

	@Test
	void testFullPrevYear()
	{
		// 5200 sales over previous calendar year, 12 week horizon
		// Expected: 5200 / 52 * 12 = 1200.00
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(new BigDecimal("5200"));

		final PrevCalendarYearAvgStrategy strategy = new PrevCalendarYearAvgStrategy(salesHistoryRepo);
		final BigDecimal result = strategy.computeForecastQty(createContext(12));

		assertThat(result).isEqualByComparingTo("1200.00");

		// Verify the query uses Jan 1 - Jan 1 of prev year
		final ArgumentCaptor<SalesHistoryQuery> queryCaptor = ArgumentCaptor.forClass(SalesHistoryQuery.class);
		verify(salesHistoryRepo).retrieveTotalSalesQty(queryCaptor.capture());
		assertThat(queryCaptor.getValue().getDateFrom()).isEqualTo(LocalDate.of(2025, 1, 1));
		assertThat(queryCaptor.getValue().getDateTo()).isEqualTo(LocalDate.of(2026, 1, 1));
	}

	@Test
	void testNoSalesInPrevYear()
	{
		when(salesHistoryRepo.retrieveTotalSalesQty(any())).thenReturn(BigDecimal.ZERO);

		final PrevCalendarYearAvgStrategy strategy = new PrevCalendarYearAvgStrategy(salesHistoryRepo);
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
