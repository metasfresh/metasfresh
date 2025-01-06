package de.metas.payment.sumup;

import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SumUpCardReaderCheckoutRequest
{
	@NonNull SumUpConfigId configId;
	@NonNull SumUpCardReaderExternalId cardReaderId;

	@NonNull Amount amount;
	@Nullable String description;
	@Nullable String callbackUrl;

	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable SumUpPOSRef posRef;
}
