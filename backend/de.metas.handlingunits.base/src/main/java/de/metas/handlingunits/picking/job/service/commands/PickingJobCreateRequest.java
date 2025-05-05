package de.metas.handlingunits.picking.job.service.commands;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingJobCreateRequest
{
	@NonNull UserId pickerId;

	@NonNull OrderId salesOrderId;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	boolean isAllowPickingAnyHU;
}
