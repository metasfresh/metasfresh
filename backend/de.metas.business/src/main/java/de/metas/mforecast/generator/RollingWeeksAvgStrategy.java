package de.metas.mforecast.generator;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RollingWeeksAvgStrategy implements ForecastCalculationStrategy
{
	private final int comparisonWeeks;
	@NonNull private final SalesHistoryRepository salesHistoryRepo;

	public RollingWeeksAvgStrategy(final int comparisonWeeks, @NonNull final SalesHistoryRepository salesHistoryRepo)
	{
		this.comparisonWeeks = comparisonWeeks;
		this.salesHistoryRepo = salesHistoryRepo;
	}

	@Override
	public BigDecimal computeForecastQty(@NonNull final ForecastCalculationContext ctx)
	{
		final SalesHistoryQuery query = SalesHistoryQuery.builder()
				.productId(ctx.getProductId())
				.dateFrom(ctx.getReferenceDate().minusWeeks(comparisonWeeks))
				.dateTo(ctx.getReferenceDate())
				.orgId(ctx.getOrgId())
				.build();

		final BigDecimal totalSales = salesHistoryRepo.retrieveTotalSalesQty(query);
		if (totalSales.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final int horizonWeeks = toWeeks(ctx);

		return totalSales
				.divide(BigDecimal.valueOf(comparisonWeeks), 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(horizonWeeks))
				.setScale(2, RoundingMode.HALF_UP);
	}

	private static int toWeeks(@NonNull final ForecastCalculationContext ctx)
	{
		if (ctx.getPrecisionUnit() == ForecastPrecisionUnit.MONTH)
		{
			// approximate: 1 month ≈ 4.33 weeks
			return (int) Math.round(ctx.getForecastHorizonInPrecisionUnits() * 4.33);
		}
		return ctx.getForecastHorizonInPrecisionUnits();
	}
}
