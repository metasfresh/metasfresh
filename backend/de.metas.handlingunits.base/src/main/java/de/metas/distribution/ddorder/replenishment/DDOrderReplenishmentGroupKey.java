package de.metas.distribution.ddorder.replenishment;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

/**
 * Identifies the product group that the picking replenishment plans as ONE DD_Order: the demand that shares
 * {@code (product, target locator, UOM)} is served by a single DD_Order carrying the summed quantity.
 *
 * <p>The source locator is deliberately NOT part of the key: it is an <i>outcome</i> of the stock-aware
 * allocation that runs once over the group's summed demand, not an input to the grouping.
 *
 * <p>The target locator is the workstation's configured pick-from locator, falling back to the workplace
 * warehouse's default locator; two workplaces sharing a target locator therefore share one group, which is
 * intended - the mover's trip is defined by where the goods land.
 */
@Value
@Builder(toBuilder = true)
public class DDOrderReplenishmentGroupKey
{
	@NonNull ProductId productId;
	@NonNull LocatorId locatorToId;
	@NonNull UomId uomId;
}
