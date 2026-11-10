package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyCode;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A card charge is sent only by the till's card checkout, outside any transaction: completing an order never sends one.
 */
class POSOrderProcessingServicesTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final POSTerminalPaymentProcessorConfig SUMUP_CONFIG = POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build();

	private POSPaymentProcessorService paymentProcessorService;
	private POSOrderProcessingServices services;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final POSTerminal terminal = mock(POSTerminal.class);
		when(terminal.getCashJournalIdNotNull()).thenReturn(POSCashJournalId.ofRepoId(1));
		when(terminal.getPaymentProcessorConfigNotNull()).thenReturn(SUMUP_CONFIG);
		final POSTerminalService terminalService = mock(POSTerminalService.class);
		when(terminalService.getPOSTerminalById(any())).thenReturn(terminal);

		final MoneyService moneyService = mock(MoneyService.class);
		when(moneyService.getCurrencyCodeByCurrencyId(any())).thenReturn(CurrencyCode.EUR);

		paymentProcessorService = mock(POSPaymentProcessorService.class);
		services = new POSOrderProcessingServices(moneyService, terminalService, mock(POSCashJournalService.class), paymentProcessorService);
	}

	private static POSOrder orderWithNewCardPayment()
	{
		return POSOrder.builder()
				.externalId(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.localId(POSOrderId.ofRepoId(10))
				.status(POSOrderStatus.WaitingPayment)
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.cashbookId(BankAccountId.ofRepoId(1))
				.cashierId(UserId.ofRepoId(100))
				.date(Instant.parse("2026-11-10T10:00:00Z"))
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(0, 1))
						.countryId(CountryId.ofRepoId(101))
						.build())
				.currencyId(EUR)
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.payments(ImmutableList.of(POSPayment.builder()
						.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
						.localId(POSPaymentId.ofRepoId(20))
						.paymentMethod(POSPaymentMethod.CARD)
						.amount(Money.of(new BigDecimal("10.00"), EUR))
						.paymentProcessingStatus(POSPaymentProcessingStatus.NEW)
						.build()))
				.build();
	}

	@Test
	void completion_neverSendsANewCardPayment()
	{
		when(paymentProcessorService.processPayment(any())).thenReturn(POSPaymentProcessResponse.builder()
				.status(POSPaymentProcessingStatus.PENDING)
				.config(SUMUP_CONFIG)
				.build());
		final POSOrder order = orderWithNewCardPayment();

		services.processPOSPayments(order);

		verify(paymentProcessorService, never()).processPayment(any());
		assertThat(order.getAllPayments().get(0).getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.NEW);
		// so the completion leaves the order waiting for payment
		assertThat(order.isPaymentsProcessedSuccessfully()).isFalse();
	}

	@Test
	void sendCardPayment_failureBeforeTheProcessorSends_isADefiniteFailure()
	{
		// e.g. no payment processor registered for the configured type: nothing was sent to the card reader
		when(paymentProcessorService.processPayment(any())).thenThrow(new AdempiereException("No payment processor registered for type SumUp"));
		final POSOrder order = orderWithNewCardPayment();

		final POSPaymentProcessResponse response = services.sendCardPayment(order.getAllPayments().get(0), order);

		assertThat(response.getStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(response.isIndeterminate()).isFalse();
		assertThat(response.getErrorMessage()).contains("No payment processor registered");
	}
}
