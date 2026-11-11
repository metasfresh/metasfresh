package de.metas.pos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.costing.ChargeId;
import de.metas.invoice.InvoiceId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonCashJournalSummary.JsonPaymentDetail;
import de.metas.pos.rest_api.json.JsonCashJournalSummary.JsonPaymentMethodSummary;
import de.metas.pos.rest_api.json.JsonCashWithdrawalRequest;
import de.metas.pos.rest_api.json.JsonCashWithdrawalResponse;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlement;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlementReleaseRequest;
import de.metas.pos.rest_api.json.JsonPOSInvoiceCardSettlementReleaseResponse;
import de.metas.pos.rest_api.json.JsonPOSInvoiceSettleRequest;
import de.metas.pos.rest_api.json.JsonPOSInvoiceSettleResponse;
import de.metas.pos.rest_api.json.JsonPOSOpenInvoice;
import de.metas.pos.rest_api.json.JsonPOSOpenInvoicesList;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrderLine;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentCheckRequest;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentReleaseRequest;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentCheckResponse;
import de.metas.pos.rest_api.json.JsonPOSCardPaymentReleaseResponse;
import de.metas.pos.rest_api.json.JsonPOSPayment;
import de.metas.pos.rest_api.json.JsonPOSPaymentStatus;
import de.metas.pos.rest_api.json.JsonPOSReturnLine;
import de.metas.pos.rest_api.json.JsonPOSReturnRequest;
import de.metas.pos.rest_api.json.JsonPOSReturnResponse;
import de.metas.pos.rest_api.json.JsonPOSTerminal;
import de.metas.pos.rest_api.json.JsonProduct;
import de.metas.pos.rest_api.json.JsonProductsSearchResult;
import de.metas.pos.websocket.json.JsonPOSOrderChangedWebSocketEvent;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonSerializeDeserializeTest
{
	<T> void testSerializeDeserialize(@NonNull final T obj) throws JsonProcessingException
	{
		//noinspection unchecked
		final Class<T> type = (Class<T>)obj.getClass();

		System.out.println("  obj: " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println(" json: " + json);
		final T obj2 = jsonObjectMapper.readValue(json, type);
		System.out.println(" obj2: " + obj2);

		assertThat(obj2).usingRecursiveComparison().isEqualTo(obj);
		assertThat(obj2).isEqualTo(obj);
	}

	@Nested
	class test_JsonPOSTerminal
	{
		@Test
		void minimal() throws JsonProcessingException
		{
			testSerializeDeserialize(
					JsonPOSTerminal.builder()
							.id(POSTerminalId.ofRepoId(1))
							.caption("terminal 1")
							.workplaceId(WorkplaceId.ofRepoId(1))
							.currencySymbol("€")
							.pricePrecision(4)
							.currencyPrecision(2)
							.availablePaymentMethods(ImmutableSet.of(
									POSPaymentMethod.CASH,
									POSPaymentMethod.CARD
							))
							.cashJournalOpen(true)
							.cashLastBalance(new BigDecimal("123.45"))
							// .products(newJsonProductsSearchResult().getList())
							// .openOrders(newJsonPOSOrdersList().getList())
							.build()
			);
		}

		@Test
		void populatedWithAllDetails() throws JsonProcessingException
		{
			testSerializeDeserialize(
					JsonPOSTerminal.builder()
							.id(POSTerminalId.ofRepoId(1))
							.caption("terminal 1")
							.workplaceId(WorkplaceId.ofRepoId(1))
							.currencySymbol("€")
							.pricePrecision(4)
							.currencyPrecision(2)
							.availablePaymentMethods(ImmutableSet.of(
									POSPaymentMethod.CASH,
									POSPaymentMethod.CARD
							))
							.cashJournalOpen(true)
							.cashLastBalance(new BigDecimal("123.45"))
							.products(newJsonProductsSearchResult().getList())
							.openOrders(newJsonPOSOrdersList().getList())
							.build()
			);
		}
	}

	@Test
	void test_JsonCashJournalSummary() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonCashJournalSummary.builder()
						.closed(true)
						.openingNote("some opening note")
						.closingNote("some closing note")
						.currencySymbol("€")
						.currencyPrecision(2)
						.paymentMethods(ImmutableList.of(
								JsonPaymentMethodSummary.builder()
										.paymentMethod(POSPaymentMethod.CASH)
										.amount(new BigDecimal("123.45"))
										.details(ImmutableList.of(
												JsonPaymentDetail.builder()
														.type(JsonCashJournalSummary.JsonPaymentDetailType.OPENING_BALANCE)
														.description("desc 1")
														.amount(new BigDecimal("23.41"))
														.build(),
												JsonPaymentDetail.builder()
														.type(JsonCashJournalSummary.JsonPaymentDetailType.CASH_PAYMENTS)
														.description("desc 2")
														.amount(new BigDecimal("100.04"))
														.build()
										))
										.build()
						))
						.build()
		);
	}

	@Test
	void test_JsonCashWithdrawalRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonCashWithdrawalRequest.builder()
						.posTerminalId(POSTerminalId.ofRepoId(1))
						.chargeId(ChargeId.ofRepoId(2))
						.amount(new BigDecimal("50.00"))
						.build()
		);
	}

	@Test
	void test_JsonCashWithdrawalResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonCashWithdrawalResponse.builder()
						.documentNo("PAY-001")
						.category("Travel expenses")
						.amount(new BigDecimal("50.00"))
						.date(Instant.parse("2026-09-24T10:15:30Z"))
						.cashier("John Cashier")
						.terminal("Terminal 1")
						.journal(JsonCashJournalSummary.builder()
								.closed(false)
								.currencySymbol("€")
								.currencyPrecision(2)
								.paymentMethods(ImmutableList.of(
										JsonPaymentMethodSummary.builder()
												.paymentMethod(POSPaymentMethod.CASH)
												.amount(new BigDecimal("123.45"))
												.details(ImmutableList.of(
														JsonPaymentDetail.builder()
																.type(JsonCashJournalSummary.JsonPaymentDetailType.OPENING_BALANCE)
																.amount(new BigDecimal("100.00"))
																.build()
												))
												.build()
								))
								.build())
						.build()
		);
	}

	/**
	 * The REST API's object mapper writes dates as timestamps (epoch seconds); the slip needs a date the client can parse as-is.
	 */
	@Test
	void test_JsonCashWithdrawalResponse_dateIsISO8601_evenWhenTheMapperWritesDatesAsTimestamps() throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper()
				.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		final String json = jsonObjectMapper.writeValueAsString(JsonCashWithdrawalResponse.builder()
				.documentNo("PAY-001")
				.category("Travel expenses")
				.amount(new BigDecimal("50.00"))
				.date(Instant.parse("2026-09-24T10:15:30Z"))
				.cashier("John Cashier")
				.terminal("Terminal 1")
				.journal(JsonCashJournalSummary.builder()
						.closed(false)
						.currencySymbol("€")
						.currencyPrecision(2)
						.paymentMethods(ImmutableList.of())
						.build())
				.build());

		assertThat(json).contains("\"date\":\"2026-09-24T10:15:30Z\"");
	}

	@Test
	void test_JsonPOSReturnRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSReturnRequest.builder()
						.posTerminalId(POSTerminalId.ofRepoId(1))
						.externalId(UUID.randomUUID())
						.lines(ImmutableList.of(
								JsonPOSReturnLine.builder()
										.productId(ProductId.ofRepoId(2))
										.qty(new BigDecimal("0.300"))
										.build()
						))
						.build()
		);
	}

	@Test
	void test_JsonPOSReturnResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSReturnResponse.builder()
						.creditMemoDocumentNo("GS-001")
						.refundAmount(new BigDecimal("4.65"))
						.journal(JsonCashJournalSummary.builder()
								.closed(false)
								.currencySymbol("€")
								.currencyPrecision(2)
								.paymentMethods(ImmutableList.of(
										JsonPaymentMethodSummary.builder()
												.paymentMethod(POSPaymentMethod.CASH)
												.amount(new BigDecimal("95.35"))
												.details(ImmutableList.of(
														JsonPaymentDetail.builder()
																.type(JsonCashJournalSummary.JsonPaymentDetailType.OPENING_BALANCE)
																.amount(new BigDecimal("100.00"))
																.build()
												))
												.build()
								))
								.build())
						.build()
		);
	}

	@Test
	void test_JsonProductsSearchResult() throws JsonProcessingException
	{
		testSerializeDeserialize(newJsonProductsSearchResult());
	}

	private static JsonProductsSearchResult newJsonProductsSearchResult()
	{
		return JsonProductsSearchResult.builder()
				.list(ImmutableList.of(
						JsonProduct.builder()
								.id(ProductId.ofRepoId(1))
								.name("product name with catch weight")
								.price(new BigDecimal("12.34"))
								.currencySymbol("€")
								.uomId(UomId.EACH)
								.uomSymbol("Stk")
								.catchWeightUomId(UomId.ofRepoId(3))
								.catchWeightUomSymbol("kg")
								.catchWeight(new BigDecimal("1.57"))
								.taxCategoryId(TaxCategoryId.ofRepoId(4))
								.build(),
						JsonProduct.builder()
								.id(ProductId.ofRepoId(2))
								.name("product name without catch weight")
								.price(new BigDecimal("44.21"))
								.currencySymbol("€")
								.uomId(UomId.EACH)
								.uomSymbol("Stk")
								.taxCategoryId(TaxCategoryId.ofRepoId(5))
								.build()
				))
				.barcodeMatched(true)
				.build();
	}

	@Test
	void test_JsonPOSOrdersList() throws JsonProcessingException
	{
		testSerializeDeserialize(newJsonPOSOrdersList());
	}

	private static JsonPOSOrdersList newJsonPOSOrdersList()
	{
		return JsonPOSOrdersList.builder()
				.list(ImmutableList.of(
						newJsonPOSOrder(),
						newJsonPOSOrder()
				))
				.missingIds(ImmutableSet.of(
						POSOrderExternalId.ofString(UUID.randomUUID().toString()),
						POSOrderExternalId.ofString(UUID.randomUUID().toString())
				))
				.build();
	}

	private static JsonPOSOrder newJsonPOSOrder()
	{
		return JsonPOSOrder.builder()
				.uuid(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.status(POSOrderStatus.WaitingPayment)
				.currencySymbol("€")
				.totalAmt(new BigDecimal("119.12"))
				.taxAmt(new BigDecimal("19.12"))
				.paidAmt(new BigDecimal("29.11"))
				.openAmt(new BigDecimal("90.01"))
				.lines(ImmutableList.of(
						JsonPOSOrderLine.builder()
								.uuid(UUID.randomUUID().toString())
								.productId(ProductId.ofRepoId(2))
								.productName("some product")
								.scannedBarcode("scanned barcode 333")
								.taxCategoryId(TaxCategoryId.ofRepoId(3))
								.currencySymbol("€")
								.price(new BigDecimal("1.23"))
								.qty(new BigDecimal("4.56"))
								.uomId(UomId.ofRepoId(4))
								.uomSymbol("Stk")
								.catchWeight(new BigDecimal("7.89"))
								.catchWeightUomId(UomId.ofRepoId(5))
								.catchWeightUomSymbol("Kg")
								.amount(new BigDecimal("119.12")) // ofc the amount is random and not calc from price x qty
								.hashCode(876)
								.build()
				))
				.payments(ImmutableList.of(
						JsonPOSPayment.builder()
								.uuid(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
								.paymentMethod(POSPaymentMethod.CARD)
								.amount(new BigDecimal("29.11"))
								.status(JsonPOSPaymentStatus.PENDING)
								.allowCheckout(true)
								.allowDelete(true)
								.allowRefund(true)
								.hashCode(543)
								.build()
				))
				.hashCode(123)
				.build();
	}

	/**
	 * A card payment whose result is unknown travels as {@code UNKNOWN}, in both directions; it can be neither checked out nor deleted.
	 * Its {@code releasableAt} travels as an ISO instant, as the invoice card settlement's.
	 */
	@Test
	void test_JsonPOSPayment_unknownStatus() throws JsonProcessingException
	{
		final JsonPOSPayment payment = JsonPOSPayment.builder()
				.uuid(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.paymentMethod(POSPaymentMethod.CARD)
				.amount(new BigDecimal("29.11"))
				.status(JsonPOSPaymentStatus.of(POSPaymentProcessingStatus.UNKNOWN))
				.allowCheckout(false)
				.allowDelete(false)
				.allowRefund(false)
				.releasableAt(Instant.parse("2026-11-10T10:03:00Z"))
				.hashCode(543)
				.build();
		testSerializeDeserialize(payment);

		assertThat(payment.getStatus()).isEqualTo(JsonPOSPaymentStatus.UNKNOWN);
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		assertThat(jsonObjectMapper.writeValueAsString(payment)).contains("\"status\":\"UNKNOWN\"");
		// also with a mapper that writes dates as timestamps, as the REST responses' one does
		assertThat(jsonObjectMapper.copy().enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(payment))
				.contains("\"releasableAt\":\"2026-11-10T10:03:00Z\"");
		assertThat(jsonObjectMapper.readValue("\"UNKNOWN\"", JsonPOSPaymentStatus.class)).isEqualTo(JsonPOSPaymentStatus.UNKNOWN);
	}

	@Test
	void test_JsonPOSCardPaymentCheckRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPOSCardPaymentCheckRequest.builder()
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.order_uuid(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.payment_uuid(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.build());
	}

	@Test
	void test_JsonPOSCardPaymentReleaseRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPOSCardPaymentReleaseRequest.builder()
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.order_uuid(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.payment_uuid(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.confirmNotCharged(true)
				.expectedStatus(JsonPOSPaymentStatus.UNKNOWN)
				.build());
	}

	@Test
	void test_JsonPOSCardPaymentCheckResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPOSCardPaymentCheckResponse.builder()
				.order(newJsonPOSOrder())
				.checkFailed(true)
				.build());
	}

	@Test
	void test_JsonPOSCardPaymentReleaseResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPOSCardPaymentReleaseResponse.builder()
				.order(newJsonPOSOrder())
				.confirmationRequired(true)
				.checkFailed(true)
				.refusedMessage("Release not yet allowed")
				.build());
	}

	@Test
	void test_JsonPOSOrderChangedWebSocketEvent() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSOrderChangedWebSocketEvent.builder()
						.posOrder(newJsonPOSOrder())
						.build()
		);
	}

	@Test
	void test_JsonPOSOpenInvoicesList() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSOpenInvoicesList.builder()
						.list(ImmutableList.of(
								JsonPOSOpenInvoice.builder()
										.invoiceId(InvoiceId.ofRepoId(1))
										.documentNo("INV-001")
										.bpartnerName("Customer Name")
										.dateInvoiced(LocalDate.of(2026, 9, 24))
										.grandTotal(new BigDecimal("100.00"))
										.openAmt(new BigDecimal("75.50"))
										.build(),
								JsonPOSOpenInvoice.builder()
										.invoiceId(InvoiceId.ofRepoId(2))
										.documentNo("INV-002")
										.bpartnerName("Another Customer")
										.dateInvoiced(LocalDate.of(2026, 9, 23))
										.grandTotal(new BigDecimal("250.00"))
										.openAmt(new BigDecimal("250.00"))
										.build()
						))
						.build()
		);
	}

	@Test
	void test_JsonPOSInvoiceSettleRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSInvoiceSettleRequest.builder()
						.posTerminalId(POSTerminalId.ofRepoId(1))
						.invoiceId(InvoiceId.ofRepoId(2))
						.cashTenderedAmount(new BigDecimal("100.00"))
						.build()
		);
	}

	@Test
	void test_JsonPOSInvoiceSettleResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSInvoiceSettleResponse.builder()
						.documentNo("INV-001")
						.amount(new BigDecimal("75.50"))
						.change(new BigDecimal("24.50"))
						.journal(JsonCashJournalSummary.builder()
								.closed(false)
								.currencySymbol("€")
								.currencyPrecision(2)
								.paymentMethods(ImmutableList.of(
										JsonPaymentMethodSummary.builder()
												.paymentMethod(POSPaymentMethod.CASH)
												.amount(new BigDecimal("175.50"))
												.details(ImmutableList.of(
														JsonPaymentDetail.builder()
																.type(JsonCashJournalSummary.JsonPaymentDetailType.OPENING_BALANCE)
																.amount(new BigDecimal("100.00"))
																.build()
												))
												.build()
								))
								.build())
						.build()
		);
	}

	private static JsonPOSInvoiceCardSettlement newJsonPOSInvoiceCardSettlement(final String status, final String allocatedAmount)
	{
		return JsonPOSInvoiceCardSettlement.builder()
				.id(POSInvoiceCardSettlementId.ofRepoId(1))
				.invoiceId(InvoiceId.ofRepoId(2))
				.documentNo("INV-001")
				.amount(new BigDecimal("75.50"))
				.allocatedAmount(new BigDecimal(allocatedAmount))
				.currencyCode("EUR")
				.status(POSInvoiceCardSettlementStatus.ofCode(status))
				.isAbandoned(false)
				.releasableAt(Instant.parse("2026-09-24T10:18:30Z"))
				.checkFailed(false)
				.build();
	}

	@Test
	void test_JsonPOSInvoiceCardSettlement() throws JsonProcessingException
	{
		testSerializeDeserialize(newJsonPOSInvoiceCardSettlement("PE", "0"));
	}

	/**
	 * The till shows "booking pending" (AP), "booking failed, inform the back office" (BE), "booked as customer credit"
	 * (CR, nothing allocated) and "invoice paid, remainder booked as customer credit" (CR, partly allocated) from the
	 * status code and the allocated amount alone.
	 */
	@ParameterizedTest
	@CsvSource({ "AP,0", "BE,0", "CR,0", "CR,50.00" })
	void test_JsonPOSInvoiceCardSettlement_tillStates(final String status, final String allocatedAmount) throws JsonProcessingException
	{
		final JsonPOSInvoiceCardSettlement settlement = newJsonPOSInvoiceCardSettlement(status, allocatedAmount);
		testSerializeDeserialize(settlement);

		final String json = JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(settlement);
		assertThat(json).contains("\"status\":\"" + status + "\"");
		assertThat(json).contains("\"isAbandoned\":false").doesNotContain("\"abandoned\"").contains("\"checkFailed\":false");
		assertThat(json).contains("\"releasableAt\":\"2026-09-24T10:18:30Z\"");
		assertThat(settlement.getAllocatedAmount()).isEqualByComparingTo(allocatedAmount);
	}

	@Test
	void test_JsonPOSInvoiceCardSettlementReleaseRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSInvoiceCardSettlementReleaseRequest.builder()
						.confirmNotCharged(true)
						.expectedStatus(POSInvoiceCardSettlementStatus.UNKNOWN)
						.build()
		);
		testSerializeDeserialize(
				JsonPOSInvoiceCardSettlementReleaseRequest.builder()
						.confirmNotCharged(false)
						.build()
		);
	}

	/**
	 * The status is the enum in Java and its 2-letter code on the wire, for every status, in both directions.
	 */
	@ParameterizedTest
	@EnumSource(POSInvoiceCardSettlementStatus.class)
	void test_JsonPOSInvoiceCardSettlement_statusWireFormIsTheCode(final POSInvoiceCardSettlementStatus status) throws JsonProcessingException
	{
		final JsonPOSInvoiceCardSettlement settlement = newJsonPOSInvoiceCardSettlement(status.getCode(), "0");
		testSerializeDeserialize(settlement);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		assertThat(jsonObjectMapper.writeValueAsString(settlement)).contains("\"status\":\"" + status.getCode() + "\"");
		assertThat(jsonObjectMapper.writeValueAsString(JsonPOSInvoiceCardSettlementReleaseRequest.builder().confirmNotCharged(true).expectedStatus(status).build()))
				.contains("\"expectedStatus\":\"" + status.getCode() + "\"");
		assertThat(jsonObjectMapper.readValue("{\"confirmNotCharged\":true,\"expectedStatus\":\"" + status.getCode() + "\"}", JsonPOSInvoiceCardSettlementReleaseRequest.class)
				.getExpectedStatus())
				.isEqualTo(status);
	}

	@Test
	void test_JsonPOSInvoiceCardSettlementReleaseResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSInvoiceCardSettlementReleaseResponse.builder()
						.settlement(newJsonPOSInvoiceCardSettlement("PE", "0"))
						.confirmationRequired(false)
						.refusedMessage("The release is not yet allowed")
						.build()
		);
	}

	@Test
	void test_JsonPOSOpenInvoice_withBlockingSettlement() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSOpenInvoice.builder()
						.invoiceId(InvoiceId.ofRepoId(2))
						.documentNo("INV-001")
						.bpartnerName("Customer Name")
						.dateInvoiced(LocalDate.of(2026, 9, 24))
						.grandTotal(new BigDecimal("100.00"))
						.openAmt(new BigDecimal("75.50"))
						.blockingCardSettlement(newJsonPOSInvoiceCardSettlement("UN", "0"))
						.build()
		);
	}

}
