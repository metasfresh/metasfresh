package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Value
@Builder
public class PickingJobReference
{
	@NonNull PickingJobId pickingJobId;
	@NonNull PickingJobAggregationType aggregationType;
	@Nullable String salesOrderDocumentNo;
	@Nullable OrderId salesOrderId;
	@Nullable BPartnerId customerId;
	@Nullable String customerName;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable ZonedDateTime deliveryDate;
	@Nullable ZonedDateTime preparationDate;
	@Nullable ProductId productId;
	@Nullable ITranslatableString productName;
	@Nullable Quantity qtyToDeliver;
	@NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
	boolean isShipmentSchedulesLocked;
	@Nullable BPartnerLocationId handoverLocationId;
}
