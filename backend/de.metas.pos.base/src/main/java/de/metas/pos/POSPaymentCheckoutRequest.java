package de.metas.pos;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class POSPaymentCheckoutRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull POSOrderExternalId posOrderExternalId;
	@NonNull POSPaymentExternalId posPaymentExternalId;

	@NonNull UserId userId;

	@Nullable BigDecimal cardPayAmount;
	@Nullable BigDecimal cashTenderedAmount;
}
