package de.metas.pos.remote;

import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class RemotePOSPayment
{
	@NonNull POSPaymentExternalId uuid;
	@NonNull POSPaymentMethod paymentMethod;
	@NonNull BigDecimal amount;
}
