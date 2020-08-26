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

package de.metas.common.shipping.shipmentcandidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.receipt.JsonCreateReceiptInfo;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonCreateShipmentResponse;
import de.metas.common.shipment.JsonLocation;
import de.metas.common.shipping.ConfiguredJsonMapper;
import de.metas.common.shipping.JsonProduct;
import de.metas.common.shipping.JsonRequestCandidateResult;
import de.metas.common.shipping.JsonRequestCandidateResults;
import de.metas.common.shipping.Outcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static de.metas.common.shipping.JsonRequestCandidateResult.builder;
import static org.assertj.core.api.Assertions.assertThat;

class JsonSerializeDeserializeTests
{
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
				.trackingNumbers(ImmutableList.of("tr-no1"))
				.movementDate(LocalDateTime.of(2020,7,24,18,13))
				.attributes(ImmutableList.of(mockAttributeInstance()))
				.shipToLocation(location)
				.build();

		final JsonCreateShipmentRequest jsonCreateShipmentRequest = JsonCreateShipmentRequest
				.builder()
				.shipperCode("shippercode")
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
		final JsonProduct productOrig = JsonProduct.builder().productNo("productNo").name("name").build();

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
		final JsonResponseShipmentCandidate scheduleOrig = JsonResponseShipmentCandidate
				.builder()
				.id(JsonMetasfreshId.of(10))
				.orgCode("orgCode")
				.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 48))
				.poReference("poReference")
				.orderDocumentNo("orderDocumentNo")
				.product(createJsonProduct())
				.customer(JsonCustomer.builder().street("street").streetNo("streetNo").postal("postal").city("city").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.ONE).uomCode("PCE").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build())
				.attributeSetInstance(JsonAttributeSetInstance.builder()
						.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_1").attributeCode("attributeCode_1").valueNumber(BigDecimal.TEN).build())
						.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_2").attributeCode("attributeCode_2").valueStr("valueStr").build())
						.build())
				.build();

		assertOK(scheduleOrig, JsonResponseShipmentCandidate.class);
	}

	private JsonProduct createJsonProduct()
	{
		return JsonProduct.builder().productNo("productNo").name("name").build();
	}

	@Test
	void shipmentScheduleList() throws IOException
	{
		// given
		final JsonResponseShipmentCandidates scheduleListOrig = JsonResponseShipmentCandidates.builder()
				.transactionKey("transactionKey")
				.hasMoreItems(false)
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(10))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 1))
						.poReference("poReference_1")
						.orderDocumentNo("orderDocumentNo_1")
						.product(JsonProduct.builder().productNo("productNo_1").name("name_1").documentNote("documentNote_1").build())
						.customer(JsonCustomer.builder().street("street_1").streetNo("streetNo_1").postal("postal_1").city("city_1").build())
						.build())
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(20))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 2))
						.poReference("poReference_2")
						.orderDocumentNo("orderDocumentNo_2")
						.product(JsonProduct.builder().productNo("productNo_2").name("name_2").build())
						.customer(JsonCustomer.builder().street("street_2").streetNo("streetNo_2").postal("postal_2").city("city_2").build())
						.build())
				.build();

		assertOK(scheduleListOrig, JsonResponseShipmentCandidates.class);
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
				.item(builder().scheduleId(JsonMetasfreshId.of(10)).outcome(Outcome.OK).build())
				.item(builder().scheduleId(JsonMetasfreshId.of(20)).outcome(Outcome.ERROR)
						.error(JsonError.ofSingleItem(JsonErrorItem.builder().message("errorMessage").stackTrace("stackTrace").build()))
						.build())
				.build();

		assertOK(resultListOrig, JsonRequestCandidateResults.class);
	}

	@Test
	void deserializeBigJson() throws IOException
	{
		final String jsonString = "{\n"
				+ "    \"transactionKey\": \"d7c3f93d-6eab-4360-8bf4-f45d1a66cd4d\",\n"
				+ "    \"items\": [\n"
				+ "        {\n"
				+ "            \"id\": 1000000,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0001\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000001,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0002\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000002,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0003\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000003,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0004\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000004,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0005\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000005,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0006\",\n"
				+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000006,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0007\",\n"
				+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000007,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0008\",\n"
				+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000008,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0009\",\n"
				+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000009,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0010\",\n"
				+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 12,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"id\": 1000011,\n"
				+ "            \"orgCode\": \"001\",\n"
				+ "            \"orderDocumentNo\": \"0012\",\n"
				+ "            \"dateOrdered\": \"2020-07-17T00:00:00\",\n"
				+ "            \"product\": {\n"
				+ "                \"productNo\": \"P002737\",\n"
				+ "                \"name\": \"Convenience Salat 250g\",\n"
				+ "                \"description\": \"\",\n"
				+ "                \"weight\": 0.250000000000\n"
				+ "            },\n"
				+ "            \"customer\": {\n"
				+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
				+ "                \"street\": \"teststraße\",\n"
				+ "                \"streetNo\": \"996\",\n"
				+ "                \"postal\": \"45678\",\n"
				+ "                \"city\": \"teststadt 3\",\n"
				+ "                \"countryCode\": \"DE\",\n"
				+ "                \"language\": \"de_DE\"\n"
				+ "            },\n"
				+ "            \"quantities\": [\n"
				+ "                {\n"
				+ "                    \"qty\": 21,\n"
				+ "                    \"uomCode\": \"Stk\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        }\n"
				+ "    ],\n"
				+ "    \"hasMoreItems\": false\n"
				+ "}";

		// when
		final JsonResponseShipmentCandidates result = ConfiguredJsonMapper.get().readValue(jsonString, JsonResponseShipmentCandidates.class);

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
				.valueDate(LocalDate.of(2020,7,24))
				.attributeCode("atrCode")
				.build(); // not a real case but it's fine for testing
	}
}
