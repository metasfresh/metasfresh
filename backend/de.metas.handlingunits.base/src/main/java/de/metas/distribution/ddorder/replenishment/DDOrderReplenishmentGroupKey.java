package de.metas.distribution.ddorder.replenishment;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

/**
 * Groups replenishment demand sharing {@code (product, target locator, UOM)} into a single DD_Order. The target locator is the
 * workstation's pick-from locator (falling back to the workplace warehouse's default); the source locator is deliberately excluded
 * since it's an outcome of the stock-aware allocation, not a grouping input.
 */
@Value
@Builder(toBuilder = true)
public class DDOrderReplenishmentGroupKey
{
	@NonNull ProductId productId;
	@NonNull LocatorId locatorToId;
	@NonNull UomId uomId;
}
