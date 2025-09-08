package de.metas.payment.sumup;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class SumUpPOSRef
{
	@Builder.Default int posOrderId = -1;
	@Builder.Default int posPaymentId = -1;
}
