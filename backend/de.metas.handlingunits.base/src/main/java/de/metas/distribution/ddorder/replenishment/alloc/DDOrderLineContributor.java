package de.metas.distribution.ddorder.replenishment.alloc;

import de.metas.picking.api.PickingJobScheduleId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

/**
 * One workstation assignment's share of a consolidated {@code DD_OrderLine}, persisted as a {@code DD_OrderLine_PickingJobSchedule} row by {@link DDOrderLineContributorRepository}.
 */
@Value(staticConstructor = "of")
public class DDOrderLineContributor
{
	@NonNull PickingJobScheduleId pickingJobScheduleId;
	@NonNull Quantity qty;
}
