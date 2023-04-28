/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.shipping.v1.shipmentcandidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonAttributeInstance;
import de.metas.common.rest_api.v1.JsonAttributeSetInstance;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.rest_api.v1.JsonQuantity;
import de.metas.common.shipping.v1.ConfiguredJsonMapper;
import de.metas.common.shipping.v1.JsonProduct;
import de.metas.common.shipping.v1.JsonRequestCandidateResult;
import de.metas.common.shipping.v1.JsonRequestCandidateResults;
import de.metas.common.shipping.v1.Outcome;
import de.metas.common.shipping.v1.receipt.JsonCreateReceiptInfo;
import de.metas.common.shipping.v1.receipt.JsonCreateReceiptsRequest;
import de.metas.common.shipping.v1.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipping.v1.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipping.v1.shipment.JsonCreateShipmentResponse;
import de.metas.common.shipping.v1.shipment.JsonLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

class JsonSerializeDeserializeTests
{
	private static final String SHIPMENT_SCHEDULE_JSON_FILE = "/de/metas/common/shipping/v1/shipmentcandidate/BigShipmentScheduleJSON.json";

	@BeforeEach
	public void beforeEach()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		assertThat(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class).isNotNull(); // just to get a compile error if not present
	}

	@Test
	void jsonCreateShipmentRequest() throws IOException
	{
		final JsonLocation location = JsonLocation.builder()
				.city("city")
				.countryCode("DE")
				.houseNo("11ER")
				.street("P street")
				.zipCode("151212")
				.build();

		final JsonCreateShipmentInfo jsonCreateShipmentInfo = JsonCreateShipmentInfo.builder()
				.shipmentScheduleId(JsonMetasfreshId.of(1))
				.businessPartnerSearchKey("bp_key")
				.deliveryRule("d_rule")
				.documentNo("dNo")
				.movementQuantity(BigDecimal.ONE)
				.productSearchKey("p_key")
				.movementDate(LocalDateTime.of(2020, 7, 24, 18, 13))
				.attributes(ImmutableList.of(mockAttributeInstance()))
				.shipToLocation(location)
				.build();

		final JsonCreateShipmentRequest jsonCreateShipmentRequest = JsonCreateShipmentRequest
				.builder()
				.createShipmentInfoList(ImmutableList.of(jsonCreateShipmentInfo))
				.build();

		assertOK(jsonCreateShipmentRequest, JsonCreateShipmentRequest.class);
	}

	@Test
	void jsonCreateShipmentResponse() throws IOException
	{
		final JsonCreateShipmentResponse createShipmentResponse = JsonCreateShipmentResponse.builder()
				.createdShipmentIdList(ImmutableList.of(JsonMetasfreshId.of(1)))
				.build();

		assertOK(createShipmentResponse, JsonCreateShipmentResponse.class);
	}

	@Test
	void product() throws IOException
	{
		final JsonProduct productOrig = JsonProduct.builder().productNo("productNo").name("name").stocked(true).build();

		assertOK(productOrig, JsonProduct.class);
	}

	private <T> void assertOK(final T objectOrig, final Class<T> valueType) throws IOException
	{
		final ObjectMapper objectMapper = ConfiguredJsonMapper.get();

		final String jsonString = objectMapper.writeValueAsString(objectOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final T object = objectMapper.readValue(jsonString, valueType);

		// then
		assertThat(object).isEqualTo(objectOrig);
	}

	@Test
	void shipmentSchedule() throws IOException
	{
		// given
		final de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidate scheduleOrig = de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidate
				.builder()
				.id(JsonMetasfreshId.of(10))
				.orgCode("orgCode")
				.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 48))
				.poReference("poReference")
				.orderDocumentNo("orderDocumentNo")
				.deliveryInfo("deliveryInfo")
				.product(createJsonProduct())
				.shipBPartner(de.metas.common.shipping.v1.shipmentcandidate.JsonCustomer.builder()
						.companyName("companyName")
						.company(true)
						.street("street").streetNo("streetNo")
						.addressSuffix1("addressSuffix1")
						.addressSuffix2("addressSuffix2")
						.addressSuffix3("addressSuffix3")
						.postal("postal").city("city").shipmentAllocationBestBeforePolicy("E").build())
				.billBPartner(de.metas.common.shipping.v1.shipmentcandidate.JsonCustomer.builder()
						.companyName("companyName")
						.company(false)
						.street("street").streetNo("streetNo")
						.addressSuffix1("addressSuffix1")
						.addressSuffix2("addressSuffix2")
						.addressSuffix3("addressSuffix3")
						.postal("postal").city("city").shipmentAllocationBestBeforePolicy("E")
						.contactEmail("contactEmail").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.ONE).uomCode("PCE").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build())
				.orderedQty(ImmutableList.of(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build()))
				.attributeSetInstance(JsonAttributeSetInstance.builder()
						.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_1").attributeCode("attributeCode_1").valueNumber(BigDecimal.TEN).build())
						.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_2").attributeCode("attributeCode_2").valueStr("valueStr").build())
						.build())
				.shipperInternalSearchKey("shipperInternalSearchKey")
				.deliveredQtyNetPrice(BigDecimal.ZERO)
				.qtyToDeliverNetPrice(BigDecimal.TEN)
				.orderedQtyNetPrice(BigDecimal.TEN)
				.build();

		assertOK(scheduleOrig, de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidate.class);
	}

	private JsonProduct createJsonProduct()
	{
		return JsonProduct.builder().productNo("productNo").name("name").stocked(true).build();
	}

	@Test
	void shipmentScheduleList() throws IOException
	{
		// given
		final de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidates scheduleListOrig = de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidates.builder()
				.transactionKey("transactionKey")
				.exportSequenceNumber(4)
				.hasMoreItems(false)
				.item(de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(10))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 1))
						.poReference("poReference_1")
						.orderDocumentNo("orderDocumentNo_1")
						.deliveryInfo("deliveryInfo")
						.product(JsonProduct.builder().productNo("productNo_1").name("name_1").stocked(true).documentNote("documentNote_1").build())
						.orderedQty(ImmutableList.of(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build()))
						.shipBPartner(de.metas.common.shipping.v1.shipmentcandidate.JsonCustomer.builder().street("street_1").streetNo("streetNo_1").postal("postal_1").city("city_1").shipmentAllocationBestBeforePolicy("E").build())
						.orderedQtyNetPrice(BigDecimal.ONE)
						.qtyToDeliverNetPrice(BigDecimal.ONE)
						.deliveredQtyNetPrice(BigDecimal.TEN)
						.build())
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(20))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 2))
						.poReference("poReference_2")
						.orderDocumentNo("orderDocumentNo_2")
						.deliveryInfo("deliveryInfo")
						.product(JsonProduct.builder().productNo("productNo_2").name("name_2").stocked(true).build())
						.orderedQty(ImmutableList.of(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build()))
						.shipBPartner(JsonCustomer.builder().street("street_2").streetNo("streetNo_2").postal("postal_2").city("city_2").shipmentAllocationBestBeforePolicy("E").build())
						.orderedQtyNetPrice(BigDecimal.ONE)
						.qtyToDeliverNetPrice(BigDecimal.ONE)
						.deliveredQtyNetPrice(BigDecimal.TEN)
						.build())
				.build();

		assertOK(scheduleListOrig, de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidates.class);
	}

	@Test
	void jsonRequestShipmentScheduleResult() throws IOException
	{
		final JsonRequestCandidateResult statusOrig = JsonRequestCandidateResult.builder()
				.scheduleId(JsonMetasfreshId.of(10))
				.outcome(Outcome.OK)
				.build();

		assertOK(statusOrig, JsonRequestCandidateResult.class);
	}

	@Test
	void jsonRequestShipmentScheduleResultList() throws IOException
	{
		final JsonRequestCandidateResults resultListOrig = JsonRequestCandidateResults.builder()
				.transactionKey("transactionKey")
				.item(JsonRequestCandidateResult.builder().scheduleId(JsonMetasfreshId.of(10)).outcome(Outcome.OK).build())
				.item(JsonRequestCandidateResult.builder().scheduleId(JsonMetasfreshId.of(20)).outcome(Outcome.ERROR)
						.error(JsonError.ofSingleItem(JsonErrorItem.builder().message("errorMessage").stackTrace("stackTrace").build()))
						.build())
				.build();

		assertOK(resultListOrig, JsonRequestCandidateResults.class);
	}

	@Test
	void deserializeBigJson() throws IOException
	{
		final InputStream bigJson = getClass().getResourceAsStream(SHIPMENT_SCHEDULE_JSON_FILE);
		assertThat(bigJson).isNotNull();

		// when
		final de.metas.common.shipping.v1.shipmentcandidate.JsonResponseShipmentCandidates result = ConfiguredJsonMapper.get()
				.readValue(bigJson, JsonResponseShipmentCandidates.class);

		// then
		assertThat(result.getTransactionKey()).isEqualTo("d7c3f93d-6eab-4360-8bf4-f45d1a66cd4d");
	}

	@Test
	void jsonCreateReceiptRequest() throws IOException
	{

		final JsonCreateReceiptInfo jsonCreateReceiptInfo = JsonCreateReceiptInfo.builder()
				.receiptScheduleId(JsonMetasfreshId.of(1))
				.productSearchKey("productSearchKey")
				.externalId("externalId")
				.movementQuantity(BigDecimal.ONE)
				.movementDate(LocalDate.now())
				.attributes(ImmutableList.of(mockAttributeInstance()))
				.dateReceived(LocalDateTime.now())
				.build();

		final JsonCreateReceiptsRequest jsonCreateReceiptsRequest = JsonCreateReceiptsRequest
				.builder()
				.jsonCreateReceiptInfoList(ImmutableList.of(jsonCreateReceiptInfo))
				.build();

		assertOK(jsonCreateReceiptsRequest, JsonCreateReceiptsRequest.class);
	}

	public JsonAttributeInstance mockAttributeInstance()
	{
		return JsonAttributeInstance.builder()
				.attributeName("name")
				.valueStr("valueStr")
				.valueNumber(BigDecimal.ONE)
				.valueDate(LocalDate.of(2020, 7, 24))
				.attributeCode("atrCode")
				.build(); // not a real case but it's fine for testing
	}
}
