package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class CreateQtyReservationRequest
{
	@NonNull OrderLineId orderLineId;
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@Nullable BPartnerId vendorBPartnerId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
	@NonNull UomId uomId;
	@NonNull QtyTU qtyTU;
	@NonNull BigDecimal qty;
	@Nullable AttributesKey attributesKey;
}
