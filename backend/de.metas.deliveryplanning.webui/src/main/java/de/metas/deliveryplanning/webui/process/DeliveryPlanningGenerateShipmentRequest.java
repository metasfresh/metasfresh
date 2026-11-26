package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.inout.InOutId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
class DeliveryPlanningGenerateShipmentRequest
{
	@NonNull DeliveryPlanningId deliveryPlanningId;
	@NonNull LocalDate deliveryDate;
	@NonNull BigDecimal qtyToShipBD;
	@Nullable InOutId b2bReceiptId;

	@Builder
	private DeliveryPlanningGenerateShipmentRequest(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final LocalDate deliveryDate,
			@NonNull final BigDecimal qtyToShipBD,
			@Nullable final InOutId b2bReceiptId)
	{
		if (qtyToShipBD.signum() <= 0)
		{
			throw new AdempiereException("Qty to ship shall be greater than zero");
		}

		this.deliveryPlanningId = deliveryPlanningId;
		this.deliveryDate = deliveryDate;
		this.qtyToShipBD = qtyToShipBD;
		this.b2bReceiptId = b2bReceiptId;
	}
}
