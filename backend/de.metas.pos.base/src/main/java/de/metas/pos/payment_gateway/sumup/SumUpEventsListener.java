package de.metas.pos.payment_gateway.sumup;

import de.metas.Profiles;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSOrdersService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Routes SumUp status changes by the transaction's POS reference: a card settlement of an invoice goes to
 * {@link POSInvoiceCardSettlementService}, a card payment of a POS order (card sale) to {@link POSOrdersService}.
 */
@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
class SumUpEventsListener implements SumUpTransactionStatusChangedListener
{
	@NonNull private final POSOrdersService posOrdersService;
	@NonNull private final POSInvoiceCardSettlementService cardSettlementService;

	@Override
	public void onStatusChanged(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		final SumUpPOSRef posRef = event.getPosRef();
		if (posRef == null)
		{
			return;
		}

		if (posRef.getPosInvoiceCardSettlementId() > 0)
		{
			cardSettlementService.onSumUpTransactionChanged(POSInvoiceCardSettlementId.ofRepoId(posRef.getPosInvoiceCardSettlementId()));
			return;
		}

		final POSOrderAndPaymentId posOrderAndPaymentId = SumUpUtils.toPOSOrderAndPaymentId(posRef);
		if (posOrderAndPaymentId == null)
		{
			return;
		}

		posOrdersService.updatePaymentStatusFromRemoteAndTryCompleteOrder(posOrderAndPaymentId, SumUpUtils.extractProcessResponse(event.getTrx()));
	}
}
