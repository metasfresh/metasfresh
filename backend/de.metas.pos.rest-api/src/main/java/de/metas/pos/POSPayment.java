package de.metas.pos;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class POSPayment
{
	@NonNull String externalId;
	@NonNull POSPaymentMethod paymentMethod;
	@NonNull BigDecimal amount;
}
