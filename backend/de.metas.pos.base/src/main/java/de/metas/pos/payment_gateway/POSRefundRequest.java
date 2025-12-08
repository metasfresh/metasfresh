package de.metas.pos.payment_gateway;

import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSRefundRequest
{
	@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
	@NonNull POSOrderAndPaymentId posOrderAndPaymentId;
}
