package de.metas.mforecast.generator;

import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Strategy for computing the forecast demand quantity for a single product.
 * <p>
 * Each implementation defines its own comparison date range logic and scaling formula:
 * <ul>
 *   <li>{@link RollingWeeksAvgStrategy} — rolling average over the last N weeks (52/26/12)</li>
 *   <li>{@link PrevCalendarYearAvgStrategy} — average over the full previous calendar year</li>
 *   <li>{@link SamePeriodPrevYearAvgStrategy} — same calendar period shifted back 1 year (no rescaling)</li>
 * </ul>
 *
 * @see ForecastCalculationStrategyFactory
 */
public interface ForecastCalculationStrategy
{
	/**
	 * Compute the forecasted demand quantity for one product, in stock UOM.
	 *
	 * @return the forecast quantity; {@link java.math.BigDecimal#ZERO} if there is no sales history
	 */
	BigDecimal computeForecastQty(@NonNull ForecastCalculationContext context);
}
