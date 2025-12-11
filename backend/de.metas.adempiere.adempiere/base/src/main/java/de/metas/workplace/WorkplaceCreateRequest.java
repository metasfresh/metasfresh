package de.metas.workplace;

import de.metas.order.OrderPickingType;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class WorkplaceCreateRequest
{
	@NonNull String name;
	@NonNull WarehouseId warehouseId;
	@Nullable LocatorId pickFromLocatorId;
	@Nullable PickingSlotId pickingSlotId;
	@Nullable SeqNo seqNo;
	@Nullable OrderPickingType orderPickingType;
	int maxPickingJobs;
}
