package de.metas.pos;

import de.metas.payment.sumup.SumUpConfigId;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSTerminalPaymentProcessorConfig
{
	@NonNull POSPaymentProcessorType type;
	@Nullable SumUpConfigId sumUpConfigId;
}
