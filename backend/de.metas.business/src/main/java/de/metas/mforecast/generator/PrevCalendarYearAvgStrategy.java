package de.metas.mforecast.generator;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class PrevCalendarYearAvgStrategy implements ForecastCalculationStrategy
{
	@NonNull private final SalesHistoryRepository salesHistoryRepo;

	public PrevCalendarYearAvgStrategy(@NonNull final SalesHistoryRepository salesHistoryRepo)
	{
		this.salesHistoryRepo = salesHistoryRepo;
	}

	@Override
	public BigDecimal computeForecastQty(@NonNull final ForecastCalculationContext ctx)
	{
		final int prevYear = ctx.getReferenceDate().getYear() - 1;
		final LocalDate dateFrom = LocalDate.of(prevYear, 1, 1);
		final LocalDate dateTo = LocalDate.of(prevYear + 1, 1, 1);

		final SalesHistoryQuery query = SalesHistoryQuery.builder()
				.productId(ctx.getProductId())
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.orgId(ctx.getOrgId())
				.build();

		final BigDecimal totalSales = salesHistoryRepo.retrieveTotalSalesQty(query);
		if (totalSales.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final int weeksInPrevYear = 52;
		final int horizonWeeks = toWeeks(ctx);

		return totalSales
				.divide(BigDecimal.valueOf(weeksInPrevYear), 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(horizonWeeks))
				.setScale(2, RoundingMode.HALF_UP);
	}

	private static int toWeeks(@NonNull final ForecastCalculationContext ctx)
	{
		if (ctx.getPrecisionUnit() == ForecastPrecisionUnit.MONTH)
		{
			return (int) Math.round(ctx.getForecastHorizonInPrecisionUnits() * 4.33);
		}
		return ctx.getForecastHorizonInPrecisionUnits();
	}
}
