package de.metas.handlingunits.picking.dd_order.reconcile;

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
 * layer ({@link DDOrderPickingReconcileService}) before building this request. The repository
 * ({@link DDOrderPickingReconcileRepository}) is pure data-access: it only builds and saves the
 * {@code I_DD_Order} / {@code I_DD_OrderLine} records.
 *
 * <p>Note on intentionally-omitted fields: there is no {@code C_BPartner_Location_ID} nor {@code PP_Plant_ID}.
 * The reconcile flow is an internal pick-to-packing move, so the partner-location and manufacturing-plant context
 * are not applicable.
 */
@Value
@Builder
public class CreateDDOrderRequest
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	/** Source (stocking) warehouse — for M_Warehouse_From_ID on header. */
	@NonNull WarehouseId sourceWarehouseId;
	/** Target (packing) warehouse — for M_Warehouse_To_ID on header. */
	@NonNull WarehouseId targetWarehouseId;
	/** In-transit warehouse — for M_Warehouse_ID (transit) on header. */
	@NonNull WarehouseId inTransitWarehouseId;
	/** Default locator in the source warehouse — for M_Locator_ID on the DD_Order line. */
	@NonNull LocatorId locatorFromId;
	/** Default locator in the target (packing) warehouse — for M_LocatorTo_ID on the DD_Order line. */
	@NonNull LocatorId locatorToId;
	/** Distribution Order document type — resolved by the Service from AD_Client / org. */
	@NonNull DocTypeId docTypeId;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull OrgId orgId;
	@NonNull Instant datePromised;
	@Nullable BPartnerId bpartnerId;
}
