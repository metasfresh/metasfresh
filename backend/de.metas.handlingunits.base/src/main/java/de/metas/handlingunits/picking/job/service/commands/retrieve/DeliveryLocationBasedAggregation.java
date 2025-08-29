package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import lombok.NonNull;

import java.util.HashSet;

class DeliveryLocationBasedAggregation
{
	@NonNull private final DeliveryLocationBasedAggregationKey key;
	private boolean partiallyPickedBefore = false;
	@NonNull private final HashSet<ShipmentScheduleAndJobScheduleId> scheduleIds = new HashSet<>();

	public DeliveryLocationBasedAggregation(@NonNull final DeliveryLocationBasedAggregationKey key)
	{
		this.key = key;
	}

	public void add(@NonNull final ScheduledPackageable item)
	{
		this.partiallyPickedBefore = this.partiallyPickedBefore || item.isPartiallyPickedOrDelivered();
		this.scheduleIds.add(item.getId());
	}

	public PickingJobCandidate toPickingJobCandidate()
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.DELIVERY_LOCATION)
				.preparationDate(key.getPreparationDate())
				.customerName(key.getCustomerName())
				.deliveryBPLocationId(key.getDeliveryBPLocationId())
				.warehouseTypeId(key.getWarehouseTypeId())
				.partiallyPickedBefore(partiallyPickedBefore)
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofCollection(scheduleIds))
				.build();
	}
}
