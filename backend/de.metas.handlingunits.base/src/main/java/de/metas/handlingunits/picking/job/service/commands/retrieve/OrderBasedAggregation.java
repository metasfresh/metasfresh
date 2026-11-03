package de.metas.handlingunits.picking.job.service.commands.retrieve;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProductsCollector;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import lombok.NonNull;

import java.util.HashSet;
import java.util.function.ToIntFunction;

public class OrderBasedAggregation
{
	@NonNull private final OrderBasedAggregationKey key;
	private boolean partiallyPickedBefore = false;
	@NonNull private final PickingJobCandidateProductsCollector productsCollector = new PickingJobCandidateProductsCollector();
	@NonNull private final HashSet<ShipmentScheduleAndJobScheduleId> scheduleIds = new HashSet<>();

	public OrderBasedAggregation(@NonNull final OrderBasedAggregationKey key)
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

	public ImmutableSet<OrderAndLineId> getSalesOrderAndLineIds() {return productsCollector.getSalesOrderAndLineIds();}

	public PickingJobCandidate toPickingJobCandidate(@NonNull final ToIntFunction<OrderAndLineId> salesOrderLineSeqNoProvider)
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.preparationDate(key.getPreparationDate())
				.salesOrderId(key.getSalesOrderId())
				.salesOrderDocumentNo(key.getSalesOrderDocumentNo())
				.customerName(key.getCustomerName())
				.deliveryBPLocationId(key.getDeliveryBPLocationId())
				.warehouseTypeId(key.getWarehouseTypeId())
				.partiallyPickedBefore(partiallyPickedBefore)
				.products(productsCollector.toProductsOrderedBySalesOrderLine(salesOrderLineSeqNoProvider))
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofCollection(scheduleIds))
				.build();

	}

}
