package de.metas.paypalplus.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentAmount
{
	@Builder.Default private long created = System.currentTimeMillis();

	String currency;

	String total;

	PaymentDetails paymentDetails;
}
