package de.metas.pos.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentId;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalId;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementCheckResult;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementReleaseRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementReleaseResult;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStartRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlement;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlementReleaseRequest;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlementReleaseResponse;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlementStartRequest;
import de.metas.pos.rest_api.json.JsonPOSOpenInvoicesList;
import de.metas.pos.rest_api.json.JsonPOSReturnLine;
import de.metas.pos.rest_api.json.JsonPOSReturnRequest;
import de.metas.pos.rest_api.json.JsonPOSReturnResponse;
import de.metas.pos.returns.POSReturnRequestedLine;
import de.metas.pos.returns.POSReturnResult;
import de.metas.pos.returns.POSReturnService;
import de.metas.pos.withdrawal.POSCashWithdrawalService;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;

/**
 * {@code POST /returns}: the controller method itself is a thin mapping
 * (request → {@link POSReturnRequestedLine}, {@link POSReturnResult} → {@link JsonPOSReturnResponse}) around
 * {@link POSReturnService}, which is exercised for real by cucumber (see {@code posReturn.feature}) — this test
 * covers only the mapping and the exception pass-through, with {@link POSReturnService} replaced by a Mockito
 * double so no return/credit-memo/settlement machinery is needed. Direct instantiation (no {@code MockMvc}),
 * following {@code BpartnerRestControllerTest}'s pattern — no test anywhere in this codebase drives a
 * controller through Spring's actual HTTP dispatch, so an {@code AdempiereException}'s message key is verified
 * to survive out of the controller method UNWRAPPED; the generic translation of any {@code AdempiereException}
 * into an HTTP response is a pre-existing, unchanged mechanism this diff does not touch.
 */
class POSRestControllerTest
{
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(1);
	private static final AdMessageKey MSG_NoTillPrice = AdMessageKey.of("de.metas.pos.Return.NoTillPrice");

