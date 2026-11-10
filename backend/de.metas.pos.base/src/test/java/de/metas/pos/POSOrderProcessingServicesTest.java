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
import de.metas.organization.OrgId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
	private POSCashJournalService journalService;
	private POSCashJournalId journalId;
	private POSOrderProcessingServices services;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		journalService = new POSCashJournalService(new POSCashJournalRepository());
		journalId = journalService.newJournal(POSCashJournalCreateRequest.builder()
						.orgId(OrgId.ofRepoId(1))
						.terminalId(POSTerminalId.ofRepoId(1))
						.dateTrx(Instant.parse("2026-11-10T08:00:00Z"))
						.cashBeginningBalance(Money.of(100, EUR))
						.build())
				.getId();

		final POSTerminal terminal = mock(POSTerminal.class);
		when(terminal.getCashJournalIdNotNull()).thenReturn(journalId);
		when(terminal.getPaymentProcessorConfigNotNull()).thenReturn(SUMUP_CONFIG);
		final POSTerminalService terminalService = mock(POSTerminalService.class);
		when(terminalService.getPOSTerminalById(any())).thenReturn(terminal);

		final MoneyService moneyService = mock(MoneyService.class);
		when(moneyService.getCurrencyCodeByCurrencyId(any())).thenReturn(CurrencyCode.EUR);

		paymentProcessorService = mock(POSPaymentProcessorService.class);
		services = new POSOrderProcessingServices(moneyService, terminalService, journalService, paymentProcessorService);
	}

	private static POSOrder orderWithNewCardPayment()
	{
		return order(ImmutableList.of(POSPayment.builder()
				.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.localId(POSPaymentId.ofRepoId(20))
				.paymentMethod(POSPaymentMethod.CARD)
				.amount(Money.of(new BigDecimal("10.00"), EUR))
				.paymentProcessingStatus(POSPaymentProcessingStatus.NEW)
				.build()));
	}

	private static POSOrder order(final ImmutableList<POSPayment> payments)
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
				.payments(payments)
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
		doThrow(new AdempiereException("No payment processor registered for type SumUp"))
				.when(paymentProcessorService).assertProcessorRegistered(POSPaymentProcessorType.SumUp);
		final POSOrder order = orderWithNewCardPayment();

		final POSPaymentProcessResponse response = services.sendCardPayment(order.getAllPayments().get(0), order);

		assertThat(response.getStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(response.isIndeterminate()).isFalse();
		assertThat(response.getErrorMessage()).contains("No payment processor registered");
		verify(paymentProcessorService, never()).processPayment(any());
	}

	@Test
	void sendCardPayment_failureOnceTheProcessorWasCalled_isNotADefiniteFailure()
	{
		// e.g. SumUp timed out (the card may be charged), and then the processor's own error handling failed on the database:
		// the outcome is unknown, so it must not be answered as a definite failure (that would make the payment retryable)
		when(paymentProcessorService.processPayment(any())).thenThrow(new DBException("database unavailable while recording the SumUp timeout"));
		final POSOrder order = orderWithNewCardPayment();

		assertThatThrownBy(() -> services.sendCardPayment(order.getAllPayments().get(0), order))
				.hasMessageContaining("database unavailable");
	}

	private static POSPayment payment(final int id, final POSPaymentMethod method, final String amount, final POSPaymentProcessingStatus status)
	{
		return POSPayment.builder()
				.externalId(POSPaymentExternalId.ofString("payment-" + id))
				.localId(POSPaymentId.ofRepoId(id))
				.paymentMethod(method)
				.amount(Money.of(new BigDecimal(amount), EUR))
				.paymentProcessingStatus(status)
				.build();
	}

	@Test
	void completionAttempts_journalEachPaymentOnce()
	{
		// a mixed sale: the cash part is paid; the card part is checked out, its result not in yet
		final POSPayment cash = payment(21, POSPaymentMethod.CASH, "4.00", POSPaymentProcessingStatus.SUCCESSFUL);
		final POSPayment cardPending = payment(22, POSPaymentMethod.CARD, "6.00", POSPaymentProcessingStatus.PENDING);

		// two completion attempts end waiting for the card (e.g. after the cash checkout, and after the card checkout's step 3)
		final POSOrder waiting = order(ImmutableList.of(cash, cardPending));
		services.processPOSPayments(waiting);
		assertThat(waiting.isPaymentsProcessedSuccessfully()).isFalse();
		services.processPOSPayments(order(ImmutableList.of(cash, cardPending)));

		// SumUp approves the card; the next attempt completes the sale
		final POSOrder paid = order(ImmutableList.of(cash, payment(22, POSPaymentMethod.CARD, "6.00", POSPaymentProcessingStatus.SUCCESSFUL)));
		services.processPOSPayments(paid);
		assertThat(paid.isPaymentsProcessedSuccessfully()).isTrue();

		// exactly one journal line per payment
		final POSCashJournal journal = journalService.getById(journalId);
		assertThat(journal.getLines())
				.extracting(line -> line.getPosOrderAndPaymentId() != null ? line.getPosOrderAndPaymentId().getPaymentId().getRepoId() : -1)
				.containsExactly(21, 22);
		assertThat(journal.getCashEndingBalance().toBigDecimal()).isEqualByComparingTo("104.00");
	}
}
