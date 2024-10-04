package de.metas.payment.sumup;

import de.metas.currency.Amount;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class SumUpTransaction
{
	@NonNull SumUpConfigId configId;
	@NonNull String externalId;
	@NonNull SumUpClientTransactionId clientTransactionId;
	@NonNull SumUpMerchantCode merchantCode;
	@NonNull Instant timestamp;

	@NonNull SumUpTransactionStatus status;
	@NonNull Amount amount;

	@Nullable String json;

	int posOrderId;
	int posPaymentId;
}