	private CurrencyId currencyId;
	private POSReturnService posReturnService;
	private POSRestController controller;
	private POSInvoiceCardSettlementService cardSettlementService;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), CASHIER_ID);

		currencyId = createCurrency();
		posReturnService = Mockito.mock(POSReturnService.class);
		cardSettlementService = Mockito.mock(POSInvoiceCardSettlementService.class);

		controller = new POSRestController(
				Mockito.mock(POSService.class),
				new CurrencyRepository(),
				Mockito.mock(POSCashWithdrawalService.class),
				posReturnService,
				Mockito.mock(POSInvoiceSettlementService.class),
				cardSettlementService);
	}

	@Test
	void createReturn_mapsRequestToRequestedLines_andResultToResponse()
	{
		final UUID externalId = UUID.randomUUID();
		final ProductId productId = ProductId.ofRepoId(2);

		final POSReturnResult cannedResult = POSReturnResult.builder()
				.returnInOutId(InOutId.ofRepoId(1))
				.invoiceCandidateIds(ImmutableList.of())
				.creditMemoId(InvoiceId.ofRepoId(3))
				.creditMemoDocumentNo("GS-100")
				.refundAmount(Money.of(new BigDecimal("4.65"), currencyId))
				.paymentId(PaymentId.ofRepoId(5))
				.journal(newJournal())
				.build();

		//noinspection unchecked
		Mockito.when(posReturnService.createReturnFromTillPrices(eq(TERMINAL_ID), eq(externalId), eq(CASHIER_ID), anyList()))
				.thenReturn(cannedResult);

		final JsonPOSReturnRequest request = JsonPOSReturnRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.externalId(externalId)
				.lines(ImmutableList.of(JsonPOSReturnLine.builder()
						.productId(productId)
						.qty(new BigDecimal("0.300"))
						.build()))
				.build();

		final JsonPOSReturnResponse response = controller.createReturn(request);

		// request mapping: JsonPOSReturnLine (no price) -> POSReturnRequestedLine, verbatim
		//noinspection unchecked
		final ArgumentCaptor<List<POSReturnRequestedLine>> linesCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(posReturnService).createReturnFromTillPrices(eq(TERMINAL_ID), eq(externalId), eq(CASHIER_ID), linesCaptor.capture());
		final List<POSReturnRequestedLine> passedLines = linesCaptor.getValue();
		assertThat(passedLines).hasSize(1);
		assertThat(passedLines.get(0).getProductId()).isEqualTo(productId);
		assertThat(passedLines.get(0).getQty()).isEqualByComparingTo("0.300");

		// response mapping: POSReturnResult -> JsonPOSReturnResponse
		assertThat(response.getCreditMemoDocumentNo()).isEqualTo("GS-100");
		assertThat(response.getRefundAmount()).isEqualByComparingTo("4.65");
		assertThat(response.getJournal()).isNotNull();
	}

	/**
	 * An {@link AdempiereException} thrown by {@link POSReturnService} (e.g. {@code MSG_NoTillPrice}) must reach
	 * the caller with its message key intact — the controller method must not catch, wrap, or replace it.
	 */
	@Test
	void createReturn_propagatesAdempiereExceptionWithMessageKeyUnwrapped()
	{
		//noinspection unchecked
		Mockito.when(posReturnService.createReturnFromTillPrices(any(), any(), any(), anyList()))
				.thenThrow(new AdempiereException(MSG_NoTillPrice).setParameter("M_Product_ID", 2));

		final JsonPOSReturnRequest request = JsonPOSReturnRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.externalId(UUID.randomUUID())
				.lines(ImmutableList.of(JsonPOSReturnLine.builder()
						.productId(ProductId.ofRepoId(2))
						.qty(BigDecimal.ONE)
						.build()))
				.build();

		final String expectedErrorCode = Optional.ofNullable(Services.get(IMsgBL.class).getErrorCode(MSG_NoTillPrice))
				.orElseGet(MSG_NoTillPrice::toAD_Message);

		assertThatThrownBy(() -> controller.createReturn(request))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).isEqualTo(expectedErrorCode));
	}

	private POSCashJournal newJournal()
	{
		return POSCashJournal.builder()
				.id(POSCashJournalId.ofRepoId(1))
				.terminalId(TERMINAL_ID)
				.dateTrx(Instant.now())
				.cashBeginningBalance(Money.of(new BigDecimal("100.00"), currencyId))
				.isClosed(false)
				.build();
	}

	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(7);
	private static final POSInvoiceCardSettlementId SETTLEMENT_ID = POSInvoiceCardSettlementId.ofRepoId(9);
	private static final Instant RELEASABLE_AT = Instant.parse("2026-09-24T10:18:30Z");

	private POSInvoiceCardSettlement newSettlement(final POSInvoiceCardSettlementStatus status)
	{
		return POSInvoiceCardSettlement.builder()
				.id(SETTLEMENT_ID)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.posTerminalId(TERMINAL_ID)
				.invoiceId(INVOICE_ID)
				.bpartnerId(BPartnerId.ofRepoId(3))
				.cashierId(CASHIER_ID)
				.amount(Money.of(new BigDecimal("75.50"), currencyId))
				.status(status)
				.created(Instant.parse("2026-09-24T10:15:30Z"))
				.build();
	}

	private void mockSettlementDetails(final POSInvoiceCardSettlement settlement, final String allocatedAmount)
	{
		Mockito.when(cardSettlementService.getInvoiceDocumentNo(settlement)).thenReturn("INV-001");
		Mockito.when(cardSettlementService.getAllocatedAmount(settlement)).thenReturn(Money.of(new BigDecimal(allocatedAmount), currencyId));
		Mockito.when(cardSettlementService.getReleasableAt(settlement)).thenReturn(RELEASABLE_AT);
	}

	/**
	 * The cashier is the logged-in user, never taken from the request; the response carries the settlement's state.
	 */
	@Test
	void startInvoiceCardSettlement_usesLoggedCashier_andMapsSettlement()
	{
		final POSInvoiceCardSettlement settlement = newSettlement(POSInvoiceCardSettlementStatus.PENDING);
		Mockito.when(cardSettlementService.start(any())).thenReturn(settlement);
		mockSettlementDetails(settlement, "0");

		final JsonPOSInvoiceCardSettlement response = controller.startInvoiceCardSettlement(JsonPOSInvoiceCardSettlementStartRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.invoiceId(INVOICE_ID)
				.build());

		final ArgumentCaptor<POSInvoiceCardSettlementStartRequest> captor = ArgumentCaptor.forClass(POSInvoiceCardSettlementStartRequest.class);
		Mockito.verify(cardSettlementService).start(captor.capture());
		assertThat(captor.getValue().getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(captor.getValue().getPosTerminalId()).isEqualTo(TERMINAL_ID);
		assertThat(captor.getValue().getInvoiceId()).isEqualTo(INVOICE_ID);

		assertThat(response.getId()).isEqualTo(SETTLEMENT_ID);
		assertThat(response.getDocumentNo()).isEqualTo("INV-001");
		assertThat(response.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
		assertThat(response.getAmount()).isEqualByComparingTo("75.50");
		assertThat(response.getAllocatedAmount()).isEqualByComparingTo("0");
		assertThat(response.getCurrencyCode()).isEqualTo("EUR");
		assertThat(response.getReleasableAt()).isEqualTo(RELEASABLE_AT);
		assertThat(response.isCheckFailed()).isFalse();
	}

	@Test
	void checkInvoiceCardSettlement_mapsStatusAllocatedAmountAndCheckFailed()
	{
		final POSInvoiceCardSettlement settlement = newSettlement(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);
		Mockito.when(cardSettlementService.check(SETTLEMENT_ID))
				.thenReturn(POSInvoiceCardSettlementCheckResult.builder().settlement(settlement).checkFailed(true).build());
		mockSettlementDetails(settlement, "50.00");

		final JsonPOSInvoiceCardSettlement response = controller.checkInvoiceCardSettlement("9");

		assertThat(response.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);
		assertThat(response.getAllocatedAmount()).isEqualByComparingTo("50.00");
		assertThat(response.isCheckFailed()).isTrue();
		assertThat(response.isAbandoned()).isFalse();
	}

	@Test
	void releaseInvoiceCardSettlement_mapsRequest_andTranslatesRefusedMessage()
	{
		final POSInvoiceCardSettlement settlement = newSettlement(POSInvoiceCardSettlementStatus.UNKNOWN);
		mockSettlementDetails(settlement, "0");
		Mockito.when(cardSettlementService.release(any())).thenReturn(POSInvoiceCardSettlementReleaseResult.builder()
				.settlement(settlement)
				.confirmationRequired(true)
				.checkFailed(true)
				.refusedMessage(TranslatableStrings.constant("Release not yet allowed"))
				.build());

		final JsonPOSInvoiceCardSettlementReleaseResponse response = controller.releaseInvoiceCardSettlement(
				"9",
				JsonPOSInvoiceCardSettlementReleaseRequest.builder().confirmNotCharged(true).expectedStatus(POSInvoiceCardSettlementStatus.UNKNOWN).build());

		final ArgumentCaptor<POSInvoiceCardSettlementReleaseRequest> captor = ArgumentCaptor.forClass(POSInvoiceCardSettlementReleaseRequest.class);
		Mockito.verify(cardSettlementService).release(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(SETTLEMENT_ID);
		assertThat(captor.getValue().getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(captor.getValue().isConfirmNotCharged()).isTrue();
		assertThat(captor.getValue().getExpectedStatus()).isEqualTo(POSInvoiceCardSettlementStatus.UNKNOWN);

		assertThat(response.isConfirmationRequired()).isTrue();
		assertThat(response.getRefusedMessage()).isEqualTo("Release not yet allowed");
		assertThat(response.getSettlement().isCheckFailed()).isTrue();
	}

	@Test
	void releaseInvoiceCardSettlement_withoutExpectedStatus_passesNull_andNoRefusedMessage()
	{
		final POSInvoiceCardSettlement settlement = newSettlement(POSInvoiceCardSettlementStatus.PENDING);
		mockSettlementDetails(settlement, "0");
		Mockito.when(cardSettlementService.release(any()))
				.thenReturn(POSInvoiceCardSettlementReleaseResult.builder().settlement(settlement).confirmationRequired(true).build());

		final JsonPOSInvoiceCardSettlementReleaseResponse response = controller.releaseInvoiceCardSettlement(
				"9",
				JsonPOSInvoiceCardSettlementReleaseRequest.builder().build());

		final ArgumentCaptor<POSInvoiceCardSettlementReleaseRequest> captor = ArgumentCaptor.forClass(POSInvoiceCardSettlementReleaseRequest.class);
		Mockito.verify(cardSettlementService).release(captor.capture());
		assertThat(captor.getValue().isConfirmNotCharged()).isFalse();
		assertThat(captor.getValue().getExpectedStatus()).isNull();
		assertThat(response.getRefusedMessage()).isNull();
	}

	/**
	 * The list takes the document number from the invoice and attaches the blocking settlement only to the invoice that has one.
	 */
	@Test
	void getOpenInvoices_attachesBlockingSettlementToItsInvoiceOnly()
	{
		final POSInvoiceCardSettlement settlement = newSettlement(POSInvoiceCardSettlementStatus.APPROVED);
		Mockito.when(cardSettlementService.getAllocatedAmount(settlement)).thenReturn(Money.of(BigDecimal.ZERO, currencyId));
		Mockito.when(cardSettlementService.getReleasableAt(settlement)).thenReturn(RELEASABLE_AT);

		final POSInvoiceSettlementService invoiceSettlementService = Mockito.mock(POSInvoiceSettlementService.class);
		Mockito.when(invoiceSettlementService.findOpenInvoices(TERMINAL_ID, "001")).thenReturn(ImmutableList.of(
				POSOpenInvoice.builder().invoiceId(INVOICE_ID).documentNo("INV-001").bpartnerName("A").dateInvoiced(LocalDate.of(2026, 9, 24))
						.grandTotal(Money.of(new BigDecimal("100"), currencyId)).openAmt(Money.of(new BigDecimal("75.50"), currencyId))
						.blockingCardSettlement(settlement).build(),
				POSOpenInvoice.builder().invoiceId(InvoiceId.ofRepoId(8)).documentNo("INV-002").bpartnerName("B").dateInvoiced(LocalDate.of(2026, 9, 24))
						.grandTotal(Money.of(new BigDecimal("10"), currencyId)).openAmt(Money.of(new BigDecimal("10"), currencyId)).build()));
		final POSRestController controllerWithInvoices = new POSRestController(
				Mockito.mock(POSService.class), new CurrencyRepository(), Mockito.mock(POSCashWithdrawalService.class),
				posReturnService, invoiceSettlementService, cardSettlementService);

		final JsonPOSOpenInvoicesList list = controllerWithInvoices.getOpenInvoices("1", "001");

		assertThat(list.getList()).hasSize(2);
		assertThat(list.getList().get(0).getBlockingCardSettlement().getDocumentNo()).isEqualTo("INV-001");
		assertThat(list.getList().get(0).getBlockingCardSettlement().getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
		assertThat(list.getList().get(1).getBlockingCardSettlement()).isNull();
		Mockito.verify(cardSettlementService, Mockito.never()).getInvoiceDocumentNo(any());
	}

	private static CurrencyId createCurrency()
	{
		final I_C_Currency record = newInstance(I_C_Currency.class);
		record.setISO_Code("EUR");
		record.setCurSymbol("€");
		record.setDescription("Euro");
		record.setStdPrecision(2);
		record.setCostingPrecision(2);
		saveRecord(record);
		return CurrencyId.ofRepoId(record.getC_Currency_ID());
	}
}
