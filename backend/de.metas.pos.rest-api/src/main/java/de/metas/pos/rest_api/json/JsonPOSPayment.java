package de.metas.pos.rest_api.json;

import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.remote.RemotePOSPayment;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSPayment
{
	@NonNull String uuid;
	@NonNull POSPaymentMethod paymentMethod;
	@NonNull BigDecimal amount;

	public static JsonPOSPayment of(@NonNull final POSPayment payment)
	{
		return builder()
				.uuid(payment.getExternalId())
				.paymentMethod(payment.getPaymentMethod())
				.amount(payment.getAmount())
				.build();
	}

	RemotePOSPayment toRemotePOSPayment()
	{
		return RemotePOSPayment.builder()
				.uuid(uuid)
				.paymentMethod(paymentMethod)
				.amount(amount)
				.build();
	}
}
