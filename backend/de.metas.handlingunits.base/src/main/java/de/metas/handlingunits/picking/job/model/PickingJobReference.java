package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

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
	@NonNull @With PickingJobCandidateProducts products;
	@Nullable ShipmentScheduleAndJobScheduleIdSet scheduleIds;
	boolean isShipmentSchedulesLocked;
	@Nullable BPartnerLocationId handoverLocationId;

	public Set<ProductId> getProductIds() {return products.getProductIds();}

	@Nullable
	public ProductId getProductId() {return products.getSingleProductIdOrNull();}

	@Nullable
	public ITranslatableString getProductName() {return products.getSingleProductNameOrNull();}

	@Nullable
	public Quantity getQtyToDeliver() {return products.getSingleQtyToDeliverOrNull();}

	@Nullable
	public Quantity getQtyAvailableToPick() {return products.getSingleQtyAvailableToPickOrNull();}
}
