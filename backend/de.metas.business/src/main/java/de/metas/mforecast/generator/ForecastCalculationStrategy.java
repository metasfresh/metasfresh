package de.metas.mforecast.generator;

import lombok.NonNull;

import java.math.BigDecimal;

public interface ForecastCalculationStrategy
{
	BigDecimal computeForecastQty(@NonNull ForecastCalculationContext context);
}
