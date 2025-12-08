package de.metas.pos.payment_gateway;

import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.POSOrderAndPaymentId;
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
	@NonNull POSOrderAndPaymentId posOrderAndPaymentId;
	@NonNull Amount amount;
}
