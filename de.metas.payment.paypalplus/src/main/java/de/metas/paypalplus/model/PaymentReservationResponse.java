package de.metas.paypalplus.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PaymentReservationResponse
{
	@NonNull
	String paymentId;

	@NonNull
	String authorizationId;

	@NonNull
	String paymentState;
}
