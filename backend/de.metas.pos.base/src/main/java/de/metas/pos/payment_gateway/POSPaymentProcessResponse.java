package de.metas.pos.payment_gateway;

import de.metas.error.AdIssueId;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class POSPaymentProcessResponse
{
	@NonNull POSPaymentProcessingStatus status;
	@NonNull POSTerminalPaymentProcessorConfig config;
	@Nullable POSCardReader cardReader;

	@Nullable String transactionId;
	/** SumUp's client transaction id of the checkout this response belongs to, if known */
	@Nullable String clientTransactionId;
	/** when the payment processor created the transaction this response belongs to, if known */
	@Nullable Instant transactionCreated;
	/**
	 * when metasfresh first saved the transaction this response belongs to, by metasfresh's own clock, if known: a transaction
	 * recorded before a card payment's current checkout started belongs to an earlier checkout of that payment
	 */
	@Nullable Instant transactionRecordedAt;
	@Nullable String summary;

	@Nullable String errorMessage;
	@Nullable AdIssueId errorId;

	/** true if the outcome of the call is unknown (the processor may or may not have acted on the request) */
	boolean isIndeterminate;

	public static POSPaymentProcessResponse error(
			final POSTerminalPaymentProcessorConfig config,
			final String errorMessage,
			final AdIssueId errorId)
	{
		return builder()
				.status(POSPaymentProcessingStatus.FAILED)
				.config(config)
				.errorMessage(errorMessage)
				.errorId(errorId)
				.build();
	}

	public static POSPaymentProcessResponse errorIndeterminate(
			final POSTerminalPaymentProcessorConfig config,
			final String errorMessage,
			final AdIssueId errorId,
			@Nullable final String clientTransactionId)
	{
		return builder()
				.status(POSPaymentProcessingStatus.FAILED)
				.config(config)
				.errorMessage(errorMessage)
				.errorId(errorId)
				.clientTransactionId(clientTransactionId)
				.isIndeterminate(true)
				.build();
	}
}
