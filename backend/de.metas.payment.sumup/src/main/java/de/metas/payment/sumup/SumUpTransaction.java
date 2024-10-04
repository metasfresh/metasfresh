package de.metas.payment.sumup;

import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
@Jacksonized
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

	@NonNull ClientAndOrgId clientAndOrgId;
	int posOrderId;
	int posPaymentId;
}
