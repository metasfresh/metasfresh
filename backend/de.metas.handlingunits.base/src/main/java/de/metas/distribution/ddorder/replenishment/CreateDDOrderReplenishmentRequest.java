package de.metas.distribution.ddorder.replenishment;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import java.time.Instant;

/**
 * Immutable request describing the single DD_Order (header + line) that the picking-reconcile flow
 * saves to the database.
 *
 * <p>All warehouse-resolution (locators, in-transit warehouse, doc-type) is performed by the Service
 * layer ({@link DDOrderPickingReplenishmentService}) before building this request. The service assembles
 * the {@code I_DD_Order} / {@code I_DD_OrderLine} records from this request and delegates their persistence
 * to {@code DDOrderLowLevelDAO} (the owner of DD_Order/DD_OrderLine persistence).
 */
@Value
@Builder
public class CreateDDOrderReplenishmentRequest
{
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@NonNull WarehouseId inTransitWarehouseId;
	@NonNull LocatorId locatorFromId;
	@NonNull LocatorId locatorToId;
	@NonNull DocTypeId docTypeId;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull OrgId orgId;
	@NonNull Instant datePromised;
}
