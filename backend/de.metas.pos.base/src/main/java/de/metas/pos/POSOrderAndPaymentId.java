package de.metas.pos;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class POSOrderAndPaymentId
{
	@NonNull POSOrderId orderId;
	@NonNull POSPaymentId paymentId;

	@Nullable
	public static POSOrderAndPaymentId ofRepoIdsOrNull(final int posOrderRepoId, final int posPaymentRepoId)
	{
		final POSOrderId posOrderId = POSOrderId.ofRepoIdOrNull(posOrderRepoId);
		if (posOrderId == null)
		{
			return null;
		}

		final POSPaymentId posPaymentId = POSPaymentId.ofRepoIdOrNull(posPaymentRepoId);
		if (posPaymentId == null)
		{
			return null;
		}

		return of(posOrderId, posPaymentId);
	}
}
