package de.metas.mforecast.generator;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Domain object for a forecast line built by the generator service.
 * No ID — always represents a new record to be persisted.
 * <p>
 * {@code qty} and {@code qtyCalculated} are initially set to the same computed value.
 * The user can later adjust {@code qty} in the UI while {@code qtyCalculated} preserves the original.
 */
@Value
@Builder
public class GeneratedForecastLine
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull UomId uomId;
	@NonNull BigDecimal qty;
	@NonNull BigDecimal qtyCalculated;
	@NonNull LocalDate datePromised;
}
