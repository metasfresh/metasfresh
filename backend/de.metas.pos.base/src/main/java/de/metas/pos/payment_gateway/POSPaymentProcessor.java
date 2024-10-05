package de.metas.pos.payment_gateway;

import lombok.NonNull;

public interface POSPaymentProcessor
{
	POSPaymentProcessorType getType();

	POSPaymentProcessResponse process(@NonNull POSPaymentProcessRequest request);

	POSRefundResponse refund(@NonNull POSRefundRequest request);
}
