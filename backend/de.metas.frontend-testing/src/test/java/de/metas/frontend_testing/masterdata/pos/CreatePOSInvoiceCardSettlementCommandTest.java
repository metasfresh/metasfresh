package de.metas.frontend_testing.masterdata.pos;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalOpenJournalRequest;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreatePOSInvoiceCardSettlementCommandTest
{
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(200);
	private static final SumUpConfigId SUMUP_CONFIG_ID = SumUpConfigId.ofRepoId(5);

	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementBooking booking;
	private POSService posService;
	private SumUpTransactionRepository sumUpTransactionRepository;
	private MasterdataContext context;
	private InvoiceId invoiceId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		booking = mock(POSInvoiceCardSettlementBooking.class);
		posService = mock(POSService.class);
		sumUpTransactionRepository = mock(SumUpTransactionRepository.class);
		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("user"), CASHIER_ID);
		context.putIdentifier(Identifier.ofString("T1"), TERMINAL_ID);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(300);
		invoice.setC_Currency_ID(318);
		invoice.setGrandTotal(new BigDecimal("16.07"));
		InterfaceWrapperHelper.saveRecord(invoice);
		invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		context.putIdentifier(Identifier.ofString("INV1"), invoiceId);
	}

	private void terminalWithSumUp(final boolean isCashJournalOpen)
	{
		final POSTerminal terminal = mock(POSTerminal.class);
		when(terminal.getId()).thenReturn(TERMINAL_ID);
		when(terminal.getName()).thenReturn("T1");
		when(terminal.isCashJournalOpen()).thenReturn(isCashJournalOpen);
		when(terminal.getPaymentProcessorConfig()).thenReturn(POSTerminalPaymentProcessorConfig.builder()
				.type(POSPaymentProcessorType.SumUp)
				.sumUpConfigId(SUMUP_CONFIG_ID)
				.build());
		when(posService.getPOSTerminalById(TERMINAL_ID)).thenReturn(terminal);
	}

	private POSInvoiceCardSettlementId create(final JsonPOSInvoiceCardSettlementRequest request)
	{
		final SumUpConfig sumUpConfig = mock(SumUpConfig.class);
		when(sumUpConfig.getMerchantCode()).thenReturn(SumUpMerchantCode.ofString("M-TEST"));
		final SumUpConfigRepository sumUpConfigRepository = mock(SumUpConfigRepository.class);
		when(sumUpConfigRepository.getById(SUMUP_CONFIG_ID)).thenReturn(sumUpConfig);
		final CurrencyRepository currencyRepository = mock(CurrencyRepository.class);
		when(currencyRepository.getCurrencyCodeById(CurrencyId.ofRepoId(318))).thenReturn(CurrencyCode.EUR);

		final JsonPOSInvoiceCardSettlementResponse response = CreatePOSInvoiceCardSettlementCommand.builder()
				.repository(repository)
				.booking(booking)
				.posService(posService)
				.sumUpTransactionRepository(sumUpTransactionRepository)
				.sumUpConfigRepository(sumUpConfigRepository)
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("S1"))
				.build()
				.execute();
		return POSInvoiceCardSettlementId.ofRepoId(response.getId());
	}

	private static JsonPOSInvoiceCardSettlementRequest.JsonPOSInvoiceCardSettlementRequestBuilder request(final POSInvoiceCardSettlementStatus status)
	{
		return JsonPOSInvoiceCardSettlementRequest.builder()
				.posTerminal(Identifier.ofString("T1"))
				.invoice(Identifier.ofString("INV1"))
				.cashier(Identifier.ofString("user"))
				.status(status);
	}

	private int countSettlements()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_POS_InvoiceCardSettlement.class).create().count();
	}

	@Test
	public void refusesTheInFlightAndTheRefundedStatuses_theBackgroundSweepWouldChangeOrBookThem()
	{
		for (final POSInvoiceCardSettlementStatus status : new POSInvoiceCardSettlementStatus[] {
				POSInvoiceCardSettlementStatus.PENDING,
				POSInvoiceCardSettlementStatus.UNKNOWN,
				POSInvoiceCardSettlementStatus.APPROVED,
				POSInvoiceCardSettlementStatus.REFUNDED })
		{
			assertThatThrownBy(() -> create(request(status).build()))
					.as("status %s", status.getCode())
					.hasMessageContaining("cannot be created in status " + status.getCode());
		}

		assertThat(countSettlements()).as("no settlement was created").isZero();
		verify(booking, never()).book(any(), anyBoolean());
	}

	@Test
	public void aBookedStatusIsBookedByTheProductionBookingFromASuccessfulSumUpTransaction()
	{
		terminalWithSumUp(false);
		doAnswer(invocation -> {
			final POSInvoiceCardSettlementId id = invocation.getArgument(0);
			repository.save(repository.getById(id).toBuilder().status(POSInvoiceCardSettlementStatus.SUCCESSFUL).build());
			return null;
		}).when(booking).book(any(), anyBoolean());

		final POSInvoiceCardSettlementId id = create(request(POSInvoiceCardSettlementStatus.SUCCESSFUL).note("a note").build());

		final ArgumentCaptor<SumUpTransaction> sumUpTrx = ArgumentCaptor.forClass(SumUpTransaction.class);
		verify(sumUpTransactionRepository).saveNew(sumUpTrx.capture());
		assertThat(sumUpTrx.getValue().getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
		assertThat(sumUpTrx.getValue().getAmount().toBigDecimal()).isEqualByComparingTo("16.07");
		assertThat(sumUpTrx.getValue().getMerchantCode()).isEqualTo(SumUpMerchantCode.ofString("M-TEST"));
		assertThat(sumUpTrx.getValue().getPosRef().getPosInvoiceCardSettlementId()).isEqualTo(id.getRepoId());
		verify(posService).openCashJournal(any(POSTerminalOpenJournalRequest.class));
		verify(booking).book(id, true);

		final POSInvoiceCardSettlement settlement = repository.getById(id);
		assertThat(settlement.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.SUCCESSFUL);
		assertThat(settlement.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(settlement.getBpartnerId()).isEqualTo(BPartnerId.ofRepoId(300));
		assertThat(settlement.getAmount().toBigDecimal()).isEqualByComparingTo("16.07");
		assertThat(settlement.getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(settlement.getNote()).isEqualTo("a note");
	}

	@Test
	public void anOpenCashJournalIsKept()
	{
		terminalWithSumUp(true);
		doAnswer(invocation -> {
			final POSInvoiceCardSettlementId id = invocation.getArgument(0);
			repository.save(repository.getById(id).toBuilder().status(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING).build());
			return null;
		}).when(booking).book(any(), anyBoolean());

		create(request(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING).build());

		verify(posService, never()).openCashJournal(any());
	}

	@Test
	public void failsIfTheBookingEndsInAnotherStatusThanRequested()
	{
		terminalWithSumUp(true);
		// the booking does nothing: the settlement stays PENDING, e.g. an invoice that is not paid for a CR request

		assertThatThrownBy(() -> create(request(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING).build()))
				.hasMessageContaining("ended in status PE instead of the requested CR");
	}

	@Test
	public void aStatusWithoutPayment_hasItsStatusNoteErrorAttemptsAndAbandonedFlagSet()
	{
		final POSInvoiceCardSettlementId id = create(request(POSInvoiceCardSettlementStatus.BOOKING_ERROR)
				.note("checked at the terminal")
				.errorMsg("booking failed")
				.bookingAttempts(3)
				.isAbandoned(true)
				.build());

		final POSInvoiceCardSettlement settlement = repository.getById(id);
		assertThat(settlement.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		assertThat(settlement.getNote()).isEqualTo("checked at the terminal");
		assertThat(settlement.getErrorMsg()).isEqualTo("booking failed");
		assertThat(settlement.getBookingAttempts()).isEqualTo(3);
		assertThat(settlement.isAbandoned()).isTrue();
		verify(booking, never()).book(any(), anyBoolean());
	}
}
