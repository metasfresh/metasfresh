/*
 * #%L
 * de-metas-common-shipmentschedule
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

package de.metas.common.shipmentschedule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static de.metas.common.shipmentschedule.JsonRequestShipmentCandidateResult.*;
import static org.assertj.core.api.Assertions.assertThat;

class JsonSerializeDeserializeTests
{

	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		assertThat(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class).isNotNull(); // just to get a compile error if not present
		objectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	void product() throws IOException
	{
		final JsonProduct productOrig = JsonProduct.builder().productNo("productNo").name("name").build();

		assertOK(productOrig, JsonProduct.class);
	}

	private <T> void assertOK(T objectOrig, Class<T> valueType) throws IOException
	{
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
				.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 48))
				.poReference("poReference")
				.orderDocumentNo("orderDocumentNo")
				.product(createJsonProduct())
				.customer(JsonCustomer.builder().street("street").streetNo("streetNo").postal("postal").city("city").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.ONE).uomCode("PCE").build())
				.quantity(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build())
				.attributeSetInstance(JsonAttributeSetInstance.builder().id(JsonMetasfreshId.of(20))
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
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 01))
						.poReference("poReference_1")
						.orderDocumentNo("orderDocumentNo_1")
						.product(JsonProduct.builder().productNo("productNo_1").name("name_1").build())
						.customer(JsonCustomer.builder().street("street_1").streetNo("streetNo_1").postal("postal_1").city("city_1").build())
						.build())
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(20))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 02))
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
		final JsonRequestShipmentCandidateResult statusOrig = JsonRequestShipmentCandidateResult.builder()
				.shipmentScheduleId(JsonMetasfreshId.of(10))
				.outcome(Outcome.OK)
				.build();

		assertOK(statusOrig, JsonRequestShipmentCandidateResult.class);
	}

	@Test
	void jsonRequestShipmentScheduleResultList() throws IOException
	{
		final JsonRequestShipmentCandidateResults resultListOrig = JsonRequestShipmentCandidateResults.builder()
				.transactionKey("transactionKey")
				.item(builder().shipmentScheduleId(JsonMetasfreshId.of(10)).outcome(Outcome.OK).build())
				.item(builder().shipmentScheduleId(JsonMetasfreshId.of(20)).outcome(Outcome.ERROR)
						.error(JsonError.builder()
								.error(JsonErrorItem.builder().message("errorMessage").build())
								.build())
						.build())
				.build();

		assertOK(resultListOrig, JsonRequestShipmentCandidateResults.class);
	}
}