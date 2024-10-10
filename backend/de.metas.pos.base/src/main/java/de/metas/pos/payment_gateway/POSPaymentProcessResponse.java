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
}
