package de.metas.handlingunits.storage;

import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

/**
 * A locator paired with a product on-hand quantity (typically in the product's stocking UOM).
 * <p>
 * Emitted by {@link ProductAvailableStockPerLocator#streamLocatorQtyOnHandOrdered(de.metas.product.ProductId, java.util.List)},
 * which filters out empty entries upstream — so consumers of that stream can rely on the qty being positive.
 * Callers using the {@code of} factory directly are responsible for what they pass in.
 */
@Value(staticConstructor = "of")
public class LocatorIdAndQty
{
	@NonNull LocatorId locatorId;
	@NonNull Quantity qty;
}
