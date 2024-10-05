package de.metas.pos.payment_gateway.sumup;

import de.metas.Profiles;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSOrdersService;
import de.metas.pos.POSPaymentProcessingStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
class SumUpEventsListener implements SumUpTransactionStatusChangedListener
{
	@NonNull private final POSOrdersService posOrdersService;

	@Override
	public void onStatusChanged(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		final POSOrderAndPaymentId posOrderAndPaymentId = extractPOSOrderAndPaymentId(event);
		if (posOrderAndPaymentId == null)
		{
			return;
		}

		final POSPaymentProcessingStatus paymentProcessingStatus = SumUpUtils.toResponseStatus(event.getStatusNew(), event.isRefundedNew());
		posOrdersService.updatePaymentStatusFromRemoteAndTryCompleteOrder(posOrderAndPaymentId, paymentProcessingStatus);
	}

	@Nullable
	private static POSOrderAndPaymentId extractPOSOrderAndPaymentId(final @NonNull SumUpTransactionStatusChangedEvent event)
	{
		final SumUpPOSRef posRef = event.getPosRef();
		return posRef == null ? null : SumUpUtils.toPOSOrderAndPaymentId(posRef);
	}
}
