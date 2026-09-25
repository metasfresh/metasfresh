package de.metas.pos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.costing.ChargeId;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonCashJournalSummary.JsonPaymentDetail;
import de.metas.pos.rest_api.json.JsonCashJournalSummary.JsonPaymentMethodSummary;
import de.metas.pos.rest_api.json.JsonCashWithdrawalRequest;
import de.metas.pos.rest_api.json.JsonCashWithdrawalResponse;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrderLine;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
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

import java.math.BigDecimal;
import java.time.Instant;
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

	@Test
	void test_JsonPOSOrderChangedWebSocketEvent() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPOSOrderChangedWebSocketEvent.builder()
						.posOrder(newJsonPOSOrder())
						.build()
		);
	}

}