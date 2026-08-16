package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.Instant;

@Value
class DeliveryPlanningGenerateReceiptRequest
{
	@NonNull DeliveryPlanningId deliveryPlanningId;
	@NonNull Instant receiptDate;
	@NonNull BigDecimal qtyToReceiveBD;

	@Builder
	private DeliveryPlanningGenerateReceiptRequest(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Instant receiptDate,
			@NonNull final BigDecimal qtyToReceiveBD)
	{
		if (qtyToReceiveBD.signum() <= 0)
		{
			throw new AdempiereException("Qty to receive shall be greater than zero");
		}

		this.deliveryPlanningId = deliveryPlanningId;
		this.receiptDate = receiptDate;
		this.qtyToReceiveBD = qtyToReceiveBD;
	}
}
