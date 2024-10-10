package de.metas.pos;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSPaymentCardProcessingDetails
{
	@NonNull POSTerminalPaymentProcessorConfig config;
	@Nullable String transactionId;
	@Nullable String summary;
}
