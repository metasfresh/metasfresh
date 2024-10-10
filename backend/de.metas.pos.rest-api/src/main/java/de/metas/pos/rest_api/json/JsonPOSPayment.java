package de.metas.pos.rest_api.json;

import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.remote.RemotePOSPayment;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSPayment
{
	@NonNull POSPaymentExternalId uuid;
	@NonNull POSPaymentMethod paymentMethod;
	@NonNull BigDecimal amount;

	@Nullable BigDecimal cashTenderedAmount;
	@Nullable BigDecimal cashGiveBackAmount;

	@Nullable JsonPOSPaymentStatus status;
	@Nullable String statusDetails;
	boolean allowCheckout;
	boolean allowDelete;
	boolean allowRefund;

	int hashCode;

	public static JsonPOSPayment of(@NonNull final POSPayment payment)
	{
		final POSPaymentProcessingStatus paymentProcessingStatus = payment.getPaymentProcessingStatus();

		return builder()
				.uuid(payment.getExternalId())
				.paymentMethod(payment.getPaymentMethod())
				.amount(payment.getAmount().toBigDecimal())
				.cashTenderedAmount(payment.getCashTenderedAmount().toBigDecimal())
				.cashGiveBackAmount(payment.getCashGiveBackAmount().toBigDecimal())
				.status(JsonPOSPaymentStatus.of(paymentProcessingStatus))
				.statusDetails(payment.getCardProcessingDetails() != null ? payment.getCardProcessingDetails().getSummary() : null)
				.allowCheckout(paymentProcessingStatus.isAllowCheckout())
				.allowDelete(paymentProcessingStatus.isAllowDelete())
				.allowRefund(payment.isAllowRefund())
				.hashCode(payment.hashCode())
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
