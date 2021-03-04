/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void givenJsonOrders_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonOrders());
	}

	@Test
	public void givenJsonDeliveries_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonDeliveries());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private JsonOrders getMockJsonOrders()
	{
		return JsonOrders.builder()
				.data(ImmutableList.of(
						JsonOrder.builder()
								.billingAddressId("billingAddressId")
								.billingAddressVersionId("billingAddressVersionId")
								.id("orderId")
								.versionId("orderVersionId")
								.orderCustomer(JsonOrderCustomer.builder()
													   .id("orderCustomerId")
													   .orderId("orderId")
													   .versionId("orderCustomerVersionId")
													   .customerId("customerId")
													   .firstName("firstName")
													   .lastName("lastName")
													   .email("email")
													   .company("company")
													   .customerNumber("customerNumber")
													   .build())
								.build()
				))
				.build();
	}

	private JsonOrderDeliveries getMockJsonDeliveries()
	{
		return JsonOrderDeliveries.builder()
				.data(ImmutableList.of(
						JsonOrderDelivery.builder()
								.shippingOrderAddress(JsonOrderAddress.builder()
															  .additionalAddressLine1("additionalAddressLine1")
															  .additionalAddressLine2("additionalAddressLine2")
															  .street("street")
															  .city("city")
															  .countryId("countryId")
															  .zipcode("zipcode")
															  .phoneNumber("phoneNumber")
															  .id("addressId")
															  .versionId("addressVersionId")
															  .build())
								.build()
				))
				.build();
	}
}

