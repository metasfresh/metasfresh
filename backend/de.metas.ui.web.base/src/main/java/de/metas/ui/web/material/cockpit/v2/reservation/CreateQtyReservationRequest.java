package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class CreateQtyReservationRequest
{
	@NonNull OrderLineId orderLineId;
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@Nullable Integer vendorBPartnerId;
	@NonNull String supplyType;
	@Nullable java.sql.Timestamp datePromised;
	@NonNull UomId uomId;
	@NonNull BigDecimal qtyTU;
	@NonNull BigDecimal qty;
	@Nullable String attributesKey;

	public static final String SUPPLY_TYPE_ON_HAND = "OH";
	public static final String SUPPLY_TYPE_PLANNED_SUPPLY = "PS";
}
