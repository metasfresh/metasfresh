package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.forex.ForexContractRef;
import de.metas.inout.InOutId;
import de.metas.process.PInstanceId;
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
	@NonNull PInstanceId adPInstanceId;
	@NonNull DeliveryPlanningId deliveryPlanningId;
	@NonNull LocalDate deliveryDate;
	@NonNull BigDecimal qtyToShipBD;
	@Nullable ForexContractRef forexContractRef;
	@Nullable InOutId b2bReceiptId;

	@Builder
	private DeliveryPlanningGenerateShipmentRequest(
			@NonNull final PInstanceId adPInstanceId,
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final LocalDate deliveryDate,
			@NonNull final BigDecimal qtyToShipBD,
			@Nullable final ForexContractRef forexContractRef,
			@Nullable final InOutId b2bReceiptId)
	{
		if (qtyToShipBD.signum() <= 0)
		{
			throw new AdempiereException("Qty to ship shall be greater than zero");
		}

		this.adPInstanceId = adPInstanceId;
		this.deliveryPlanningId = deliveryPlanningId;
		this.deliveryDate = deliveryDate;
		this.qtyToShipBD = qtyToShipBD;
		this.forexContractRef = forexContractRef;
		this.b2bReceiptId = b2bReceiptId;
	}
}
