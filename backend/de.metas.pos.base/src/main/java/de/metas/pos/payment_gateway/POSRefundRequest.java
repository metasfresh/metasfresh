package de.metas.pos.payment_gateway;

import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSRefundRequest
{
	@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
	@NonNull POSOrderAndPaymentId posOrderAndPaymentId;
	/** SumUp's client transaction id of the card payment's charge; null if the payment names none (e.g. paid before it was stored) */
	@Nullable String sumUpClientTransactionId;
}
