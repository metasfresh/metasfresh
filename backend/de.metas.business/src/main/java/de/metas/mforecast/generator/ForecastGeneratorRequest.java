package de.metas.mforecast.generator;

import de.metas.product.ProductId;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class ForecastGeneratorRequest
{
	@Nullable ProductCategoryId productCategoryId;
	@Nullable ProductId productId;
	@Nullable ForecastComparisonPeriod comparisonPeriodOverride;
	@Nullable ForecastPrecisionUnit precisionUnitOverride;
	boolean deleteExistingLines;

	public Optional<ForecastComparisonPeriod> getComparisonPeriodOverride()
	{
		return Optional.ofNullable(comparisonPeriodOverride);
	}

	public Optional<ForecastPrecisionUnit> getPrecisionUnitOverride()
	{
		return Optional.ofNullable(precisionUnitOverride);
	}
}
