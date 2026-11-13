package de.metas.pos;

import lombok.NonNull;
import lombok.Value;

/** A card {@code C_POS_Payment} reduced to its id and raw status code. */
@Value
public class POSCardPaymentStatus
{
	@NonNull POSPaymentId paymentId;
	@NonNull String statusCode;
}
