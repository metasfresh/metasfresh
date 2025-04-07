package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.inout.ShipmentScheduleId;
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

	@NonNull PickingJobAggregationType aggregationType;
	@Nullable OrderId salesOrderId;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	boolean isAllowPickingAnyHU;
	@Nullable ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
}
