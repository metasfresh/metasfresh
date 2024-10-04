package de.metas.pos.payment_gateway;

import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.POSOrderId;
import de.metas.pos.POSPaymentId;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSPaymentProcessRequest
{
	@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull POSOrderId posOrderId;
	@NonNull POSPaymentId posPaymentId;
	@NonNull Amount amount;
}
