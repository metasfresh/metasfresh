package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/**
 * Immutable context passed to a {@link ForecastCalculationStrategy} for computing the forecast quantity of one product.
 * <p>
 * Contains everything a strategy needs — no external service dependencies required:
 * <ul>
 *   <li>{@code referenceDate} — the forecast's promised date; strategies derive their comparison date range from this</li>
 *   <li>{@code precisionUnit} — whether the forecast operates in weeks or months</li>
 *   <li>{@code forecastHorizonInPrecisionUnits} — how far into the future to forecast (frequency + buffer)</li>
 * </ul>
 */
@Value
@Builder
public class ForecastCalculationContext
{
	@NonNull ProductId productId;

	/** The forecast's reference date (typically M_Forecast.DatePromised). Strategies compute their comparison range relative to this. */
	@NonNull LocalDate referenceDate;

	@NonNull ForecastPrecisionUnit precisionUnit;

	/** Total forecast horizon = order cycle frequency + safety buffer, in precision units. */
	int forecastHorizonInPrecisionUnits;

	@NonNull OrgId orgId;
}
