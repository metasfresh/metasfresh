package de.metas.pos.payment_gateway.sumup;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpCallException;
import de.metas.payment.sumup.SumUpCardReaderCheckoutRequest;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpService;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSOrderId;
import de.metas.pos.POSPaymentId;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SumUpPaymentProcessorTest
{
	private static final SumUpConfigId CONFIG_ID = SumUpConfigId.ofRepoId(1);

	private SumUpService sumUpService;
	private SumUpPaymentProcessor processor;
	private POSTerminalPaymentProcessorConfig terminalConfig;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sumUpService = mock(SumUpService.class);
		final SumUpConfig sumUpConfig = mock(SumUpConfig.class);
		when(sumUpConfig.getId()).thenReturn(CONFIG_ID);
		when(sumUpConfig.getDefaultCardReaderExternalIdNotNull()).thenReturn(SumUpCardReaderExternalId.ofString("reader-1"));
		when(sumUpService.getConfig(CONFIG_ID)).thenReturn(sumUpConfig);

		terminalConfig = POSTerminalPaymentProcessorConfig.builder()
				.type(POSPaymentProcessorType.SumUp)
				.sumUpConfigId(CONFIG_ID)
				.build();

		processor = new SumUpPaymentProcessor(sumUpService);
	}

	private POSPaymentProcessRequest.POSPaymentProcessRequestBuilder requestBuilder()
	{
		return POSPaymentProcessRequest.builder()
				.paymentProcessorConfig(terminalConfig)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.amount(Amount.of(new BigDecimal("12.50"), CurrencyCode.EUR));
	}

	private SumUpCardReaderCheckoutRequest captureCheckoutRequest()
	{
		final ArgumentCaptor<SumUpCardReaderCheckoutRequest> captor = ArgumentCaptor.forClass(SumUpCardReaderCheckoutRequest.class);
		verify(sumUpService).cardReaderCheckout(captor.capture());
		return captor.getValue();
	}

	@Test
	void settlementRequest_sendsSettlementPosRef_andDescription()
	{
		when(sumUpService.cardReaderCheckout(any())).thenThrow(new AdempiereException("stop here"));

		processor.process(requestBuilder()
				.posInvoiceCardSettlementId(POSInvoiceCardSettlementId.ofRepoId(77))
				.description("Invoice 1234")
				.build());

		final SumUpCardReaderCheckoutRequest checkout = captureCheckoutRequest();
		assertThat(checkout.getPosRef().getPosInvoiceCardSettlementId()).isEqualTo(77);
		assertThat(checkout.getPosRef().getPosOrderId()).isEqualTo(-1);
		assertThat(checkout.getPosRef().getPosPaymentId()).isEqualTo(-1);
		assertThat(checkout.getDescription()).isEqualTo("Invoice 1234");
	}

	@Test
	void orderRequest_unchanged()
	{
		when(sumUpService.cardReaderCheckout(any())).thenThrow(new AdempiereException("stop here"));

		processor.process(requestBuilder()
				.posOrderAndPaymentId(POSOrderAndPaymentId.of(POSOrderId.ofRepoId(10), POSPaymentId.ofRepoId(20)))
				.build());

		final SumUpCardReaderCheckoutRequest checkout = captureCheckoutRequest();
		assertThat(checkout.getPosRef().getPosOrderId()).isEqualTo(10);
		assertThat(checkout.getPosRef().getPosPaymentId()).isEqualTo(20);
		assertThat(checkout.getPosRef().getPosInvoiceCardSettlementId()).isEqualTo(-1);
		assertThat(checkout.getDescription()).isNull();
	}

	@Test
	void definiteRejection_mapsToErrorNotIndeterminate()
	{
		when(sumUpService.cardReaderCheckout(any())).thenThrow(new SumUpCallException(new IOException("400"), true));

		final POSPaymentProcessResponse response = processor.process(requestBuilder()
				.posInvoiceCardSettlementId(POSInvoiceCardSettlementId.ofRepoId(77))
				.build());

		assertThat(response.getStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(response.isIndeterminate()).isFalse();
	}

	@Test
	void indeterminateFailure_mapsToIndeterminate()
	{
		when(sumUpService.cardReaderCheckout(any())).thenThrow(new SumUpCallException(new IOException("timeout"), false));

		final POSPaymentProcessResponse response = processor.process(requestBuilder()
				.posInvoiceCardSettlementId(POSInvoiceCardSettlementId.ofRepoId(77))
				.build());

		assertThat(response.getStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(response.isIndeterminate()).isTrue();
	}

	@Test
	void configErrorBeforeCall_mapsToErrorNotIndeterminate()
	{
		when(sumUpService.getConfig(CONFIG_ID)).thenThrow(new AdempiereException("no default card reader"));

		final POSPaymentProcessResponse response = processor.process(requestBuilder()
				.posInvoiceCardSettlementId(POSInvoiceCardSettlementId.ofRepoId(77))
				.build());

		assertThat(response.getStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(response.isIndeterminate()).isFalse();
		verify(sumUpService, never()).cardReaderCheckout(any());
	}
}
