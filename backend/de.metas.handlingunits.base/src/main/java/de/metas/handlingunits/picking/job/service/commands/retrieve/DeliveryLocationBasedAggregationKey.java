package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;

@Value
@Builder
class DeliveryLocationBasedAggregationKey
{
	@NonNull OrgId orgId;
	@Nullable InstantAndOrgId preparationDate;
	@Nullable String customerName;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;

	public static DeliveryLocationBasedAggregationKey of(@NonNull final ScheduledPackageable item)
	{
		return builder()
				.orgId(item.getOrgId())
				.preparationDate(item.getPreparationDate())
				.customerName(item.getCustomerName())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.warehouseTypeId(item.getWarehouseTypeId())
				.build();
	}
}
