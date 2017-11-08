package de.metas.picking.legacy.form;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Immutable Key to identify an individual table row.
 *
 * @author ts
 *
 */
@Value
@Builder
@EqualsAndHashCode(exclude = {
		"bpartnerId", // FIXME: preserving the old logic, i.e. bpartnerId is not part of the indexing key but used only for searching
		"seqNo" // we are not adding this to key because we want to search for a key (without knowing the seqNo)
})
public final class TableRowKey
{
	private final int bpartnerId;
	private final String bpartnerAddress;
	private final int warehouseId;
	private final int warehouseDestId;
	private final int productId;
	private final int shipperId;
	private final String deliveryVia;
	// private final int singleShipmentOrderId; // not used anymore
	private final int orderId;
	private final int shipmentScheduleId;
	private final int seqNo;
}
