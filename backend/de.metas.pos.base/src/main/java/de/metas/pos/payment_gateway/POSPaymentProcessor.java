package de.metas.pos.payment_gateway;

import lombok.NonNull;

public interface POSPaymentProcessor
{
	POSPaymentProcessorType getType();

	POSPaymentProcessResponse process(@NonNull POSPaymentProcessRequest request);

	POSPaymentProcessResponse refund(@NonNull POSRefundRequest request);
}
