package de.metas.mforecast.generator;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SamePeriodPrevYearAvgStrategy implements ForecastCalculationStrategy
{
	@NonNull private final SalesHistoryRepository salesHistoryRepo;

	public SamePeriodPrevYearAvgStrategy(@NonNull final SalesHistoryRepository salesHistoryRepo)
	{
		this.salesHistoryRepo = salesHistoryRepo;
	}

	@Override
	public BigDecimal computeForecastQty(@NonNull final ForecastCalculationContext ctx)
	{
		final int horizonWeeks = toWeeks(ctx);

		final LocalDate dateFrom = ctx.getReferenceDate().minusYears(1);
		final LocalDate dateTo = dateFrom.plusWeeks(horizonWeeks);

		final SalesHistoryQuery query = SalesHistoryQuery.builder()
				.productId(ctx.getProductId())
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.orgId(ctx.getOrgId())
				.build();

		return salesHistoryRepo.retrieveTotalSalesQty(query);
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
