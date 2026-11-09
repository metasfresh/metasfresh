package de.metas.pos.payment_gateway;

import de.metas.error.AdIssueId;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSPaymentProcessResponse
{
	@NonNull POSPaymentProcessingStatus status;
	@NonNull POSTerminalPaymentProcessorConfig config;
	@Nullable POSCardReader cardReader;

	@Nullable String transactionId;
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
			final AdIssueId errorId)
	{
		return builder()
				.status(POSPaymentProcessingStatus.FAILED)
				.config(config)
				.errorMessage(errorMessage)
				.errorId(errorId)
				.isIndeterminate(true)
				.build();
	}
}
