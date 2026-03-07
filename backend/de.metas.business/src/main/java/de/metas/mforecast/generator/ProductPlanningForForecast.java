package de.metas.mforecast.generator;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class ProductPlanningForForecast
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@Nullable ForecastComparisonPeriod forecastComparisonPeriod;
	@Nullable ForecastPrecisionUnit forecastPrecisionUnit;
	@Nullable Integer forecastFrequency;
	@Nullable Integer forecastBufferTime;
	@Nullable BigDecimal deliveryTimePromised;
}
