package de.metas.vertical.pharma.msv3.protocol.order;

import java.io.IOException;


import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class OrderCreateRequestTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(OrderCreateRequest.builder()
				.orderId(Id.of("123"))
				.supportId(SupportIDType.of(222))
				.bpartnerId(BPartnerId.of(1, 2))
				.orderPackage(OrderCreateRequestPackage.builder()
						.id(Id.of("123/1"))
						.supportId(SupportIDType.of(2221))
						.orderType(OrderType.NORMAL)
						.orderIdentification("orderIdentification")
						.packingMaterialId("packingMaterialId")
						.item(OrderCreateRequestPackageItem.builder()
								.id(OrderCreateRequestPackageItemId.random())
								.pzn(PZN.of(1000123))
								.qty(Quantity.of(66))
								.deliverySpecifications(DeliverySpecifications.NORMAL)
								.build())
						.build())
				.build());
	}

	public void testSerializeDeserialize(final OrderCreateRequest request) throws IOException
	{
		final String json = jsonObjectMapper.writeValueAsString(request);
		final OrderCreateRequest request2 = jsonObjectMapper.readValue(json, OrderCreateRequest.class);
		Assertions.assertEquals(request, request2);
	}

}
