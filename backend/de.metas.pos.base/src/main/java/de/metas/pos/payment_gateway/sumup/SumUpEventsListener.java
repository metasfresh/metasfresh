package de.metas.pos.payment_gateway.sumup;

import de.metas.Profiles;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import de.metas.pos.POSOrderId;
import de.metas.pos.POSOrdersService;
import de.metas.pos.POSPaymentId;
import de.metas.pos.POSPaymentProcessingStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
class SumUpEventsListener implements SumUpTransactionStatusChangedListener
{
	@NonNull private final POSOrdersService posOrdersService;

	@Override
	public void onStatusChanged(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		final POSOrderId posOrderId = POSOrderId.ofRepoIdOrNull(event.getPosOrderId());
		if (posOrderId == null)
		{
			return;
		}

		final POSPaymentId posPaymentId = POSPaymentId.ofRepoIdOrNull(event.getPosPaymentId());
		if (posPaymentId == null)
		{
			return;
		}

		final POSPaymentProcessingStatus paymentProcessingStatus = SumUpUtils.toResponseStatus(event.getStatusNew());
		posOrdersService.updatePaymentStatusFromRemoteAndTryCompleteOrder(posOrderId, posPaymentId, paymentProcessingStatus);
	}
}
