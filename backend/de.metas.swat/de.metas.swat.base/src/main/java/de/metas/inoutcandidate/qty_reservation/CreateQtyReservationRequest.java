package de.metas.inoutcandidate.qty_reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class CreateQtyReservationRequest
{
	@NonNull OrderAndLineId orderAndLineId;
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@Nullable BPartnerId vendorBPartnerId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
	@NonNull QtyTU qtyTU;
	@NonNull Quantity qty;
	@Nullable AttributesKey attributesKey;
	@Nullable ProjectId projectId;
}
