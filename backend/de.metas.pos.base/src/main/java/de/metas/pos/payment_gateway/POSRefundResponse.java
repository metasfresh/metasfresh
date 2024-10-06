package de.metas.pos.payment_gateway;

import de.metas.pos.POSPaymentProcessingStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class POSRefundResponse
{
	POSPaymentProcessingStatus status;
}
