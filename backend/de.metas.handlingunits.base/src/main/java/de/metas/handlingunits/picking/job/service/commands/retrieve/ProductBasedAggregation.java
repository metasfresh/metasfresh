package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.quantity.Quantity;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashSet;

class ProductBasedAggregation
{
	@NonNull private final ProductBasedAggregationKey key;
	@Nullable private ITranslatableString productName;
	@NonNull private Quantity qtyToDeliver;
	private boolean partiallyPickedBefore = false;
	@NonNull private final HashSet<ShipmentScheduleAndJobScheduleId> scheduleIds = new HashSet<>();

	public ProductBasedAggregation(@NonNull final ProductBasedAggregationKey key)
	{
		this.key = key;
		this.qtyToDeliver = Quantity.zero(key.getUom());
	}

	public void add(@NonNull final ScheduledPackageable item)
	{
		if (productName == null)
		{
			productName = TranslatableStrings.anyLanguage(item.getProductName());
		}

		this.qtyToDeliver = this.qtyToDeliver.add(item.getQtyToDeliver());

		if (item.isPartiallyPickedOrDelivered())
		{
			partiallyPickedBefore = true;
		}

		scheduleIds.add(item.getId());
	}

	public PickingJobCandidate toPickingJobCandidate()
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.PRODUCT)
				.warehouseTypeId(key.getWarehouseTypeId())
				.partiallyPickedBefore(partiallyPickedBefore)
				.productId(key.getProductId())
				.productName(productName)
				.qtyToDeliver(qtyToDeliver)
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofCollection(scheduleIds))
				.build();
	}
}
