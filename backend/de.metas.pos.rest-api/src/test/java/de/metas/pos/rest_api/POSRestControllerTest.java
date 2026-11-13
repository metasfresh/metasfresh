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
import de.metas.pos.POSCardPaymentCheckResult;
import de.metas.pos.POSCardPaymentReleaseRequest;
import de.metas.pos.POSCardPaymentReleaseResult;
import de.metas.pos.POSCardReleaseWait;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSShipFrom;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentCheckRequest;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentCheckResponse;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentReleaseRequest;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentReleaseResponse;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrderMapper;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonPOSPaymentStatus;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.pricing.PricingSystemAndListId;
import org.adempiere.warehouse.WarehouseId;
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
	private POSService posService;
	private POSCardReleaseWait cardReleaseWait;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), CASHIER_ID);

		currencyId = createCurrency();
		posReturnService = Mockito.mock(POSReturnService.class);
		cardSettlementService = Mockito.mock(POSInvoiceCardSettlementService.class);
		posService = Mockito.mock(POSService.class);
		cardReleaseWait = Mockito.mock(POSCardReleaseWait.class);

		controller = new POSRestController(
				posService,
				new CurrencyRepository(),
				Mockito.mock(POSCashWithdrawalService.class),
				posReturnService,
				Mockito.mock(POSInvoiceSettlementService.class),
				cardSettlementService,
				new JsonPOSOrderMapper(cardReleaseWait));
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
				posReturnService, invoiceSettlementService, cardSettlementService, new JsonPOSOrderMapper(cardReleaseWait));

		final JsonPOSOpenInvoicesList list = controllerWithInvoices.getOpenInvoices("1", "001");

		assertThat(list.getList()).hasSize(2);
		assertThat(list.getList().get(0).getBlockingCardSettlement().getDocumentNo()).isEqualTo("INV-001");
		assertThat(list.getList().get(0).getBlockingCardSettlement().getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
		assertThat(list.getList().get(1).getBlockingCardSettlement()).isNull();
		Mockito.verify(cardSettlementService, Mockito.never()).getInvoiceDocumentNo(any());
	}

	private static final POSOrderExternalId ORDER_UUID = POSOrderExternalId.ofString(UUID.randomUUID().toString());
	private static final POSPaymentExternalId PAYMENT_UUID = POSPaymentExternalId.ofString(UUID.randomUUID().toString());
	private static final Instant CHECKOUT_STARTED_AT = Instant.parse("2026-11-10T10:00:00Z");

	private POSOrder newOrderWithCardPayment(final POSPaymentProcessingStatus status)
	{
		return POSOrder.builder()
				.externalId(ORDER_UUID)
				.status(POSOrderStatus.WaitingPayment)
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.cashbookId(BankAccountId.ofRepoId(1))
				.cashierId(CASHIER_ID)
				.date(CHECKOUT_STARTED_AT)
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
						.countryId(CountryId.ofRepoId(101))
						.build())
				.currencyId(currencyId)
				.posTerminalId(TERMINAL_ID)
				.isTaxIncluded(true)
				.payments(ImmutableList.of(POSPayment.builder()
						.externalId(PAYMENT_UUID)
						.paymentMethod(POSPaymentMethod.CARD)
						.amount(Money.of(new BigDecimal("10.00"), currencyId))
						.paymentProcessingStatus(status)
						.cardCheckoutStartedAt(CHECKOUT_STARTED_AT)
						.build()))
				.build();
	}

	private void mockReleasableAt()
	{
		Mockito.when(cardReleaseWait.getReleasableAt(ClientAndOrgId.ofClientAndOrg(1, 1), CHECKOUT_STARTED_AT))
				.thenReturn(CHECKOUT_STARTED_AT.plusSeconds(180));
	}

	@Test
	void getOpenOrders_unknownCardPayment_hasReleasableAt()
	{
		mockReleasableAt();
		Mockito.when(posService.getOpenOrders(any(), any(), any()))
				.thenReturn(ImmutableList.of(newOrderWithCardPayment(POSPaymentProcessingStatus.UNKNOWN)));

		final JsonPOSOrdersList response = controller.getOpenOrders("1", null);

		assertThat(response.getList()).hasSize(1);
		assertThat(response.getList().get(0).getPayments().get(0).getReleasableAt()).isEqualTo(CHECKOUT_STARTED_AT.plusSeconds(180));
	}

	@Test
	void checkPayment_usesLoggedCashier_andMapsOrder_withReleasableAtOfUnknownCardPayment()
	{
		final POSOrder order = newOrderWithCardPayment(POSPaymentProcessingStatus.UNKNOWN);
		Mockito.when(posService.checkCardPayment(any(), any(), any(), any()))
				.thenReturn(POSCardPaymentCheckResult.builder().order(order).checkFailed(false).build());
		mockReleasableAt();

		final JsonPOSCardPaymentCheckResponse checkResponse = controller.checkPayment(JsonPOSCardPaymentCheckRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.order_uuid(ORDER_UUID)
				.payment_uuid(PAYMENT_UUID)
				.build());
		assertThat(checkResponse.isCheckFailed()).isFalse();
		final JsonPOSOrder response = checkResponse.getOrder();

		Mockito.verify(posService).checkCardPayment(TERMINAL_ID, ORDER_UUID, PAYMENT_UUID, CASHIER_ID);
		assertThat(response.getUuid()).isEqualTo(ORDER_UUID);
		assertThat(response.getPayments()).hasSize(1);
		assertThat(response.getPayments().get(0).getStatus()).isEqualTo(JsonPOSPaymentStatus.UNKNOWN);
		assertThat(response.getPayments().get(0).getReleasableAt()).isEqualTo(CHECKOUT_STARTED_AT.plusSeconds(180));
			}

	/**
	 * SumUp could not be asked: the till is told so, as by the invoice card settlement's check.
	 */
	@Test
	void checkPayment_mapsCheckFailed()
	{
		final POSOrder order = newOrderWithCardPayment(POSPaymentProcessingStatus.UNKNOWN);
		Mockito.when(posService.checkCardPayment(any(), any(), any(), any()))
				.thenReturn(POSCardPaymentCheckResult.builder().order(order).checkFailed(true).build());
		mockReleasableAt();

		final JsonPOSCardPaymentCheckResponse response = controller.checkPayment(JsonPOSCardPaymentCheckRequest.builder()
				.posTerminalId(TERMINAL_ID).order_uuid(ORDER_UUID).payment_uuid(PAYMENT_UUID).build());

		assertThat(response.isCheckFailed()).isTrue();
		assertThat(response.getOrder().getPayments().get(0).getStatus()).isEqualTo(JsonPOSPaymentStatus.UNKNOWN);
	}

	@Test
	void checkPayment_paymentNotUnknown_hasNoReleasableAt_andReleaseWaitIsNotAsked()
	{
		final POSOrder order = newOrderWithCardPayment(POSPaymentProcessingStatus.SUCCESSFUL);
		Mockito.when(posService.checkCardPayment(any(), any(), any(), any()))
				.thenReturn(POSCardPaymentCheckResult.builder().order(order).build());

		final JsonPOSOrder response = controller.checkPayment(JsonPOSCardPaymentCheckRequest.builder()
				.posTerminalId(TERMINAL_ID).order_uuid(ORDER_UUID).payment_uuid(PAYMENT_UUID).build()).getOrder();

		assertThat(response.getPayments().get(0).getReleasableAt()).isNull();
		Mockito.verifyZeroInteractions(cardReleaseWait);
	}

	@Test
	void releasePayment_mapsRequest_andTranslatesRefusedMessage()
	{
		final POSOrder order = newOrderWithCardPayment(POSPaymentProcessingStatus.UNKNOWN);
		mockReleasableAt();
		Mockito.when(posService.releaseCardPayment(any())).thenReturn(POSCardPaymentReleaseResult.builder()
				.order(order)
				.confirmationRequired(true)
				.checkFailed(true)
				.refusedMessage(TranslatableStrings.constant("Release not yet allowed"))
				.build());

		final JsonPOSCardPaymentReleaseResponse response = controller.releasePayment(JsonPOSCardPaymentReleaseRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.order_uuid(ORDER_UUID)
				.payment_uuid(PAYMENT_UUID)
				.confirmNotCharged(true)
				.expectedStatus(JsonPOSPaymentStatus.UNKNOWN)
				.build());

		final ArgumentCaptor<POSCardPaymentReleaseRequest> captor = ArgumentCaptor.forClass(POSCardPaymentReleaseRequest.class);
		Mockito.verify(posService).releaseCardPayment(captor.capture());
		assertThat(captor.getValue().getPosTerminalId()).isEqualTo(TERMINAL_ID);
		assertThat(captor.getValue().getPosOrderExternalId()).isEqualTo(ORDER_UUID);
		assertThat(captor.getValue().getPosPaymentExternalId()).isEqualTo(PAYMENT_UUID);
		assertThat(captor.getValue().getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(captor.getValue().isConfirmNotCharged()).isTrue();
		assertThat(captor.getValue().getExpectedStatus()).isEqualTo(POSPaymentProcessingStatus.UNKNOWN);

		assertThat(response.getOrder().getUuid()).isEqualTo(ORDER_UUID);
		assertThat(response.getOrder().getPayments().get(0).getReleasableAt()).isEqualTo(CHECKOUT_STARTED_AT.plusSeconds(180));
		assertThat(response.isConfirmationRequired()).isTrue();
		assertThat(response.isCheckFailed()).isTrue();
		assertThat(response.getRefusedMessage()).isEqualTo("Release not yet allowed");
	}

	@Test
	void releasePayment_withoutExpectedStatus_passesNull_andNoRefusedMessage()
	{
		final POSOrder order = newOrderWithCardPayment(POSPaymentProcessingStatus.SUCCESSFUL);
		Mockito.when(posService.releaseCardPayment(any()))
				.thenReturn(POSCardPaymentReleaseResult.builder().order(order).confirmationRequired(true).build());

		final JsonPOSCardPaymentReleaseResponse response = controller.releasePayment(JsonPOSCardPaymentReleaseRequest.builder()
				.posTerminalId(TERMINAL_ID).order_uuid(ORDER_UUID).payment_uuid(PAYMENT_UUID).build());

		final ArgumentCaptor<POSCardPaymentReleaseRequest> captor = ArgumentCaptor.forClass(POSCardPaymentReleaseRequest.class);
		Mockito.verify(posService).releaseCardPayment(captor.capture());
		assertThat(captor.getValue().isConfirmNotCharged()).isFalse();
		assertThat(captor.getValue().getExpectedStatus()).isNull();
		assertThat(response.isConfirmationRequired()).isTrue();
		assertThat(response.isCheckFailed()).isFalse();
		assertThat(response.getRefusedMessage()).isNull();
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
