package de.metas.pos.payment_gateway;

import de.metas.error.AdIssueId;
import de.metas.pos.POSPaymentProcessingStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSPaymentProcessResponse
{
	@NonNull POSPaymentProcessingStatus status;
	@Nullable String errorMessage;
	@Nullable AdIssueId errorId;

	public static POSPaymentProcessResponse error(final String errorMessage, final AdIssueId errorId)
	{
		return builder()
				.status(POSPaymentProcessingStatus.FAILED)
				.errorMessage(errorMessage)
				.errorId(errorId)
				.build();
	}
}
