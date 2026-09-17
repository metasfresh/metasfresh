package de.metas.mforecast.generator;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Per-product forecast configuration extracted from {@code PP_Product_Planning}.
 * <p>
 * When the forecast generator runs, it loads one of these for every product that has
 * a planning record with {@code IsExcludeFromForecast = N}. The object tells the
 * generator <em>how</em> to compute the expected demand for this product:
 *
 * <h3>What does it represent?</h3>
 * A single product's "forecast recipe": which algorithm to use, what time granularity,
 * how far into the future the forecast should reach, and how much safety stock to add.
 *
 * <h3>Properties</h3>
 * <ul>
 *   <li><b>{@code forecastCalculationMethod}</b> — the algorithm that determines which
 *       historical sales data is averaged. For example "rolling average of the last 52 weeks"
 *       or "same calendar period one year ago". If {@code null}, the product is skipped.</li>
 *   <li><b>{@code forecastPrecisionUnit}</b> — whether the forecast works in weeks or months.
 *       Affects how frequency and buffer are interpreted. Defaults to WEEK if not set.</li>
 *   <li><b>{@code forecastFrequency}</b> — the order cycle length expressed in precision units.
 *       E.g. "8 weeks" means the company orders this product every 8 weeks.
 *       If not configured, falls back to the product's lead time ({@code deliveryTimePromised}).</li>
 *   <li><b>{@code forecastBufferTime}</b> — additional safety stock expressed in precision units,
 *       added on top of frequency. E.g. frequency=4 + buffer=8 → total horizon of 12 weeks.</li>
 *   <li><b>{@code deliveryTimePromised}</b> — the product's lead time in days from
 *       {@code PP_Product_Planning.DeliveryTime_Promised}. Only used as a fallback when
 *       {@code forecastFrequency} is not set.</li>
 * </ul>
 *
 * <h3>How is the forecast horizon computed?</h3>
 * {@code forecastHorizon = frequency + bufferTime} (in precision units).
 * The algorithm then scales historical sales into this horizon window.
 */
@Value
@Builder
public class ProductPlanningForForecast
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;

	/** Which historical period to compare against. {@code null} means "not configured — skip this product". */
	@Nullable ForecastCalculationMethod forecastCalculationMethod;

	/** Time bucket granularity: week or month. Falls back to WEEK if not set. */
	@Nullable ForecastPrecisionUnit forecastPrecisionUnit;

	/** Order cycle length in precision units. If {@code null}, derived from {@link #deliveryTimePromised}. */
	@Nullable Integer forecastFrequency;

	/** Safety stock buffer in precision units, added to frequency to get the total forecast horizon. */
	@Nullable Integer forecastBufferTime;

	/** Product lead time in days (from PP_Product_Planning.DeliveryTime_Promised). Used as fallback for frequency. */
	@Nullable BigDecimal deliveryTimePromised;
}
