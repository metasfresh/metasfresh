package de.metas.deliveryplanning;

import lombok.Value;

/**
 * The two flags {@code M_Delivery_Planning} stores ABOUT its allocations. Allocations are the fact; these
 * are a projection of that fact onto the planning row, stored so SQL-side readers do not have to join:
 * migration 5822060 made {@code IsAllocated} a real column precisely because the grid displays it across a
 * whole result set and a lazy {@code ColumnSQL} cost one query per row there.
 * <p>
 * Deliberately NOT fields on {@link DeliveryPlanning}: a projection sitting beside the fact it projects can
 * disagree with it, and {@code DeliveryPlanning.isAllocated()} already answers the question from the
 * allocations themselves.
 */
@Value(staticConstructor = "of")
public class AllocationDerivedFlags
{
	boolean allocated;
	boolean readyForReceipt;
}
