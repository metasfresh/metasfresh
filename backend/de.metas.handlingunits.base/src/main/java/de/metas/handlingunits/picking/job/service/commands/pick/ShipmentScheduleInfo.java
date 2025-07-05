package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class ShipmentScheduleInfo
{
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull WarehouseId warehouseId;
	@NonNull BPartnerId bpartnerId;
	@NonNull Optional<OrderLineId> salesOrderLineId;

	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;

	@NonNull I_M_ShipmentSchedule record;
}
