package org.adempiere.inout.util;

import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder
public class ShipmentScheduleQtyOnHandSegment
{
	@NonNull WarehouseId warehouseId;
	@NonNull ProductId productId;
	@Nullable PPOrderId pickFromManufacturingOrderId;
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@NonNull TableRecordReference sourceRef;
}
