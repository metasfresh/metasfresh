/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.shipping.v2.shipment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class JsonCreateShipmentInfoTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = newJsonObjectMapper();
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void test() throws IOException
	{
		final JsonCreateShipmentInfo shipmentInfo = JsonCreateShipmentInfo.builder()
				.shipmentScheduleId(JsonMetasfreshId.of(1))
				.shipmentScheduleIdentifier(ShipmentScheduleIdentifier.builder()
													.externalHeaderId("externalHeaderId")
													.externalLineId("externalLineId")
													.build())
				.deliveryRule("deliveryRule")
				.movementQuantity(BigDecimal.TEN)
				.businessPartnerSearchKey("businessPartnerSearchKey")
				.documentNo("documentNo")
				.shipperInternalName("shipperInternalName")
				.shipToLocation(JsonLocation.builder()
										.city("city")
										.countryCode("countryCode")
										.houseNo("houseNo")
										.street("street")
										.zipCode("zipCode")
										.build())
				.attributes(ImmutableList.of(JsonAttributeInstance.builder()
													 .attributeCode("attributeCode")
													 .valueDate(LocalDate.now())
													 .valueNumber(BigDecimal.TEN)
													 .valueStr("valueStr").build()))
				.build();

		testSerializeDeserialize(shipmentInfo);
	}

	private void testSerializeDeserialize(final Object obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
