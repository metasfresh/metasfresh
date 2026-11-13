package de.metas.pos.payment_gateway.sumup;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSOrdersService;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class SumUpEventsListenerTest
{
	private POSOrdersService posOrdersService;
	private POSInvoiceCardSettlementService cardSettlementService;
	private SumUpEventsListener listener;

	@BeforeEach
	void beforeEach()
	{
		posOrdersService = mock(POSOrdersService.class);
		cardSettlementService = mock(POSInvoiceCardSettlementService.class);
		listener = new SumUpEventsListener(posOrdersService, cardSettlementService);
	}

	private static SumUpTransactionStatusChangedEvent successfulEvent(final SumUpPOSRef posRef)
	{
		return SumUpTransactionStatusChangedEvent.ofNewTransaction(SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString(UUID.randomUUID().toString()))
				.clientTransactionId(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
				.merchantCode(SumUpMerchantCode.ofString("merchant code"))
				.timestamp(SystemTime.asInstant())
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.amount(Amount.of("16.07", CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1000000))
				.posRef(posRef)
				.build());
	}

	/**
	 * Card sales are unchanged: an order + payment reference still goes to the POS orders service only.
	 */
	@Test
	void orderRef_routesToPOSOrdersService()
	{
		listener.onStatusChanged(successfulEvent(SumUpPOSRef.builder().posOrderId(31).posPaymentId(32).build()));

		final ArgumentCaptor<POSOrderAndPaymentId> orderAndPaymentId = ArgumentCaptor.forClass(POSOrderAndPaymentId.class);
		final ArgumentCaptor<POSPaymentProcessResponse> response = ArgumentCaptor.forClass(POSPaymentProcessResponse.class);
		verify(posOrdersService).updatePaymentStatusFromRemoteAndTryCompleteOrder(orderAndPaymentId.capture(), response.capture());
		assertThat(orderAndPaymentId.getValue().getOrderId().getRepoId()).isEqualTo(31);
		assertThat(orderAndPaymentId.getValue().getPaymentId().getRepoId()).isEqualTo(32);
		assertThat(response.getValue().getStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		verify(cardSettlementService, never()).onSumUpTransactionChanged(any());
	}

	@Test
	void settlementRef_routesToCardSettlementService()
	{
		listener.onStatusChanged(successfulEvent(SumUpPOSRef.builder().posInvoiceCardSettlementId(41).build()));

		verify(cardSettlementService).onSumUpTransactionChanged(POSInvoiceCardSettlementId.ofRepoId(41));
		verify(posOrdersService, never()).updatePaymentStatusFromRemoteAndTryCompleteOrder(any(), any());
	}
}
