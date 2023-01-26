package de.metas.deliveryplanning;

import de.metas.inout.InOutId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class DeliveryPlanningReceiptInfo
{
	@NonNull private final ReceiptScheduleId receiptScheduleId;
	@Nullable private InOutId receiptId;

	@Nullable private ColorId receivedStatusColorId;

	public boolean isReceived()
	{
		return getReceiptId() != null;
	}

	public void updateReceivedStatusColor(@NonNull final DeliveryStatusColorPalette colorPalette)
	{
		setReceivedStatusColorId(isReceived() ? colorPalette.getDeliveredColorId() : colorPalette.getNotDeliveredColorId());
	}
}
