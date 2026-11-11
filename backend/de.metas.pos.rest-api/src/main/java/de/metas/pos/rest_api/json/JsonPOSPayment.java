package de.metas.pos.rest_api.json;

import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentCardProcessingDetails;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.payment_gateway.POSCardReader;
import de.metas.pos.remote.RemotePOSPayment;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

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

	@Nullable String cardReaderName;

	@Nullable JsonPOSPaymentStatus status;
	@Nullable String statusDetails;
	boolean allowCheckout;
	boolean allowDelete;
	boolean allowRefund;

	/**
	 * For a card payment whose result is unknown: the instant from which the cashier may release it (the start of its
	 * current checkout plus the configured wait); null for any other payment.
	 */
	@Nullable Instant releasableAt;

	int hashCode;

	/**
	 * @param releasableAt see {@link #releasableAt}
	 */
	public static JsonPOSPayment of(@NonNull final POSPayment payment, @Nullable final Instant releasableAt)
	{
		final POSPaymentProcessingStatus paymentProcessingStatus = payment.getPaymentProcessingStatus();

		return builder()
				.uuid(payment.getExternalId())
				.paymentMethod(payment.getPaymentMethod())
				.amount(payment.getAmount().toBigDecimal())
				.cashTenderedAmount(payment.getCashTenderedAmount().toBigDecimal())
				.cashGiveBackAmount(payment.getCashGiveBackAmount().toBigDecimal())
				.cardReaderName(extractCardReaderName(payment))
				.status(JsonPOSPaymentStatus.of(paymentProcessingStatus))
				.statusDetails(payment.getCardProcessingDetails() != null ? payment.getCardProcessingDetails().getSummary() : null)
				.allowCheckout(paymentProcessingStatus.isAllowCheckout())
				.allowDelete(payment.isAllowDelete())
				.allowRefund(payment.isAllowRefund())
				.releasableAt(releasableAt)
				.hashCode(payment.hashCode())
				.build();
	}

	@Nullable
	private static String extractCardReaderName(@NonNull final POSPayment payment)
	{
		final POSPaymentCardProcessingDetails cardProcessingDetails = payment.getCardProcessingDetails();
		if (cardProcessingDetails == null)
		{
			return null;
		}

		final POSCardReader cardReader = cardProcessingDetails.getCardReader();
		if (cardReader == null)
		{
			return null;
		}

		return cardReader.getName();
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
