package de.metas.payment.sumup;

import de.metas.currency.Amount;
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
	
	int posOrderId;
	int posPaymentId;
}
