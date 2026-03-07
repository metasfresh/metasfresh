package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ForecastCalculationContext
{
	@NonNull ProductId productId;
	@NonNull LocalDate referenceDate;
	@NonNull ForecastPrecisionUnit precisionUnit;
	int forecastHorizonInPrecisionUnits;
	@NonNull OrgId orgId;
}
