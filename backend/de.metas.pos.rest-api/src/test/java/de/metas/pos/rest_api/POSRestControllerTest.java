package de.metas.pos.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalId;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
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

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), CASHIER_ID);

		currencyId = createCurrency();
		posReturnService = Mockito.mock(POSReturnService.class);

		controller = new POSRestController(
				Mockito.mock(POSService.class),
				new CurrencyRepository(),
				Mockito.mock(POSCashWithdrawalService.class),
				posReturnService);
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
