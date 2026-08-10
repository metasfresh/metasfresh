package de.metas.deliveryplanning;

import de.metas.inout.InOutId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class DeliveryPlanningReceiptInfo
{
	@NonNull private final DeliveryPlanningId deliveryPlanningId;
	@Nullable private final OrderAndLineId purchaseOrderAndLineId;
	@NonNull private final ReceiptScheduleId receiptScheduleId;
	@NonNull private final OrgId orgId;
	boolean isB2B;

	@Nullable private InOutId receiptId;
	@Nullable private ColorId receivedStatusColorId;

	@Nullable
	public OrderId getPurchaseOrderId() {return purchaseOrderAndLineId != null ? purchaseOrderAndLineId.getOrderId() : null;}

	public boolean isReceived()
	{
		return getReceiptId() != null;
	}

	public void updateReceivedStatusColor(@NonNull final DeliveryStatusColorPalette colorPalette)
	{
		setReceivedStatusColorId(isReceived() ? colorPalette.getDeliveredColorId() : colorPalette.getNotDeliveredColorId());
	}
}
