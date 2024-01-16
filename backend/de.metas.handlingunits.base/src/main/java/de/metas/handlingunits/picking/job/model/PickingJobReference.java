package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingJobReference
{
	@NonNull PickingJobId pickingJobId;
	@NonNull String salesOrderDocumentNo;
	@NonNull String customerName;
	@NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
}
