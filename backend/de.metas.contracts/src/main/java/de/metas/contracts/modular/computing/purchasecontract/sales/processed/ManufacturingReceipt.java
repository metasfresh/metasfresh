package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;

/**
 * Manufacturing Order Receipt (in modular contracts ubiquitous language)
 */
@Value
@Builder
class ManufacturingReceipt
{
	@NonNull PPCostCollectorId id;
	@NonNull PPOrderId manufacturingOrderId;
	@NonNull InstantAndOrgId transactionDate;
	@NonNull WarehouseId warehouseId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyReceived;
}
