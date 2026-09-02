package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class OrderBasedAggregationKey
{
	@NonNull InstantAndOrgId preparationDate;
	@NonNull OrderId salesOrderId;
	@NonNull String salesOrderDocumentNo;
	@NonNull String customerName;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;

	public static OrderBasedAggregationKey of(@NonNull final ScheduledPackageable item)
	{
		return OrderBasedAggregationKey.builder()
				.preparationDate(item.getPreparationDate())
				.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.warehouseTypeId(item.getWarehouseTypeId())
				.build();
	}
}
