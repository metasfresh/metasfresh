package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProductsCollector;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import lombok.NonNull;

import java.util.HashSet;

class ProductBasedAggregation
{
	@NonNull private final ProductBasedAggregationKey key;
	private boolean partiallyPickedBefore = false;
	@NonNull private final PickingJobCandidateProductsCollector productsCollector = new PickingJobCandidateProductsCollector();
	@NonNull private final HashSet<ShipmentScheduleAndJobScheduleId> scheduleIds = new HashSet<>();

	public ProductBasedAggregation(@NonNull final ProductBasedAggregationKey key)
	{
		this.key = key;
	}

	public void add(@NonNull final ScheduledPackageable item)
	{
		if (item.isPartiallyPickedOrDelivered())
		{
			partiallyPickedBefore = true;
		}

		productsCollector.collect(item);
		scheduleIds.add(item.getId());
	}

	public PickingJobCandidate toPickingJobCandidate()
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.PRODUCT)
				.warehouseTypeId(key.getWarehouseTypeId())
				.partiallyPickedBefore(partiallyPickedBefore)
				.products(productsCollector.toProducts())
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofCollection(scheduleIds))
				.build();
	}
}
