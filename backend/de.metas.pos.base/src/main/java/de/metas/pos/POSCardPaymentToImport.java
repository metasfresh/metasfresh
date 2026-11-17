package de.metas.pos;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

/**
 * A card payment whose SumUp transaction the background sweep is to import (see {@link POSOrdersService#getCardPaymentsToImport()})
 */
@Value(staticConstructor = "of")
public class POSCardPaymentToImport
{
	@NonNull POSOrderAndPaymentId id;

	/**
	 * When its checkout started, or, without a recorded checkout start, when the payment was created
	 */
	@NonNull Instant checkoutStartedAt;
}
