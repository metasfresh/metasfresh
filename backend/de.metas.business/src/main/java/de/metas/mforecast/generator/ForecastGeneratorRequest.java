package de.metas.mforecast.generator;

import de.metas.product.ProductId;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Parameters for the M_Forecast_GenerateLines process, capturing the user's filter and override choices.
 * <p>
 * The process can optionally restrict which products are included ({@code productCategoryId}, {@code productId})
 * and override the per-product calculation method ({@code calculationMethodOverride}).
 * When an override is set, it takes precedence over the value configured in PP_Product_Planning for every product.
 */
@Value
@Builder
public class ForecastGeneratorRequest
{
	/** If set, only products in this category are included. */
	@Nullable ProductCategoryId productCategoryId;

	/** If set, only this single product is included. */
	@Nullable ProductId productId;

	/** If set, overrides the per-product Forecast_CalculationMethod from PP_Product_Planning. */
	@Nullable ForecastCalculationMethod calculationMethodOverride;

	/** If {@code true}, all existing M_ForecastLine records for this forecast are deleted before generating new ones. */
	boolean deleteExistingLines;

	public Optional<ForecastCalculationMethod> getCalculationMethodOverride()
	{
		return Optional.ofNullable(calculationMethodOverride);
	}

}
