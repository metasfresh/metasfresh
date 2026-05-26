package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Immutable request describing the single Completed DD_Order that the picking-reconcile flow
 * creates for a packing-warehouse shipment schedule.
 *
 * <p>Consumed exclusively by {@link DDOrderPickingReconcileRepository#createCompletedDDOrder(CreateDDOrderRequest)}.
 * Kept minimal — only the fields that method actually needs.
 */
@Value
@Builder
public class CreateDDOrderRequest
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull OrgId orgId;
	@NonNull Instant datePromised;
	@Nullable BPartnerId bpartnerId;
}
