package de.metas.vertical.pharma.msv3.protocol.order;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart.Type;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrderCreateResponseTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(OrderCreateResponse.ok(
				OrderResponse.builder()
						.orderId(Id.of("123"))
						.supportId(SupportIDType.of(222))
						.bpartnerId(BPartnerId.of(1, 2))
						.nightOperation(true)
						.orderPackage(OrderResponsePackage.builder()
								.id(Id.of("123/1"))
								.supportId(SupportIDType.of(2221))
								.orderType(OrderType.NORMAL)
								.orderIdentification("orderIdentification")
								.packingMaterialId("packingMaterialId")
								.item(OrderResponsePackageItem.builder()
										.requestId(OrderCreateRequestPackageItemId.random())
										.pzn(PZN.of(1000123))
										.qty(Quantity.of(66))
										.deliverySpecifications(DeliverySpecifications.NORMAL)
										.olCandId(1234567)
										.purchaseCandidateId(MSV3PurchaseCandidateId.ofRepoId(1234))
										.part(OrderResponsePackageItemPart.builder()
												.qty(Quantity.of(66))
												.type(Type.KEINE_LIEFERUNG_ABER_DISPO_MOEGLICH)
												.defectReason(OrderDefectReason.MANUFACTURER_NOT_AVAILABLE)
												.deliveryDate(ZonedDateTime.now())
												.tour("tour")
												.tourId("tourId")
												.tourDeviation(true)
												.build())
										.build())
								.build())
						.build()));
	}

	public void testSerializeDeserialize(final OrderCreateResponse request) throws IOException
	{
		System.out.println("Object: " + request);

		final String json = jsonObjectMapper.writeValueAsString(request);
		System.out.println("JSON: " + json);

		final OrderCreateResponse request2 = jsonObjectMapper.readValue(json, OrderCreateResponse.class);
		System.out.println("Object deserialized: " + request2);

		Assert.assertEquals(request, request2);
	}

}
