package de.metas.distribution.ddorder.replenishment;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Immutable request describing the single DD_Order (header + line) that the picking-reconcile flow
 * saves to the database.
 *
 * <p>All warehouse-resolution (locators, in-transit warehouse, doc-type) is performed by the Service
 * layer ({@link DDOrderPickingReplenishmentService}) before building this request. The repository
 * ({@link DDOrderPickingReplenishmentRepository}) is pure data-access: it only builds and saves the
 * {@code I_DD_Order} / {@code I_DD_OrderLine} records.
 *
 * <p>Note on intentionally-omitted fields: there is no {@code C_BPartner_Location_ID} nor {@code PP_Plant_ID}.
 * The reconcile flow is an internal pick-to-packing move, so the partner-location and manufacturing-plant context
 * are not applicable.
 */
@Value
@Builder
public class CreateDDOrderReplenishmentRequest
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
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
	@Nullable BPartnerId bpartnerId;
}
